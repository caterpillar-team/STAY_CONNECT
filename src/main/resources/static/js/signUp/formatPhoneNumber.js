// phoneFormatter.js
document.getElementById('phone').addEventListener('input', function (e) {
  var target = e.target;
  var phone = target.value.replace(/[^0-9]/g, '');
  var formattedPhone = '';

  if (phone.length < 4) {
    formattedPhone = phone;
  } else if (phone.length < 7) {
    formattedPhone = phone.substring(0, 3) + '-' + phone.substring(3);
  } else if (phone.length < 11) {
    formattedPhone =
      phone.substring(0, 3) +
      '-' +
      phone.substring(3, 6) +
      '-' +
      phone.substring(6);
  } else {
    formattedPhone =
      phone.substring(0, 3) +
      '-' +
      phone.substring(3, 7) +
      '-' +
      phone.substring(7, 11);
  }

  target.value = formattedPhone;
});
