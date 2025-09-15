   // ✅ 현재 페이지 테이블의 체크박스만 대상으로 제한
   const $selectAll = document.getElementById('selectAll');
   const itemSelector = 'tbody input[type="checkbox"][name="ids"]';
   const getItems = () => document.querySelectorAll(itemSelector);

   function setAll(checked) { getItems().forEach(cb => cb.checked = checked); }

   function syncMaster() {
     const items = getItems();
     const total = items.length;
     const checked = [...items].filter(cb => cb.checked).length;
     $selectAll.checked = (total > 0 && checked === total);
     $selectAll.indeterminate = (checked > 0 && checked < total); // 부분 선택 표시
   }

   // ✅ 전체선택 클릭 시: 부분 선택 상태면 "전부 해제"로 동작하게
   $selectAll.addEventListener('click', (e) => {
     const items = getItems();
     const total = items.length;
     const checked = [...items].filter(cb => cb.checked).length;

     if ($selectAll.indeterminate || (checked > 0 && checked < total)) {
       // 부분 선택 상태 → 한 번에 전부 해제
       e.preventDefault(); // 기본 토글 막고 직접 처리
       $selectAll.indeterminate = false;
       $selectAll.checked = false;
       setAll(false);
     }
     // 그 외에는 change 핸들러가 처리 (전부 선택/전부 해제)
   });

   // ✅ 전체선택 change: 체크 여부로 전부 선택/해제
   $selectAll.addEventListener('change', () => {
     setAll($selectAll.checked);
   });

   // ✅ 개별 체크 변화에 따라 마스터 상태 동기화
   document.addEventListener('change', (e) => {
     if (e.target.matches(itemSelector)) syncMaster();
   });

   // 초기 동기화
   syncMaster();

   // 👇 기존 다중삭제 버튼 JS는 유지하되, 선택자만 더 안전하게
   document.getElementById('deleteSelected').addEventListener('click', function() {
     const ids = [...document.querySelectorAll(itemSelector)]
       .filter(cb => cb.checked)
       .map(cb => cb.value);

     if (!ids.length) { alert('삭제할 항목을 선택하세요.'); return; }
     if (!confirm(ids.length + '건을 삭제하시겠습니까?')) return;

     const form = document.createElement('form');
     form.method = 'post';
     form.action = '/doctor/deleteSelected';

     ids.forEach(id => {
       const input = document.createElement('input');
       input.type = 'hidden';
       input.name = 'ids';
       input.value = id;
       form.appendChild(input);
     });

     // 🔐 CSRF hidden 추가(레이아웃 head에 메타가 있다고 가정)
     const csrfParam = document.querySelector('meta[name="_csrf_parameter"]').content;
     const csrfToken = document.querySelector('meta[name="_csrf"]').content;
     const csrfInput = document.createElement('input');
     csrfInput.type = 'hidden';
     csrfInput.name = csrfParam;
     csrfInput.value = csrfToken;
     form.appendChild(csrfInput);

     document.body.appendChild(form);
     form.submit();
   });

    // 국적 팝업 열기
    function openCountryPopup() {
      const w = 780, h = 620;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/country_form', 'countryPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 국적 선택 콜백 (팝업에서 window.opener.setCountry 호출)
    function setCountry(iso2, countryKr) {
      const disp = document.getElementById('nationalityDisplay');
      if (disp) disp.textContent = `${countryKr} (${iso2})`;

      const hidden = document.getElementById('nationalityInput');
      if (hidden) hidden.value = `${countryKr} (${iso2})`;
    }

    // 도로명 주소 팝업
    function openRoadPopup() {
      const w = 900, h = 640;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/road/popup', 'roadPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 도로명 선택 콜백
    function setRoad(addrStr /*, roadCode, emdSeqNo */) {
      const addr = document.getElementById('addr1');
      if (addr) addr.value = addrStr || '';
    }

    // 직급/직책 팝업
    function openJobPopup() {
      const w = 700, h = 520;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/jobcode/popup', 'job_code',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 팝업 -> 본창 메시지 처리
    window.addEventListener('message', (e) => {
      if (e.data?.source !== 'jobcode-popup') return;
      const { rankCode, positionCode } = e.data.payload || {};

      const rankInput = document.getElementById('rank');
      if (rankInput) rankInput.value = rankCode ?? '';

      const positionInput = document.getElementById('position');
      if (positionInput) positionInput.value = positionCode ?? '';
    });

    // 전역 사용을 위해 노출
    window.openCountryPopup = openCountryPopup;
    window.setCountry = setCountry;
    window.openRoadPopup = openRoadPopup;
    window.setRoad = setRoad;
    window.openJobPopup = openJobPopup;
