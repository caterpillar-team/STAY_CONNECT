<<<<<<< Updated upstream
// 별점
document.addEventListener("DOMContentLoaded", function () {
  const stars = document.querySelectorAll(".reviewInputRatingStar");
  const ratingInput = document.getElementById("ratingInput");

  stars.forEach((star) => {
    star.addEventListener("click", function () {
      const starIndex = parseInt(this.getAttribute("data-star")) - 1; // 별점 값 계산
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

// const messageEl = document.querySelector('.message');
// console.log(messageEl);
// if (messageEl !== null && messageEl.innerHTML !== "") {
//     alert(messageEl.innerHTML);
// }

// 모든 수정 버튼 선택
const editButtons = document.querySelectorAll(".editButton");

editButtons.forEach((edit) => {
  edit.addEventListener("click", function () {
    console.log("editButton clicked");
    const accId = edit.getAttribute("data-accid");
    const reviewId = edit.getAttribute("data-reviewid");

    const reviewActionsNode = edit.parentNode;
    console.log(accId, reviewId);

    // axios.post(`/accom/detail/${accId}/update/${reviewId}`)
    //     .then(resp => {
    //         console.log(resp)
    //     })
    //     .catch(err => {
    //         console.log(err);
    //     })
  });
});

// 리뷰 수정
function updateReview(accommodationId, reviewId, el) {
  console.log("수정버튼 clicked");
  console.log(accommodationId, reviewId);
  const result = confirm("수정하시겠습니까?");
  if (result) {
    console.log(el);
    const parentNode = el.parentNode;

    const updateFormEl = parentNode.querySelector(".updateReviewForm");
    updateFormEl.classList.remove("hidden");
  } else {
    alert("수정 취소");
  }
}

// 별점 수정
const starsEdit = [
  ...document.getElementsByClassName("reviewInputRatingStarEdit"),
];

// const reviewInputRatingResultEdit = document.getElementById("ratingResultEdit");

function rateEdit(starElement, reviewId, starNumber) {
  const starClassActive = "reviewInputRatingStarEdit fas fa-star";
  const starClassInactive = "reviewInputRatingStarEdit far fa-star";
  const starsEdit = document.querySelectorAll(
    `.reviewInputRatingStarEdit${reviewId}`,
  );

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
  console.log(
    `Rating input value for review ID ${reviewId}: ${ratingInputEdit.value}`,
  );

  // 별점 결과 업데이트
  const ratingResultEdit = document.getElementById(
    `ratingResultEdit${reviewId}`,
  );
  printRatingResultEdit(ratingResultEdit, starNumber);
}

function printRatingResultEdit(resultElement, num = 0) {
  resultElement.textContent = `${num}/5`;
}

// 수정 완료 버튼 처리
const updateFormBtnEls = document.querySelectorAll(".updateButton");

updateFormBtnEls.forEach((btn) => {
  btn.addEventListener("click", function () {
    const form = btn.parentNode.parentNode;
    const accId = form.accId.value;
    const reviewId = form.reviewId.value;
    const rate = form.rate.value;
    const contents = form.contents.value;
    console.log("!!", accId, reviewId, rate, contents);

    const formData = new FormData();
    formData.append("accId", accId);
    formData.append("reviewId", reviewId);
    formData.append("rate", rate);
    formData.append("contents", contents);

    if (contents === "" || contents === null) {
      alert("빈값을 넣을 수 없습니다.");
      return;
    }
    axios
      .post(`/accommodation/detail/update/review`, formData, {
        headers: { "Content-Type": "www-x-form-urlencoded" },
      })
      .then((resp) => {
        console.log(resp);
        alert(resp.data.message);
        location.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  });
});

function isValid(form) {
  if (form.contents.value == null || form.contents.value === "") {
    alert("메시지를 작성하셔야 됩니다.");
    return false;
  }
  return true;
}

// 리뷰 삭제 버튼 이벤트
const deleteButtons = document.querySelectorAll(".deleteButton");
deleteButtons.forEach((deleteButton) => {
  deleteButton.addEventListener("click", function (event) {
    event.preventDefault();

    const form = deleteButton.parentNode;
    const reviewId = form.reviewId.value; // 삭제할 리뷰의 ID
    const accId = form.accId.value; //   해당 숙소의 ID
    console.log(reviewId, accId);

    if (confirm("삭제하시겠습니까?")) {
      axios
        .delete(`/accommodation/detail/${accId}/delete/${reviewId}`)
        .then((response) => {
          console.log(response.data);
          alert(response.data.message);
          location.reload();
          if (response.data.message === "리뷰가 성공적으로 삭제되었습니다.") {
            const reviewElement = document.getElementById(`review${reviewId}`);
            if (reviewElement) {
              reviewElement.remove();
            }
          }
        })
        .catch((err) => {
          console.log(err);
          alert("리뷰 삭제 중 오류가 발생했습니다.");
        });
    }
  });
});
=======
// 후기 작성 버튼에 이벤트 리스너 추가
document.querySelector("#addReview").addEventListener("click", function(event) {
    event.preventDefault(); // 폼의 기본 동작인 서버 전송을 중지

    // 후기 데이터 가져오기
    const contents = document.querySelector('textarea[name="content"]').value;
    const rate = 5; // 예시로 평점을 5로 설정

    // ReviewDto 객체 생성
    const reviewDto = {
        contents: contents,
        rate: rate
    };

    // Ajax 요청 보내기
    $.ajax({
        type: 'POST',
        url: '/addReview',
        contentType: 'application/json',
        data: JSON.stringify(reviewDto), // ReviewDto 전송
        success: function(response) {
            console.log('Success:', response);
            // 성공적으로 처리된 경우, 다음 작업 수행
            // 서버로부터 받은 후기 정보를 동적으로 목록에 추가하는 코드 작성
            const reviewContainer = document.querySelector('.reviewContainer');
            const newReview = document.createElement('div');
            newReview.innerHTML = `
                    <div class="reviewWriter">
                        <img alt="" th:src="@{/img/StayConnect-main.jpg}">
                        <span class="user">${response.user.username}</span>
                    </div>
                    <div class="reviewContent">
                        <p>${response.contents}</p>
                    </div>
                    <div class="ratingStar">
                        <span></span> <!-- 별점 표시 -->
                    </div>
                `;
            reviewContainer.appendChild(newReview);
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            // 오류가 발생한 경우, 오류 처리 수행
        }
    });
});
>>>>>>> Stashed changes
