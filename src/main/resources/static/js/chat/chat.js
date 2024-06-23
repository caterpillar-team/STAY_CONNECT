var stompClient = null; //클라이언트를 저장하는 변수임

var username = /*[[${username}]]*/ 'Someone';
var roomId = /*[[${roomId}]]*/ 'defaultRoom';

function connect() {
    //연결 함수 설정
    var socket = new SockJS('/ws'); // '/ws'엔트포인트로 SockJS 객체 생성
    stompClient = Stomp.over(socket); //클라이언트 생성
    stompClient.connect(
        //클라이언트 사용해서 서버 연결
        {},
        function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/sub/chatroom/' + roomId, function (messageOutput) {
                //'/topic/public'로 오는 메시지 구독
                showMessage(JSON.parse(messageOutput.body)); //수신된 메시지(JSON)를 풀어서 출력
            });
        },
        function (error) {
            console.error('STOMP error', error);
        }
    );
}

function sendMessage(messageContent) {
    //메시지 전송
    var messageContent = document.getElementById('myMessage').value;
    var message = {
        sender: username.sender,
        contents: messageContent,
        createdAt: new Date().toISOString(),
        roomId: roomId,
    };

    stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message)); //엔드포인트로 전송, 메시지는 JSON문자열로 묶어서 반환
}

function showMessage(message) {
    //메시지 보여줌
    var chatLog = document.querySelector('.chatLog');
    var messageElement = document.createElement('div');
    messageElement.classList.add('message'); // = <div class="message"></div>
    messageElement.textContent = message.sender + ' : ' + message.contents;
    chatLog.appendChild(messageElement);
}

function createChatRoom() {
    fetch('/chat/createRoom')
        .then((response) => response.json())
        .then((data) => {
            roomId = data.roomId;
            connect(roomId);
        })
        .catch((error) => console.error('Error creating room:', error));
}

window.addEventListener('load', function () {
    connect();

    document.querySelector('#message button').addEventListener('click', function (event) {
        event.preventDefault();
        sendMessage();
        document.getElementById('myMessage').value = '';
    });
});
