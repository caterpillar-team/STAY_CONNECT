document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const scrollToHotelList = urlParams.get("scroll");
  if (scrollToHotelList === "true") {
    document.getElementById("hotelList").scrollIntoView({ behavior: "smooth" });
  }
});

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
