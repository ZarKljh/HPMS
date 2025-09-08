
    function submitForm(event) {
        event.preventDefault(); // 링크 기본 동작 막기
        document.getElementById('registrationForm').submit(); // 폼 전송
    }

    function submitDeleteForm(id, page, size) {
        if (!confirm('정말 삭제하시겠습니까?')) {
            return; // 사용자가 취소하면 아무 동작 없음
        }

        const form = document.getElementById('deleteForm');
        form.querySelector('input[name="id"]').value = id;
        form.querySelector('input[name="page"]').value = page;
        form.querySelector('input[name="size"]').value = size;
        form.submit();
    }

    /* 목록화면에서 모든 체크박스 선택하기 시작 */
  /*document.addEventListener("DOMContentLoaded", function () {
    const form = document.forms["selectedPersonnel"];
    const masterCheckbox = form.querySelector('input[name="checkedAllIds"]');
    const checkboxes = form.querySelectorAll('input[name="checkedIds"]');

    // 전체 선택 기능
    masterCheckbox.addEventListener("change", function () {
      checkboxes.forEach(cb => cb.checked = masterCheckbox.checked);
    });

    // 제출 시 체크 여부 확인
    form.addEventListener("submit", function (e) {
      const anyChecked = [...checkboxes].some(cb => cb.checked);
      if (!anyChecked) {
        e.preventDefault(); // 제출 막기
        alert("하나 이상의 항목을 선택해주세요.");
      }
    });
  });*/
  // checkedAllIds 선택시 모든 체크박스 선택/해제 하기
document.addEventListener("DOMContentLoaded", function () {
  const masterCheckbox = document.querySelector('input[name="checkedAllIds"]');
  const checkboxes = document.querySelectorAll('input[name="checkedIds"]');

  if (masterCheckbox) {
    masterCheckbox.addEventListener("change", function () {
      checkboxes.forEach(function (cb) {
        cb.checked = masterCheckbox.checked;
      });
    });
  }
});

/* 제한된 체크박스 확인 후, 폼 COMMIT하기 */
function submitFormIfValid() {
  const form = document.forms["selectedPersonnel"];
  const checkboxes = form.querySelectorAll('input[name="checkedIds"]');
  const anyChecked = [...checkboxes].some(cb => cb.checked);

  if (!anyChecked) {
    alert("하나 이상의 항목을 선택해주세요.");
    return;
  }

  form.submit(); // 이제는 조건을 만족했을 때만 submit
}
