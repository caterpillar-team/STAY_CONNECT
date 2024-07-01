/*<![CDATA[*/
document.addEventListener('DOMContentLoaded', function () {
    var ctx = document.getElementById('monthlyReservationsChart').getContext('2d');

    var reservationStats = /*[[${reservationStats}]]*/ "[]";
    console.log('Raw reservationStats:', reservationStats);
    reservationStats = JSON.parse(reservationStats);

    console.log('Parsed reservationStats: ', reservationStats);

    var labels = reservationStats.map((stat) => stat[0] + '-' + (stat[1] < 10 ? '0' : '') + stat[1]);
    var data = reservationStats.map((stat) => stat[2]);

    console.log('Labels: ', labels);
    console.log('Data: ', data);

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
    var options = {
        scales: {
            y: {
                beginAtZero: true,
            },
        },
    };
    var usageChart = new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: options,
    });
});
/*]]>*/


