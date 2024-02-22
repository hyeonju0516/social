
/*window.onload = function() {
	axios.get('https://jsonplaceholder.typicode.com/posts/1')
		.then(function(response) {
						
		})
		.catch(function(error) {
			console.error('데이터를 가져오는 중 오류 발생:', error);
		});
};*/


function boardDelete(id) {
   let url = "/board/delete";

   if (confirm("삭제하시겠습니까?")) {
      axios.post(url, id,
      { headers: { 'Content-Type': 'application/json' }}
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

      axios.post(url, { useremail : useremail,
      					board_id : parseInt(board_id)
      },{ headers: { 'Content-Type': 'application/json' }}
  
      ).then(response => {
      
      	let res = response.data;
      	
      	if(res.likeStatus){
			document.getElementById('likeStatus').innerText = "좋아요 취소";
      	}else{
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

