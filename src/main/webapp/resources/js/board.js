
function boardDelete(id) {
	let url = "/board/delete";

	if (confirm("삭제하시겠습니까?")) {
		axios.post(url, id,
			{ headers: { 'Content-Type': 'application/json' } }
		).then(response => {

			console.log(response.data);

			if (response.data === "성공") {
				window.location.href = "/board/";
			} else {
				window.location.href = "/board/" + id;
			}

			alert("삭제되었습니다.");

		}).catch(err => {
			if (err.response && err.response.status === 502) {
				alert("[삭제 오류]" + err.response.data);
			} else {
				alert("[시스템 오류]" + err.message);
			}
		});
	} else {
		alert("취소되었습니다.");
	}
}

function boardLikes(board_id, useremail) {
	let url = "/board/like";

	axios.post(url, {
		useremail: useremail,
		board_id: parseInt(board_id)
	}, { headers: { 'Content-Type': 'application/json' } }

	).then(response => {

		let res = response.data;

		if (res.likeStatus) {
			document.getElementById('likeStatus').innerText = "좋아요 취소";
		} else {
			document.getElementById('likeStatus').innerText = "좋아요";
		}

		document.getElementById('likeCount').innerText = res.board_likes;

	}).catch(err => {
		if (err.response && err.response.status === 502) {
			alert("[삭제 오류]" + err.response.data);
		} else {
			alert("[시스템 오류]" + err.message);
		}
	});

}

function loadItems(page, board_id) {
	let url = `/board/commentList?page=${page}&board_id=${board_id}`;

	axios.get(url)
		.then(function(response) {

			let data = response.data;

			let resultHtml = `
		          <table border="1px">
		              <tr>
		                <th>번호</th>
						<th>작성자</th>
						<th>댓글내용</th>
						<th>등록일</th>
						<th>수정</th>
						<th>삭제</th>
		              </tr>
        `;

			let useremail = document.getElementById('useremail').value;

			if (data.entityList) {
				data.entityList.forEach(function(c) {
					resultHtml += `
	              <tr>
						<td>${c.comment_id}</td>
						<td>${c.useremail}</td>
						<td><span class="comment-content">${c.comment_content}</span>
							<input type="text" class="edit-comment" style="display: none;"
							value="${c.comment_content}"></td>
						<td>${c.comment_regdate}</td>
					`;


					if (useremail == c.useremail) {
						resultHtml += `
						<td>
							<button data-idx="${c.comment_id}" class="edit-btn">수정</button>
						</td>
						<td>
							<button type="button" data-idx="${c.comment_id}" onclick="commentDelete(${c.comment_id})">삭제</button>
						</td>
						`;
					}
				});

			} else {
				resultHtml += `
        	<tr>
                <td colspan="6">작성된 댓글이 없습니다.</td>
			`;
			}

			resultHtml += `
				</tr>
	            </table>
          `;

			document.getElementById('commentList').innerHTML = resultHtml;

			// button 이벤트 적용 여부
			if (data.start != data.page) {
				document.getElementById("firstB").classList.remove("selectArrow");
				document.getElementById("ltB").classList.remove("selectArrow");
			} else {
				document.getElementById("firstB").classList.add("selectArrow");
				document.getElementById("ltB").classList.add("selectArrow");
			}

			let pageValues = document.getElementsByClassName("pageValue");

			for (let i = 0; i < pageValues.length; i++) {

				if (pageValues[i].textContent == data.page) {
					pageValues[i].classList.add("selectPage");
				} else {
					pageValues[i].classList.remove("selectPage");
				}
			}

			if (data.end != data.page) {
				document.getElementById("lastB").classList.remove("selectArrow");
				document.getElementById("gtB").classList.remove("selectArrow");
			} else {
				document.getElementById("lastB").classList.add("selectArrow");
				document.getElementById("gtB").classList.add("selectArrow");
			}

			document.getElementById('templtB').value = data.page - 1;
			document.getElementById('tempgtB').value = data.page + 1;


		})
		.catch(function(error) {
			console.error('Error loading page: ' + error);
		});
}

function getInputValue(elementId) {
	return document.getElementById(elementId).value;
}

function commentDelete(comment_id) {

	let url = `/board/deleteComment?comment_id=${comment_id}`;


	if (confirm("삭제하시겠습니까?")) {
		axios.delete(url
		).then(response => {
			alert("삭제되었습니다.");
			location.reload();

		}).catch(err => {
			if (err.response && err.response.status === 502) {
				alert("[삭제 오류]" + err.response.data);
			} else {
				alert("[시스템 오류]" + err.message);
			}
		});
	} else {
		alert("취소되었습니다.");
	}

}

function toggleLike(board_id, useremail) {
	console.log(board_id, useremail);

	let url = "/board/likesInsert/" + board_id + "/" + useremail;

	axios.post(url)
		.then(response => {
			let likeCountElement = document.getElementById('likeCount');
			let currentLikeCount = parseInt(likeCountElement.textContent);

			if (response.status === 200) {
				likeCountElement.textContent = currentLikeCount + 1; // 좋아요 추가
			} else if (response.status === 204) {
				likeCountElement.textContent = currentLikeCount - 1; // 좋아요 삭제
			}
		})
		.catch(error => {
			console.error('Error toggling like:', error);
		});
}


// 댓글 수정
document.addEventListener('DOMContentLoaded', function () {
    let editButtons = document.querySelectorAll('.edit-btn');

    editButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            let row = button.closest('tr');
            let commentContentSpan = row.querySelector('.comment-content');
            let editCommentInput = row.querySelector('.edit-comment');
            
            if (button.classList.contains('update-btn')) {
                let updatedComment = editCommentInput.value;
                commentContentSpan.textContent = updatedComment;
                commentContentSpan.style.display = 'inline';
                editCommentInput.style.display = 'none';
                button.textContent = '수정';
                button.classList.remove('update-btn');

                let commentId = button.getAttribute('data-idx');
                updateCommentOnServer(commentId, updatedComment);
            } else {
                commentContentSpan.style.display = 'none';
                editCommentInput.style.display = 'block';
                button.textContent = '수정 완료';
                button.classList.add('update-btn');
            }
            

        });
    });

    function updateCommentOnServer(commentId, updatedComment) {
	    let url = `/board/updateComments?comment_id=${commentId}&comment_content=${updatedComment}`;

	    axios.post(url)
	        .then(response => {
	            console.log(response.data);
	        })
	        .catch(error => {
	            console.error('Error updating comment:', error);
	        });
		}
});