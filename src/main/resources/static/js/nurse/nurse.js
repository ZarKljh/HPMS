// =======================
// 폼 공통
// =======================
document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form[action*="/nurse/create"]:not([action*="/info"])');
    if (!form) return;

    // -----------------------
    // 공통 유효성 함수
    // -----------------------
    function emailCheck(email_address) {
        email_address = email_address.trim().replace(/\s+/g,'');
        const regex = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/i;
        return regex.test(email_address);
    }

    function isHyphenPhoneNumber(tel) {
        const regex = /^0\d{1,2}-\d{3,4}-\d{4}$/;
        return regex.test(tel);
    }

    function showError(field, message) {
        clearError(field);
        const error = document.createElement('div');
        error.className = 'error-message';
        error.style.cssText = 'color:red;font-size:12px;margin-top:5px;';
        error.textContent = message;
        field.parentNode.appendChild(error);
        field.style.borderColor = 'red';
    }

    function clearError(field) {
        const error = field.parentNode.querySelector('.error-message');
        if (error) error.remove();
        field.style.borderColor = '';
    }

    // -----------------------
    // 필드 유효성 검사
    // -----------------------
    function validateField(field) {
        const value = field.value.trim();
        let isValid = true;
        clearError(field);

        const name = field.name.includes('.') ? field.name.split('.')[1] : field.name;

        switch(name) {
            case 'firstName':
                if(!value){ showError(field,'이름을 입력해주세요.'); isValid=false; }
                else if(value.length>50){ showError(field,'이름은 50자 이하로 입력해주세요.'); isValid=false; }
                else if(!/^[가-힣a-zA-Z\s]+$/.test(value)){ showError(field,'이름은 한글 또는 영문만 입력 가능합니다.'); isValid=false; }
                break;
            case 'lastName':
                if(!value){ showError(field,'성을 입력해주세요.'); isValid=false; }
                else if(value.length>50){ showError(field,'성은 50자 이하로 입력해주세요.'); isValid=false; }
                else if(!/^[가-힣a-zA-Z\s]+$/.test(value)){ showError(field,'성은 한글 또는 영문만 입력 가능합니다.'); isValid=false; }
                break;
            case 'tel':
            case 'emgcCntc':
                if(!value){ showError(field,'전화번호를 입력해주세요.'); isValid=false; }
                else if(!isHyphenPhoneNumber(value)){ showError(field,'전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)'); isValid=false; }
                break;
            case 'email':
                if(!value){ showError(field,'이메일을 입력해주세요.'); isValid=false; }
                else if(!emailCheck(value)){ showError(field,'유효하지 않은 이메일 주소입니다.'); isValid=false; }
                break;
            case 'pcd':
                if(!value){ showError(field,'우편번호를 입력해주세요.'); isValid=false; }
                else if(!/^\d{5}$/.test(value)){ showError(field,'우편번호는 5자리 숫자로 입력해주세요.'); isValid=false; }
                break;
            case 'defAdd':
                if(!value){ showError(field,'주소를 입력해주세요.'); isValid=false; }
                else if(value.length>250){ showError(field,'주소는 250자 이하로 입력해주세요.'); isValid=false; }
                break;
            case 'detAdd':
                if(value.length>250){ showError(field,'상세주소는 250자 이하로 입력해주세요.'); isValid=false; }
                break;
            case 'rnNo':
                if(!value){ showError(field,'간호사 자격증 번호를 입력해주세요.'); isValid=false; }
                else if(!/^\d{6}$/.test(value)){ showError(field,'자격증 번호는 6자리 숫자로 입력해주세요.'); isValid=false; }
                break;
            case 'gradDate':
            case 'dateOfBirth':
            case 'hireDate':
                if(!value){ showError(field,'날짜를 입력해주세요.'); isValid=false; }
                else if(!/^\d{8}$/.test(value)){ showError(field,'YYYYMMDD 형식으로 입력해주세요.'); isValid=false; }
                else{
                    const y = parseInt(value.substring(0,4)), m = parseInt(value.substring(4,6)), d = parseInt(value.substring(6,8));
                    const maxYear = (name==='hireDate')?new Date().getFullYear()+1:new Date().getFullYear();
                    if(y<1900||y>maxYear){ showError(field,'올바른 연도를 입력해주세요.'); isValid=false; }
                    else if(m<1||m>12){ showError(field,'올바른 월을 입력해주세요.'); isValid=false; }
                    else if(d<1||d>31){ showError(field,'올바른 일을 입력해주세요.'); isValid=false; }
                    else { const testDate=new Date(y,m-1,d); if(testDate.getFullYear()!==y||testDate.getMonth()!==m-1||testDate.getDate()!==d){ showError(field,'존재하지 않는 날짜입니다.'); isValid=false; } }
                }
                break;
            case 'picture':
                const fileInput = document.getElementById('pictureFile');
                const existingPicture = document.querySelector('input[name="existingPicture"]');
                const hasNewFile = fileInput && fileInput.files.length>0;
                const hasExistingPicture = existingPicture && existingPicture.value;
                if(!hasNewFile && !hasExistingPicture){
                    showError(fileInput||field,'사진을 선택해주세요.'); isValid=false;
                }else if(hasNewFile){
                    const file = fileInput.files[0];
                    const maxSize = 5*1024*1024;
                    const allowed = ['image/jpeg','image/jpg','image/png','image/gif','image/webp'];
                    if(file.size>maxSize){ showError(fileInput,'파일 크기는 5MB 이하입니다.'); isValid=false; }
                    else if(!allowed.includes(file.type)){ showError(fileInput,'지원하지 않는 파일 형식입니다.'); isValid=false; }
                }
                break;
            case 'ms':
                if(!value){ showError(field,'병역을 선택해주세요.'); isValid=false; }
                break;
            case 'natn':
                if(!value){ showError(field,'국적을 선택해주세요.'); isValid=false; }
                break;
        }
        return isValid;
    }

    // -----------------------
    // 폼 전체 유효성 검사
    // -----------------------
    function validateForm() {
        let isValid=true;
        const fields = form.querySelectorAll('input, select, textarea');
        fields.forEach(f=>{ if(!validateField(f)) isValid=false; });
        const gender = form.querySelector('input[name*="gender"]:checked');
        if(!gender){
            const genderInput = form.querySelector('input[name*="gender"]');
            if(genderInput){ showError(genderInput,'성별을 선택해주세요.'); isValid=false; }
        }
        return isValid;
    }

    // -----------------------
    // 버튼 클릭 이벤트
    // -----------------------
    const submitBtn = Array.from(form.querySelectorAll('button, input[type="submit"]'))
        .find(btn => /등록|다음|저장/.test(btn.textContent));
    if(submitBtn){
        submitBtn.type='button';
        submitBtn.addEventListener('click', function(){
            if(validateForm()) form.submit();
            else{
                const firstError=form.querySelector('.error-message');
                if(firstError) firstError.scrollIntoView({behavior:'smooth', block:'center'});
                alert('입력 정보를 다시 확인해주세요.');
            }
        });
    }
});

// =======================
// 기본 정보 등록 폼 (/nurse/create)
// =======================
document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form[action*="/nurse/create"]:not([action*="/info"])');
    if (!form) return;

    function emailCheck(email_address) {
        email_address = email_address.trim().replace(/\s+/g, '');
        const email_regex = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/i;
        return email_regex.test(email_address);
    }

    function isHyphenPhoneNumber(tel) {
        const regex = /^0\d{1,2}-\d{3,4}-\d{4}$/;
        return regex.test(tel);
    }

    function showError(field, message) {
        clearError(field);
        const errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        errorElement.style.cssText = 'color:red;font-size:12px;margin-top:5px;';
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
        field.style.borderColor = 'red';
    }

    function clearError(field) {
        const errorMessage = field.parentNode.querySelector('.error-message');
        if (errorMessage) errorMessage.remove();
        field.style.borderColor = '';
    }

    function validateField(field) {
        const value = field.value.trim();
        let isValid = true;
        clearError(field);
        switch (field.name) {
            case 'firstName':
                if (!value) { showError(field,'이름을 입력해주세요.'); isValid=false; }
                else if (value.length>50) { showError(field,'이름은 50자 이하로 입력해주세요.'); isValid=false; }
                break;
            case 'lastName':
                if (!value) { showError(field,'성을 입력해주세요.'); isValid=false; }
                else if (value.length>50) { showError(field,'성은 50자 이하로 입력해주세요.'); isValid=false; }
                break;
        }
        return isValid;
    }

    function validateForm() {
        let isFormValid = true;
        const requiredFields = form.querySelectorAll('input[required], textarea[required], select[required]');
        requiredFields.forEach(field => {
            if (!validateField(field)) isFormValid=false;
        });
        const genderInputs = form.querySelectorAll('input[name*="gender"]');
        if (genderInputs.length>0 && !form.querySelector('input[name*="gender"]:checked')) {
            showError(genderInputs[0],'성별을 선택해주세요.');
            isFormValid=false;
        }
        return isFormValid;
    }

    const submitBtn = Array.from(form.querySelectorAll('button, input[type="submit"]'))
        .find(btn => /등록|다음|저장/.test(btn.textContent));
    if (submitBtn) {
        submitBtn.type='button';
        submitBtn.addEventListener('click', function() {
            if (validateForm()) form.submit();
            else {
                const firstError = form.querySelector('.error-message');
                if (firstError) firstError.scrollIntoView({behavior:'smooth', block:'center'});
                alert('입력 정보를 다시 확인해주세요.');
            }
        });
    }
});


// =======================
// 상세 정보 등록 폼 (/nurse/create/info)
// =======================
document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form[action*="/nurse/create/info"]');
    if (!form) return;

    function emailCheck(email_address) {
        email_address = email_address.trim().replace(/\s+/g, '');
        const email_regex = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/i;
        return email_regex.test(email_address);
    }

    function isHyphenPhoneNumber(tel) {
        const regex = /^0\d{1,2}-\d{3,4}-\d{4}$/;
        return regex.test(tel);
    }

    function showError(field, message) {
        clearError(field);
        const errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        errorElement.style.cssText = 'color:red;font-size:12px;margin-top:5px;';
        errorElement.textContent = message;
        field.parentNode.appendChild(errorElement);
        field.style.borderColor = 'red';
    }

    function clearError(field) {
        const errorMessage = field.parentNode.querySelector('.error-message');
        if (errorMessage) errorMessage.remove();
        field.style.borderColor = '';
    }

    function validateField(field) {
        const value = field.value.trim();
        let isValid = true;
        clearError(field);
        switch (field.name) {
            case 'firstName':
                if (!value) { showError(field,'이름을 입력해주세요.'); isValid=false; }
                else if (value.length>50) { showError(field,'이름은 50자 이하로 입력해주세요.'); isValid=false; }
                break;
            case 'lastName':
                if (!value) { showError(field,'성을 입력해주세요.'); isValid=false; }
                else if (value.length>50) { showError(field,'성은 50자 이하로 입력해주세요.'); isValid=false; }
                break;
            case 'tel':
                if (!value) { showError(field,'전화번호를 입력해주세요.'); isValid=false; }
                else if (!isHyphenPhoneNumber(value)) { showError(field,'전화번호 형식이 올바르지 않습니다.'); isValid=false; }
                break;
            case 'email':
                if (!value) { showError(field,'이메일을 입력해주세요.'); isValid=false; }
                else if (!emailCheck(value)) { showError(field,'유효하지 않은 이메일입니다.'); isValid=false; }
                break;
        }
        return isValid;
    }

    function validateForm() {
        let isFormValid = true;
        const requiredFields = form.querySelectorAll('input[required], textarea[required], select[required]');
        requiredFields.forEach(field => {
            if (!validateField(field)) isFormValid=false;
        });
        return isFormValid;
    }

    const submitBtn = Array.from(form.querySelectorAll('button, input[type="submit"]'))
        .find(btn => /등록|다음|저장/.test(btn.textContent));
    if (submitBtn) {
        submitBtn.type='button';
        submitBtn.addEventListener('click', function() {
            if (validateForm()) form.submit();
            else {
                const firstError = form.querySelector('.error-message');
                if (firstError) firstError.scrollIntoView({behavior:'smooth', block:'center'});
                alert('입력 정보를 다시 확인해주세요.');
            }
        });
    }
});


// =======================
// 정보 수정 폼 (/nurse/modify/{id})
// =======================
document.addEventListener("DOMContentLoaded", function() {
    const modifyForm = document.querySelector('form[th\\:action*="/nurse/modify"]');
    const saveButton = modifyForm.querySelector('button[type="button"]');

    saveButton.addEventListener("click", function() {
        let valid = true;

        // 모든 required input 체크
        modifyForm.querySelectorAll("input[required], textarea[required]").forEach(input => {
            if (!input.value.trim()) {
                valid = false;
                input.style.border = "1px solid red";
            } else {
                input.style.border = "";
            }
        });

        // 라디오 버튼 체크 (성별)
        const genders = modifyForm.querySelectorAll('input[type="radio"][name="nurseMain.gender"]');
        if (![...genders].some(r => r.checked)) {
            valid = false;
            genders.forEach(r => r.parentElement.style.color = "red");
        } else {
            genders.forEach(r => r.parentElement.style.color = "");
        }

        // selectbox hidden 필드 체크
        modifyForm.querySelectorAll("input[type='hidden'][required]").forEach(hidden => {
            if (!hidden.value.trim()) valid = false;
        });

        if (valid) {
            modifyForm.submit();
        } else {
            alert("모든 필수 항목을 입력해주세요.");
        }
    });
});
