document.addEventListener('DOMContentLoaded', function () {
   if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
         function (position) {
            const container = document.getElementById('map');
            const options = {
               center: new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude),
               level: 3,
            };

            const map = new kakao.maps.Map(container, options);

            const markerPosition = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
            const marker = new kakao.maps.Marker({
               position: markerPosition,
            });
            marker.setMap(map);
         },
         function (error) {
            switch (error.code) {
               case error.PERMISSION_DENIED:
                  console.error('User denied the request for Geolocation.');
                  break;
               case error.POSITION_UNAVAILABLE:
                  console.error('Location information is unavailable.');
                  const container = document.getElementById('map');
                  const options = {
                     center: new kakao.maps.LatLng(37.5665, 126.978),
                     level: 3,
                  };
                  const map = new kakao.maps.Map(container, options);
                  const markerPosition = new kakao.maps.LatLng(37.5665, 126.978);
                  const marker = new kakao.maps.Marker({
                     position: markerPosition,
                  });
                  marker.setMap(map);
                  break;
               case error.TIMEOUT:
                  console.error('The request to get user location timed out.');
                  break;
               case error.UNKNOWN_ERROR:
                  console.error('An unknown error occurred.');
                  break;
            }
         },
         {
            enableHighAccuracy: true,
            timeout: 10000,
            maximumAge: 0,
         },
      );
   } else {
      console.error('Geolocation is not supported by this browser.');
   }
});
