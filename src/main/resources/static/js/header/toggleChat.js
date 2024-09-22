document.addEventListener('DOMContentLoaded', function () {
   function toggleChat() {
      const chatContainer = document.getElementById('chatContainer');
      chatContainer.classList.toggle('hidden');
   }

   const inquiryChatBtn = document.getElementById('inquiry_chat_btn');
   if (inquiryChatBtn) {
      inquiryChatBtn.addEventListener('click', function () {
         toggleChat();
      });
   }
});
