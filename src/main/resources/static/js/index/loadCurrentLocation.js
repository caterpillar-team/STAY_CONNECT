function loadMapWithCurrentLocation() {
   if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
         function (position) {
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;

            const container = document.getElementById('map');
            const options = {
               center: new kakao.maps.LatLng(lat, lng),
               level: 3,
            };

            const map = new kakao.maps.Map(container, options);

            const markerPosition = new kakao.maps.LatLng(lat, lng);
            const marker = new kakao.maps.Marker({
               position: markerPosition,
            });
            marker.setMap(map);
         },
         function (error) {
            console.error('Error occurred. Error code: ' + error.code);
            switch (error.code) {
               case error.PERMISSION_DENIED:
                  console.error('User denied the request for Geolocation.');
                  break;
               case error.POSITION_UNAVAILABLE:
                  console.error('Location information is unavailable.');
                  break;
               case error.TIMEOUT:
                  console.error('The request to get user location timed out.');
                  break;
               case error.UNKNOWN_ERROR:
                  console.error('An unknown error occurred.');
                  break;
            }
         },
      );
   } else {
      console.error('Geolocation is not supported by this browser.');
   }
}

window.onload = loadMapWithCurrentLocation;
