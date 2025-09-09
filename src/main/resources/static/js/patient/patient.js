$('.navbar-toggle').click(function() {
	let a = $('.menubox_2').hasClass('active');

	if(a) {
		$('.menubox_2').removeClass('active');
	} else {
		$('.menubox_2').addClass('active');
	}
});


    function openCountryPopup() {
          const w = 780, h = 620;
          const left = (screen.width - w) / 2;
          const top = (screen.height - h) / 2;
          // 팝업 URL은 사용 중인 컨트롤러 경로에 맞춰 통일
          window.open('/global/country_form', 'countryPopup',
            `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

        // 팝업에서 호출
    function setCountry(iso2, countryKr) {
      const disp = document.getElementById('nationalityDisplay');
      const input = document.getElementById('nationalityInput');
      if (disp) disp.textContent = `${countryKr} (${iso2})`;
      if (input) input.value = countryKr; // 또는 iso2 저장을 원하면 input.value = iso2;
    }

  // 🔹 검색폼 토글
    function toggleSearchForm() {
        const form = document.getElementById('searchForm');
        form.style.display = (form.style.display === 'none') ? 'block' : 'none';
    }

    // 🔹 검색 조건 행 추가
    function addConditionRow() {
        const row = document.querySelector('.search-row').cloneNode(true);
        row.querySelectorAll('input, select').forEach(el => el.value = '');

         // 🔹 logicalOperator를 기본값 AND로 설정
        const logicalOperatorSelect = row.querySelector('select[name="logicalOperator[]"]');
        if (logicalOperatorSelect) {
            logicalOperatorSelect.value = 'AND';
        }

        document.getElementById('searchConditions').appendChild(row);
    }

    // 🔹 검색 조건 행 삭제
    function removeConditionRow(btn) {
        const rows = document.querySelectorAll('.search-row');
        if (rows.length > 1) {
            btn.closest('.search-row').remove();
        } else {
            toggleSearchForm();
        }
    }

    // 🔹 전화번호 컬럼 선택 시 연산자 제한 및 입력 타입 변경
    function updateOperatorOptions(select) {
        const operatorSelect = select.closest('.search-row').querySelector('select[name="operator[]"]');
        const valueContainer = select.closest('.search-row');

        if (select.value === 'mobilePhone' || select.value === 'guardianTel') {
            operatorSelect.innerHTML = '<option value="like">포함</option>';
        } else if (select.value === 'firstName' || select.value === 'lastName'){
            operatorSelect.innerHTML = '<option value="like">포함</option>';
        } else if (select.value === 'gender'){
            operatorSelect.innerHTML = '<option value="=">=</option>';
            const oldInput = valueContainer.querySelector('input[name="value[]"]');
            //input 태그의 타입을 select 로 바꾸고 옵션을 male, female, X 3가지 변경
            if (oldInput) {
                const newSelect = document.createElement('select');
                newSelect.name = 'value[]';
                newSelect.className = 'form-select';
                newSelect.innerHTML = `
                    <option value="">선택</option>
                    <option value="M">남</option>
                    <option value="F">여</option>
                    <option value="X">그외</option>
                `;
                oldInput.replaceWith(newSelect);
            }
        } else if (select.value === 'foreigner') {
            operatorSelect.innerHTML = '<option value="=">=</option>';
            //input 태그의 타입을 select 로 바꾸고 옵션을 내국인, 외국인 2가지 변경
            const oldInput = valueContainer.querySelector('input[name="value[]"]');
            if (oldInput) {
                const newSelect = document.createElement('select');
                newSelect.name = 'value[]';
                newSelect.className = 'form-select';
                newSelect.innerHTML = `
                    <option value="">선택</option>
                    <option value="0">내국인</option>
                    <option value="1">외국인</option>
                `;
                oldInput.replaceWith(newSelect);
            }
        } else {
            operatorSelect.innerHTML = `
                <option value="=">=</option>
                <option value="like">포함</option>
                <option value=">">></option>
                <option value="<"><</option>
            `;
        }
        const valueInput = select.closest('.search-row').querySelector('input[name="value[]"]');
        if (['birth','createDate','lastVisitDate'].includes(select.value)) {
            valueInput.type = 'date';
        } else {
            valueInput.type = 'text';
        }
    }
