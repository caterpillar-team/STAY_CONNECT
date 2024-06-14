// 별점
document.addEventListener('DOMContentLoaded', function() {
    const stars = document.querySelectorAll('.reviewInputRatingStar');
    const ratingInput = document.getElementById('ratingInput');
    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            ratingInput.value = index + 1;
            stars.forEach((s, i) => {
                if (i <= index) {
                    s.classList.remove('far');
                    s.classList.add('fas');
                } else {
                    s.classList.remove('fas');
                    s.classList.add('far');
                }
            });
        });
    });

    // 탭유지 수정중
    // // 로컬 스토리지에서 활성 탭 확인
    // var activeTab = localStorage.getItem('activeTab');
    // if (activeTab) {
    //     var tabElement = document.querySelector(activeTab);
    //     if (tabElement) {
    //         var tab = new bootstrap.Tab(tabElement);
    //         tab.show();
    //     }
    // }
    //
    // // 탭 버튼 클릭 이벤트 추가
    // var tabButtons = document.querySelectorAll('.nav-link');
    // tabButtons.forEach(function(button) {
    //     button.addEventListener('click', function(event) {
    //         var tabId = event.target.getAttribute('data-bs-target');
    //         localStorage.setItem('activeTab', tabId);
    //     });
    // });
});
