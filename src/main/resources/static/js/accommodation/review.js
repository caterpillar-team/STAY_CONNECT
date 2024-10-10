// 별점
document.addEventListener('DOMContentLoaded', function () {
   const stars = document.querySelectorAll('.reviewInputRatingStar');
   const ratingInput = document.getElementById('ratingInput');

   stars.forEach(star => {
      star.addEventListener('click', function () {
         const starIndex = parseInt(this.getAttribute('data-star')) - 1; // 별점 값 계산
         ratingInput.value = starIndex + 1;

         stars.forEach((s, index) => {
            if (index <= starIndex) {
               s.classList.remove('far');
               s.classList.add('fas');
            } else {
               s.classList.remove('fas');
               s.classList.add('far');
            }
         });
      });
   });
});

// 모든 수정 버튼 선택
const editButtons = document.querySelectorAll('.editButton');

editButtons.forEach(edit => {
   edit.addEventListener('click', function () {
      // const accId = edit.getAttribute("data-accid");
      // const reviewId = edit.getAttribute("data-reviewid");
      // const reviewActionsNode = edit.parentNode;
   });
});

// 리뷰 수정
function updateReview(accommodationId, reviewId, el) {
   const result = confirm('수정하시겠습니까?');
   if (result) {
      const parentNode = el.parentNode;

      const updateFormEl = parentNode.querySelector('.updateReviewForm');
      updateFormEl.classList.remove('hidden');
   } else {
      alert('수정 취소');
   }
}

// 별점 수정
const starsEdit = [...document.getElementsByClassName('reviewInputRatingStarEdit')];

// const reviewInputRatingResultEdit = document.getElementById("ratingResultEdit");

function rateEdit(starElement, reviewId, starNumber) {
   // const starClassActive = "reviewInputRatingStarEdit fas fa-star";
   // const starClassInactive = "reviewInputRatingStarEdit far fa-star";
   const starsEdit = document.querySelectorAll(`.reviewInputRatingStarEdit${reviewId}`);

   starsEdit.forEach((star, index) => {
      if (index < starNumber) {
         star.classList.remove('far');
         star.classList.add('fas');
      } else {
         star.classList.remove('fas');
         star.classList.add('far');
      }
   });

   const ratingInputEdit = document.getElementById(`ratingInputEdit${reviewId}`);
   if (!ratingInputEdit) {
      console.error(`Rating input element not found for review ID ${reviewId}`);
      return;
   }

   ratingInputEdit.value = starNumber; // 클릭한 별점 값을 input에 설정

   // 별점 결과 업데이트
   const ratingResultEdit = document.getElementById(`ratingResultEdit${reviewId}`);
   printRatingResultEdit(ratingResultEdit, starNumber);
}

function printRatingResultEdit(resultElement, num = 0) {
   resultElement.textContent = `${num}/5`;
}

// 수정 완료 버튼 처리
const updateFormBtnEls = document.querySelectorAll('.updateButton');

updateFormBtnEls.forEach(btn => {
   btn.addEventListener('click', function () {
      const form = btn.parentNode.parentNode;
      const accId = form.accId.value;
      const reviewId = form.reviewId.value;
      const rate = form.rate.value;
      const contents = form.contents.value;

      const formData = new FormData();
      formData.append('accId', accId);
      formData.append('reviewId', reviewId);
      formData.append('rate', rate);
      formData.append('contents', contents);

      if (contents === '' || contents === null) {
         alert('빈값을 넣을 수 없습니다.');
         return;
      }
      axios
         .post(`/accommodation/detail/update/review`, formData, {
            headers: { 'Content-Type': 'www-x-form-urlencoded' },
         })
         .then(resp => {
            alert(resp.data.message);
            location.reload();
         })
         .catch(err => {
            console.error(err);
         });
   });
});

function isValid(form) {
   if (form.contents.value == null || form.contents.value === '') {
      alert('메시지를 작성하셔야 됩니다.');
      return false;
   }
   return true;
}

// 리뷰 삭제 버튼 이벤트
const deleteButtons = document.querySelectorAll('.deleteButton');
deleteButtons.forEach(deleteButton => {
   deleteButton.addEventListener('click', function (event) {
      event.preventDefault();

      const form = deleteButton.parentNode;
      const reviewId = form.reviewId.value; // 삭제할 리뷰의 ID
      const accId = form.accId.value; //   해당 숙소의 ID

      if (confirm('삭제하시겠습니까?')) {
         axios
            .delete(`/accommodation/detail/${accId}/delete/${reviewId}`)
            .then(response => {
               alert(response.data.message);
               location.reload();
               if (response.data.message === '리뷰가 성공적으로 삭제되었습니다.') {
                  const reviewElement = document.getElementById(`review${reviewId}`);
                  if (reviewElement) {
                     reviewElement.remove();
                  }
               }
            })
            .catch(err => {
               console.error(err);
               alert('리뷰 삭제 중 오류가 발생했습니다.');
            });
      }
   });
});
