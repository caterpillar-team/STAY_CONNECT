// 별점
document.addEventListener("DOMContentLoaded", function () {
    const stars = document.querySelectorAll(".reviewInputRatingStar");
    const ratingInput = document.getElementById("ratingInput");

    stars.forEach(star => {
        star.addEventListener("click", function () {
            const starIndex = parseInt(this.getAttribute('data-star')) - 1; // 별점 값 계산
            ratingInput.value = starIndex + 1;

            stars.forEach((s, index) => {
                if (index <= starIndex) {
                    s.classList.remove("far");
                    s.classList.add("fas");
                } else {
                    s.classList.remove("fas");
                    s.classList.add("far");
                }
            });
        });
    });
});

const messageEl = document.querySelector('.message');
console.log(messageEl);
if (messageEl !== null && messageEl.innerHTML !== "") {
    alert(messageEl.innerHTML);
}

// 모든 수정 버튼 선택
const editButtons = document.querySelectorAll('.editButtoneditButton');

editButtons.forEach(edit => {

    edit.addEventListener('click', function () {

        const accId = edit.getAttribute('data-accid');
        const reviewId = edit.getAttribute('data-reviewid');

        const reviewActionsNode = edit.parentNode;
        console.log(accId, reviewId);


        // axios.post(`/accom/detail/${accId}/update/${reviewId}`)
        //     .then(resp => {
        //         console.log(resp)
        //     })
        //     .catch(err => {
        //         console.log(err);
        //     })
    })
});

// 리뷰 수정
function updateReview(accommodationId, accommodationId, el) {
    console.log(accommodationId, accommodationId)
    console.log(el);
    const parentNode = el.parentNode;

    const updateFormEl = parentNode.querySelector('.updateReviewForm');
    updateFormEl.classList.remove("hidden");

}

// 별점 수정
const starsEdit = [...document.getElementsByClassName("reviewInputRatingStarEdit")];

// const reviewInputRatingResultEdit = document.getElementById("ratingResultEdit");

function rateEdit(starElement, reviewId, starNumber) {
    const starClassActive = "reviewInputRatingStarEdit fas fa-star";
    const starClassInactive = "reviewInputRatingStarEdit far fa-star";
    const starsEdit = document.querySelectorAll(`.reviewInputRatingStarEdit${reviewId}`);

    starsEdit.forEach((star, index) => {
        if (index < starNumber) {
            star.classList.remove("far");
            star.classList.add("fas");
        } else {
            star.classList.remove("fas");
            star.classList.add("far");
        }
    });

    const ratingInputEdit = document.getElementById(`ratingInputEdit${reviewId}`);
    if (!ratingInputEdit) {
        console.error(`Rating input element not found for review ID ${reviewId}`);
        return;
    }

    ratingInputEdit.value = starNumber; // 클릭한 별점 값을 input에 설정
    console.log(`Rating input value for review ID ${reviewId}: ${ratingInputEdit.value}`);

    // 별점 결과 업데이트
    const ratingResultEdit = document.getElementById(`ratingResultEdit${reviewId}`);
    printRatingResultEdit(ratingResultEdit, starNumber);
}

function printRatingResultEdit(resultElement, num = 0) {
    resultElement.textContent = `${num}/5`;
}

// 수정 버튼마다 클릭 이벤트 처리
editButtons.forEach(editButton => {
    editButton.addEventListener('click', function (event) {
        event.preventDefault();

        const reviewId = this.dataset.reviewId; // 수정할 리뷰의 ID
        const editForm = document.getElementById(`editForm${reviewId}`);

        // 보기/수정 폼 토글
        editForm.classList.toggle('show');

    });
});

// 수정 완료 버튼 처리
const updateButtons = document.querySelectorAll('.updateButton');
updateButtons.forEach(updateButton => {
    updateButton.addEventListener('click', function (event) {
        event.preventDefault();

        const reviewId = this.dataset.reviewId; // 수정할 리뷰의 ID
        console.log(`Update button clicked for review ID ${reviewId}`);

        const contents = document.querySelector(`#editForm${reviewId} textarea[name="contents"]`).value;
        if (contents) {
            console.log(`Contents for review ID ${reviewId}: ${contents.value}`);
        } else {
            console.error(`Textarea contents for review ID ${reviewId} not found.`);
        }

        const rate = document.getElementById(`ratingInputEdit${reviewId}`).value;
        console.log(`Rating input value for review ID ${reviewId}: ${rate}`);

        const reviewDto = {
            id: reviewId,
            contents: contents,
            rate: rate
        };

        console.log(`Review DTO for review ID ${reviewId}:`, reviewDto);

        axios.post(`/user/accom/detail/update/${reviewId}`, reviewDto)
            .then(response => {
                console.log(`Update response for review ID ${reviewId}:`, response.data);
                alert(response.data.message);
                if (response.data.message === '리뷰가 성공적으로 수정되었습니다.') {
                    // 성공적으로 수정된 경우, 수정 폼 숨기기
                    const editForm = document.getElementById(`editForm${reviewId}`);
                    editForm.classList.remove('show');

                    // 수정된 리뷰 내용 업데이트
                    const reviewContent = document.querySelector(`#review${reviewId} .reviewContent p`);
                    reviewContent.textContent = response.data.contents;
                    console.log(`Review content updated for review ID ${reviewId}`);

                    // 별점 UI 업데이트
                    const newRate = response.data.rate;
                    const starsEdit = document.querySelectorAll(`#review${reviewId} .reviewInputRatingStarEdit`);
                    starsEdit.forEach((star, index) => {
                        if (index < newRate) {
                            star.classList.remove("far");
                            star.classList.add("fas");
                        } else {
                            star.classList.remove("fas");
                            star.classList.add("far");
                        }
                    });
                    console.log(`Star UI updated for review ID ${reviewId}`);

                    const ratingResult = document.getElementById(`ratingResult${reviewId}`);
                    if (ratingResult) {
                        printRatingResultEdit(ratingResult, newRate);
                    } else {
                        console.error("Rating Result element not found");
                    }
                }
            })
            .catch(error => {
                if (error.response && error.response.status === 401) {
                    console.log("로그인이 필요합니다.")
                    alert('로그인이 필요합니다.');
                } else {
                    console.error('Error:', error);
                }
            });
    });
});

// 리뷰 삭제 버튼 이벤트
const deleteButtons = document.querySelectorAll('.deleteButton');
deleteButtons.forEach(deleteButton => {
    deleteButton.addEventListener('click', function (event) {
        event.preventDefault();

        const reviewId = this.dataset.reviewId; // 삭제할 리뷰의 ID
        const accId = this.dataset.accId; // 해당 숙소의 ID

        if (confirm('삭제하시겠습니까?')) {
            fetch(`/user/accom/detail/delete/${reviewId}`, {
                method: 'POST'
            })
                .then(response => response.json())
                .then(data => {
                    alert(data.message);
                    if (data.message === '리뷰가 성공적으로 삭제되었습니다.') {
                        // 삭제 후 필요한 처리
                        const reviewElement = document.getElementById(`review${reviewId}`);
                        if (reviewElement) {
                            reviewElement.remove();
                        }
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('삭제 중 오류가 발생했습니다.');
                });
        }
    });
});
