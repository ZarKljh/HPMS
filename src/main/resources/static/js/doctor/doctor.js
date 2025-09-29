/* ========================================================================
 * HPMS - 공통 JS (리스트/에딧/팝업/유효성검사)  — v1.3 unified
 * - 요소가 없으면 자동으로 해당 블록을 건너뜁니다(안전).
 * - 숫자만 입력/유효성 검사(전화/사무실전화/비상/팩스/휴대폰/우편번호/
 *   상세주소[필수]/도로명주소[필수]/국적[필수]) 포함.
 * - join/edit 폼은 브라우저 기본 required 보다 먼저 검사되도록
 *   "제출 버튼 클릭" 시점 캡처 + submit 시점 이중 안전망을 적용.
 * ===================================================================== */

/* ========================================================================
 * 공통 유틸 / 숫자만 검증
 * ===================================================================== */
(function () {
  'use strict';

  // 숫자만 (하이픈 허용하려면: const ONLY_NUM = /^[0-9-]+$/;)
  const ONLY_NUM = /^\d+$/;

  // name 우선, 없으면 id로 찾기 (form 범위 지정 가능)
  function findField(fieldNameOrId, form) {
    if (!fieldNameOrId) return null;
    try {
      const sel = `[name="${fieldNameOrId}"], #${CSS.escape(fieldNameOrId)}`;
      return form ? form.querySelector(sel) : document.querySelector(sel);
    } catch {
      // CSS.escape 미지원 환경 대응: id 쿼리 최소한 시도
      return (form ? form.querySelector(`[name="${fieldNameOrId}"]`) : document.querySelector(`[name="${fieldNameOrId}"]`))
          || (form ? form.querySelector('#' + fieldNameOrId) : document.getElementById(fieldNameOrId));
    }
  }

  function resetBorder(el) {
    if (!el) return;
    try { el.style.borderColor = ''; } catch (_) {}
  }

  function markError(el) {
    if (!el) return;
    try { el.style.borderColor = 'red'; } catch (_) {}
    try { el.focus(); } catch (_) {}
  }

  // 실시간: 입력값을 숫자만으로 강제
  function attachDigitOnlyInput(fieldNameOrId) {
    const el = findField(fieldNameOrId);
    if (!el) return; // 필드 없으면 조용히 패스
    el.addEventListener('input', () => {
      const v = el.value || '';
      const fixed = v.replace(/\D+/g, '');
      if (v !== fixed) el.value = fixed;
    });
  }
  window.attachDigitOnlyInput = attachDigitOnlyInput; // 외부 사용 가능

  // 공용 숫자 검증기 (필수여부/라벨/폼범위)
  function numericCheck(fieldNameOrId, isRequired, label, form) {
    const el = findField(fieldNameOrId, form);
    if (!el) return true; // 필드 없으면 통과
    resetBorder(el);

    const v = (el.value || '').trim();
    if (isRequired && v === '') {
      alert(`${label}는(은) 필수입니다.`);
      markError(el);
      return false;
    }
    if (v !== '' && !ONLY_NUM.test(v)) {
      alert(`${label}는(은) 숫자만 입력하세요.`);
      markError(el);
      return false;
    }
    return true;
  }

  /* ---------------------------------------------------------------------
   * [필수] 상세주소: NULL/빈값 불가
   *   - 기본 name/id: detailedAddress
   * ------------------------------------------------------------------- */
  function validateDetailedAddressNotEmpty(fieldNameOrId = 'detailedAddress', form) {
    // 상세주소는 기본적으로 name="detailedAddress" (id='addr2'일 수 있음)
    let el = findField(fieldNameOrId, form);
    if (!el) el = findField('addr2', form); // 보조 id
    if (!el) return true; // 필드가 없으면 통과

    resetBorder(el);
    const v = (el.value || '').trim();
    if (v === '') {
      alert('상세주소는 필수입니다.');
      markError(el);
      return false;
    }
    return true;
  }
  window.validateDetailedAddressNotEmpty = validateDetailedAddressNotEmpty;

  /* ---------------------------------------------------------------------
   * [필수] 도로명주소: NULL/빈값 불가
   *   - 기본 name/id: defaultAddress
   *   - 보조 id: addr1
   * ------------------------------------------------------------------- */
  function validateDefaultAddressNotEmpty(fieldNameOrId = 'defaultAddress', form) {
    let el = findField(fieldNameOrId, form);
    if (!el) el = findField('addr1', form); // 보조 id
    if (!el) return true;

    resetBorder(el);
    const v = (el.value || '').trim();
    if (v === '') {
      alert('도로명주소는 필수입니다.');
      markError(el);
      return false;
    }
    return true;
  }
  window.validateDefaultAddressNotEmpty = validateDefaultAddressNotEmpty;

  /* ---------------------------------------------------------------------
   * [필수] 국적: NULL/빈값 불가
   *   - 기본 name: nationality (hidden)
   *   - 보조 id: nationalityInput
   * ------------------------------------------------------------------- */
  function validateNationalityNotEmpty(fieldNameOrId = 'nationality', form) {
    // hidden input(name=nationality) 또는 id=nationalityInput 을 우선 검사
    let el = findField(fieldNameOrId, form) || findField('nationalityInput', form);
    if (!el) return true;

    resetBorder(el);
    const v = (el.value || '').trim();
    if (v === '') {
      alert('국적은 필수입니다. [국적 검색]을 이용해 선택해 주세요.');
      // 표시 영역(span)도 있으면 함께 강조
      const disp = document.getElementById('nationalityDisplay');
      if (disp) { try { disp.style.border = '1px solid red'; } catch(_) {} }
      markError(el);
      return false;
    } else {
      const disp = document.getElementById('nationalityDisplay');
      if (disp) { try { disp.style.border = ''; } catch(_) {} }
    }
    return true;
  }
  window.validateNationalityNotEmpty = validateNationalityNotEmpty;

  /* ---------------------------------------------------------------------
   * 숫자 검증 래퍼 (필드명 기본값 포함)
   * - 전화번호/사무실전화: 필수 + 숫자
   * - 비상연락처/팩스/휴대폰/우편번호: 값 있으면 숫자
   * ------------------------------------------------------------------- */
  function validatePhoneNumberDigits(fieldNameOrId = 'telephone', form) {
    return numericCheck(fieldNameOrId, true, '전화번호', form);
  }
  function validateOfficePhoneDigits(fieldNameOrId = 'officeTelephone', form) {
    return numericCheck(fieldNameOrId, true, '사무실 전화', form);
  }
  function validateEmergencyDigits(fieldNameOrId = 'emergencyContact', form) {
    return numericCheck(fieldNameOrId, false, '비상연락처', form);
  }
  function validateFaxDigits(fieldNameOrId = 'faxNumber', form) {
    return numericCheck(fieldNameOrId, false, '팩스 번호', form);
  }
  function validateMobileDigits(fieldNameOrId = 'mobilePhone', form) {
    return numericCheck(fieldNameOrId, false, '휴대전화번호', form);
  }
  function validatePostcodeDigits(fieldNameOrId = 'postcode', form) {
    return numericCheck(fieldNameOrId, false, '우편번호', form);
  }

  // 전역 노출
  window.validatePhoneNumberDigits = validatePhoneNumberDigits;
  window.validateOfficePhoneDigits = validateOfficePhoneDigits;
  window.validateEmergencyDigits   = validateEmergencyDigits;
  window.validateFaxDigits         = validateFaxDigits;
  window.validateMobileDigits      = validateMobileDigits;
  window.validatePostcodeDigits    = validatePostcodeDigits;

  /* ---------------------------------------------------------------------
   * (호환용) form 인자를 받는 이전 인터페이스 유지
   * ------------------------------------------------------------------- */
  window.validateTelephoneNumeric = (form) => validatePhoneNumberDigits('telephone', form);
  window.validateEmergencyContactNumeric = (form) => validateEmergencyDigits('emergencyContact', form);
  window.validateFaxNumeric = (form, field) => validateFaxDigits(field || 'faxNumber', form);
  window.validateMobileNumeric = (form, field) => validateMobileDigits(field || 'mobilePhone', form);
  window.validatePostcodeNumeric = (form) => validatePostcodeDigits('postcode', form);

  // 한 번에 검사하고 싶을 때
  window.validateAllDoctorFields = function (form) {
    if (!validateDefaultAddressNotEmpty('defaultAddress', form))  return false; // 도로명주소(필수)
    if (!validateDetailedAddressNotEmpty('detailedAddress', form))return false; // 상세주소(필수)
    if (!validateNationalityNotEmpty('nationality', form))        return false; // 국적(필수)

    if (!validatePhoneNumberDigits('telephone', form))            return false; // 필수+숫자
    if (!validateOfficePhoneDigits('officeTelephone', form))      return false; // 필수+숫자
    if (!validateEmergencyDigits('emergencyContact', form))       return false; // 선택+숫자
    if (!validateFaxDigits('faxNumber', form))                    return false; // 선택+숫자
    if (!validateMobileDigits('mobilePhone', form))               return false; // 선택+숫자
    if (!validatePostcodeDigits('postcode', form))                return false; // 선택+숫자
    return true;
  };
})();

/* ========================================================================
 * 안전하게 페이지별 구역을 나눠 실행
 * ===================================================================== */
document.addEventListener('DOMContentLoaded', () => {
  /* ================================
   * 1) 리스트 페이지 전용(체크박스/선택 삭제)
   *  - 마스터 체크박스 id: #selectAll 또는 #checkAll 둘 다 지원
   *  - 항목 체크박스 name: ids 또는 checkedIds 둘 다 지원
   *  - 삭제 버튼 id: #deleteSelected 또는 #deleteSelectedBtn 둘 다 지원
   * ============================== */
  (function listPageBlock() {
    const $selectAll =
      document.getElementById('selectAll') ||
      document.getElementById('checkAll');
    const deleteBtn =
      document.getElementById('deleteSelected') ||
      document.getElementById('deleteSelectedBtn');

    if (!$selectAll && !deleteBtn) return;

    const itemSelectors = [
      'tbody input[type="checkbox"][name="ids"]',
      'tbody input[type="checkbox"][name="checkedIds"]'
    ];
    const getItems = () => itemSelectors.flatMap(sel => Array.from(document.querySelectorAll(sel)));
    const setAll = (checked) => getItems().forEach(cb => cb.checked = checked);

    function syncMaster() {
      const items = getItems();
      const total = items.length;
      const checked = items.filter(cb => cb.checked).length;
      if ($selectAll) {
        $selectAll.checked = (total > 0 && checked === total);
        $selectAll.indeterminate = (checked > 0 && checked < total);
      }
    }

    if ($selectAll) {
      $selectAll.addEventListener('click', (e) => {
        const items = getItems();
        const total = items.length;
        const checked = items.filter(cb => cb.checked).length;
        if ($selectAll.indeterminate || (checked > 0 && checked < total)) {
          e.preventDefault();
          $selectAll.indeterminate = false;
          $selectAll.checked = false;
          setAll(false);
        }
      });
      $selectAll.addEventListener('change', () => setAll($selectAll.checked));
    }

    document.addEventListener('change', (e) => {
      if (e.target.matches(itemSelectors.join(','))) syncMaster();
    });

    syncMaster();

    if (deleteBtn) {
      deleteBtn.addEventListener('click', function () {
        const items = getItems().filter(cb => cb.checked);
        if (!items.length) { alert('삭제할 항목을 선택하세요.'); return; }
        if (!confirm(items.length + '건을 삭제하시겠습니까?')) return;

        const paramName = items[0].name || 'ids';
        const existForm = document.getElementById('deleteForm');
        const form = existForm || document.createElement('form');
        if (!existForm) {
          form.method = 'post';
          form.action = '/doctor/deleteSelected';
        }

        Array.from(form.querySelectorAll(`input[name="${paramName}"]`)).forEach(n => n.remove());
        items.forEach(cb => {
          const input = document.createElement('input');
          input.type = 'hidden';
          input.name = paramName;
          input.value = cb.value;
          form.appendChild(input);
        });

        const tokenEl = document.querySelector('meta[name="_csrf"]');
        const paramEl = document.querySelector('meta[name="_csrf_parameter"]');
        const csrfParamName = (paramEl && paramEl.content) || '_csrf';
        const csrfToken = tokenEl && tokenEl.content;
        if (csrfToken) {
          Array.from(form.querySelectorAll(`input[name="${csrfParamName}"]`)).forEach(n => n.remove());
          const csrfInput = document.createElement('input');
          csrfInput.type = 'hidden';
          csrfInput.name = csrfParamName;
          csrfInput.value = csrfToken;
          form.appendChild(csrfInput);
        }

        if (!existForm) document.body.appendChild(form);
        form.submit();
      });
    }
  })();

  /* ================================
   * 2) 부모 페이지(doctor_edit 등) 전용: 팝업 SET 처리
   * ============================== */
  (function parentEditPageBlock() {
    const hasEditFields =
      document.getElementById('rank') || document.querySelector('[name="rank"]') ||
      document.getElementById('position') || document.querySelector('[name="position"]');

    if (!hasEditFields) return;

    // 팝업 → 본창 메시지 처리 (직급/직책 세팅)
    window.addEventListener('message', (e) => {
      if (!e?.data) return;
      if (e.data.source !== 'jobcode-popup') return;

      const { rankCode, positionCode } = e.data.payload || {};
      const rankEl = document.getElementById('rank') || document.querySelector('[name="rank"]');
      const posEl  = document.getElementById('position') || document.querySelector('[name="position"]');

      // 요구사항: "의과대학생" → "대학생"만 치환
      const normalizeKor = (val) =>
        (val && String(val).trim() === '의과대학생') ? '대학생' : val;

      if (rankEl) {
        rankEl.value = normalizeKor(rankCode ?? '');
        rankEl.dispatchEvent(new Event('input', { bubbles:true }));
        rankEl.dispatchEvent(new Event('change', { bubbles:true }));
      }
      if (posEl) {
        posEl.value = normalizeKor(positionCode ?? '');
        posEl.dispatchEvent(new Event('input', { bubbles:true }));
        posEl.dispatchEvent(new Event('change', { bubbles:true }));
      }
    });

    // 팝업 열기 전역 함수
    function openJobPopup() {
      const w = 700, h = 520;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/jobcode/popup', 'job_code',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }
    window.openJobPopup = openJobPopup;

    // 국적/도로명 세팅 콜백
    function setCountry(iso2, countryKr) {
      const disp = document.getElementById('nationalityDisplay');
      if (disp) disp.textContent = `${countryKr} (${iso2})`;
      const hidden = document.getElementById('nationalityInput') || findField('nationality');
      if (hidden) hidden.value = `${countryKr} (${iso2})`;
    }
    window.setCountry = setCountry;

    function setRoad(addrStr /*, roadCode, emdSeqNo */) {
      const addr = document.getElementById('addr1') || findField('defaultAddress');
      if (addr) addr.value = addrStr || '';
    }
    window.setRoad = setRoad;
  })();

  /* ================================
   * 3) 팝업 페이지 전용(잡코드/도로명/국적)
   * ============================== */
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
    window.applySelection = applySelection;

    // 도로명 검색/선택
    const rowsTbody = document.getElementById('rows');
    if (rowsTbody) {
      let currentPage = 0, totalPages = 0, pageSize = 10;

      function load(page) {
        const kw = (document.getElementById('kw')?.value) || '';
        fetch(`/global/road/search?kw=${encodeURIComponent(kw)}&page=${page}&size=${pageSize}`)
          .then(r => r.json())
          .then(data => {
            currentPage = data.number;
            totalPages  = data.totalPages;

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
              const choose = () => chooseRoad(addrStr, a);
              tr.ondblclick = choose;
              tr.querySelector('button').onclick = choose;
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

    function chooseCountry(iso2, countryKr) {
      if (window.opener && !window.opener.closed && typeof window.opener.setCountry === 'function') {
        window.opener.setCountry(iso2, countryKr);
      }
      window.close();
    }
    window.chooseCountry = chooseCountry;

    function chooseRoad(addrStr, a) {
      if (window.opener && !window.opener.closed) {
        window.opener.setRoad(addrStr, a.road_code || '', a.emd_seq_no || '');
      }
      window.close();
    }
    window.chooseRoad = chooseRoad;

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

  /* ================================
   * 4) 공통: 숫자만 입력 바인딩 + 폼 제출 시 검증
   *  - join/edit 등 POST 폼에 자동 적용
   *  - "제출 버튼 클릭" 캡처 + "submit" 이중 안전망
   * ============================== */
  (function commonValidationAutoBind() {
    // 실시간 숫자만 입력 제한 (존재하는 필드만 적용)
    [
      'telephone',       // 메인 전화 (필수)
      'officeTelephone', // 사무실 전화 (필수)
      'emergencyContact',// 비상연락처 (선택)
      'faxNumber',       // 팩스 (선택) — 필드명이 다르면 바꾸세요
      'mobilePhone',     // 휴대전화 (선택)
      'postcode'         // 우편번호 (선택)
    ].forEach(window.attachDigitOnlyInput);

    const forms = Array.from(document.querySelectorAll('form[method="post"]'));
    if (!forms.length) return;

    forms.forEach((form) => {
      function runDoctorChecks() {
        let ok = true;

        // ✅ 주소/국적 필수
        ok = window.validateDefaultAddressNotEmpty('defaultAddress', form) && ok;  // 도로명주소
        ok = window.validateDetailedAddressNotEmpty('detailedAddress', form) && ok; // 상세주소
        ok = window.validateNationalityNotEmpty('nationality', form) && ok;         // 국적

        // ✅ 숫자 검증
        ok = window.validatePhoneNumberDigits('telephone', form)         && ok; // 필수+숫자
        ok = window.validateOfficePhoneDigits('officeTelephone', form)   && ok; // 필수+숫자
        ok = window.validateEmergencyDigits('emergencyContact', form)    && ok; // 선택+숫자
        ok = window.validateFaxDigits('faxNumber', form)                 && ok; // 선택+숫자
        ok = window.validateMobileDigits('mobilePhone', form)            && ok; // 선택+숫자
        ok = window.validatePostcodeDigits('postcode', form)             && ok; // 선택+숫자

        return ok;
      }

      // (선택) 브라우저 기본 required 를 끄고 싶으면 주석 해제
      // form.setAttribute('novalidate', '');

      // 1) 제출 버튼 클릭 시점 — 캡처 단계에서 선제 검사
      const submitBtn = form.querySelector('button[type="submit"], input[type="submit"]');
      if (submitBtn) {
        submitBtn.addEventListener('click', function (e) {
          if (!runDoctorChecks()) {
            e.preventDefault();
            e.stopPropagation();
          }
        }, true); // capture
      }

      // 2) 최종 submit 시점 — 한 번 더 안전망
      form.addEventListener('submit', function (e) {
        if (!runDoctorChecks()) {
          e.preventDefault();
        }
      });
    });
  })();
});
