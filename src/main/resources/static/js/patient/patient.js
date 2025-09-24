$('.navbar-toggle').click(function() {
	let a = $('.menubox_2').hasClass('active');

	if(a) {
		$('.menubox_2').removeClass('active');
	} else {
		$('.menubox_2').addClass('active');
	}
});



    /* create form validate 시작 */

    function validateCreateForm(event){

        event.preventDefault(); // 폼 제출 막기

        const form = document.forms['patientCreateForm'];

        const birth = form.querySelector("input[name='birth']");
        const startDate = new Date('1900-01-01');
        const nowDate = new Date();
        const formatBirth = new Date(birth.value);
        if(!birth.value) {
            alert("날짜를 입력해주세요.");
            return false;
        } else if(formatBirth < startDate) {
            alert("생년월일은 1900년1월1일 이전은 입력할 수 없습니다");
            return false;
        } else if(formatBirth > nowDate) {
            alert("생년월일은 오늘날짜 이후 날짜는 입력할 수 없습니다");
            return false;
        }

        const gender = form.querySelector("select[name='gender']");
        if(!gender.value) {
            alert("성별을 선택해주세요.");
            return false;
        } else if(gender.value != 'M' && gender.value != 'F' && gender.value !='X'){
            alert("올바른 값을 선택해주세요.");
            return false;
        }

        const foreigner = form.querySelector("select[name='foreigner']");
        if(foreigner.value != 0 && foreigner.value != 1) {
            alert("내외국인 여부를 선택해주세요");
            return false;
        }

        const nameRegexHan = /^[가-힣]+$/;
        const nameRegexEng = /^[a-zA-Z]+$/;
        const nameRegexEngCapital = /^[A-Z]+$/;
        const nameRegex = /^[가-힣a-zA-Z]+$/;

        const firstNameInput = form.querySelector("input[name=firstName]");
        const lastNameInput = form.querySelector("input[name=lastName]");
        const middleNameInput = form.querySelector("input[name=middleName]");

        /*이름 앞뒤 빈칸제거 + 중간빈칸제거*/

        let firstName = firstNameInput.value.trim().replace(/ /g,"");
        let lastName = lastNameInput.value.trim().replace(/ /g,"");
        let middleName = middleNameInput.value.trim().replace(/ /g,"");

        firstNameInput.value = firstName;
        lastNameInput.value = lastName;
        middleNameInput.value = middleName;

        if(!nameRegexHan.test(firstName) || !nameRegexHan.test(lastName) || !nameRegexHan.test(middleName)){
            alert("성명 성과 이름, 중간이름 한글로 작성해주세요");
            return false;
        } else if(firstName.length < 1 || lastName.length < 1) {
            alert("성명 성과 이름은 필수항목입니다");
            return false;
        }

        const passport = form.querySelector("input[name='passport']");
        /*여권번호를 입력했을 때에만 검증*/
        if(passport.value.length > 1) {
            /*입력된 가장 첫자리만 오려서 가져온다*/
            const passportPartEng = passport.value.slice(0,1);
            /*입력된 여권번호의 가장 첫자리가 영문 대문자인지 검증*/
            if(!nameRegexEngCapital.test(passportPartEng)){
                alert("여권번호의 가장 앞자리는 영문 대문자입니다");
                return false;
            /*입력된 여권번호가 8자리 인지 검증*/
            } else if (passport.value.length != 8) {
                alert("여권번호는 8자리 입니다");
                return false;
            }
        }

        const passFirstNameInput = form.querySelector("input[name=passFirstName]");
        const passLastNameInput = form.querySelector("input[name=passLastName]");
        const passMiddleNameInput = form.querySelector("input[name=passMiddleName]");

        /*이름 앞뒤 빈칸제거 + 중간빈칸제거*/

        let passFirstName = passFirstNameInput.value.trim().replace(/ /g,"");
        let passLastName = passLastNameInput.value.trim().replace(/ /g,"");
        let passMiddleName = passMiddleNameInput.value.trim().replace(/ /g,"");

        passFirstNameInput.value = passFirstName;
        passLastNameInput.value = passLastName;
        passMiddleNameInput.value = passMiddleName;

        /*여권번호를 작성했을 시에만 검증*/
        if(passport.value.length > 1){
            if(!nameRegexEng.test(passFirstName) || !nameRegexEng.test(passLastName) || !nameRegexEng.test(passMiddleName)){
                alert("여권 성명 성과 이름, 중간이름 영문으로 작성해주세요");
                return false;
            } else if(firstName.length < 1 || lastName.length < 1) {
                alert("성명 성과 이름은 필수항목입니다");
                return false;
            }
        }
        /*숫자와 자릿수 설정*/
        const numberOnlyRegex = /^[0-9]{9,12}$/;
        /*휴대전화번호 검증*/
        const mobilePhone = form.querySelector("input[name='mobilePhone']").value.trim();
        if(mobilePhone.length === 0) {
            alert("휴대전화번호는 필수항목입니다");
            return false;
        } else if(!numberOnlyRegex.test(mobilePhone)) {
            alert("연락처에는 숫자만 넣어주세요");
            return false;
        }

        /*보호자 정보 검증*/
        const guardianFirstNameInput = form.querySelector("input[name=guardianFirstName]");
        const guardianLastNameInput = form.querySelector("input[name=guardianLastName]");

        let guardianFirstName = guardianFirstNameInput.value.trim().replace(/ /g,"");
        let guardianLastName = guardianLastNameInput.value.trim().replace(/ /g,"");

        guardianFirstNameInput.value = guardianFirstName;
        guardianLastNameInput.value = guardianLastName;

        if(guardianFirstName.length < 1 || guardianLastName.length < 1) {
            alert("보호자 성명 성과 이름은 필수 항목입니다");
            return false;
        } else if(!nameRegex.test(guardianFirstName) || !nameRegex.test(guardianLastName)){
            alert("보호자 성명 성과 이름은 한글 혹은 영문으로 작성해주세요");
            return false;
        }


        /*휴대전화번호 검증*/
        const guardianTel = form.querySelector("input[name='guardianTel']").value.trim();
        if(guardianTel.length === 0) {
            alert("보호자 연락처는 필수항목입니다");
            return false;
        } else if(!numberOnlyRegex.test(guardianTel)) {
            alert("연락처에는 숫자만 넣어주세요");
            return false;
        }
        const numberOnlyRegexForPcd = /^[0-9]{5,6}$/;
        const homePcd = form.querySelector("input[name='homePcd']").value.trim();
        if( homePcd.length  === 0 ){
            alert("실거주지 우편번호는 필수항목입니다");
            return false;
        } else if (!numberOnlyRegexForPcd.test(homePcd)) {
            alert("우편번호는 5자리 혹은 6자리의 숫자만 입력해주세요");
            return false;
        }

        form.submit();
        }


    /*체크박스 체크된 환자정보 삭제기능*/
    /* 제한된 체크박스 확인 후, 폼 COMMIT하기 */
    function submitFormIfValid() {
      const form = document.forms["selectedPatient"];
      const checkboxes = form.querySelectorAll('input[name="checkedIds"]');
      var anyChecked = Array.prototype.some.call(checkboxes, function(cb) {
          return cb.checked;
      });

      if (!anyChecked) {
        alert("하나 이상의 항목을 선택해주세요.");
        return;
      }

      form.submit();
    }

    function submitForm(event) {
        event.preventDefault(); // 링크 기본 동작 막기
        document.getElementById('registrationForm').submit(); // 폼 전송
    }


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

        if (['dayOfBirth','createDate','lastVisitDate'].includes(select.value)) {
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