// 리뷰 추가 이벤트
const addReviewButton = document.querySelector("#addReview");
if (addReviewButton) {
    addReviewButton.addEventListener("click", function (event) {
        event.preventDefault();

        const contents = document.querySelector('textarea[name="contents"]').value;
        const rate = document.querySelector('input[name="rate"]').value;
        const roomInfoId = document.querySelector('input[name="roomInfoId"]').value;
        const createdAt = document.querySelector('input[name="createdAt"]').value;

        // 서버로부터 받은 사용자 정보 사용하여 리뷰 작성자의 이름 설정
        const username = response.username;

        const reviewDto = {
            contents: contents,
            rate: rate,
            roomInfoId: roomInfoId,
            username: username,
            createdAt: createdAt
        };

        $.ajax({
            type: 'POST',
            url: '/accom/detail/' + roomInfoId + '/addReview',
            contentType: 'application/json',
            data: JSON.stringify(reviewDto),
            success: function (response) {
                alert(response.message);
                if (response.message === "리뷰가 성공적으로 추가되었습니다.") {
                    const reviewContainer = document.querySelector('.reviewContainer');
                    const newReview = document.createElement('div');
                    newReview.classList.add('reviewContainer');

                    const userImage = document.createElement('img');
                    userImage.setAttribute('alt', '');
                    userImage.setAttribute('src', '/img/StayConnect-main.jpg');
                    newReview.appendChild(userImage);

                    const usernameSpan = document.createElement('span');
                    usernameSpan.classList.add('user');
                    usernameSpan.textContent = response.username; // 서버로부터 받은 사용자 정보 사용하여 리뷰 작성자의 이름 설정
                    newReview.appendChild(usernameSpan);

                    const ratingStarDiv = document.createElement('div');
                    ratingStarDiv.classList.add('ratingStar');
                    for (let i = 1; i <= 5; i++) {
                        const starIcon = document.createElement('i');
                        starIcon.classList.add('fa');
                        if (i <= response.rate) {
                            starIcon.classList.add('fas', 'fa-star');
                        } else {
                            starIcon.classList.add('far', 'fa-star');
                        }
                        ratingStarDiv.appendChild(starIcon);
                    }
                    newReview.appendChild(ratingStarDiv);

                    const reviewContentDiv = document.createElement('div');
                    reviewContentDiv.classList.add('reviewContent');
                    const reviewContentP = document.createElement('p');
                    reviewContentP.textContent = response.contents;
                    reviewContentDiv.appendChild(reviewContentP);
                    newReview.appendChild(reviewContentDiv);

                    const reviewActionsDiv = document.createElement('div');
                    reviewActionsDiv.classList.add('reviewActions');
                    if (response.currentUser === response.username) {
                        const updateLink = document.createElement('a');
                        updateLink.setAttribute('href', '/reviews/' + response.id + '/update');
                        updateLink.textContent = '수정';
                        reviewActionsDiv.appendChild(updateLink);

                        const deleteLink = document.createElement('a');
                        deleteLink.setAttribute('href', '/reviews/' + response.id + '/delete');
                        deleteLink.textContent = '삭제';
                        reviewActionsDiv.appendChild(deleteLink);
                    }
                    newReview.appendChild(reviewActionsDiv);

                    reviewContainer.appendChild(newReview);
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status === 401) {
                    alert("로그인이 필요합니다.");
                } else {
                    console.error('Error:', error);
                }
            }
        });
    });
}

// 리뷰 삭제버튼 이벤트
document.querySelector("#deleteReviewForm").addEventListener("submit", function (event) {
    event.preventDefault();

    // accomsId 가져오기
    var accomsId = document.getElementById("accId").value;

    // reviewId 가져오기
    var reviewId = document.getElementById("reviewId").value;

    // 삭제 확인 메시지 표시
    if (confirm('삭제하시겠습니까?')) {
        // 삭제를 확인한 경우, 폼 submit
        this.submit();
    } else {
        // 삭제를 취소한 경우, 아무런 작업도 수행하지 않음
        return false;
    }
});
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