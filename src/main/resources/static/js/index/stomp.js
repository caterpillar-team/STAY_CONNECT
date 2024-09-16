document.addEventListener('DOMContentLoaded', function () {
   if (typeof StompJs === 'undefined') {
      console.error('STOMP.js is not loaded');
   } else {
      console.log('STOMP.js is loaded');

      // SockJS 및 StompJs 클라이언트 설정
      var socket = new SockJS('/ws');
      var stompClient = new StompJs.Client({
         webSocketFactory: () => socket,
         reconnectDelay: 5000,
      });

      stompClient.onConnect = function (frame) {
         console.log('Connected: ' + frame);
      };

      stompClient.activate(); // STOMP 클라이언트 활성화
   }
});
