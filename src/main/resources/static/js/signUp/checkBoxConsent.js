document.addEventListener('DOMContentLoaded', () => {
  const mainCheckbox = document.getElementById('id_a');
  const checkbox1 = document.getElementById('id_b');
  const checkbox2 = document.getElementById('id_c');
  const checkbox3 = document.getElementById('id_d');
  const checkbox4 = document.getElementById('id_e');

  // 메인 체크박스 클릭 이벤트 리스너
  mainCheckbox.addEventListener('click', (event) => {
    const isChecked = event.currentTarget.checked;
    checkbox1.checked = isChecked;
    checkbox2.checked = isChecked;
    checkbox3.checked = isChecked;
    checkbox4.checked = isChecked;
  });

  // 개별 체크박스가 해제될 때 메인 체크박스도 해제
  const individualCheckHandler = (event) => {
    if (!event.currentTarget.checked) {
      mainCheckbox.checked = false;
    }
  };

  checkbox1.addEventListener('click', individualCheckHandler);
  checkbox2.addEventListener('click', individualCheckHandler);
  checkbox3.addEventListener('click', individualCheckHandler);
  checkbox4.addEventListener('click', individualCheckHandler);
});
