$('.navbar-toggle').click(function() {
	let a = $('.menubox_2').hasClass('active');

	if(a) {
		$('.menubox_2').removeClass('active');
	} else {
		$('.menubox_2').addClass('active');
	}
});


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
        if (select.value === 'mobilePhone' || select.value === 'guardianTel') {
            operatorSelect.innerHTML = '<option value="like">포함</option>';
        } else if (select.value === 'firstName' || select.value === 'lastName'){
            operatorSelect.innerHTML = '<option value="like">포함</option>';
        } else if (select.value === 'gender' || select.value === 'foreigner'){
            operatorSelect.innerHTML = '<option value="=">=</option>';
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
