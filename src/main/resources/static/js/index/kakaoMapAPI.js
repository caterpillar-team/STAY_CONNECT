// 지도 API 설정
var container = document.getElementById('map');
var options = {
  center: new kakao.maps.LatLng(35.8664929, 128.5935208),
  level: 3,
};

// 지도 생성
var map = new kakao.maps.Map(container, options);

// 지도 유형 컨트롤 추가
var mapTypeControl = new kakao.maps.MapTypeControl();
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 줌 컨트롤 추가
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 마커 생성
var marker = new kakao.maps.Marker({
  position: map.getCenter(),
});
marker.setMap(map); // 마커를 지도 위에 표시
