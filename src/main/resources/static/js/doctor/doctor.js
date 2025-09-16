// 안전하게 페이지별 구역을 나눠 실행
document.addEventListener('DOMContentLoaded', () => {
  // ================================
  // 1) 리스트 페이지 전용(체크박스/삭제)
  // ================================
  (function listPageBlock() {
    const $selectAll = document.getElementById('selectAll');
    const deleteBtn  = document.getElementById('deleteSelected');

    // 이 페이지(템플릿)에 해당 요소가 없으면 전체 블록 건너뜀
    if (!$selectAll && !deleteBtn) return;

    const itemSelector = 'tbody input[type="checkbox"][name="ids"]';
    const getItems = () => document.querySelectorAll(itemSelector);
    const setAll = (checked) => getItems().forEach(cb => cb.checked = checked);

    function syncMaster() {
      const items = getItems();
      const total = items.length;
      const checked = [...items].filter(cb => cb.checked).length;
      if ($selectAll) {
        $selectAll.checked = (total > 0 && checked === total);
        $selectAll.indeterminate = (checked > 0 && checked < total);
      }
    }

    if ($selectAll) {
      $selectAll.addEventListener('click', (e) => {
        const items = getItems();
        const total = items.length;
        const checked = [...items].filter(cb => cb.checked).length;

        if ($selectAll.indeterminate || (checked > 0 && checked < total)) {
          e.preventDefault();
          $selectAll.indeterminate = false;
          $selectAll.checked = false;
          setAll(false);
        }
      });

      $selectAll.addEventListener('change', () => {
        setAll($selectAll.checked);
      });
    }

    document.addEventListener('change', (e) => {
      if (e.target.matches(itemSelector)) syncMaster();
    });

    // 초기 동기화
    syncMaster();

    if (deleteBtn) {
      deleteBtn.addEventListener('click', function() {
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

        const csrfParamEl = document.querySelector('meta[name="_csrf_parameter"]');
        const csrfTokenEl = document.querySelector('meta[name="_csrf"]');
        if (csrfParamEl && csrfTokenEl) {
          const csrfInput = document.createElement('input');
          csrfInput.type = 'hidden';
          csrfInput.name = csrfParamEl.content;
          csrfInput.value = csrfTokenEl.content;
          form.appendChild(csrfInput);
        }

        document.body.appendChild(form);
        form.submit();
      });
    }
  })();

  // ================================
  // 2) 부모 페이지(doctor_edit) 전용: 팝업 SET 처리
  // ================================
  (function parentEditPageBlock() {
    // 이 페이지에 rank/position 필드가 하나도 없으면 건너뜀
    const hasEditFields =
      document.getElementById('rank') || document.querySelector('[name="rank"]') ||
      document.getElementById('position') || document.querySelector('[name="position"]');

    if (!hasEditFields) return;

    // 팝업 → 본창 메시지 처리
    window.addEventListener('message', (e) => {
      if (!e?.data) return;
      if (e.data.source !== 'jobcode-popup') return;

      const { rankCode, positionCode } = e.data.payload || {};
      const rankEl = document.getElementById('rank') || document.querySelector('[name="rank"]');
      const posEl  = document.getElementById('position') || document.querySelector('[name="position"]');

      if (rankEl) {
        rankEl.value = rankCode ?? '';
        rankEl.dispatchEvent(new Event('input', { bubbles:true }));
        rankEl.dispatchEvent(new Event('change', { bubbles:true }));
      }
      if (posEl) {
        posEl.value = positionCode ?? '';
        posEl.dispatchEvent(new Event('input', { bubbles:true }));
        posEl.dispatchEvent(new Event('change', { bubbles:true }));
      }
      console.log('[parent] message received:', e.data);
    });

    // 팝업 열기 API (인라인 onclick 사용하므로 전역 노출)
    function openJobPopup() {
      const w = 700, h = 520;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/jobcode/popup', 'job_code',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }
    window.openJobPopup = openJobPopup;

    // 국적/도로명 SET (부모에서 호출됨)
    function setCountry(iso2, countryKr) {
      const disp = document.getElementById('nationalityDisplay');
      if (disp) disp.textContent = `${countryKr} (${iso2})`;
      const hidden = document.getElementById('nationalityInput');
      if (hidden) hidden.value = `${countryKr} (${iso2})`;
    }
    window.setCountry = setCountry;

    function setRoad(addrStr /*, roadCode, emdSeqNo */) {
      const addr = document.getElementById('addr1');
      if (addr) addr.value = addrStr || '';
    }
    window.setRoad = setRoad;
  })();

  // ================================
  // 3) 팝업 페이지 전용(잡코드/도로명) - 요소 존재 시에만 실행
  // ================================
  (function popupPageBlock() {
    function makeOptionText(item) {
      const code = item.code ?? '';
      const kor  = item.korName ?? '';
      const eng  = item.engName ?? '';
      return `[${code}] ${kor} / ${eng}`;
    }
    async function fetchJson(url) {
      const res = await fetch(url);
      if (!res.ok) throw new Error(`${url} ${res.status}`);
      return res.json();
    }

    // 잡코드 팝업: 셀렉트가 있을 때만 로드
    const posSel = document.getElementById('positionSelect');
    if (posSel) {
      fetchJson('/global/jobcode/positions')
        .then(list => {
          posSel.innerHTML = '';
          (list || []).forEach(item => {
            const opt = document.createElement('option');
            opt.value = item.korName;
            opt.dataset.kor = item.korName;
            opt.textContent = makeOptionText(item);
            posSel.appendChild(opt);
          });
        })
        .catch(err => console.warn('직책 로드 실패:', err));
    }

    const rankSel = document.getElementById('rankSelect');
    if (rankSel) {
      fetchJson('/global/jobcode/ranks')
        .then(list => {
          rankSel.innerHTML = '';
          (list || []).forEach(item => {
            const opt = document.createElement('option');
            opt.value = item.korName;
            opt.dataset.kor = item.korName;
            opt.textContent = makeOptionText(item);
            rankSel.appendChild(opt);
          });
        })
        .catch(err => console.warn('직급 로드 실패:', err));
    }

    // 팝업에서 부모로 값 전송 (잡코드)
    function applySelection() {
      const rankOpt = document.getElementById('rankSelect')?.selectedOptions[0];
      const posOpt  = document.getElementById('positionSelect')?.selectedOptions[0];
      const rankKor     = rankOpt ? (rankOpt.dataset.kor || rankOpt.value) : '';
      const positionKor = posOpt  ? (posOpt.dataset.kor  || posOpt.value)  : '';

      window.opener?.postMessage({
        source: 'jobcode-popup',
        payload: { rankCode: rankKor, positionCode: positionKor }
      }, '*');

      window.close();
    }
    // 팝업에서 쓰려면 노출
    window.applySelection = applySelection;

    // 도로명 팝업
    const rowsTbody = document.getElementById('rows');
    if (rowsTbody) {
      let currentPage = 0, totalPages = 0, prevPage = 0, nextPage = 0, pageSize = 10;

      function load(page) {
        const kw = (document.getElementById('kw')?.value) || '';
        fetch(`/global/road/search?kw=${encodeURIComponent(kw)}&page=${page}&size=${pageSize}`)
          .then(r => r.json())
          .then(data => {
            currentPage = data.number;
            totalPages  = data.totalPages;
            prevPage = Math.max(0, currentPage - 1);
            nextPage = Math.min(totalPages - 1, currentPage + 1);

            const prevBtn = document.getElementById('prev');
            const nextBtn = document.getElementById('next');
            const pageInfo= document.getElementById('pageInfo');
            if (prevBtn) prevBtn.disabled = currentPage === 0;
            if (nextBtn) nextBtn.disabled = (currentPage >= totalPages - 1 || totalPages === 0);
            if (pageInfo) pageInfo.textContent = `${totalPages ? (currentPage+1) : 0}/${totalPages}`;

            rowsTbody.innerHTML = '';
            if (!data.content.length) {
              rowsTbody.innerHTML = `<tr><td colspan="6" style="text-align:center;">결과가 없습니다.</td></tr>`;
              return;
            }
            data.content.forEach(a => {
              const addrStr = [a.sido_kor, a.sgg_kor, a.emd_kor, a.road_name_kor].filter(Boolean).join(' ');
              const tr = document.createElement('tr');
              tr.innerHTML = `
                <td>${a.sido_kor ?? ''}</td>
                <td>${a.sgg_kor ?? ''}</td>
                <td>${a.emd_kor ?? ''}</td>
                <td>${a.road_name_kor ?? ''}</td>
                <td>${a.road_code ?? ''}</td>
                <td><button type="button">선택</button></td>`;
              tr.ondblclick = () => chooseRoad(addrStr, a);
              tr.querySelector('button').onclick = () => chooseRoad(addrStr, a);
              rowsTbody.appendChild(tr);
            });
          })
          .catch(e => {
            alert('검색 중 오류가 발생했습니다.');
            console.error(e);
          });
      }
      window.loadRoadPage = load; // 필요시 외부에서 호출
    }

    // 국적 팝업 콜백 (팝업에서 호출)
    function chooseCountry(iso2, countryKr) {
      if (window.opener && !window.opener.closed && typeof window.opener.setCountry === 'function') {
        window.opener.setCountry(iso2, countryKr);
      }
      window.close();
    }
    window.chooseCountry = chooseCountry;

    // 도로명 팝업 콜백 (팝업에서 호출)
    function chooseRoad(addrStr, a) {
      if (window.opener && !window.opener.closed) {
        window.opener.setRoad(addrStr, a.road_code || '', a.emd_seq_no || '');
      }
      window.close();
    }
    window.chooseRoad = chooseRoad;

    // 팝업 열기(부모에서도 쓸 수 있게 전역 노출)
    function openCountryPopup() {
      const w = 780, h = 620;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/country_form', 'countryPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }
    window.openCountryPopup = openCountryPopup;

    function openRoadPopup() {
      const w = 900, h = 640;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/road/popup', 'roadPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }
    window.openRoadPopup = openRoadPopup;
  })();
});
