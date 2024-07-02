// scrollToTab 함수 정의
function scrollToTab() {
    const tabButton = document.getElementById('myTab');

    if (tabButton) {
        const header = document.querySelector('header'); // 헤더 요소 선택
        const headerHeight = header ? header.offsetHeight : 0; // 헤더 높이 계산
        const tabButtonPosition = tabButton.getBoundingClientRect().top + window.pageYOffset - headerHeight;
        window.scrollTo({ top: tabButtonPosition, behavior: 'smooth' });
    }
}

// document 로드 후 실행
document.addEventListener("DOMContentLoaded", function () {
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
        const header = document.querySelector('header'); // 헤더 요소 선택
        const headerHeight = header ? header.offsetHeight : 0; // 헤더 높이 계산
        const roomchkSectionPosition = roomchkSection.getBoundingClientRect().top + window.pageYOffset - headerHeight;
        window.scrollTo({ top: roomchkSectionPosition, behavior: 'smooth' });

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
        // 후기 목록 섹션 가져오기
        const reviewSection = document.getElementById('review');

        // 후기 목록 섹션이 있으면 해당 섹션으로 스크롤을 내리기
        if (reviewSection) {
            const header = document.querySelector('header'); // 헤더 요소 선택
            const headerHeight = header ? header.offsetHeight : 0; // 헤더 높이 계산
            const reviewSectionPosition = reviewSection.getBoundingClientRect().top + window.pageYOffset - headerHeight;
            window.scrollTo({ top: reviewSectionPosition, behavior: 'smooth' });
        } else {
            // 후기 목록이 없으면 일반적으로 후기 입력란 섹션으로 스크롤을 내리기
            const reviewInputSection = document.querySelector('.reviewInput');

            if (reviewInputSection) {
                const header = document.querySelector('header'); // 헤더 요소 선택
                const headerHeight = header ? header.offsetHeight : 0; // 헤더 높이 계산
                const reviewInputSectionPosition = reviewInputSection.getBoundingClientRect().top + window.pageYOffset - headerHeight;
                window.scrollTo({ top: reviewInputSectionPosition, behavior: 'smooth' });
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

    // 플로팅 버튼
    // 초기 로드 시 플로팅 버튼 표시
    const scrollToTabButton = document.getElementById('scrollToTabButton');
    scrollToTabButton.style.display = 'none';

    // 스크롤 이벤트 핸들러
    window.addEventListener('scroll', function () {
        const footer = document.querySelector('footer');
        const accomImgSection = document.querySelector('.accomImg');
        const footerTop = footer.getBoundingClientRect().top;
        const accomImgBottom = accomImgSection.getBoundingClientRect().bottom;
        const buttonHeight = scrollToTabButton.clientHeight;

        // 플로팅 버튼이 accomDetailInfo-section 위에 있을 때 처리
        if (accomImgBottom > 0 && accomImgBottom < window.innerHeight) {
            scrollToTabButton.style.display = 'none'; // 플로팅 버튼 숨기기
        } else {
            scrollToTabButton.style.display = 'block'; // 플로팅 버튼 보이기
        }

        // 푸터가 뷰포트에 진입할 때까지 버튼 고정
        if (footerTop > window.innerHeight - buttonHeight) {
            scrollToTabButton.style.bottom = '15px'; // 기본 여백
        } else {
            const bottomMargin = window.innerHeight - footerTop + 15; // 추가 여백
            scrollToTabButton.style.bottom = bottomMargin + 'px';
        }
    });

    // 플로팅 버튼 클릭 이벤트 추가
    scrollToTabButton.addEventListener('click', scrollToTab);
});
