//  결제
document.addEventListener('DOMContentLoaded', function () {
   IMP.init('imp66857630');

   var merchantUid = new Date().getTime();

   const payBtn = document.querySelectorAll('#reservationLink'); // 예약 버튼들을 선택

   const confirmBtns = document.querySelectorAll('#confirm_btn'); // 결제 확인 버튼들을 선택
   const closeBtns = document.querySelectorAll('.btn-secondary');

   // 예약하기 버튼
   payBtn.forEach(function (button) {
      button.addEventListener('click', function () {
         const userId = document.getElementById('userId').value;
         const buyerTel = document.getElementById('buyerTel').value;

         // 로그인하지 않은 유저라면 로그인창으로 이동
         if (userId) {
            // 로그인된 경우
            handleReservation(button);
         } else {
            alert('로그인이 필요합니다.');
            window.location.href = '/auth/signin';
         }
      });
   });

   function handleReservation(button) {
      const dataTarget = button.getAttribute('data-target');
      const roomInfoId = dataTarget.split('-')[1];
      const modalId = `reservationModal-${roomInfoId}`;

      const modal = document.getElementById(modalId);
      if (modal) {
         $(modal).modal('show');
      }
   }

   // 결제 버튼
   confirmBtns.forEach(function (button) {
      button.addEventListener('click', function () {

         var modal = button.closest('.modal');

         var merchantUid = new Date().getTime();

         // userId와 roomInfoId를 단일 값으로 추출
         const userId = document.getElementById('userId').value;
         const roomInfoId = document.getElementById('roomInfoId').value;

         IMP.request_pay(
            {
               pg: 'html5_inicis',
               pay_method: 'card',
               merchant_uid: merchantUid,
               name: '테스트 결제',
               amount: 100,
               buyer_tel: buyerTel,
            },
            function (resp) {

               if (resp.success) {
                  const paymentData = {
                     imp_uid: resp.imp_uid,
                     merchant_uid: resp.merchant_uid,
                     paid_amount: resp.paid_amount,
                     pay_method: resp.pay_method,
                     userId: userId,
                     roomInfoId: roomInfoId,
                  };

                  const formData = new FormData();
                  formData.append('imp_uid', resp.imp_uid);
                  formData.append('merchant_uid', resp.merchant_uid);
                  formData.append('paid_amount', resp.paid_amount);
                  formData.append('pay_method', resp.pay_method);
                  formData.append('userId', userId);
                  formData.append('roomInfoId', roomInfoId);

                  function logFormData(formData) {
                     for (const entry of formData.entries()) {
                        const [key, value] = entry;
                     }
                  }

                  logFormData(formData);

                  axios
                     .post('/user/paySuccess', formData, {
                        headers: { 'Content-Type': 'x-www-form-urlencoded' },
                     })
                     .then(response => {

                        modal.style.display = 'none';
                        var modalBackdrop = document.querySelector('.modal-backdrop');
                        if (modalBackdrop) {
                           modalBackdrop.parentNode.removeChild(modalBackdrop);
                        }
                        document.body.classList.remove('modal-open');
                        document.body.style = '';

                        window.location.href = '/user/myPage';
                     })
                     .catch(error => {
                        console.error('Error creating reservation:', error);
                        alert('예약 생성 중 오류가 발생했습니다. 다시 시도해주세요.');
                     });
               } else {
                  alert('결제에 실패했습니다.');
                  console.error('Payment failed:', resp);
               }
            },
         );
      });
   });

   // 닫기 버튼
   closeBtns.forEach(function (button) {
      button.addEventListener('click', function () {
         const modal = button.closest('.modal');
         if (modal) {
            $(modal).modal('hide');
         }
      });
   });
});
