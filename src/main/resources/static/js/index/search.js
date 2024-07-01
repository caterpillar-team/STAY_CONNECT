document.addEventListener("DOMContentLoaded", function () {
  fetchAccommodations(0);
});

function fetchAccommodations(page) {
  axios
    .get("/accommodation", {
      params: { page: page },
    })
    .then(function (response) {
      const data = response.data;

      // Clear the existing accommodations with fade-out effect
      const hotelList = document.getElementById("hotelList");
      hotelList.style.opacity = 0; // Set initial opacity to 0

      // Delay the DOM update to allow fade-out effect to complete
      setTimeout(() => {
        hotelList.innerHTML = "";

        // Append new accommodations
        data.accommodations.forEach(function (accommodation) {
          const card = document.createElement("div");
          card.className = "card";
          card.innerHTML = `
                          <img src="https://placehold.co/300"/>
                          <a href="/accommodation/detail/${accommodation.id}">
                              <p>${accommodation.accommodationName}</p>
                              <p>${formatNumberWithCommas(accommodation.minPrice)}원 ~</p>
                          </a>
                      `;
          hotelList.appendChild(card);
        });

        // Fade-in effect
        hotelList.style.opacity = 1;

        // Update pagination
        const pagination = document.querySelector(".pagination");
        pagination.innerHTML = "";
        for (let i = data.startPage; i <= data.endPage; i++) {
          const pageLink = document.createElement("a");
          pageLink.href = "#";
          pageLink.className = i === data.nowPage ? "active" : "";
          pageLink.innerHTML = `<span>${i}</span>`;
          pageLink.onclick = function (event) {
            event.preventDefault();
            fetchAccommodations(i - 1);
          };
          pagination.appendChild(pageLink);
        }
      }, 300); // Adjust the delay to match the CSS transition duration
    })
    .catch(function (error) {
      console.error("Error fetching accommodations:", error);
    });
}

// 숫자 3자리마다 콤마
function formatNumberWithCommas(number) {
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function performSearch() {
  console.log("performSearch() 실행");
  const searchText = document.getElementById("searchText").value;
  axios
    .get("/search", {
      params: {
        searchText: searchText,
      },
    })
    .then(function (response) {
      console.log("Response data:", response.data); // 데이터 출력
      const hotelList = document.getElementById("hotelList");
      hotelList.innerHTML = "";
      if (Array.isArray(response.data)) {
        response.data.forEach(function (post) {
          const card = document.createElement("div");
          card.className = "card";
          card.innerHTML =
            '<img src="https://placehold.co/300" />' +
            '<a href="/accommodation/detail/' +
            post.id +
            '">' +
            "<p>" +
            post.accommodationName +
            "</p>" +
            "<p>" +
            formatNumberWithCommas(post.minPrice) +
            "원 ~</p>" +
            "</a>";
          hotelList.appendChild(card);
        });
      } else {
        console.log("Expected an array but got:", response.data);
      }
    })
    .catch(function (error) {
      console.log("Error:", error);
    });
}
