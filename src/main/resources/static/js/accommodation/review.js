const messageEl = document.querySelector('.message');
console.log(messageEl);
if (messageEl !== null && messageEl.innerHTML !== "")
    alert(messageEl);

// 모든 수정 버튼 선택
const editButtons = document.querySelectorAll('.editButtoneditButton');

editButtons.forEach(edit => {

    edit.addEventListener('click', function () {

        const accId = edit.getAttribute('data-accomid');
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


function updateReview(accid, reviewid, el) {
    console.log(accid, reviewid)
    console.log(el);
    const parentNode = el.parentNode;

    const updateFormEl = parentNode.querySelector('.updateReviewForm');
    updateFormEl.classList.remove("hidden");

}

//     = "editButton"
//     method = "post"
//     onsubmit = "return false"
//     th:action = "@{/accom/detail/{accId}/update/{reviewId}(accId=${accom.id}, reviewId=${review.id})}" >
//         < button
//     type = "submit" > 수정 < /button>
// </form>
//@{/accom/detail/{accId}/update/{reviewId}(accId=${accom.id}, reviewId=${review.id})}


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
        const contents = document.querySelector(`#editForm${reviewId} textarea[name="contents"]`).value;
        const rate = document.querySelector(`#editForm${reviewId} input[name="rate"]`).value;

        const reviewDto = {
            id: reviewId,
            contents: contents,
            rate: rate
        };

        axios.post(`/accom/detail/update/${reviewId}`, reviewDto)
            .then(response => {
                alert(response.data.message);
                if (response.data.message === '리뷰가 성공적으로 수정되었습니다.') {
                    // 성공적으로 수정된 경우, 수정 폼 숨기기
                    const editForm = document.getElementById(`editForm${reviewId}`);
                    editForm.classList.remove('show');

                    // 수정된 리뷰 내용 업데이트
                    const reviewContent = document.querySelector(`#review${reviewId} .reviewContent p`);
                    reviewContent.textContent = response.data.contents;

                }
            })
            .catch(error => {
                if (error.response && error.response.status === 401) {
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
            fetch(`/accom/detail/delete/${reviewId}`, {
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
