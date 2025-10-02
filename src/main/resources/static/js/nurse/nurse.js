// =======================
// 수정된 통합 스크립트 (유효성 + 팝업 + 삭제)
// =======================
document.addEventListener("DOMContentLoaded", function() {

    // ----------- 공통 유효성 함수 -----------
    function emailCheck(email_address) {
        email_address = email_address.trim().replace(/\s+/g,'');
        const regex = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/i;
        return regex.test(email_address);
    }

    function isHyphenPhoneNumber(tel) {
        const regex = /^0\d{1,2}-\d{3,4}-\d{4}$/;
        return regex.test(tel);
    }

    // 에러 표시/제거 함수
    function showError(field, message) {
        if (!field) return;

        let container = field.closest('.input-list');
        if (!container) {
            container = field.parentNode;
        }
        if (!container) return;

        clearError(field);

        const error = document.createElement('div');
        error.className = 'error-message';
        error.style.cssText = 'color:red;font-size:12px;margin-top:5px;';
        error.textContent = message;

        container.appendChild(error);
        field.style.borderColor = 'red';
    }

    function clearError(field) {
        if (!field) return;

        let container = field.closest('.input-list');
        if (!container) {
            container = field.parentNode;
        }
        if (!container) return;

        const error = container.querySelector('.error-message');
        if (error) error.remove();
        field.style.borderColor = '';
    }

    // ----------- 필드별 유효성 검사 -----------
    function validateField(field) {
        if (!field) return true;

        const value = field.value.trim();
        let isValid = true;
        clearError(field);

        // 중첩 구조 처리: nurseMain.firstName → firstName
        const name = field.name.includes('.')
            ? field.name.split('.').pop()
            : field.name;

        // required가 아닌 필드는 비어있어도 OK (단, 값이 있으면 형식 검증)
        const isRequired = field.hasAttribute('required');
        if (!isRequired && !value) {
            return true;
        }

        switch(name) {
            case 'firstName':
                if(!value){
                    showError(field,'이름을 입력해주세요.');
                    isValid=false;
                }
                else if(value.length>50){
                    showError(field,'이름은 50자 이하로 입력해주세요.');
                    isValid=false;
                }
                else if(!/^[가-힣a-zA-Z\s]+$/.test(value)){
                    showError(field,'이름은 한글 또는 영문만 입력 가능합니다.');
                    isValid=false;
                }
                break;

            case 'lastName':
                if(!value){
                    showError(field,'성을 입력해주세요.');
                    isValid=false;
                }
                else if(value.length>50){
                    showError(field,'성은 50자 이하로 입력해주세요.');
                    isValid=false;
                }
                else if(!/^[가-힣a-zA-Z\s]+$/.test(value)){
                    showError(field,'성은 한글 또는 영문만 입력 가능합니다.');
                    isValid=false;
                }
                break;

            case 'middleName':
                if(value && value.length>50){
                    showError(field,'중간이름은 50자 이하로 입력해주세요.');
                    isValid=false;
                }
                else if(value && !/^[가-힣a-zA-Z\s]+$/.test(value)){
                    showError(field,'중간이름은 한글 또는 영문만 입력 가능합니다.');
                    isValid=false;
                }
                break;

            case 'tel':
            case 'emgcCntc':
                if(!value){
                    showError(field,'전화번호를 입력해주세요.');
                    isValid=false;
                }
                else if(!isHyphenPhoneNumber(value)){
                    showError(field,'전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)');
                    isValid=false;
                }
                break;

            case 'emgcFName':
            case 'emgcLName':
                if(!value){
                    showError(field,'비상연락처 성명을 입력해주세요.');
                    isValid=false;
                }
                else if(value.length>50){
                    showError(field,'성명은 50자 이하로 입력해주세요.');
                    isValid=false;
                }
                else if(!/^[가-힣a-zA-Z\s]+$/.test(value)){
                    showError(field,'성명은 한글 또는 영문만 입력 가능합니다.');
                    isValid=false;
                }
                break;

            case 'emgcRel':
                if(!value){
                    showError(field,'관계를 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'emgcMName':
                if(value && value.length>50){
                    showError(field,'중간이름은 50자 이하로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'emgcNote':
            case 'note':
                if(value && value.length>250){
                    showError(field,'비고는 250자 이하로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'email':
                if(!value){
                    showError(field,'이메일을 입력해주세요.');
                    isValid=false;
                }
                else if(!emailCheck(value)){
                    showError(field,'유효하지 않은 이메일 주소입니다.');
                    isValid=false;
                }
                break;

            case 'pcd':
                if(!value){
                    showError(field,'우편번호를 입력해주세요.');
                    isValid=false;
                }
                else if(!/^\d{5,6}$/.test(value)){
                    showError(field,'우편번호는 5~6자리 숫자로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'defAdd':
                if(!value){
                    showError(field,'주소를 입력해주세요.');
                    isValid=false;
                }
                else if(value.length>250){
                    showError(field,'주소는 250자 이하로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'detAdd':
                if(value && value.length>250){
                    showError(field,'상세주소는 250자 이하로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'rnNo':
                if(!value){
                    showError(field,'간호사 자격증 번호를 입력해주세요.');
                    isValid=false;
                }
                else if(value.length > 20){
                    showError(field,'자격증 번호는 20자 이하로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'edbc':
                if(!value){
                    showError(field,'최종학력을 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'gradDate':
            case 'dateOfBirth':
            case 'hireDate':
                if(!value){
                    showError(field,'날짜를 입력해주세요.');
                    isValid=false;
                }
                else if(!/^\d{8}$/.test(value)){
                    showError(field,'YYYYMMDD 형식으로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'fl':
                if(value && value.length>250){
                    showError(field,'외국어는 250자 이하로 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'ms':
                if(!value){
                    showError(field,'병역을 선택해주세요.');
                    isValid=false;
                }
                break;

            case 'dept':
            case 'rank':
                if(!value){
                    showError(field,'선택해주세요.');
                    isValid=false;
                }
                break;

            case 'natn':
                if(!value){
                    showError(field,'국적을 선택해주세요.');
                    isValid=false;
                }
                break;

            case 'dss':
                if(!value){
                    showError(field,'장애여부를 선택해주세요.');
                    isValid=false;
                }
                break;

            case 'carr':
                if(!value){
                    showError(field,'경력을 입력해주세요.');
                    isValid=false;
                }
                else if(!/^\d+$/.test(value)){
                    showError(field,'경력은 숫자만 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'sts':
                if(!value){
                    showError(field,'상태를 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'wt':
                if(!value){
                    showError(field,'근무형태를 입력해주세요.');
                    isValid=false;
                }
                break;

            case 'writer':
            case 'modifier':
                if(!value){
                    showError(field,'작성자/수정자를 입력해주세요.');
                    isValid=false;
                }
                break;
        }

        return isValid;
    }

    // ----------- 폼 전체 유효성 검사 -----------
    function validateForm(form) {
        let isValid = true;
        const failedFields = [];

        // 모든 input, select, textarea 필드 검사
        const fields = form.querySelectorAll('input, select, textarea');
        fields.forEach(field => {
            // CSRF 토큰과 pictureUrl은 건너뛰기
            if (field.type === 'hidden' &&
                (field.name === '_csrf' || field.name.includes('csrf') || field.id === 'pictureUrl')) {
                return;
            }

            if (field && field.name && !validateField(field)) {
                failedFields.push({
                    name: field.name,
                    value: field.value,
                    required: field.hasAttribute('required')
                });
                isValid = false;
            }
        });

        // 성별 라디오 버튼 검증
        const genderRadios = form.querySelectorAll('input[name*="gender"]');
        if (genderRadios.length > 0) {
            const genderChecked = form.querySelector('input[name*="gender"]:checked');
            if (!genderChecked) {
                const firstGenderRadio = genderRadios[0];
                showError(firstGenderRadio, '성별을 선택해주세요.');
                failedFields.push({ name: 'gender', value: '', required: true });
                isValid = false;
            }
        }

        // 사진 파일 검증
        const fileInput = document.getElementById('pictureFile');
        const isCreateForm = !document.querySelector('input[name="pictureUrl"]');

        if (fileInput && isCreateForm) {
            const existingPicture = document.querySelector('input[name="existingPicture"]');
            const hasNewFile = fileInput.files.length > 0;
            const hasExistingPicture = existingPicture && existingPicture.value?.trim() !== '';

            if (!hasNewFile && !hasExistingPicture) {
                showError(fileInput, '사진을 선택해주세요.');
                failedFields.push({ name: 'pictureFile', value: '', required: true });
                isValid = false;
            }
            else if (hasNewFile) {
                const file = fileInput.files[0];
                const maxSize = 5 * 1024 * 1024;
                const allowed = ['image/jpeg','image/jpg','image/png','image/gif','image/webp'];

                if (file.size > maxSize) {
                    showError(fileInput, '파일 크기는 5MB 이하입니다.');
                    failedFields.push({ name: 'pictureFile', value: file.name, required: true });
                    isValid = false;
                }
                else if (!allowed.includes(file.type)) {
                    showError(fileInput, '지원하지 않는 파일 형식입니다.');
                    failedFields.push({ name: 'pictureFile', value: file.name, required: true });
                    isValid = false;
                }
            }
        } else if (fileInput && !isCreateForm && fileInput.files.length > 0) {
            const file = fileInput.files[0];
            const maxSize = 5 * 1024 * 1024;
            const allowed = ['image/jpeg','image/jpg','image/png','image/gif','image/webp'];

            if (file.size > maxSize) {
                showError(fileInput, '파일 크기는 5MB 이하입니다.');
                failedFields.push({ name: 'pictureFile', value: file.name, required: false });
                isValid = false;
            }
            else if (!allowed.includes(file.type)) {
                showError(fileInput, '지원하지 않는 파일 형식입니다.');
                failedFields.push({ name: 'pictureFile', value: file.name, required: false });
                isValid = false;
            }
        }

        // 실패한 필드 로그 출력
        if (failedFields.length > 0) {
            console.log('=== Validation Failed Fields ===');
            failedFields.forEach(f => {
                console.log(`❌ ${f.name}: "${f.value}" (required: ${f.required})`);
            });
        }

        return isValid;
    }

    // ----------- 숫자 전용 입력 제한 -----------
    document.querySelectorAll(".numeric-only").forEach(input => {
        input.addEventListener("input", function () {
            this.value = this.value.replace(/[^0-9]/g, "");
        });
    });

    // ----------- 제출 버튼 이벤트 -----------
    document.querySelectorAll('form').forEach(form => {

        const allButtons = form.querySelectorAll('button[type="button"]');

        console.log('=== Form Validation Debug ===');
        console.log('Form found:', form);
        console.log('Total buttons:', allButtons.length);

        allButtons.forEach(btn => {
            const btnText = btn.textContent?.trim() || '';
            console.log('Button text:', btnText, 'Classes:', btn.className);

            const isSubmitButton =
                btn.classList.contains('save_btn') ||
                btnText === '등록' ||
                btnText === '저장' ||
                btnText === '수정' ||
                btnText === '다음';

            const isExcludeButton =
                btnText === '초기화' ||
                btnText === '목록' ||
                btnText === '취소';

            if (!isSubmitButton || isExcludeButton) {
                console.log('Skipping button:', btnText);
                return;
            }

            console.log('✓ Attaching validation to button:', btnText);

            btn.addEventListener('click', function(e) {
                e.stopPropagation();

                console.log('=== Button Clicked ===');
                console.log('Button:', btnText);

                if (validateForm(form)) {
                    console.log('✓ Validation passed - submitting form');
                    form.submit();
                } else {
                    console.log('✗ Validation failed');

                    const firstError = form.querySelector('.error-message');
                    if (firstError) {
                        firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });

                        const errorInput = firstError.parentNode?.querySelector('input, select, textarea');
                        if (errorInput) {
                            setTimeout(() => errorInput.focus(), 300);
                        }
                    }
                    alert('입력 정보를 다시 확인해주세요.');
                }
            }, { capture: true });
        });
    });

    /* ----------- 사진 미리보기 (등록 + 수정) ----------- */
        const fileInput = document.getElementById("pictureFile");
        const previewImg = document.getElementById("picturePreview");
        const existingPicture = document.getElementById("pictureFileInput");

        if (fileInput && previewImg) {
            fileInput.addEventListener("change", function(e) {
                const file = fileInput.files[0];
                if (!file) {
                    // 선택 취소 시 기존 이미지 또는 기본 이미지
                    previewImg.src = existingPicture.value || '/images/default.png';
                    return;
                }

                const maxSize = 5 * 1024 * 1024;
                const allowed = ["image/jpeg","image/png","image/jpg","image/gif","image/webp"];

                if (file.size > maxSize) {
                    alert("파일 크기는 5MB 이하입니다.");
                    fileInput.value = "";
                    return;
                }

                if (!allowed.includes(file.type)) {
                    alert("지원하지 않는 파일 형식입니다.");
                    fileInput.value = "";
                    return;
                }

                const reader = new FileReader();
                reader.onload = function(e) {
                    previewImg.src = e.target.result; // 새 이미지 미리보기
                }
                reader.readAsDataURL(file);

                existingPicture.value = ""; // 새 이미지 선택 시 기존 값 제거

                const formData = new FormData();
                formData.append('file', file);

                fetch('/nurse/existingPicture', { method: 'POST', body: formData })
                    .then(res => res.text())
                    .then(url => { existingPicture.value = url; }) // hidden 필드에 URL 넣기
                    .catch(err => { alert('업로드 실패'); console.error(err); });
            });
        }

    window.previewImage = function(event) {
        const file = event.target.files[0];
        const preview = document.getElementById("picturePreview");
        if (file && preview) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = "block";
            };
            reader.readAsDataURL(file);
        }
    };

    // ----------- 자격증 -----------
    const showBtn = document.getElementById("showLicenseFormBtn");
    const cancelBtn = document.getElementById("cancelLicenseFormBtn");
    const form = document.getElementById("newLicenseForm");
    const noMsg = document.getElementById("noLicenseMsg");
    const btnBox = document.getElementById("addLicenseBtnBox");

    // 자격증 추가 버튼 클릭 시
    if (showBtn) { // null 체크
        showBtn.addEventListener("click", function() {
            console.log("자격증 추가 버튼 클릭됨");
            form.style.display = "block";     // 폼 보여주기
            btnBox.style.display = "none";    // 버튼 숨기기
            if (noMsg) noMsg.style.display = "none";  // 메시지 숨기기
            form.scrollIntoView({ behavior: "smooth", block: "start" }); // 스크롤 이동
        });
    }

        // 취소 버튼 클릭 시
    if (cancelBtn) {
        cancelBtn.addEventListener("click", function() {
            console.log("취소 버튼 클릭됨");
            form.style.display = "none";      // 폼 숨기기
            btnBox.style.display = "block";   // 버튼 복원
            if (noMsg) noMsg.style.display = "block"; // 메시지 복원
            btnBox.scrollIntoView({ behavior: "smooth", block: "center" }); // 스크롤 이동
        });
    }

    /* ----------- selectbox 처리 ----------- */
    document.querySelectorAll(".select-wrapper").forEach(wrapper => {
        const toggleBtn = wrapper.querySelector(".toggle_btn");
        const options = wrapper.querySelector(".selectbox_option");
        const hiddenInput = wrapper.querySelector("input[type='hidden']");

        if (!toggleBtn || !options || !hiddenInput) return;

        if (hiddenInput.value) {
            const match = Array.from(options.querySelectorAll(".option-btn"))
                .find(o => o.getAttribute("data-value") === hiddenInput.value);
            const displayText = match ? match.textContent : hiddenInput.value;
            toggleBtn.innerHTML = `${displayText} <span class="material-symbols-rounded">arrow_drop_down</span>`;
        }

        toggleBtn.addEventListener("click", () => {
            options.classList.toggle("hide");
        });

        options.querySelectorAll(".option-btn").forEach(opt => {
            opt.addEventListener("click", () => {
                hiddenInput.value = opt.getAttribute("data-value");
                toggleBtn.innerHTML = opt.textContent + '<span class="material-symbols-rounded">arrow_drop_down</span>';
                options.classList.add("hide");
            });
        });

        document.addEventListener("click", e => {
            if (!wrapper.contains(e.target)) {
                options.classList.add("hide");
            }
        });
    });

    // ----------- 팝업 기능 -----------
    window.openCountryPopup = function() {
        const w = 780, h = 620, left = (screen.width - w) / 2, top = (screen.height - h) / 2;
        window.open('/global/country_form', 'countryPopup', `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    };

    window.setCountry = function(iso2, countryKr) {
        const disp = document.getElementById('nationalityDisplay');
        if (disp) disp.textContent = `${countryKr} (${iso2})`;
        const hidden = document.getElementById('nationalityInput');
        if (hidden) hidden.value = `${countryKr} (${iso2})`;
    };

    window.openRoadPopup = function() {
        const w = 900, h = 640, left = (screen.width - w) / 2, top = (screen.height - h) / 2;
        window.open('/global/road/popup', 'roadPopup', `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    };

    window.setRoad = function(addrStr) {
        const addr = document.getElementById('addr1');
        if (addr) addr.value = addrStr || '';
    };

    window.openJobPopup = function() {
        const w = 700, h = 520, left = (screen.width - w) / 2, top = (screen.height - h) / 2;
        window.open('/global/jobcode/popup', 'job_code', `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    };

    window.addEventListener('message', e => {
        if (e.data?.source !== 'jobcode-popup') return;
        const { rankCode, positionCode } = e.data.payload || {};
        const rankInput = document.getElementById('rank');
        if (rankInput) rankInput.value = rankCode || '';
        const positionInput = document.getElementById('position');
        if (positionInput) positionInput.value = positionCode || '';
    });

    // ----------- 리스트 선택 삭제 -----------
    const checkAll = document.getElementById('checkAll');
    if (checkAll) {
        checkAll.addEventListener('change', () => {
            document.querySelectorAll('.nurse-checkbox').forEach(cb => cb.checked = checkAll.checked);
        });
    }

    const deleteBtn = document.getElementById('deleteSelectedBtn');
    const deleteForm = document.getElementById('deleteForm');
    if (deleteBtn && deleteForm) {
        deleteBtn.addEventListener('click', () => {
            const checked = document.querySelectorAll('.nurse-checkbox:checked');
            if (checked.length === 0) {
                alert('삭제할 항목을 선택해주세요.');
                return;
            }
            if (confirm(`선택한 ${checked.length}개 항목을 삭제하시겠습니까?`)) {
                deleteForm.innerHTML = '';
                const csrfMeta = document.querySelector('meta[name="_csrf"]');
                const csrfHeader = csrfMeta ? csrfMeta.getAttribute('content') : '';
                const csrfInput = document.createElement('input');
                csrfInput.type = 'hidden';
                csrfInput.name = '_csrf';
                csrfInput.value = csrfHeader;
                deleteForm.appendChild(csrfInput);

                checked.forEach(cb => {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'checkedIds';
                    input.value = cb.value;
                    deleteForm.appendChild(input);
                });
                deleteForm.submit();
            }
        });
    }
});