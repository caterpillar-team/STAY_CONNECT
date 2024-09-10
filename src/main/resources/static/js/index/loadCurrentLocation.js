function loadMapWithCurrentLocation() {
   if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
         function (position) {
            var lat = position.coords.latitude;
            var lng = position.coords.longitude;

            var container = document.getElementById('map');
            var options = {
               center: new kakao.maps.LatLng(lat, lng),
               level: 3,
            };

            var map = new kakao.maps.Map(container, options);

            var markerPosition = new kakao.maps.LatLng(lat, lng);
            var marker = new kakao.maps.Marker({
               position: markerPosition,
            });
            marker.setMap(map);
         },
         function (error) {
            console.error('Error occurred. Error code: ' + error.code);
         },
      );
   } else {
      console.error('Geolocation is not supported by this browser.');
   }
}
window.onload = loadMapWithCurrentLocation;
