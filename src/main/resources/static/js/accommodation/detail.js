// 별점
document.addEventListener("DOMContentLoaded", function () {
    const stars = document.querySelectorAll(".reviewInputRatingStar");
    const ratingInput = document.getElementById("ratingInput");
    stars.forEach((star, index) => {
        star.addEventListener("click", () => {
            ratingInput.value = index + 1;
            stars.forEach((s, i) => {
                if (i <= index) {
                    s.classList.remove("far");
                    s.classList.add("fas");
                } else {
                    s.classList.remove("fas");
                    s.classList.add("far");
                }
            });
        });
    });

    // 스크롤
    // 탭 리스트 가져오기
    const tabList = document.getElementById('myTab').querySelectorAll('.nav-link');

    // 객실확인 탭 요소 가져오기
    const roomchkTab = document.getElementById('roomchk-tab');

    // 객실확인 탭 클릭 이벤트 리스너 추가
    roomchkTab.addEventListener('click', function () {
        // 객실확인 목록이 있는 섹션 요소 가져오기
        const roomchkSection = document.getElementById('roomchk');

        // 객실확인 목록 섹션으로 스크롤 내리기
        roomchkSection.scrollIntoView({behavior: 'smooth', block: 'start'});

        // 탭 메뉴의 활성화 상태 변경
        tabList.forEach(tab => {
            if (tab.id === 'roomchk-tab') {
                tab.classList.add('active');
            } else {
                tab.classList.remove('active');
            }
        });
    });
    // 리뷰 탭 요소 가져오기
    const reviewTab = document.getElementById('review-tab');

    // 리뷰 탭 클릭 이벤트 리스너 추가
    reviewTab.addEventListener('click', function () {
        // 후기 목록 섹션을 찾습니다.
        const reviewSection = document.getElementById('review');

        // 후기 입력란 섹션이 있으면 해당 섹션으로 스크롤을 내립니다.
        if (reviewSection) {
            reviewSection.scrollIntoView({behavior: 'smooth', block: 'start'});
        } else {
            // 후기 목록이 없으면 일반적으로 후기 목록 섹션으로 스크롤을 내립니다.
            const reviewInputSection = document.querySelector('.reviewInput');

            if (reviewInputSection) {
                reviewInputSection.scrollIntoView({behavior: 'smooth', block: 'start'});
            }
        }

        // 탭 메뉴의 활성화 상태 변경
        tabList.forEach(tab => {
            if (tab.id === 'review-tab') {
                tab.classList.add('active');
            } else {
                tab.classList.remove('active');
            }
        });
    });

    document.getElementById('scrollToTabButton').addEventListener('click', scrollToTab);

    function scrollToTab() {
        const tabButton = document.getElementById('myTab'); // 탭 메뉴가 위치한 부모 요소 ID를 가져옵니다.

        if (tabButton) {
            tabButton.parentElement.scrollIntoView({behavior: 'smooth', block: 'start'});
        }
    }
});
