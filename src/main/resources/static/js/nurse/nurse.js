document.addEventListener("DOMContentLoaded", function() {

  // =======================
  // 1. Navbar 토글
  // =======================
  const navbarToggle = document.querySelector('.navbar-toggle');
  if (navbarToggle) {
    navbarToggle.addEventListener('click', () => {
      const menubox = document.querySelector('.menubox_2');
      if (menubox) menubox.classList.toggle('active');
    });
  }

  // =======================
  // 2. 삭제 버튼 확인
  // =======================
  const deleteElements = document.getElementsByClassName("delete");
  Array.from(deleteElements).forEach(element => {
    element.addEventListener('click', function() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        location.href = this.dataset.uri;
      }
    });
  });

  // =======================
  // 3. 자격증 추가 폼
  // =======================
  const showBtn = document.getElementById("showLicenseFormBtn");
  const cancelBtn = document.getElementById("cancelLicenseFormBtn");
  const form = document.getElementById("newLicenseForm");
  const noMsg = document.getElementById("noLicenseMsg");
  const btnBox = document.getElementById("addLicenseBtnBox");

  if (showBtn && form && btnBox) {
    showBtn.addEventListener("click", () => {
      form.style.display = "block";
      btnBox.style.display = "none";
      if (noMsg) noMsg.style.display = "none";
      form.scrollIntoView({ behavior: "smooth", block: "start" });
    });

    cancelBtn.addEventListener("click", () => {
      form.style.display = "none";
      btnBox.style.display = "block";
      if (noMsg) noMsg.style.display = "block";
      btnBox.scrollIntoView({ behavior: "smooth", block: "center" });
    });
  }

  // =======================
  // 4. 검색 & 페이지 이동
  // =======================
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
      document.getElementById('kw').value = document.getElementById('search_kw').value;
      document.getElementById('page').value = 0;
      document.getElementById('searchForm').submit();
    });
  }

  // =======================
  // 5. 로그인 탭 & 폼
  // =======================
  const tabs = document.querySelectorAll('#myTab button[data-bs-toggle="tab"]');
    tabs.forEach(tab => {
      tab.addEventListener('shown.bs.tab', function (event) {
        const targetId = event.target.getAttribute('data-bs-target');
        const targetPane = document.querySelector(targetId);
        if (targetPane) {
          const firstInput = targetPane.querySelector('input[type="text"]');
          if (firstInput) {
            firstInput.focus();
          }
        }
      });
    });

    // 폼 제출 시 입력 검증 (HTML5 required 속성으로 대체 가능)
    const loginForms = document.querySelectorAll('#guest-nav-home, #official-nav-home');
    loginForms.forEach(form => {
      form.addEventListener('submit', function (e) {
        const username = this.querySelector('input[name="username"]').value.trim();
        const password = this.querySelector('input[name="password"]').value.trim();

        if (!username || !password) {
          e.preventDefault();
          alert('아이디와 비밀번호를 입력해주세요.');
          return false;
        }
      });
    });
});

//주소
function openRoadPopup() {
    const w = 900, h = 640;
    const left = (screen.width - w)/2, top = (screen.height - h)/2;
    window.open('/global/road/popup', 'roadPopup',
      `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
  }

  // 팝업에서 호출: 값만 채움 (저장은 최종 "수정 저장"에서 함께)
  function setRoad(addrStr, roadCode, emdSeqNo) {
    // 기본주소에 도로명 기반 주소를 채우고, 상세주소는 사용자가 이어서 입력
    document.getElementById('addr1').value = addrStr || '';
    // 필요하면 숨김 필드로 roadCode/emdSeqNo 따로 보관 가능
    // 예: <input type="hidden" name="roadCode" id="roadCodeHidden"> 등을 미리 만들어 두고 값만 주입
    // document.getElementById('roadCodeHidden').value = roadCode || '';
    // document.getElementById('emdSeqHidden').value  = emdSeqNo || '';
  }

// input number length
function numberMaxLength(e){
    if(e.value.length > e.maxLength){
        e.value = e.value.slice(0, e.maxLength);
    }
}

// 국적
function openCountryPopup() {
    const w = 780, h = 620;
    const left = (screen.width - w) / 2;
    const top = (screen.height - h) / 2;
    window.open('/global/country_form', 'countryPopup',
      `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
  }
  // 팝업에서 호출 (자동 저장 금지, 값만 세팅)
  function setCountry(iso2, countryKr) {
    const disp = document.getElementById('nationalityDisplay');
    if (disp) disp.textContent = `${countryKr} (${iso2})`;

    const hidden = document.getElementById('nationalityInput');
    if (hidden) hidden.value = `${countryKr} (${iso2})`;
  }
  // 팝업에서 호출: 값만 채움 (저장은 최종 "등록"에서 함께)
      function setCountry(iso2, countryKr) {
        const disp  = document.getElementById('nationalityDisplay');
        const input = document.getElementById('nationalityInput');
        const combined = `${countryKr} (${iso2})`;
        if (disp)  disp.textContent = combined;
        if (input) input.value = combined; // ★ 서버에는 "대한민국 (KR)" 로 저장
      }