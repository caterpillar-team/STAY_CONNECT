document.addEventListener("DOMContentLoaded", function () {
  fetchAccommodations(0);
});

function fetchAccommodations(page, searchText = "") {
  axios
    .get("/search", {
      params: {
        searchText: searchText,
        page: page,
      },
    })
    .then(function (response) {
      const data = response.data;

      const hotelList = document.getElementById("hotelList");
      hotelList.style.opacity = 0;

      setTimeout(() => {
        hotelList.innerHTML = "";

        data.accommodations.forEach(function (accommodation) {
          const card = createAccommodationCard(accommodation);
          hotelList.appendChild(card);
        });

        hotelList.style.opacity = 1;

        // 페이지네이션 렌더링
        renderPagination(data.totalPages, data.nowPage - 1);
      }, 300);
    })
    .catch(function (error) {
      console.error("Error fetching accommodations:", error);
    });
}

// 숙소 카드 생성 함수
function createAccommodationCard(accommodation) {
  const card = document.createElement("div");
  card.className = "card";
  card.innerHTML = `
    <img src="https://placehold.co/300"/>
    <a href="/accommodation/detail/${accommodation.id}">
      <p>${accommodation.accommodationName}</p>
      <p>${formatNumberWithCommas(accommodation.minPrice)}원 ~</p>
    </a>
  `;
  return card;
}

// 숫자 3자리마다 콤마
function formatNumberWithCommas(number) {
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function performSearch() {
  console.log("performSearch() 실행");
  const searchText = document.getElementById("searchText").value;
  fetchAccommodations(0, searchText);
}

function renderPagination(totalPages, currentPage) {
  const pagination = document.querySelector(".pagination");
  pagination.innerHTML = "";

  for (let i = 1; i <= totalPages; i++) {
    const pageLink = document.createElement("a");
    pageLink.href = "#";
    pageLink.className = i === currentPage + 1 ? "active" : "";
    pageLink.innerHTML = `<span>${i}</span>`;
    pageLink.onclick = function (event) {
      event.preventDefault();
      fetchAccommodations(i - 1, document.getElementById("searchText").value);
    };
    pagination.appendChild(pageLink);
  }
}
