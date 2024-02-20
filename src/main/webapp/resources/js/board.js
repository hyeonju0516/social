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

function boardLikes(id) {
   let url = "/board/likes";

   if (confirm("삭제하시겠습니까?")) {
      axios.post(url, id,
      { headers: { 'Content-Type': 'application/json' }}
      ).then(response => {
      	
      	if (!response.data) {
            document.getElementById("likeStatus").innerHTML = "좋아요";
         } else {
         	document.getElementById("likeStatus").innerHTML = "좋아요 취소";
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
