// 리뷰 수정 이벤트 수정중
// function showEditForm(reviewId) {
//     document.getElementById('editForm' + reviewId).style.display = 'block';
// }
//
// // 수정 취소 이벤트
// function hideEditForm(reviewId) {
//     document.getElementById('editForm' + reviewId).style.display = 'none';
// }
//
// // 별점 설정 이벤트
// function setRating(star, reviewId) {
//     var stars = document.querySelectorAll('#editForm' + reviewId + ' .fa-star');
//     stars.forEach(function(starElement, index) {
//         if (index < star) {
//             starElement.classList.add('fas');
//             starElement.classList.remove('far');
//         } else {
//             starElement.classList.add('far');
//             starElement.classList.remove('fas');
//         }
//     });
//
//     // 별점 입력 필드 업데이트
//     document.querySelector('#editForm' + reviewId + ' .ratingInput').value = star;
// }
//
// // 폼 서브밋 이벤트 리스너
// document.querySelector("#UpdateReviewForm").addEventListener("submit", function(event) {
//     event.preventDefault();
//
//     // accomId 가져오기
//     var accomId = document.getElementById("accId").value;
//
//     // reviewId 가져오기
//     var reviewId = document.getElementById("reviewId").value;
//
//     // 삭제 확인 메시지 표시
//     if (confirm('수정하시겠습니까?')) {
//         // 삭제를 확인한 경우, 폼 submit
//         this.submit();
//     } else {
//         // 삭제를 취소한 경우, 아무런 작업도 수행하지 않음
//         return false;
//     }
// });