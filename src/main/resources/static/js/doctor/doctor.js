    // 선택 삭제
  document.getElementById('deleteSelected').addEventListener('click', function() {
    const ids = Array.from(document.querySelectorAll('input[name="ids"]:checked')).map(cb => cb.value);
    if (ids.length === 0) {
      alert('삭제할 항목을 선택하세요.');
      return;
    }
    if (!confirm(ids.length + '건을 삭제하시겠습니까?')) return;

    const form = document.createElement('form');
    form.method = 'post';
    form.action = '/doctor/deleteSelected'; // ✅ 컨트롤러에서 처리
    ids.forEach(id => {
      const input = document.createElement('input');
      input.type = 'hidden';
      input.name = 'ids';
      input.value = id;
      form.appendChild(input);
    });
    document.body.appendChild(form);
    form.submit();
  });
    document.getElementById('selectAll').addEventListener('change', function(e) {
      const checked = e.target.checked;
      document.querySelectorAll('input[name="ids"]').forEach(cb => cb.checked = checked);
    });

    // doctor_edit.js

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
