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