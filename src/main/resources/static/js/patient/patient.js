$('.navbar-toggle').click(function() {
	let a = $('.menubox_2').hasClass('active');

	if(a) {
		$('.menubox_2').removeClass('active');
	} else {
		$('.menubox_2').addClass('active');
	}
});

    function openRoadPopup(input) {
      roadTargetInput = input;
      const w = 900, h = 640;
      const left = (screen.width - w)/2, top = (screen.height - h)/2;
      window.open('/global/road/popup', 'roadPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 도로명 팝업에서 호출: 기본주소만 채움
    function setRoad(addrStr, roadCode, emdSeqNo) {
      if(roadTargetInput) {
        roadTargetInput.value = addrStr || '';
      }

      // 필요하면 roadCode/emdSeqNo 숨김 필드 추가 후 주입 가능
    }

    function openCountryPopup() {
        const w = 780, h = 620;
        const left = (screen.width - w) / 2;
        const top = (screen.height - h) / 2;
        window.open('/global/country_form', 'countryPopup',
          `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

      // 팝업에서 호출 (자동 저장 금지, 값만 세팅)
    function setCountry(iso2, countryKr) {
          // 선택된 국가를 표시하는 요소 (선택사항)
          const disp = document.getElementById('nationality');
          if (disp) disp.textContent = `${countryKr} (${iso2})`;

          // hidden input 값 업데이트
          const hidden = document.getElementById('nationality');
          if (hidden) hidden.value = `${countryKr} (${iso2})`;
    }

  //  검색폼 토글
    function toggleSearchForm() {
        const form = document.getElementById('searchForm');
        form.style.display = (form.style.display === 'none') ? 'block' : 'none';
    }

    // 검색 조건 행 추가
    function addConditionRow() {
        const row = document.querySelector('.search-row').cloneNode(true);
        row.querySelectorAll('input, select').forEach(el => el.value = '');

         //  logicalOperator를 기본값 AND로 설정
        const logicalOperatorSelect = row.querySelector('select[name="logicalOperator[]"]');
        if (logicalOperatorSelect) {
            logicalOperatorSelect.value = 'AND';
        }

        document.getElementById('searchConditions').appendChild(row);
    }

    //  검색 조건 행 삭제
    function removeConditionRow(btn) {
        const rows = document.querySelectorAll('.search-row');
        if (rows.length > 1) {
            btn.closest('.search-row').remove();
        } else {
            toggleSearchForm();
        }
    }

     // input 태그 생성 함수
    function createInput(type) {
        const input = document.createElement('input');
        input.type = type;
        input.name = 'value[]';
        input.className = 'form-control';
        return input;
    }

    // select 태그 생성 함수
    function createSelect(options) {
        const selectEl = document.createElement('select');
        selectEl.name = 'value[]';
        selectEl.className = 'form-select';
        selectEl.innerHTML = options;
        return selectEl;
    }
    //  전화번호 컬럼 선택 시 연산자 제한 및 입력 타입 변경
    function updateOperatorOptions(select) {
        //비교연산자셀렉트
        const operatorSelect = select.closest('.search-row').querySelector('select[name="operator[]"]');
        const valueContainer = select.closest('.search-row');

        if (select.value === 'mobilePhone' || select.value === 'guardianTel') {
            operatorSelect.innerHTML = '<option value="like">포함</option>';
            const oldInput = valueContainer.querySelector('input[name="value[]"]');
            if(oldInput){
                const newInput = document.createElement('input');
                newInput.type = 'text';
                newInput.name = 'value[]';
                newInput.className = 'form-control';
                newInput.placeholder = '값 입력';
                oldInput.replaceWith(newInput);
            }
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
                return;
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
                return;
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
        const oldSelect = valueContainer.querySelector('select[name="value[]"]');

        if (['birth','createDate','lastVisitDate'].includes(select.value)) {
            valueInput.type = 'date';
        }
        else if (oldSelect && ['lastName','firstName', 'mobilePhone', 'guardianTel'].includes(select.value)) {
             const newInput = document.createElement('input');
             newInput.name = 'value[]';
             newInput.type = 'text';
             newInput.className = 'form-control';
             newInput. placeholder = "값 입력";

             oldSelect.replaceWith(newInput);
             return;
        }
        else {
            valueInput.type = 'text';
        }
   }