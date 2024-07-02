var stompClient = null;
var username = /*[[${username}]]*/ 'Admin';
var currentRooms = {};

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect(
        {},
        function (frame) {
            console.log('Connected: ' + frame);
            fetchActiveRooms();
        },
        function (error) {
            console.error('STOMP error', error);
        }
    );
}

function fetchActiveRooms() {
    fetch('/chat/rooms')
        .then((response) => response.json())
        .then((rooms) => {
            fetchMessageCounts().then((messageCounts) => {
                rooms.forEach((room) => {
                    if (!currentRooms[room.roomId]) {
                        // 새로운 roomId인 경우에만 구독
                        currentRooms[room.roomId] = true;
                        createChatLog(room.roomId, messageCounts[room.roomId] || 0);
                        stompClient.subscribe('/sub/chatroom/' + room.roomId, function (messageOutput) {
                            showMessage(JSON.parse(messageOutput.body), room.roomId);
                        });
                    }
                });
            });
        })
        .catch((error) => console.error('Error fetching rooms:', error));
}

function fetchMessageCounts() {
    return fetch('/chat/messageCounts')
        .then((response) => response.json())
        .catch((error) => {
            console.error('Error fetching message counts:', error);
            return {};
        });
}

function createChatLog(roomId, messageCount) {
    var chatLogContainer = document.createElement('div');
    chatLogContainer.id = 'chatContainer-' + roomId;
    chatLogContainer.classList.add('chatContainer');
    var roomContainer = document.getElementById('roomsContainer');
    roomContainer.appendChild(chatLogContainer);

    var roomTitle = document.createElement('h2');
    roomTitle.textContent = 'Room: ' + roomId + ' (Messages: ' + messageCount + ')';
    chatLogContainer.appendChild(roomTitle);

    var chatLog = document.createElement('div');
    chatLog.id = 'chatLog-' + roomId;
    chatLog.classList.add('chatLog');
    chatLogContainer.appendChild(chatLog);

    var messageForm = document.createElement('form');
    messageForm.id = `messageForm-${roomId}`;
    messageForm.classList.add('messageForm');
    messageForm.innerHTML = `
        <input type="text" id="messageInput-${roomId}" placeholder="메시지를 작성해주세요" />
        <button type="button" onclick="sendMessage('${roomId}')">전송</button>
    `;
    chatLogContainer.appendChild(messageForm);

    fetchMessages(roomId); // Fetch and display all messages for this room
}

window.addEventListener('load', function () {
    connect();
});

function fetchMessages(roomId) {
    fetch('/chat/messages/' + roomId)
        .then((response) => response.json())
        .then((messages) => {
            messages.forEach((message) => showMessage(message, roomId));
        })
        .catch((error) => console.error('Error fetching messages:', error));
}

function sendMessage(roomId) {
    var messageContent = document.querySelector(`#messageInput-${roomId}`).value;
    var message = {
        sender: username,
        contents: messageContent,
        createdAt: new Date().toISOString(),
        roomId: roomId, // roomId를 올바르게 설정
    };

    stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
}

function showMessage(message, roomId) {
    var chatLog = document.querySelector('#chatLog-' + roomId); // Ensure each chat room has a unique chatLog element
    if (!chatLog) {
        // If no chat log exists for this room, create one dynamically
        chatLog = document.createElement('div');
        chatLog.id = 'chatLog-' + roomId;
        chatLog.classList.add('chatLog');
        var roomContainer = document.getElementById('roomsContainer');
        roomContainer.appendChild(chatLog);
    }
    var messageElement = document.createElement('div');
    messageElement.classList.add('message');
    messageElement.textContent = message.sender + ' : ' + message.contents;
    chatLog.appendChild(messageElement);
}

function createChatLog(roomId, messageCount) {
    var chatLogContainer = document.createElement('div');
    chatLogContainer.id = 'chatContainer-' + roomId;
    chatLogContainer.classList.add('chatContainer');
    var roomContainer = document.getElementById('roomsContainer');
    roomContainer.appendChild(chatLogContainer);

    var roomTitle = document.createElement('h2');
    roomTitle.textContent = 'Room: ' + roomId + ' (Messages: ' + messageCount + ')';
    chatLogContainer.appendChild(roomTitle);

    var chatLog = document.createElement('div');
    chatLog.id = 'chatLog-' + roomId;
    chatLog.classList.add('chatLog');
    chatLogContainer.appendChild(chatLog);

    var messageForm = document.createElement('form');
    messageForm.id = `messageForm-${roomId}`;
    messageForm.classList.add('messageForm');
    messageForm.innerHTML = `
                <input type="text" id="messageInput-${roomId}" placeholder="메시지를 작성해주세요" />
                <button type="button" onclick="sendMessage('${roomId}')">전송</button>
            `;
    chatLogContainer.appendChild(messageForm);

    fetchMessages(roomId);
}


window.addEventListener('load', function () {
    connect();
});
