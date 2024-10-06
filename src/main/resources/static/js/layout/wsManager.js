const WebSocketManager = {
   socket: null,
   stompClient: null,

   connect() {
      if (this.stompClient && this.stompClient.connected) {
         return;
      }

      this.socket = new SockJS('/ws');
      this.stompClient = new StompJs.Client({
         webSocketFactory: () => this.socket,
         reconnectDelay: 5000,
         heartbeatIncoming: 4000,
         heartbeatOutgoing: 4000,

         onConnect: frame => {
            console.log('Connected: ' + frame);
            const role = localStorage.getItem('role');

            if (role === 'ROLE_ADMIN') {
               this.stompClient.subscribe('/user/chat/message', function (messageOutput) {
                  showMessage(messageOutput.body);
               });
               this.stompClient.subscribe('/inquiry/self', function (messageOutput) {
                  showMessage(messageOutput.body);
               });
               this.stompClient.subscribe('/inquiry/claims', function (messageOutput) {
                  showMessage(messageOutput.body);
               });
            } else if (role === 'ROLE_USER') {
               this.stompClient.subscribe('/user/chat/message', function (messageOutput) {
                  showMessage(messageOutput.body);
               });
               this.stompClient.subscribe('/inquiry/self', function (messageOutput) {
                  showMessage(messageOutput.body);
               });
            } else {
               console.error('Unknown role: ' + role);
            }
         },

         onStompError: frame => {
            console.log('STOMP: ' + frame);
         },
      });
      this.stompClient.activate();
   },

   publish(destination, message) {
      if (this.stompClient && this.stompClient.connected) {
         this.stompClient.publish({
            destination: destination,
            body: message,
         });
      }
   },
};

window.onload = function () {
   WebSocketManager.connect();

   const chatInput = document.getElementById('chatInput');
   const sendButton = document.getElementById('sendButton');

   chatInput.addEventListener('keydown', function (event) {
      if (event.key === 'Enter' && chatInput.value.trim() !== '') {
         sendMessage();
      }
   });

   sendButton.addEventListener('click', function () {
      sendMessage();
   });
};

function sendMessage() {
   const chatInput = document.getElementById('chatInput');
   const message = chatInput.value.trim();

   if (message !== '') {
      WebSocketManager.publish('/app/inquiry/message', message);
      chatInput.value = '';
   }
}

function showMessage(message) {
   const chatMessages = document.getElementById('chatMessages');
   const messageElement = document.createElement('div');
   messageElement.innerText = message;
   chatMessages.appendChild(messageElement);
}
