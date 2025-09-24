// =======================
// 1. 선택 삭제 기능
// =======================
function deleteSelected() {
    console.log('deleteSelected 함수 호출됨');

    const checkedBoxes = document.querySelectorAll('.nurse-checkbox:checked');
    console.log('선택된 체크박스 개수:', checkedBoxes.length);

    if (checkedBoxes.length === 0) {
        alert('삭제할 항목을 선택해주세요.');
        return;
    }

    if (!confirm(`선택된 ${checkedBoxes.length}명의 간호사 정보를 삭제하시겠습니까?`)) {
        return;
    }

    // deleteForm 준비
    let form = document.getElementById('deleteForm');
    if (!form) {
        console.log('deleteForm 없음 - 새로 생성');
        form = document.createElement('form');
        form.id = 'deleteForm';
        form.method = 'POST';
        form.action = '/nurse/deleteMultiple';
        form.style.display = 'none';
        document.body.appendChild(form);
    }
    form.innerHTML = '';

    // CSRF 토큰 추가
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    if (csrfToken) {
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf';
        csrfInput.value = csrfToken;
        form.appendChild(csrfInput);
    } else {
        console.error('CSRF 토큰을 찾을 수 없음');
    }

    // 선택된 ID 추가
    checkedBoxes.forEach(cb => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'checkedIds';
        input.value = cb.value;
        form.appendChild(input);
    });

    console.log('폼 제출 시작');
    form.submit();
}

// =======================
// 2. DOM 로드 후 이벤트 바인딩
// =======================
document.addEventListener("DOMContentLoaded", function() {
    console.log('DOM 로드 완료');

    // (1) 선택 삭제 버튼
    const deleteBtn = document.getElementById('deleteSelectedBtn');
    if (deleteBtn) deleteBtn.addEventListener('click', deleteSelected);

    // (2) 체크박스 전체 선택/해제
    const checkAll = document.getElementById('checkAll');
    if (checkAll) {
        checkAll.addEventListener('change', function() {
            document.querySelectorAll('.nurse-checkbox')
                .forEach(cb => cb.checked = this.checked);
        });
    }

    // (3) 개별 삭제 버튼
    const deleteElements = document.getElementsByClassName("delete");
    Array.from(deleteElements).forEach(element => {
        element.addEventListener('click', function() {
            if (confirm("정말로 삭제하시겠습니까?")) {
                location.href = this.dataset.uri;
            }
        });
    });

    // (4) Navbar 토글
    const navbarToggle = document.querySelector('.navbar-toggle');
    if (navbarToggle) {
        navbarToggle.addEventListener('click', () => {
            const menubox = document.querySelector('.menubox_2');
            if (menubox) menubox.classList.toggle('active');
        });
    }

    // (5) 자격증 추가/취소 버튼
    const showBtn = document.getElementById("showLicenseFormBtn");
    const cancelBtn = document.getElementById("cancelLicenseFormBtn");
    const licenseForm = document.getElementById("newLicenseForm");
    const noMsg = document.getElementById("noLicenseMsg");
    const btnBox = document.getElementById("addLicenseBtnBox");

    if (showBtn && licenseForm && btnBox) {
        showBtn.addEventListener("click", () => {
            licenseForm.style.display = "block";
            btnBox.style.display = "none";
            if (noMsg) noMsg.style.display = "none";
            licenseForm.scrollIntoView({ behavior: "smooth" });
        });

        if (cancelBtn) {
            cancelBtn.addEventListener("click", () => {
                licenseForm.style.display = "none";
                btnBox.style.display = "block";
                if (noMsg) noMsg.style.display = "block";
                btnBox.scrollIntoView({ behavior: "smooth" });
            });
        }
    }

    // (6) 검색 & 페이지 이동
    const pageElements = document.getElementsByClassName("page-link");
    Array.from(pageElements).forEach(element => {
        element.addEventListener('click', function() {
            document.getElementById('page').value = this.dataset.page;
            document.getElementById('searchForm').submit();
        });
    });

    const btnSearch = document.getElementById("btn_search");
    if (btnSearch) {
        btnSearch.addEventListener('click', () => {
            const searchInput = document.getElementById('search_kw');
            const kwInput = document.getElementById('kw');
            const pageInput = document.getElementById('page');
            if (kwInput && searchInput) kwInput.value = searchInput.value;
            if (pageInput) pageInput.value = 0;
            document.getElementById('searchForm').submit();
        });
    }

    // (7) 로그인 폼 입력 검증
    const tabs = document.querySelectorAll('#myTab button[data-bs-toggle="tab"]');
    tabs.forEach(tab => {
        tab.addEventListener('shown.bs.tab', function (event) {
            const targetPane = document.querySelector(event.target.getAttribute('data-bs-target'));
            if (targetPane) {
                const firstInput = targetPane.querySelector('input[type="text"]');
                if (firstInput) firstInput.focus();
            }
        });
    });

    const loginForms = document.querySelectorAll('#guest-nav-home, #official-nav-home');
    loginForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const username = this.querySelector('input[name="username"]').value.trim();
            const password = this.querySelector('input[name="password"]').value.trim();
            if (!username || !password) {
                e.preventDefault();
                alert('아이디와 비밀번호를 입력해주세요.');
            }
        });
    });
});

// =======================
// 3. 전역 유틸 함수
// =======================

// 주소 팝업
function openRoadPopup() {
    const w = 900, h = 640;
    const left = (screen.width - w)/2, top = (screen.height - h)/2;
    window.open('/global/road/popup', 'roadPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
}
function setRoad(addrStr, roadCode, emdSeqNo) {
    const addr1Element = document.getElementById('addr1');
    if (addr1Element) addr1Element.value = addrStr || '';
}

// input number length 제한
function numberMaxLength(e){
    if(e.value.length > e.maxLength){
        e.value = e.value.slice(0, e.maxLength);
    }
}

// 국적 팝업
function openCountryPopup() {
    const w = 780, h = 620;
    const left = (screen.width - w) / 2;
    const top = (screen.height - h) / 2;
    window.open('/global/country_form', 'countryPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
}
function setCountry(iso2, countryKr) {
    const disp  = document.getElementById('nationalityDisplay');
    const input = document.getElementById('nationalityInput');
    const combined = `${countryKr} (${iso2})`;
    if (disp)  disp.textContent = combined;
    if (input) input.value = combined;
}

// =======================
// 이메일 유효성 검사
// =======================
function emailCheck(email_address) {
    email_address = email_address.trim().replace(/\s+/g, '');

    const email_regex = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/i;
    return email_regex.test(email_address);
}

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(e) {
        const emailInput = form.querySelector('[name="email"]'); // th:field로 바인딩된 input
        const email = emailInput.value;

        if (!emailCheck(email)) {
            e.preventDefault();
            alert('유효하지 않은 이메일 주소입니다.');
            emailInput.focus();

            // 입력 값에서 공백 제거 후 다시 넣기
            emailInput.value = email.trim().replace(/\s+/g, '');
        }
    });
});

// =======================
// 셀렉트 박스
// =======================
  document.querySelectorAll(".select-wrapper").forEach(wrapper => {
    const toggleBtn = wrapper.querySelector(".toggle_btn");
    const optionList = wrapper.querySelector(".selectbox_option");
    const hiddenInput = wrapper.querySelector("input[type=hidden]");

    // 드롭다운 열기/닫기
    toggleBtn.addEventListener("click", () => {
      optionList.classList.toggle("hide");
    });

    // 옵션 선택 시
    optionList.querySelectorAll(".option-btn").forEach(btn => {
      btn.addEventListener("click", () => {
        toggleBtn.textContent = btn.textContent;   // 선택된 값 보이기
        hiddenInput.value = btn.dataset.value;     // 폼에 값 저장
        optionList.classList.add("hide");          // 드롭다운 닫기

        // active 스타일 적용
        optionList.querySelectorAll(".option-btn").forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
      });
    });

    // 바깥 클릭 시 닫기
    document.addEventListener("click", e => {
      if (!wrapper.contains(e.target)) {
        optionList.classList.add("hide");
      }
    });
  });

// =======================
// 이메일 유효성 검사
// =======================
function isHyphenPhoneNumber(tel) {
    const regex = /^0\d{1,2}-\d{3,4}-\d{4}$/;
    return regex.test(tel);
}
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(e) {
        // 검사할 전화번호 input을 배열로
        const phoneFields = [
            form.querySelector('[name="tel"]'),
            form.querySelector('[name="emgcCntc"]')
        ];

        for (const input of phoneFields) {
            const value = input.value.trim();
            if (!isHyphenPhoneNumber(value)) {
                e.preventDefault(); // 제출 막기
                alert(`${input.previousElementSibling.textContent} 형식이 올바르지 않습니다.\n예: 010-1234-5678`);
                input.focus();
                return; // 첫 번째 오류만 처리
            }
        }
    });
});
    console.log(isHyphenPhoneNumber("010-1234-5678")); // true
    console.log(isHyphenPhoneNumber("02-123-4567"));   // true
    console.log(isHyphenPhoneNumber("031-123-4567"));  // true
    console.log(isHyphenPhoneNumber("01012345678"));   // false (하이픈 없음)