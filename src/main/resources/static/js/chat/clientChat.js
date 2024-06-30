var stompClient = null;
var username = /*[[${username}]]*/ 'Someone';
var roomId = /*[[${roomId}]]*/ 'defaultRoom';

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect(
        {},
        function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/sub/chatroom/' + roomId, function (messageOutput) {
                showMessage(JSON.parse(messageOutput.body));
            });
        },
        function (error) {
            console.error('STOMP error', error);
        }
    );
}

function fetchMessages(roomId) {
    fetch('/chat/messages/' + roomId)
        .then(response => response.json())
        .then(messages => {
            messages.forEach(showMessage);
        })
        .catch(error => console.error('Error fetching messages:', error));
}

function sendMessage() {
    var messageContent = document.getElementById('myMessage').value;
    var message = {
        sender: username,
        contents: messageContent,
        createdAt: new Date().toISOString(),
        roomId: roomId,
    };

    stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
}

function showMessage(message) {
    var chatLog = document.querySelector('.chatLog');
    var messageElement = document.createElement('div');
    messageElement.classList.add('message');
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
    createChatRoom();

    document.querySelector('#sendMessage').addEventListener('click', function (event) {
        event.preventDefault();
        sendMessage();
        document.getElementById('myMessage').value = '';
    });
});

