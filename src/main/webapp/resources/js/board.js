function keywordClear() {
    let searchTypeSelect = document.getElementById("searchType");
    let keywordInput = document.getElementById("keyword");

	if (searchTypeSelect.value === "all") {
        keywordInput.value = "";
        keywordInput.readOnly = true;
        keywordInput.style.backgroundColor = "#eee";
    } else {
        // 다른 값이 선택되면 readonly 속성을 제거
        keywordInput.readOnly = false;
        keywordInput.style.backgroundColor = "#fff";
    }
}
      
function boardDelete(id) {
	let url = "/board/delete";

	if (confirm("삭제하시겠습니까?")) {
		axios.post(url, id,
			{ headers: { 'Content-Type': 'application/json' } }
		).then(response => {

			console.log(response.data);

			if (response.data === "성공") {
				window.location.href = "/board/";
				alert("삭제되었습니다.");
			} else {
				window.location.href = "/board/" + id;
			}


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
			console.log(data);

			let resultHtml = `
		          <ul>
        `;

			let useremail = document.getElementById('useremail').value;
			let username = document.getElementById('username').value;

			if (data.entityList) {
				data.entityList.forEach(function(c) {
					resultHtml += `
	              			<li style="margin-left:${c.comment_indent*5}%;">
	                        	<div>
	                                <p>${c.useremail}</p>
	                                <span>${c.comment_regdate}</span>
                                </div>
	                           	<div>
	                                <p class="comment-content">${c.comment_content}</p>
	                                <textarea class="edit-comment" style="display: none;">${c.comment_content}</textarea>
                                </div>
                                <div>
                                	<a id="toggle-reply" onclick="toggleReply(${c.comment_id})">답글달기</a>

					`;


					if (useremail == c.useremail) {
						resultHtml += `
								<div>
		                             <button data-idx="${c.comment_id}" class="edit-btn" onclick="commentUpdate(this)">수정</button>
		                             <button class="del-btn" type="button" data-idx="${c.comment_id}" onclick="commentDelete(${c.comment_id})">삭제</button>
		                        </div>
	                        </div>
	                        <div class="commentInsert" id="reply-${c.comment_id}" style="display: none;">
                                	<form action="ReplyInsert" method="post">
										<p>${useremail} &#40;${username}&#41;</p>
										<input type="hidden" id="board_id" name="board_id" value="${c.board_id}" />
										<input type="hidden" id="useremail" name="useremail" value="${useremail}" />
										<input type="hidden" id="comment_root" name="comment_root" value="${c.comment_id}" />
										<input type="hidden" id="comment_steps" name="comment_steps" value="${c.comment_steps}" />
										<input type="hidden" id="comment_indent" name="comment_indent" value="${c.comment_indent}" />
										<textarea id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required ></textarea>
										<button>등록</button>
										<button type="button" onclick="cancleComment(${c.comment_id})">취소</button>
									</form>
								</div>
	                    </li>
						`;
					}else{
						resultHtml += `
							</div>
							<div id="reply-${c.comment_id}" style="display: none;">
                                	<form action="ReplyInsert" method="post">
										<span>${useremail}</span>
										<input type="hidden" id="board_id" name="board_id" value="${c.board_id}" />
										<input type="hidden" id="useremail" name="useremail" value="${useremail}" />
										<input type="hidden" id="comment_root" name="comment_root" value="${c.comment_id}" />
										<input type="hidden" id="comment_steps" name="comment_steps" value="${c.comment_steps}" />
										<input type="hidden" id="comment_indent" name="comment_indent" value="${c.comment_indent}" />
										<input type="text" id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required />
										<button>등록</button>
									</form>
								</div>
							</li>
						</ul>
						`;
					}
				});

			} else {
				resultHtml += `
        			<div>작성된 댓글이 없습니다.<div>
				`;
			}


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

	let url = `/board/deleteComment`;


	if (confirm("삭제하시겠습니까?")) {
		axios.post(url,comment_id,{ headers: { 'Content-Type': 'application/json' } }
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
function commentUpdate(button) {

    let row = button.closest('li');
    let commentContentSpan = row.querySelector('.comment-content');
    let editCommentInput = row.querySelector('.edit-comment');

    if (button.classList.contains('update-btn')) {
        let updatedComment = editCommentInput.value;
        commentContentSpan.textContent = updatedComment;
        commentContentSpan.style.display = 'block';
        editCommentInput.style.display = 'none';
        row.querySelector('.del-btn').style.display = "inline-block";
        button.textContent = '수정';
        button.classList.remove('update-btn');

        let commentId = button.getAttribute('data-idx');
        updateCommentOnServer(commentId, updatedComment);
    } else {
        commentContentSpan.style.display = 'none';
        editCommentInput.style.display = 'block';
        button.textContent = '수정 완료';
        button.classList.add('update-btn');

        row.querySelector('.del-btn').style.display = "none";
    }
}

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


// 답글 기능
function toggleReply(comment_id) {
    const reply = document.getElementById(`reply-${comment_id}`);
    reply.style.display = (reply.style.display === 'none') ? 'block' : 'none';
}

function cancleComment(comment_id){
	const reply = document.getElementById(`reply-${comment_id}`);
	
	reply.style.display = 'none';
	reply.querySelector('#comment_content').value = "";
}