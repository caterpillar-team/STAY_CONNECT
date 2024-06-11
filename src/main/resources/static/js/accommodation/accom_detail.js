// 별점
document.addEventListener('DOMContentLoaded', function() {
    const stars = document.querySelectorAll('.reviewInputRatingStar');
    const ratingInput = document.getElementById('ratingInput');
    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            ratingInput.value = index + 1;
            stars.forEach((s, i) => {
                if (i <= index) {
                    s.classList.remove('far');
                    s.classList.add('fas');
                } else {
                    s.classList.remove('fas');
                    s.classList.add('far');
                }
            });
        });
    });
});