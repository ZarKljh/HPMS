
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
/*
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

*/
/* 제한된 체크박스 확인 후, 폼 COMMIT하기 */
/*

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
*/
   /* 목록화면에서 모든 체크박스 선택하기 시작 - update version */
document.addEventListener("DOMContentLoaded", function () {
  const form = document.forms["selectedPersonnel"];
  if(!form) return;
  const masterCheckbox = form.querySelector('input[name="checkedAllIds"]');
  const checkboxes = form.querySelectorAll('input[name="checkedIds"]');

  if (masterCheckbox) {
    // 전체 선택 기능
    masterCheckbox.addEventListener("change", function () {
      checkboxes.forEach(cb => cb.checked = masterCheckbox.checked);
    });

    // 개별 체크박스 상태 변화 감지 → 전체 선택 상태 업데이트
    checkboxes.forEach(cb => {
      cb.addEventListener("change", function () {
        const allChecked = [...checkboxes].every(c => c.checked);
        masterCheckbox.checked = allChecked;
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

  form.submit();
}

function personnel_registration(event){
    event.preventDefault(); // 폼 제출 막기
    //const txtNationality = document.forms['registration'].nationality.value;
/*        console.log(document.forms['registration']); // 폼 객체 확인
        console.log(document.forms['registration'].nationality); // 필드 객체 확인
        console.log(document.forms['registration'].nationality.value); // 값 확인
        console.log(document.forms['registration'].nationality.value); // 값 확인
        console.log(document.forms['registration'].lastName.value); // 값 확인
        console.log(document.forms['registration'].firstName.value); // 값 확인*/

    const form = document.forms['registration'];

    // const lastName = form.lastName.value.trim();  // 앞뒤 공백 제거
    // const firstName = form.firstName.value.trim();

    const firstName = document.forms['registration'].firstName.value.trim();
    const lastName = document.forms['registration'].lastName.value.trim();
    const nameRegex = /^[가-힣a-zA-Z]+$/;

    if (!nameRegex.test(firstName)) {
        alert("이름은 한글 또는 영문만 입력해주세요.");
    }
    if (firstName.length < 1) {
        alert("성은 필수입력 대상입니다.");
        return;
    }

    if (!nameRegex.test(lastName)) {
        alert("이름은 한글 또는 영문만 입력해주세요.");
    }
    if (lastName.length < 1) {
        alert("이름은 필수 입력대상입니다.");
        return;
    }

    const newEmail = document.forms['registration'].email.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const atCount = (newEmail.match(/@/g) || []).length;
    console.log("이메일:", newEmail);

    if (newEmail.length > 0) {
        if (!emailRegex.test(newEmail)) {
            alert(newEmail + "은 정상적인 이메일 주소가 아닙니다.");
            return false;
        }
        if (atCount !== 1) {
            alert(newEmail + " 은 '@' 기호가 정확히 한 번 포함되어야 합니다.");
            return false;
        }
    }

    const cellPhone = document.forms['registration'].cellPhone.value.trim();
    // 숫자만 있는지 확인하는 정규표현식
    const numberOnlyRegex = /^[0-9]{10,12}$/;

    if (cellPhone.length === 0) {
        alert("휴대전화번호는 필수 입력 항목입니다.");
        return false;
    }

    if (!numberOnlyRegex.test(cellPhone)) {
        alert("휴대전화번호는 숫자만으로 10~12자리여야 합니다.");
        return false;
    }

    document.forms['registration'].submit();

    //alert(txtNationality);
}
function personnel_update(event){
    //onclick="document.forms['updatePersonnel'].submit(); return false;"
    event.preventDefault(); // 폼 제출 막기

        const form = document.forms['updatePersonnel'];

        // const lastName = form.lastName.value.trim();  // 앞뒤 공백 제거
        // const firstName = form.firstName.value.trim();

        const firstName = document.forms['updatePersonnel'].firstName.value.trim();
        const lastName = document.forms['updatePersonnel'].lastName.value.trim();
        const nameRegex = /^[가-힣a-zA-Z]+$/;
/*
        console.log(firstName);
        console.log(lastName);
*/
    if (!nameRegex.test(firstName)) {
        alert("이름은 한글 또는 영문만 입력해주세요.");
    }
    if (firstName.length < 1) {
        alert("성은 필수입력 대상입니다.");
        return;
    }

    if (!nameRegex.test(lastName)) {
        alert("이름은 한글 또는 영문만 입력해주세요.");
    }
    if (lastName.length < 1) {
        alert("이름은 필수 입력대상입니다.");
        return;
    }

    const newEmail = document.forms['updatePersonnel'].email.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const atCount = (newEmail.match(/@/g) || []).length;
    console.log("이메일:", newEmail);
    if (newEmail.length > 0) {
        if (!emailRegex.test(newEmail)) {
            alert(newEmail + "은 정상적인 이메일 주소가 아닙니다.");
            return false;
        }
        if (atCount !== 1) {
            alert(newEmail + " 은 '@' 기호가 정확히 한 번 포함되어야 합니다.");
            return false;
        }
    }
    const cellPhone = document.forms['updatePersonnel'].cellPhone.value.trim();
    // 숫자만 있는지 확인하는 정규표현식
    const numberOnlyRegex = /^[0-9]{10,12}$/;

    if (cellPhone.length === 0) {
        alert("휴대전화번호는 필수 입력 항목입니다.");
        return false;
    }

    if (!numberOnlyRegex.test(cellPhone)) {
        alert("휴대전화번호는 숫자만으로 10~12자리여야 합니다.");
        return false;
    }

    document.forms['updatePersonnel'].submit();
}