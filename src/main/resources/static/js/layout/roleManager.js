document.addEventListener('DOMContentLoaded', function () {
   const storedRole = localStorage.getItem('role');
   if (!storedRole) {
      axios
         .get('/api/user/role', {
            withCredentials: true, // 쿠키를 포함하기 위한 설정
         })
         .then(response => {
            const userRole = response.data.role;
            localStorage.setItem('role', userRole);
         })
         .catch(error => {
            if (error.response && error.response.status === 401) {
               console.log('사용자가 로그인되어 있지 않습니다.');
            } else {
               console.error('사용자 역할 정보를 가져오는 데 실패했습니다:', error);
            }
         });
   }

   const logoutLink = document.getElementById('logout');
   if (logoutLink) {
      logoutLink.addEventListener('click', function (event) {
         event.preventDefault();
         localStorage.removeItem('role');
         window.location.href = '/user/logout';
      });
   }
});
