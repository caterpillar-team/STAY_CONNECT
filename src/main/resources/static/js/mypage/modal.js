$(document).ready(function () {
   $('#reservationLink').addClass('selected');
   $('.reservation__list').addClass('selected');
   $('.reservation-chk').addClass('selected');

   $('#reservationLink').click(function () {
      $('.reservation__list').addClass('selected');
      $('.reservation-chk').addClass('selected');
      $('.modify__info').removeClass('selected');
      $('.my-info').removeClass('selected');
      $('#reservationLink').addClass('selected');
      $('#myInfoLink').removeClass('selected');
   });

   $('#myInfoLink').click(function () {
      $('.reservation__list').removeClass('selected');
      $('.reservation-chk').removeClass('selected');
      $('.modify__info').addClass('selected');
      $('.my-info').addClass('selected');
      $('#reservationLink').removeClass('selected');
      $('#myInfoLink').addClass('selected');
   });

   // 모달 창 열기
   $('.cancel-reservation').click(function () {
      const reservationId = $(this).data('id');
      $('#confirmCancel').data('id', reservationId);
      $('#reservationModal').data('id', reservationId);
      $('#cancelModal').modal('show');
   });

   $('#hamburger-icon').click(function () {
      $('#nav-menu').toggle(); // 메뉴 표시/숨기기 토글
   });

   // 예약 취소 확인
   $('#confirmCancel').click(function () {
      const reservationId = $(this).data('id');
      // 예약 취소
      $('#cancelModal').modal('hide'); // 취소 확인 모달 닫기
      $('#reservationModal').modal('show'); // 예약 확인 모달 열기
   });

   // 환불 버튼 클릭 이벤트 핸들러
   $('#refund_btn').click(function () {
      const reservationId = $('#reservationModal').data('id');
      // 환불
      processRefund(reservationId);
   });

   // 회원 탈퇴 모달 열기
   $('#deleteAccountBtn').click(function () {
      const userId = $(this).data('id');
      $('#confirmDelete').data('id', userId);
      $('#deleteAccountModal').modal('show');
   });

   // 회원 탈퇴 확인
   $('#confirmDelete').click(function () {
      const userId = $(this).data('id');
      deleteUser(userId);
   });

   // 모달 닫기 버튼 클릭 시 모달 숨기기
   $('.modal .btn-secondary').click(function () {
      $(this).closest('.modal').modal('hide');
   });
});

function processRefund(reservationId) {
   location.href = '/user/cancel_reservation?reservationId=' + reservationId;
}

function deleteUser(userId) {
   $.post('/user/deleteUser/' + userId, function () {
      window.location.href = '/login?accountDeleted'; // 탈퇴 후 로그인 페이지로 리다이렉트
   });
}
