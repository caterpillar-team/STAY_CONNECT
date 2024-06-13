document.addEventListener('DOMContentLoaded', function () {
  const city1 = document.querySelector('.city1');
  const city2 = document.querySelector('.city2');
  const boxes1 = document.querySelectorAll('.city1 .box');
  const boxes2 = document.querySelectorAll('.city2 .box');

  const showBoxes = () => {
    const triggerBottom = window.innerHeight * 0.8;
    const city1Top = city1.getBoundingClientRect().top;
    if (city1Top < triggerBottom) {
      boxes1.forEach((box) => box.classList.add('show'));
    }

    const city2Top = city2.getBoundingClientRect().top;
    if (city2Top < triggerBottom) {
      boxes2.forEach((box) => box.classList.add('show'));
    }
  };

  window.addEventListener('scroll', showBoxes);
  showBoxes();
});

document.querySelectorAll('.city1 .box a').forEach(function (anchor) {
  anchor.addEventListener('click', function (event) {
    event.preventDefault();
    window.scrollTo(0, document.body.scrollHeight);
  });
});

document.querySelectorAll('.city2 .box a').forEach(function (anchor) {
  anchor.addEventListener('click', function (event) {
    event.preventDefault();
    window.scrollTo(0, document.body.scrollHeight);
  });
});

document.querySelectorAll('.btn_search').forEach(function (anchor) {
  anchor.addEventListener('click', function (event) {
    event.preventDefault();
    window.scrollTo(0, document.body.scrollHeight);
  });
});
