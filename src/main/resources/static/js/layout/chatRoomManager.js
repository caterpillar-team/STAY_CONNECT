document.addEventListener('DOMContentLoaded', function () {
   function toggleChat() {
      const chatContainer = document.getElementById('chatContainer');
      const isHidden = chatContainer.classList.toggle('hidden');
      return !isHidden;
   }

   function toggleAdminChat() {
      const chatListContainer = document.getElementById('chatListContainer');
      const isHidden = chatListContainer.classList.toggle('hidden');
      return !isHidden;
   }

   const inquiryChatBtn = document.getElementById('inquiry_chat_btn');
   if (inquiryChatBtn) {
      inquiryChatBtn.addEventListener('click', function (event) {
         event.preventDefault();
         const isChatVisible = toggleChat();

         if (isChatVisible) {
            axios
               .post('/chatRoom')
               .then(function (response) {
                  console.log(response.data);
               })
               .catch(function (error) {
                  console.error(error);
               });
         }
      });
   }

   const chatListBtn = document.getElementById('chat_list_btn');
   if(chatListBtn) {
      chatListBtn.addEventListener('click', function (event) {
         event.preventDefault();
         const isChatVisible = toggleAdminChat();

         if (isChatVisible) {
            axios
               .get('/chatRoom')
               .then(function (response) {
                  console.log(response.data);
               })
               .catch(function (error) {
                  console.error(error);
               });
         }
      });
   }
});
