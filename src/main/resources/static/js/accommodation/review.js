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
const updateForms = document.querySelectorAll(".updateReviewForm");

// updateForms.forEach((form) => {
//   form.addEventListener("submit", function (event) {
//     event.preventDefault(); // 폼의 기본 제출 동작 막기
//     alert("테스트");
//     if (isValid(form)) {
//       const reviewId =
//         this.querySelector(".updateButton").getAttribute("data-review-id");
//       console.log(`Update button clicked for review ID ${reviewId}`);
//
//       const contents = this.querySelector(`textarea[name="contents"]`).value;
//       if (!contents) {
//         alert("내용을 입력해주세요.");
//         console.error(`Textarea contents for review ID ${reviewId} not found.`);
//         return;
//       }
//
//       const rate = document.getElementById(`ratingInputEdit${reviewId}`).value;
//       console.log(`Rating input value for review ID ${reviewId}: ${rate}`);
//
//       const reviewDto = {
//         id: reviewId,
//         contents: contents,
//         rate: rate,
//       };
//
//       console.log(`Review DTO for review ID ${reviewId}:`, reviewDto);
//
//       // AJAX 요청 전에 폼 제출 방지를 위해 event.stopPropagation() 추가
//       event.stopPropagation();
//
//       axios
//         .post(`/accommodation/detail/update/review`, reviewDto)
//         .then((response) => {
//           console.log(
//             `Update response for review ID ${reviewId}:`,
//             response.data,
//           );
//
//           // 성공적으로 수정된 경우
//           if (response.data.message === "리뷰가 성공적으로 수정되었습니다.") {
//             // 성공 메시지 표시
//             alert(response.data.message);
//
//             // 성공적으로 수정된 경우, 수정 폼 숨기기
//             const editForm = document.getElementById(`editForm${reviewId}`);
//             if (editForm) {
//               editForm.classList.remove("show");
//             } else {
//               console.error(`Edit form not found for review ID ${reviewId}`);
//             }
//
//             // 수정된 리뷰 내용 업데이트
//             const reviewContent = document.querySelector(
//               `#review${reviewId} .reviewContent p`,
//             );
//             if (reviewContent) {
//               reviewContent.textContent = response.data.contents;
//               console.log(`Review content updated for review ID ${reviewId}`);
//             } else {
//               console.error(
//                 `Review content element not found for review ID ${reviewId}`,
//               );
//             }
//
//             // 별점 UI 업데이트
//             const newRate = response.data.rate;
//             const starsEdit = document.querySelectorAll(
//               `#review${reviewId} .reviewInputRatingStarEdit`,
//             );
//             starsEdit.forEach((star, index) => {
//               if (index < newRate) {
//                 star.classList.remove("far");
//                 star.classList.add("fas");
//               } else {
//                 star.classList.remove("fas");
//                 star.classList.add("far");
//               }
//             });
//             console.log(`Star UI updated for review ID ${reviewId}`);
//
//             const ratingResult = document.getElementById(
//               `ratingResult${reviewId}`,
//             );
//             if (ratingResult) {
//               printRatingResultEdit(ratingResult, newRate);
//             } else {
//               console.error("Rating Result element not found");
//             }
//           } else {
//             // 다른 메시지 처리
//             alert(response.data.message);
//           }
//         })
//         .catch((error) => {
//           if (error.response && error.response.status === 401) {
//             console.log("로그인이 필요합니다.");
//             alert("로그인이 필요합니다.");
//           } else {
//             console.error("Error:", error);
//             alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
//           }
//         });
//     } else {
//       console.log("validation Check Error..");
//     }
//   });
// });

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

    const reviewId = this.dataset.reviewId; // 삭제할 리뷰의 ID
    const accId = this.dataset.accId; // 해당 숙소의 ID

    if (confirm("삭제하시겠습니까?")) {
      fetch(`/accommodation/detail/delete/${reviewId}`, {
        method: "POST",
      })
        .then((response) => response.json())
        .then((data) => {
          alert(data.message);
          if (data.message === "리뷰가 성공적으로 삭제되었습니다.") {
            // 삭제 후 필요한 처리
            const reviewElement = document.getElementById(`review${reviewId}`);
            if (reviewElement) {
              reviewElement.remove();
            }
          }
        })
        .catch((error) => {
          console.error("Error:", error);
          alert("삭제 중 오류가 발생했습니다.");
        });
    }
  });
});
