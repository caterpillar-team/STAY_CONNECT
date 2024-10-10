document.addEventListener('DOMContentLoaded', function () {
   const role = localStorage.getItem('role');
   if (role === 'ROLE_USER') {
      function toggleChat() {
         const chatContainer = document.getElementById('chatContainer');
         const isHidden = chatContainer.classList.toggle('hidden');
         return !isHidden;
      }

      const inquiryChatBtn = document.getElementById('inquiry_chat_btn');
      if (inquiryChatBtn) {
         inquiryChatBtn.addEventListener('click', function (event) {
            event.preventDefault();
            const isChatVisible = toggleChat();

            if (isChatVisible) {
               axios
                  .post('/api/user/chatRoom')
                  .then(function (response) {
                     console.log(response.data);
                  })
                  .catch(function (error) {
                     console.error(error);
                  });
            }
         });
      }
   } else if (role === 'ROLE_ADMIN') {
      function toggleAdminChat() {
         const ChatRoomListContainer = document.getElementById('ChatRoomListContainer');
         const isHidden = ChatRoomListContainer.classList.toggle('hidden');
         return !isHidden;
      }

      function fetchChatRoomList() {
         const ChatRoomListContainer = document.getElementById('ChatRoomListContainer');
         const isHidden = ChatRoomListContainer.classList.contains('hidden');

         if (!isHidden) {
            axios
               .get('/api/admin/chatRoom/list')
               .then(function (response) {
                  const chatRooms = response.data;
                  const assignedChatRoomList = document.getElementById('assignedChatRoomList');
                  const unassignedChatRoomList = document.getElementById('unassignedChatRoomList');

                  assignedChatRoomList.innerHTML = '';
                  unassignedChatRoomList.innerHTML = '';

                  const assignedChatRooms = chatRooms.assignedChatRooms || [];
                  const unassignedChatRooms = chatRooms.unassignedChatRooms || [];

                  assignedChatRooms.forEach(chatRoom => {
                     const li = document.createElement('li');
                     li.className = 'ChatRoomListItem';
                     li.innerHTML = `
               <span>${chatRoom.userName}</span>
               <a class="UnassignLink" th:href="@{/api/admin/unassignChatRoom/${chatRoom.id}}">할당 해제</a>
            `;
                     assignedChatRoomList.appendChild(li);
                  });

                  unassignedChatRooms.forEach(chatRoom => {
                     const li = document.createElement('li');
                     li.className = 'ChatRoomListItem';
                     li.innerHTML = `
               <span>${chatRoom.userName}</span>
               <a class="AssignLink" th:href="@{/api/admin/assignChatRoom/${chatRoom.id}}">할당</a>
            `;
                     unassignedChatRoomList.appendChild(li);
                  });
                  return true;
               })
               .catch(function (error) {
                  console.error('Error fetching chat room list:', error);
                  return false;
               });
         }
      }

      const chatListBtn = document.getElementById('chat_list_btn');
      if (chatListBtn) {
         chatListBtn.addEventListener('click', function (event) {
            event.preventDefault();
            toggleAdminChat();
            fetchChatRoomList();
         });
      }
   }
});
