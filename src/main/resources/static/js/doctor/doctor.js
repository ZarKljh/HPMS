/* ============================================================================
 * DoctorM / DoctorDTL 폼 유효성 검사 (엔티티 @NotNull/@NotBlank 기준)
 * - 한 번만 alert (모아 보여주기) + 각 필드 옆 인라인 에러 + 빨간 * 표시
 * - 전화/사무실전화/비상/우편번호는 숫자만 입력 강제
 * - 날짜 합리성(1900-01-01 ~ 오늘) 기본 포함
 * - edit/join 공용 (페이지마다 있는 필드만 자동 인식)
 * ========================================================================== */
(function () {
  'use strict';

  /* -----------------------------------------
   * 공용 유틸
   * --------------------------------------- */
  const ONLY_NUM = /^\d+$/;
  const DATE_MIN = new Date('1900-01-01');
  const TODAY = new Date(); TODAY.setHours(0,0,0,0);

  // name 우선, 없으면 id로도 찾기
  function findField(nameOrId, root) {
    if (!nameOrId) return null;
    const scope = root || document;
    try {
      const sel = `[name="${nameOrId}"], #${CSS.escape(nameOrId)}`;
      return scope.querySelector(sel);
    } catch {
      return scope.querySelector(`[name="${nameOrId}"]`) || scope.getElementById(nameOrId);
    }
  }

  function ensureErrorSpan(input) {
    // input 옆에 .field-error <small> 하나 보유
    let holder = input.closest('.input-list') || input.parentElement || input;
    let err = holder.querySelector('.field-error');
    if (!err) {
      err = document.createElement('small');
      err.className = 'field-error';
      err.style.cssText = 'color:#d32f2f; margin-left:8px;';
      holder.appendChild(err);
    }
    return err;
  }

  function clearError(input) {
    if (!input) return;
    try { input.style.borderColor = ''; } catch(_) {}
    const err = (input.closest('.input-list') || input.parentElement || input).querySelector('.field-error');
    if (err) err.textContent = '';
  }

  function setError(input, message) {
    if (!input) return;
    try { input.style.borderColor = '#d32f2f'; } catch(_) {}
    const err = ensureErrorSpan(input);
    err.textContent = message || '';
  }

  function addRequiredAsterisk(input) {
    if (!input) return;
    // 이미 붙였으면 패스
    const holder = input.closest('.input-list') || input.parentElement || input;
    let star = holder.querySelector('.required-star');
    if (!star) {
      star = document.createElement('span');
      star.className = 'required-star';
      star.textContent = ' *';
      star.style.cssText = 'color:#d32f2f; margin-left:4px; font-weight:700;';
      // label이 있으면 label 다음에, 없으면 input 뒤에
      const label = holder.querySelector('label');
      if (label) {
        label.appendChild(star);
      } else {
        input.insertAdjacentElement('afterend', star);
      }
    }
  }

  function attachDigitOnlyInput(input) {
    if (!input) return;
    input.addEventListener('input', () => {
      const v = input.value || '';
      const fixed = v.replace(/\D+/g, '');
      if (v !== fixed) input.value = fixed;
    });
  }

  function parseDate(value) {
    if (!value) return null;
    const d = new Date(value);
    return isNaN(d.getTime()) ? null : d;
  }

  /* -----------------------------------------
   * 필수 필드(엔티티 @NotNull/@NotBlank) 정의
   * - 페이지에 실제 존재하는 필드만 검사됨
   * - 필요 시 data-optional="true" 로 특정 페이지에서 면제 가능
   * --------------------------------------- */
  const REQUIRED_FIELDS = [
    // DoctorM (@NotBlank/@NotNull)
    { name: 'department',        label: '부서',              type: 'text',   max: 4 },
    { name: 'rank',              label: '직급',              type: 'text',   max: 4 },
    { name: 'firstName',         label: '이름',              type: 'text',   max: 50 },
    { name: 'lastName',          label: '성',                type: 'text',   max: 50 },
    { name: 'gender',            label: '성별',              type: 'radio' },
    { name: 'dateOfBirth',       label: '생년월일',          type: 'date' },
    { name: 'telephone',         label: '전화번호',          type: 'digits', max: 20 },
    { name: 'hireDate',          label: '입사일',            type: 'date' },
    { name: 'status',            label: '상태',              type: 'text',   max: 3 },
    { name: 'workType',          label: '근무형태',          type: 'text',   max: 4 },

    // DoctorDTL (@NotBlank/@NotNull)
    { name: 'image',             label: '이미지',            type: 'text',   max: 250 },
    { name: 'userId',            label: 'User ID',           type: 'text',   max: 20 },
    { name: 'userPassword',      label: 'Password',          type: 'text',   max: 100 },
    { name: 'officeTelephone',   label: '사무실 전화',       type: 'digits', max: 20 },
    { name: 'registrationNumber',label: '면허번호',          type: 'text',   max: 10 },
    { name: 'major',             label: '전공과',            type: 'text',   max: 50 },
    // createDate, writer 는 화면에서 자동세팅/서버세팅일 확률이 높아 기본 제외
  ];

  // 숫자 전용(선택입력) – 값이 있으면 숫자여야 함
  const DIGIT_ONLY_OPTIONAL = [
    { name: 'emergencyContact',  label: '비상연락처', max: 20 },
    { name: 'postcode',          label: '우편번호',   max: 6  }
  ];

  // 주소 필수 정책이 있다면 여기에 켜기
  const ADDRESS_REQUIRED = [
    // { name: 'defaultAddress',   label: '도로명주소',  type: 'text', max: 250 },
    // { name: 'detailedAddress',  label: '상세주소',    type: 'text', max: 250 },
  ];

  /* -----------------------------------------
   * 필수 * 표시 부착
   * --------------------------------------- */
  function decorateRequiredStars(form) {
    [...REQUIRED_FIELDS, ...ADDRESS_REQUIRED].forEach(f => {
      const input = findField(f.name, form);
      if (input && input.dataset.optional !== 'true') addRequiredAsterisk(input);
    });
  }

  /* -----------------------------------------
   * 본 검증
   * --------------------------------------- */
  function validateDoctorForm(e) {
    if (e) e.preventDefault();
    const form = e ? e.target : document.querySelector('form');
    if (!form) return true;

    let firstErrorInput = null;
    const alertMessages = [];
    const pushError = (input, msg) => {
      if (!firstErrorInput) firstErrorInput = input;
      setError(input, msg);
      alertMessages.push('• ' + msg);
    };

    // 먼저 전체 에러 초기화
    Array.from(form.querySelectorAll('input,select,textarea')).forEach(clearError);

    // 1) 엔티티 기반 필수
    REQUIRED_FIELDS.forEach(f => {
      const input = findField(f.name, form);
      if (!input) return;
      if (input.dataset.optional === 'true') return; // 페이지에서 면제

      if (f.type === 'radio') {
        const radios = form.querySelectorAll(`input[type="radio"][name="${f.name}"]`);
        const checked = Array.from(radios).some(r => r.checked);
        if (!checked) {
          pushError(radios[0] || input, `${f.label}을(를) 선택해 주세요.`);
        }
        return;
      }

      const v = (input.value || '').trim();

      // 공백 체크
      if (v === '') {
        pushError(input, `${f.label}은(는) 필수입니다.`);
        return;
      }

      // 유형별 검증
      if (f.type === 'digits') {
        if (!ONLY_NUM.test(v)) pushError(input, `${f.label}은(는) 숫자만 입력하세요.`);
      } else if (f.type === 'date') {
        const d = parseDate(v);
        if (!d) {
          pushError(input, `${f.label} 형식이 올바르지 않습니다(YYYY-MM-DD).`);
        } else {
          d.setHours(0,0,0,0);
          if (d < DATE_MIN) pushError(input, `${f.label}은(는) 1900-01-01 이후여야 합니다.`);
          if (d > TODAY)   pushError(input, `${f.label}은(는) 오늘 이후 날짜일 수 없습니다.`);
        }
      }

      // 길이 제한(HTML maxlength와 f.max 둘 중 더 엄격한 값)
      const max = f.max ?? (input.maxLength > 0 ? input.maxLength : null);
      if (max && v.length > max) {
        pushError(input, `${f.label}은(는) 최대 ${max}자까지 입력 가능합니다.`);
      }
    });

    // 2) 주소 정책 (필요 시 주석 해제 후 사용)
    ADDRESS_REQUIRED.forEach(f => {
      const input = findField(f.name, form);
      if (!input) return;
      const v = (input.value || '').trim();
      if (v === '') pushError(input, `${f.label}은(는) 필수입니다.`);
      const max = f.max ?? (input.maxLength > 0 ? input.maxLength : null);
      if (max && v.length > max) pushError(input, `${f.label}은(는) 최대 ${max}자까지 입력 가능합니다.`);
    });

    // 3) 숫자만(선택) — 값이 있을 때만 검사
    DIGIT_ONLY_OPTIONAL.forEach(f => {
      const input = findField(f.name, form);
      if (!input) return;
      const v = (input.value || '').trim();
      if (v === '') return;
      if (!ONLY_NUM.test(v)) pushError(input, `${f.label}은(는) 숫자만 입력하세요.`);
      const max = f.max ?? (input.maxLength > 0 ? input.maxLength : null);
      if (max && v.length > max) pushError(input, `${f.label}은(는) 최대 ${max}자까지 입력 가능합니다.`);
    });

    // 4) 즉시 숫자 강제 바인딩(존재 시)
    ['telephone','officeTelephone','emergencyContact','postcode'].forEach(n => {
      const el = findField(n, form);
      if (el) attachDigitOnlyInput(el);
    });

    // 결과 처리
    if (alertMessages.length) {
      // 한 번만 alert
      alert('입력값을 확인해 주세요:\n\n' + alertMessages.join('\n'));
      if (firstErrorInput) {
        try { firstErrorInput.focus(); } catch(_) {}
        try { firstErrorInput.scrollIntoView({behavior:'smooth', block:'center'}); } catch(_) {}
      }
      return false;
    }

    // 통과 → 제출
    form.submit();
    return true;
  }

  /* -----------------------------------------
   * 초기 바인딩
   * --------------------------------------- */
  document.addEventListener('DOMContentLoaded', () => {
    const forms = Array.from(document.querySelectorAll('form[method="post"]'));
    if (!forms.length) return;

    forms.forEach(form => {
      // 필수 * 붙이기
      decorateRequiredStars(form);

      // 제출 가로채기 (alert 1회 + 인라인 에러)
      form.addEventListener('submit', function (e) {
        // 기본 submit 막고 커스텀 검증 실행
        e.preventDefault();
        validateDoctorForm(e);
      });

      // 엔터로 제출될 때도 동일 동작 보장
      form.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
          // textarea는 제외
          if (e.target && e.target.tagName === 'TEXTAREA') return;
          e.preventDefault();
          validateDoctorForm({ preventDefault:()=>{}, target: form });
        }
      });
    });
  });
})();
