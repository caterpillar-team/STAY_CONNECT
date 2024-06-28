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
                rooms.forEach((room) => {
                    if (!currentRooms[room.roomId]) { // 새로운 roomId인 경우에만 구독
                        currentRooms[room.roomId] = true;
                        createChatLog(room.roomId); // Create chat log for each room
                        stompClient.subscribe('/sub/chatroom/' + room.roomId, function(messageOutput){
                            showMessage(JSON.parse(messageOutput.body), room.roomId);
                        });
                    }
                });
            })
            .catch((error) => console.error('Error fetching rooms:', error));
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

        function createChatLog(roomId) {
            var chatLogContainer = document.createElement('div');
            chatLogContainer.id = 'chatContainer-' + roomId;
            chatLogContainer.classList.add('chatContainer');
            var roomContainer = document.getElementById('roomsContainer');
            roomContainer.appendChild(chatLogContainer);

            var roomTitle = document.createElement('h2');
            roomTitle.textContent = 'Room: ' + roomId;
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
        }

        window.addEventListener('load', function () {
            connect();
        });