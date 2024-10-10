// 차트를 보여주는 함수
function showChart(chartId) {
   var charts = document.querySelectorAll('.chartContainer');
   charts.forEach(function (chart) {
      chart.style.display = 'none';
   });
   document.getElementById(chartId).style.display = 'block';
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', function () {
   showChart('monthlyReservationsChartContainer'); // 기본적으로 월별 예약 차트를 표시

   // 차트 렌더링 함수들 호출
   fetchDashboardStats();
   fetchMonthlyReservationsChart();
   fetchReservationsByRegionChart();
   fetchUsersByAgeChart();
});

// 데이터 가져오기 및 차트 렌더링 함수들
function fetchMonthlyReservationsChart() {
   // 월별 예약 차트 데이터 가져오기 (API 호출 예시)
   fetch('/api/reservationStats')
      .then(response => response.json())
      .then(reservationStats => {
         var ctx = document.getElementById('monthlyReservationsChart').getContext('2d');
         var labels = reservationStats.map(stat => stat[0] + '-' + (stat[1] < 10 ? '0' : '') + stat[1]);
         var data = reservationStats.map(stat => stat[2]);

         var chartData = {
            labels: labels,
            datasets: [
               {
                  label: 'Monthly Reservations',
                  data: data,
                  backgroundColor: 'rgba(75, 192, 192, 0.2)',
                  borderColor: 'rgba(75, 192, 192, 1)',
                  borderWidth: 1,
               },
            ],
         };

         new Chart(ctx, {
            type: 'line',
            data: chartData,
            options: {
               plugins: {
                  legend: { display: false },
               },
               scales: {
                  y: { beginAtZero: true },
               },
            },
         });
      })
      .catch(error => console.error('Error fetching monthly reservation stats:', error));
}

function fetchReservationsByRegionChart() {
   // 지역별 예약 차트 데이터 가져오기 (API 호출 예시)
   fetch('/api/reservationsByRegion')
      .then(response => response.json())
      .then(reservationsByRegion => {
         var ctx = document.getElementById('regionChart').getContext('2d');
         var labels = reservationsByRegion.map(stat => stat.region);
         var data = reservationsByRegion.map(stat => stat.count);

         var chartData = {
            labels: labels,
            datasets: [
               {
                  label: 'Reservations By Region',
                  data: data,
                  backgroundColor: [
                     'rgba(255, 99, 132, 0.2)',
                     'rgba(54, 162, 235, 0.2)',
                     'rgba(255, 206, 86, 0.2)',
                     'rgba(75, 192, 192, 0.2)',
                     'rgba(153, 102, 255, 0.2)',
                     'rgba(255, 159, 64, 0.2)',
                  ],
                  borderColor: [
                     'rgba(255, 99, 132, 1)',
                     'rgba(54, 162, 235, 1)',
                     'rgba(255, 206, 86, 1)',
                     'rgba(75, 192, 192, 1)',
                     'rgba(153, 102, 255, 1)',
                     'rgba(255, 159, 64, 1)',
                  ],
                  borderWidth: 1,
               },
            ],
         };

         new Chart(ctx, {
            type: 'pie',
            data: chartData,
            options: {
               plugins: {
                  legend: { display: false },
               },
               scales: {
                  y: { beginAtZero: true },
               },
            },
         });
      })
      .catch(error => console.error('Error fetching reservations by region:', error));
}

function fetchUsersByAgeChart() {
   // 연령별 이용자 차트 데이터 가져오기 (API 호출 예시)
   fetch('/api/ageStats')
      .then(response => response.json())
      .then(ageStats => {
         var ctx = document.getElementById('ageChart').getContext('2d');
         var labels = ageStats.map(stat => stat.ageRange);
         var data = ageStats.map(stat => stat.count);

         var chartData = {
            labels: labels,
            datasets: [
               {
                  label: 'Users By Age',
                  data: data,
                  backgroundColor: 'rgba(75, 192, 192, 0.2)',
                  borderColor: 'rgba(75, 192, 192, 1)',
                  borderWidth: 1,
               },
            ],
         };

         new Chart(ctx, {
            type: 'bar',
            data: chartData,
            options: {
               plugins: {
                  legend: { display: false },
               },
               scales: {
                  y: { beginAtZero: true },
               },
            },
         });
      })
      .catch(error => console.error('Error fetching users by age:', error));
}

// 대시보드 데이터 가져오기 (예시)
function fetchDashboardStats() {
   fetch('/admin/dashboardStats')
      .then(response => response.json())
      .then(data => {
         document.getElementById('totalUsers').innerText = data.totalUsers;
         document.getElementById('totalReservations').innerText = data.totalReservations;
         document.getElementById('totalSales').innerText = data.totalSales;
      })
      .catch(error => console.error('Error fetching dashboard stats:', error));
}

// 시간 업데이트 함수
function updateDateTime() {
   var now = new Date();
   var formattedDate = now.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
   });

   document.getElementById('currentDateTimeUsers').innerText = formattedDate;
   document.getElementById('currentDateTimeReservations').innerText = formattedDate;
   document.getElementById('currentDateTimeSales').innerText = formattedDate;
}
