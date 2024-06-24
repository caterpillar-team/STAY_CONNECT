//  결제
document.addEventListener('DOMContentLoaded', function () {
    IMP.init('imp66857630');

    var merchantUid = new Date().getTime();
    console.log('merchantUid:', merchantUid);

    const payBtn = document.querySelectorAll('#reservationLink');   // 예약 버튼들을 선택
    console.log('payBtn:', payBtn);

    const confirmBtns = document.querySelectorAll('#confirm_btn');  // 결제 확인 버튼들을 선택
    console.log('comfirmBtns', confirmBtns)
    const closeBtns = document.querySelectorAll('.btn-secondary');

    // 예약하기 버튼
    payBtn.forEach(function (button) {
        button.addEventListener('click', function () {
            console.log('Pay button clicked');

            const userId = document.getElementById('userId').value;
            console.log("currentUser", userId)
            const buyerTel = document.getElementById('buyerTel').value;
            console.log("buyerTel", buyerTel)

            if (userId===null || userId === "") {
                window.location.href = '/auth/signin';
            } else {
                // 로그인된 경우 결제 모달 표시
                const dataTarget = button.getAttribute('data-target');
                console.log('dataTarget:', dataTarget);

                const roomInfoId = dataTarget.split('-')[1];
                console.log('roomInfoId:', roomInfoId);
                const modalId = `reservationModal-${roomInfoId}`;
                console.log("madalId:", modalId)

                // 클릭한 버튼에 해당하는 모달 표시
                const modal = document.getElementById(modalId);
                console.log("modal : ", modal);
                if (modal) {
                    $(modal).modal('show');
                }
            }
        });
    });

    // 결제 버튼
    confirmBtns.forEach(function (button) {
        button.addEventListener('click', function () {
            console.log('confirm button clicked');

            var modal = button.closest('.modal');
            console.log('modal:', modal);

            var merchantUid = new Date().getTime();
            console.log('merchantUid:', merchantUid);

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
                    console.log('Payment response:', resp)

                    if (resp.success) {
                        const paymentData = {
                            imp_uid: resp.imp_uid,
                            merchant_uid: resp.merchant_uid,
                            paid_amount: resp.paid_amount,
                            pay_method: resp.pay_method,
                            userId: userId,
                            roomInfoId: roomInfoId
                        };

                        console.log('Payment data:', paymentData);

                        const formData = new FormData();
                        formData.append('imp_uid', resp.imp_uid);
                        formData.append('merchant_uid', resp.merchant_uid);
                        formData.append('paid_amount', resp.paid_amount);
                        formData.append('pay_method', resp.pay_method);
                        formData.append('userId', userId);
                        formData.append('roomInfoId', roomInfoId);

                        console.log('Form data:', formData);

                        axios.post('/user/paySuccess', formData, {headers: {'Content-Type': 'x-www-form-urlencoded'}})
                            .then(response => {
                                console.log('Reservation created:', response.data);

                                modal.style.display = 'none';
                                var modalBackdrop = document.querySelector('.modal-backdrop');
                                if (modalBackdrop) {
                                    modalBackdrop.parentNode.removeChild(modalBackdrop);
                                }
                                document.body.classList.remove('modal-open');
                                document.body.style = '';

                                window.location.href = '/';
                            })
                            .catch(error => {
                                console.error('Error creating reservation:', error);
                                alert("예약 생성 중 오류가 발생했습니다. 다시 시도해주세요.");
                            });
                    } else {
                        alert("결제에 실패했습니다.");
                        console.error('Payment failed:', resp);
                    }
                }
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
