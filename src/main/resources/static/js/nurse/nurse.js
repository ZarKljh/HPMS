// =======================
// 1. 선택 삭제 기능
// =======================
function deleteSelected() {
    console.log('deleteSelected 함수 호출됨');

    const checkedBoxes = document.querySelectorAll('.nurse-checkbox:checked');
    console.log('선택된 체크박스 개수:', checkedBoxes.length);

    if (checkedBoxes.length === 0) {
        alert('삭제할 항목을 선택해주세요.');
        return;
    }

    if (!confirm(`선택된 ${checkedBoxes.length}명의 간호사 정보를 삭제하시겠습니까?`)) {
        return;
    }

    // deleteForm 준비
    let form = document.getElementById('deleteForm');
    if (!form) {
        console.log('deleteForm 없음 - 새로 생성');
        form = document.createElement('form');
        form.id = 'deleteForm';
        form.method = 'POST';
        form.action = '/nurse/deleteMultiple';
        form.style.display = 'none';
        document.body.appendChild(form);
    }
    form.innerHTML = '';

    // CSRF 토큰 추가
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    if (csrfToken) {
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf';
        csrfInput.value = csrfToken;
        form.appendChild(csrfInput);
    } else {
        console.error('CSRF 토큰을 찾을 수 없음');
    }

    // 선택된 ID 추가
    checkedBoxes.forEach(cb => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'checkedIds';
        input.value = cb.value;
        form.appendChild(input);
    });

    console.log('폼 제출 시작');
    form.submit();
}

// =======================
// 2. 전역 유틸 함수
// =======================

// 주소 팝업
function openRoadPopup() {
    const w = 900, h = 640;
    const left = (screen.width - w)/2, top = (screen.height - h)/2;
    window.open('/global/road/popup', 'roadPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
}
function setRoad(addrStr, roadCode, emdSeqNo) {
    const addr1Element = document.getElementById('addr1');
    if (addr1Element) addr1Element.value = addrStr || '';
}

// input number length 제한
function numberMaxLength(e){
    if(e.value.length > e.maxLength){
        e.value = e.value.slice(0, e.maxLength);
    }
}

// 국적 팝업
function openCountryPopup() {
    const w = 780, h = 620;
    const left = (screen.width - w) / 2;
    const top = (screen.height - h) / 2;
    window.open('/global/country_form', 'countryPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
}
function setCountry(iso2, countryKr) {
    const disp  = document.getElementById('nationalityDisplay');
    const input = document.getElementById('nationalityInput');
    const combined = `${countryKr} (${iso2})`;
    if (disp)  disp.textContent = combined;
    if (input) input.value = combined;
}

// =======================================================================================
// 메인 DOM 로드 이벤트
// =======================================================================================
document.addEventListener("DOMContentLoaded", function() {
    console.log('DOM 로드 완료');

    // =======================
    // 공통 유틸 함수들
    // =======================

    // 이메일 유효성 검사
    function emailCheck(email_address) {
        email_address = email_address.trim().replace(/\s+/g, '');
        const email_regex = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/i;
        return email_regex.test(email_address);
    }

    // 전화번호 유효성 검사
    function isHyphenPhoneNumber(tel) {
        const regex = /^0\d{1,2}-\d{3,4}-\d{4}$/;
        return regex.test(tel);
    }

    // 에러 메시지 표시 함수
    function showError(field, message) {
        clearError(field);

        const errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        errorElement.style.cssText = 'color: red; font-size: 12px; margin-top: 5px;';
        errorElement.textContent = message;

        field.parentNode.appendChild(errorElement);
        field.style.borderColor = 'red';
    }

    // 에러 메시지 제거 함수
    function clearError(field) {
        const errorMessage = field.parentNode.querySelector('.error-message');
        if (errorMessage) {
            errorMessage.remove();
        }
        field.style.borderColor = '';
    }

    // =======================
    // 공통 셀렉트박스 처리
    // =======================
    const selectboxes = document.querySelectorAll('[data-role="selectbox"], .select-wrapper');

    selectboxes.forEach(selectbox => {
        const toggleBtn = selectbox.querySelector('.toggle_btn');
        const optionList = selectbox.querySelector('.selectbox_option');
        const hiddenInput = selectbox.querySelector('input[type="hidden"]');
        const optionBtns = selectbox.querySelectorAll('.option-btn');

        if (!toggleBtn || !optionList || !hiddenInput) return;

        // 현재 값으로 초기 설정 (수정 폼용)
        if (hiddenInput.value) {
            const currentOption = Array.from(optionBtns).find(btn =>
                btn.getAttribute('data-value') === hiddenInput.value
            );
            if (currentOption) {
                toggleBtn.childNodes[0].textContent = currentOption.textContent;
            }
        }

        // 드롭다운 토글
        toggleBtn.addEventListener('click', function(e) {
            e.preventDefault();
            optionList.classList.toggle('hide');
        });

        // 옵션 선택
        optionBtns.forEach(btn => {
            btn.addEventListener('click', function(e) {
                e.preventDefault();
                const value = this.getAttribute('data-value');
                const text = this.textContent;

                hiddenInput.value = value;
                toggleBtn.childNodes[0].textContent = text;
                optionList.classList.add('hide');

                // active 스타일 적용
                optionBtns.forEach(b => b.classList.remove('active'));
                this.classList.add('active');

                // 에러 메시지 제거
                clearError(hiddenInput);
            });
        });

        // 외부 클릭시 드롭다운 닫기
        document.addEventListener('click', function(e) {
            if (!selectbox.contains(e.target)) {
                optionList.classList.add('hide');
            }
        });
    });

    // =======================================================================================
    // 공통 폼 유효성 검사 함수
    // =======================================================================================

    // 필드별 유효성 검사 함수
    function validateField(field, formType = 'general') {
        const fieldName = field.name;
        const value = field.value.trim();
        let isValid = true;

        clearError(field);

        // nurseMain과 nurseInformation 필드명 처리
        const actualFieldName = fieldName.includes('.') ?
            fieldName.split('.')[1] : fieldName;

        switch (actualFieldName) {
            case 'dept':
                if (!value) {
                    showError(field, '부서를 선택해주세요.');
                    isValid = false;
                }
                break;

            case 'rank':
                if (!value) {
                    showError(field, '직급을 선택해주세요.');
                    isValid = false;
                }
                break;

            case 'firstName':
                const isRequired = field.hasAttribute('required');
                if (isRequired && !value) {
                    showError(field, '이름을 입력해주세요.');
                    isValid = false;
                } else if (value && value.length > 50) {
                    showError(field, '이름은 50자 이하로 입력해주세요.');
                    isValid = false;
                } else if (value && !/^[가-힣a-zA-Z\s]+$/.test(value)) {
                    showError(field, '이름은 한글 또는 영문만 입력 가능합니다.');
                    isValid = false;
                }
                break;

            case 'lastName':
                const isLastNameRequired = field.hasAttribute('required');
                if (isLastNameRequired && !value) {
                    showError(field, '성을 입력해주세요.');
                    isValid = false;
                } else if (value && value.length > 50) {
                    showError(field, '성은 50자 이하로 입력해주세요.');
                    isValid = false;
                } else if (value && !/^[가-힣a-zA-Z\s]+$/.test(value)) {
                    showError(field, '성은 한글 또는 영문만 입력 가능합니다.');
                    isValid = false;
                }
                break;

            case 'middleName':
                if (value && value.length > 50) {
                    showError(field, '중간이름은 50자 이하로 입력해주세요.');
                    isValid = false;
                } else if (value && !/^[가-힣a-zA-Z\s]+$/.test(value)) {
                    showError(field, '중간이름은 한글 또는 영문만 입력 가능합니다.');
                    isValid = false;
                }
                break;

            case 'tel':
            case 'emgcCntc':
                if (!value) {
                    showError(field, '전화번호를 입력해주세요.');
                    isValid = false;
                } else if (!isHyphenPhoneNumber(value)) {
                    showError(field, '전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)');
                    isValid = false;
                }
                break;

            case 'emgcFName':
                if (!value) {
                    showError(field, '비상연락처 이름을 입력해주세요.');
                    isValid = false;
                } else if (value.length > 50) {
                    showError(field, '이름은 50자 이하로 입력해주세요.');
                    isValid = false;
                } else if (!/^[가-힣a-zA-Z\s]+$/.test(value)) {
                    showError(field, '이름은 한글 또는 영문만 입력 가능합니다.');
                    isValid = false;
                }
                break;

            case 'emgcLName':
                if (!value) {
                    showError(field, '비상연락처 성을 입력해주세요.');
                    isValid = false;
                } else if (value.length > 50) {
                    showError(field, '성은 50자 이하로 입력해주세요.');
                    isValid = false;
                } else if (!/^[가-힣a-zA-Z\s]+$/.test(value)) {
                    showError(field, '성은 한글 또는 영문만 입력 가능합니다.');
                    isValid = false;
                }
                break;

            case 'emgcMName':
                if (value && value.length > 50) {
                    showError(field, '중간이름은 50자 이하로 입력해주세요.');
                    isValid = false;
                } else if (value && !/^[가-힣a-zA-Z\s]+$/.test(value)) {
                    showError(field, '중간이름은 한글 또는 영문만 입력 가능합니다.');
                    isValid = false;
                }
                break;

            case 'emgcRel':
                if (!value) {
                    showError(field, '관계를 입력해주세요.');
                    isValid = false;
                } else if (value.length > 10) {
                    showError(field, '관계는 10자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'emgcNote':
                if (value && value.length > 250) {
                    showError(field, '비고는 250자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'email':
                if (!value) {
                    showError(field, '이메일을 입력해주세요.');
                    isValid = false;
                } else if (!emailCheck(value)) {
                    showError(field, '유효하지 않은 이메일 주소입니다.');
                    isValid = false;
                }
                break;

            case 'pcd':
                if (!value) {
                    showError(field, '우편번호를 입력해주세요.');
                    isValid = false;
                } else if (!/^\d{5}$/.test(value)) {
                    showError(field, '우편번호는 5자리 숫자로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'defAdd':
                if (!value) {
                    showError(field, '주소를 입력해주세요.');
                    isValid = false;
                } else if (value.length > 250) {
                    showError(field, '주소는 250자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'detAdd':
                if (value && value.length > 250) {
                    showError(field, '상세주소는 250자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'rnNo':
                if (!value) {
                    showError(field, '간호사 자격증 번호를 입력해주세요.');
                    isValid = false;
                } else if (!/^\d{6}$/.test(value)) {
                    showError(field, '자격증 번호는 6자리 숫자로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'edbc':
                if (!value) {
                    showError(field, '최종학력을 입력해주세요.');
                    isValid = false;
                } else if (value.length > 50) {
                    showError(field, '최종학력은 50자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'gradDate':
            case 'dateOfBirth':
            case 'hireDate':
                if (!value) {
                    let fieldLabel = actualFieldName === 'gradDate' ? '졸업날짜' :
                                   actualFieldName === 'dateOfBirth' ? '생년월일' : '입사일';
                    showError(field, `${fieldLabel}을 입력해주세요.`);
                    isValid = false;
                } else if (!/^\d{8}$/.test(value)) {
                    let fieldLabel = actualFieldName === 'gradDate' ? '졸업날짜' :
                                   actualFieldName === 'dateOfBirth' ? '생년월일' : '입사일';
                    showError(field, `${fieldLabel}은 YYYYMMDD 형식으로 8자리 숫자를 입력해주세요.`);
                    isValid = false;
                } else {
                    // 날짜 유효성 검사
                    const year = parseInt(value.substring(0, 4));
                    const month = parseInt(value.substring(4, 6));
                    const day = parseInt(value.substring(6, 8));

                    const currentYear = new Date().getFullYear();
                    const maxYear = actualFieldName === 'hireDate' ? currentYear + 1 : currentYear;

                    if (year < 1900 || year > maxYear) {
                        showError(field, '올바른 연도를 입력해주세요.');
                        isValid = false;
                    } else if (month < 1 || month > 12) {
                        showError(field, '올바른 월을 입력해주세요.');
                        isValid = false;
                    } else if (day < 1 || day > 31) {
                        showError(field, '올바른 일을 입력해주세요.');
                        isValid = false;
                    } else {
                        // 실제 날짜 유효성 확인
                        const testDate = new Date(year, month - 1, day);
                        if (testDate.getFullYear() !== year ||
                            testDate.getMonth() !== month - 1 ||
                            testDate.getDate() !== day) {
                            showError(field, '존재하지 않는 날짜입니다.');
                            isValid = false;
                        }
                    }
                }
                break;

            case 'fl':
                if (value && value.length > 250) {
                    showError(field, '외국어는 250자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'ms':
                if (!value) {
                    showError(field, '병역을 선택해주세요.');
                    isValid = false;
                }
                break;

            case 'natn':
                if (!value) {
                    showError(field, '국적을 선택해주세요.');
                    isValid = false;
                }
                break;

            case 'dss':
                if (!value) {
                    showError(field, '장애여부를 선택해주세요.');
                    isValid = false;
                }
                break;

            case 'carr':
                if (!value) {
                    showError(field, '경력을 입력해주세요.');
                    isValid = false;
                } else if (!/^\d{1,5}$/.test(value)) {
                    showError(field, '경력은 숫자로 입력해주세요. (최대 5자리)');
                    isValid = false;
                }
                break;

            case 'picture':
                if (!value) {
                    showError(field, '사진 URL을 입력해주세요.');
                    isValid = false;
                } else if (value.length > 250) {
                    showError(field, '사진 URL은 250자 이하로 입력해주세요.');
                    isValid = false;
                } else if (!/^https?:\/\/.+\.(jpg|jpeg|png|gif|webp)$/i.test(value)) {
                    showError(field, '올바른 이미지 URL을 입력해주세요. (jpg, png, gif, webp 형식)');
                    isValid = false;
                }
                break;

            case 'sts':
                if (!value) {
                    showError(field, '상태를 입력해주세요.');
                    isValid = false;
                } else if (value.length > 3) {
                    showError(field, '상태는 3자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'wt':
                if (!value) {
                    showError(field, '근무형태를 입력해주세요.');
                    isValid = false;
                } else if (value.length > 4) {
                    showError(field, '근무형태는 4자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;

            case 'writer':
            case 'modifier':
                const writerLabel = actualFieldName === 'writer' ? '작성자' : '수정자';
                if (!value) {
                    showError(field, `${writerLabel}를 입력해주세요.`);
                    isValid = false;
                } else if (value.length > 250) {
                    showError(field, `${writerLabel}는 250자 이하로 입력해주세요.`);
                    isValid = false;
                }
                break;

            case 'note':
                if (value && value.length > 250) {
                    showError(field, '비고는 250자 이하로 입력해주세요.');
                    isValid = false;
                }
                break;
        }

        return isValid;
    }

    // =============================================================================================
    // 기본 정보 등록 폼 (/nurse/create) - 시작
    // =============================================================================================
    const mainForm = document.querySelector('form[action*="/nurse/create"]:not([action*="/info"])');
    if (mainForm) {
        console.log('기본 정보 등록 폼 발견');

        // 전체 폼 유효성 검사
        function validateMainForm() {
            let isFormValid = true;

            // 모든 필수 필드 검사
            const requiredFields = mainForm.querySelectorAll('input[required], input[type="hidden"][required]');
            requiredFields.forEach(field => {
                if (!validateField(field, 'main')) {
                    isFormValid = false;
                }
            });

            // 성별 라디오 버튼 검사
            const genderChecked = mainForm.querySelector('input[name="gender"]:checked');
            if (!genderChecked) {
                const genderInputs = mainForm.querySelectorAll('input[name="gender"]');
                if (genderInputs.length > 0) {
                    showError(genderInputs[0], '성별을 선택해주세요.');
                    isFormValid = false;
                }
            }

            return isFormValid;
        }

        // 입력 이벤트 처리 (에러 메시지 제거)
        const textInputs = mainForm.querySelectorAll('input[type="text"], input[type="number"]');
        textInputs.forEach(input => {
            input.addEventListener('input', function() {
                clearError(this);
            });
        });

        // 성별 라디오 버튼 에러 메시지 제거
        const genderInputs = mainForm.querySelectorAll('input[name="gender"]');
        genderInputs.forEach(input => {
            input.addEventListener('change', function() {
                const existingError = mainForm.querySelector('.error-message');
                if (existingError && existingError.textContent.includes('성별')) {
                    existingError.remove();
                }
            });
        });

        // 다음 버튼 클릭 이벤트 - 실제 버튼 구조에 맞게 수정
        const nextBtn = mainForm.querySelector('.next_btn_box .button-link:not([type="reset"]):not([onclick])');
        console.log('다음 버튼 찾기:', nextBtn);

        if (nextBtn) {
            // 기본 제출 방지를 위해 type을 button으로 설정
            if (nextBtn.type !== 'button') {
                nextBtn.type = 'button';
            }

            nextBtn.addEventListener('click', function(e) {
                e.preventDefault();
                console.log('다음 버튼 클릭됨');

                if (validateMainForm()) {
                    console.log('기본 정보 폼 유효성 검사 통과 - 제출');
                    mainForm.submit();
                } else {
                    console.log('기본 정보 폼 유효성 검사 실패');

                    // 첫 번째 에러 필드로 스크롤
                    const firstError = mainForm.querySelector('.error-message');
                    if (firstError) {
                        firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    }

                    alert('입력 정보를 다시 확인해주세요.');
                }
            });
        } else {
            // 대체 선택자로 다시 시도
            const alternativeBtn = mainForm.querySelector('button:contains("다음")') ||
                                 mainForm.querySelector('.button-link[href="#"]') ||
                                 Array.from(mainForm.querySelectorAll('button')).find(btn =>
                                     btn.textContent.trim() === '다음'
                                 );

            console.log('대체 버튼 찾기:', alternativeBtn);

            if (alternativeBtn) {
                alternativeBtn.type = 'button';
                alternativeBtn.addEventListener('click', function(e) {
                    e.preventDefault();
                    console.log('대체 다음 버튼 클릭됨');

                    if (validateMainForm()) {
                        console.log('기본 정보 폼 유효성 검사 통과 - 제출');
                        mainForm.submit();
                    } else {
                        console.log('기본 정보 폼 유효성 검사 실패');

                        const firstError = mainForm.querySelector('.error-message');
                        if (firstError) {
                            firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                        }

                        alert('입력 정보를 다시 확인해주세요.');
                    }
                });
            }
        }
    }
    // =============================================================================================
    // 기본 정보 등록 폼 (/nurse/create) - 끝
    // =============================================================================================

    // =============================================================================================
    // 상세 정보 등록 폼 (/nurse/create/info) - 시작
    // =============================================================================================
    const infoForm = document.querySelector('form[action*="/nurse/create/info"]');
    if (infoForm) {
        console.log('상세 정보 등록 폼 발견');

        // 전체 폼 유효성 검사
        function validateInfoForm() {
            let isFormValid = true;

            // 모든 필수 필드 검사
            const requiredFields = infoForm.querySelectorAll('input[required], input[type="hidden"][required]');
            requiredFields.forEach(field => {
                if (!validateField(field, 'info')) {
                    isFormValid = false;
                }
            });

            return isFormValid;
        }

        // 입력 이벤트 처리 (에러 메시지 제거)
        const textInputs = infoForm.querySelectorAll('input[type="text"], input[type="number"], textarea');
        textInputs.forEach(input => {
            input.addEventListener('input', function() {
                clearError(this);
            });
        });

        // 등록 버튼 클릭 이벤트
        const submitBtn = infoForm.querySelector('.save_btn') ||
                         Array.from(infoForm.querySelectorAll('button')).find(btn =>
                             btn.textContent.trim() === '등록'
                         );

        if (submitBtn) {
            submitBtn.type = 'button';
            submitBtn.addEventListener('click', function(e) {
                e.preventDefault();

                if (validateInfoForm()) {
                    console.log('상세 정보 폼 유효성 검사 통과 - 제출');
                    infoForm.submit();
                } else {
                    console.log('상세 정보 폼 유효성 검사 실패');

                    const firstError = infoForm.querySelector('.error-message');
                    if (firstError) {
                        firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    }

                    alert('입력 정보를 다시 확인해주세요.');
                }
            });
        }
    }
    // =============================================================================================
    // 상세 정보 등록 폼 (/nurse/create/info) - 끝
    // =============================================================================================

    // =============================================================================================
    // 정보 수정 폼 (/nurse/modify/{id}) - 시작
    // =============================================================================================
    const modifyForm = document.querySelector('form[action*="/nurse/modify"]');
    if (modifyForm) {
        console.log('정보 수정 폼 발견');

        // 전체 폼 유효성 검사
        function validateModifyForm() {
            let isFormValid = true;

            // 모든 필수 필드 검사
            const requiredFields = modifyForm.querySelectorAll('input[required], input[type="hidden"][required]');
            requiredFields.forEach(field => {
                if (!validateField(field, 'modify')) {
                    isFormValid = false;
                }
            });

            // 성별 라디오 버튼 검사
            const genderChecked = modifyForm.querySelector('input[name*="gender"]:checked');
            if (!genderChecked) {
                const genderInputs = modifyForm.querySelectorAll('input[name*="gender"]');
                if (genderInputs.length > 0) {
                    showError(genderInputs[0], '성별을 선택해주세요.');
                    isFormValid = false;
                }
            }

            return isFormValid;
        }

        // 입력 이벤트 처리 (에러 메시지 제거)
        const textInputs = modifyForm.querySelectorAll('input[type="text"], input[type="number"], textarea');
        textInputs.forEach(input => {
            input.addEventListener('input', function() {
                clearError(this);
            });
        });

        // 성별 라디오 버튼 에러 메시지 제거
        const genderInputs = modifyForm.querySelectorAll('input[name*="gender"]');
        genderInputs.forEach(input => {
            input.addEventListener('change', function() {
                const existingError = modifyForm.querySelector('.error-message');
                if (existingError && existingError.textContent.includes('성별')) {
                    existingError.remove();
                }
            });
        });

        // 저장 버튼 클릭 이벤트
        const saveBtn = Array.from(modifyForm.querySelectorAll('button')).find(btn =>
            btn.textContent.trim() === '저장'
        );

        if (saveBtn) {
            saveBtn.type = 'button';
            saveBtn.addEventListener('click', function(e) {
                e.preventDefault();

                if (validateModifyForm()) {
                    console.log('정보 수정 폼 유효성 검사 통과 - 제출');
                    modifyForm.submit();
                } else {
                    console.log('정보 수정 폼 유효성 검사 실패');

                    const firstError = modifyForm.querySelector('.error-message');
                    if (firstError) {
                        firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    }

                    alert('입력 정보를 다시 확인해주세요.');
                }
            });
        }
    }
    // =============================================================================================
    // 정보 수정 폼 (/nurse/modify/{id}) - 끝
    // =============================================================================================

    // =============================================================================================
    // 기타 기능들 (선택 삭제, 체크박스, 검색, 로그인 등) - 시작
    // =============================================================================================

    // 선택 삭제 버튼
    const deleteBtn = document.getElementById('deleteSelectedBtn');
    if (deleteBtn) deleteBtn.addEventListener('click', deleteSelected);

    // 체크박스 전체 선택/해제
    const checkAll = document.getElementById('checkAll');
    if (checkAll) {
        checkAll.addEventListener('change', function() {
            document.querySelectorAll('.nurse-checkbox')
                .forEach(cb => cb.checked = this.checked);
        });
    }

    // 개별 삭제 버튼
    const deleteElements = document.getElementsByClassName("delete");
    Array.from(deleteElements).forEach(element => {
        element.addEventListener('click', function() {
            if (confirm("정말로 삭제하시겠습니까?")) {
                location.href = this.dataset.uri;
            }
        });
    });

    // Navbar 토글
    const navbarToggle = document.querySelector('.navbar-toggle');
    if (navbarToggle) {
        navbarToggle.addEventListener('click', () => {
            const menubox = document.querySelector('.menubox_2');
            if (menubox) menubox.classList.toggle('active');
        });
    }

    // 자격증 추가/취소 버튼
    const showBtn = document.getElementById("showLicenseFormBtn");
    const cancelBtn = document.getElementById("cancelLicenseFormBtn");
    const licenseForm = document.getElementById("newLicenseForm");
    const noMsg = document.getElementById("noLicenseMsg");
    const btnBox = document.getElementById("addLicenseBtnBox");

    if (showBtn && licenseForm && btnBox) {
        showBtn.addEventListener("click", () => {
            licenseForm.style.display = "block";
            btnBox.style.display = "none";
            if (noMsg) noMsg.style.display = "none";
            licenseForm.scrollIntoView({ behavior: "smooth" });
        });

        if (cancelBtn) {
            cancelBtn.addEventListener("click", () => {
                licenseForm.style.display = "none";
                btnBox.style.display = "block";
                if (noMsg) noMsg.style.display = "block";
                btnBox.scrollIntoView({ behavior: "smooth" });
            });
        }
    }

    // 검색 & 페이지 이동
    const pageElements = document.getElementsByClassName("page-link");
    Array.from(pageElements).forEach(element => {
        element.addEventListener('click', function() {
            document.getElementById('page').value = this.dataset.page;
            document.getElementById('searchForm').submit();
        });
    });

    const btnSearch = document.getElementById("btn_search");
    if (btnSearch) {
        btnSearch.addEventListener('click', () => {
            const searchInput = document.getElementById('search_kw');
            const kwInput = document.getElementById('kw');
            const pageInput = document.getElementById('page');
            if (kwInput && searchInput) kwInput.value = searchInput.value;
            if (pageInput) pageInput.value = 0;
            document.getElementById('searchForm').submit();
        });
    }

    // 로그인 폼 입력 검증
    const tabs = document.querySelectorAll('#myTab button[data-bs-toggle="tab"]');
    tabs.forEach(tab => {
        tab.addEventListener('shown.bs.tab', function (event) {
            const targetPane = document.querySelector(event.target.getAttribute('data-bs-target'));
            if (targetPane) {
                const firstInput = targetPane.querySelector('input[type="text"]');
                if (firstInput) firstInput.focus();
            }
        });
    });

    const loginForms = document.querySelectorAll('#guest-nav-home, #official-nav-home');
    loginForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const username = this.querySelector('input[name="username"]').value.trim();
            const password = this.querySelector('input[name="password"]').value.trim();
            if (!username || !password) {
                e.preventDefault();
                alert('아이디와 비밀번호를 입력해주세요.');
            }
        });
    });

    // =============================================================================================
    // 기타 기능들 (선택 삭제, 체크박스, 검색, 로그인 등) - 끝
    // =============================================================================================

});

// 디버깅용 테스트 코드 - 콘솔에서 실행하거나 페이지에 임시로 추가

document.addEventListener("DOMContentLoaded", function() {
    console.log('=== 디버깅 시작 ===');

    // 1. 모든 폼 찾기
    const allForms = document.querySelectorAll('form');
    console.log('페이지의 모든 폼:', allForms);
    console.log('폼 개수:', allForms.length);

    allForms.forEach((form, index) => {
        console.log(`폼 ${index}:`, form);
        console.log(`  action:`, form.getAttribute('action'));
        console.log(`  th:action:`, form.getAttribute('th:action'));
    });

    // 2. 각 폼별로 찾기 시도
    console.log('=== 폼 찾기 테스트 ===');

    // 기본 정보 폼
    const mainForm1 = document.querySelector('form[th\\:action*="/nurse/create"]');
    const mainForm2 = document.querySelector('form[action*="/nurse/create"]');
    console.log('기본 정보 폼 (th:action):', mainForm1);
    console.log('기본 정보 폼 (action):', mainForm2);

    // 상세 정보 폼
    const infoForm1 = document.querySelector('form[th\\:action*="/nurse/create/info"]');
    const infoForm2 = document.querySelector('form[action*="/nurse/create/info"]');
    console.log('상세 정보 폼 (th:action):', infoForm1);
    console.log('상세 정보 폼 (action):', infoForm2);

    // 수정 폼
    const modifyForm1 = document.querySelector('form[th\\:action*="/nurse/modify"]');
    const modifyForm2 = document.querySelector('form[action*="/nurse/modify"]');
    console.log('수정 폼 (th:action):', modifyForm1);
    console.log('수정 폼 (action):', modifyForm2);

    // 3. 버튼 찾기
    console.log('=== 버튼 찾기 테스트 ===');

    const submitButtons = document.querySelectorAll('button[type="submit"]');
    const allButtons = document.querySelectorAll('button');

    console.log('submit 타입 버튼들:', submitButtons);
    console.log('모든 버튼들:', allButtons);

    allButtons.forEach((btn, index) => {
        console.log(`버튼 ${index}:`, btn.textContent.trim(), btn.type, btn.className);
    });

    // 4. 실제 폼 찾기 (더 유연한 방식)
    console.log('=== 유연한 폼 찾기 ===');

    // action 속성으로 찾기
    const createForm = document.querySelector('form[action*="create"]');
    const createInfoForm = document.querySelector('form[action*="create/info"]');
    const modifyFormAlt = document.querySelector('form[action*="modify"]');

    console.log('create 폼:', createForm);
    console.log('create/info 폼:', createInfoForm);
    console.log('modify 폼:', modifyFormAlt);

    console.log('=== 디버깅 끝 ===');
});

// 수동 테스트용 함수들
function testFormValidation() {
    console.log('=== 수동 폼 테스트 ===');

    // 첫 번째 폼 선택
    const firstForm = document.querySelector('form');
    if (firstForm) {
        console.log('첫 번째 폼 찾음:', firstForm);

        // 필수 필드들 확인
        const requiredFields = firstForm.querySelectorAll('input[required]');
        console.log('필수 필드들:', requiredFields);

        // 버튼 확인
        const buttons = firstForm.querySelectorAll('button');
        console.log('폼 내 버튼들:', buttons);

        // 간단한 검증 함수 테스트
        if (requiredFields.length > 0) {
            const firstField = requiredFields[0];
            console.log('첫 번째 필수 필드:', firstField.name, firstField.value);

            if (!firstField.value) {
                console.log('첫 번째 필수 필드가 비어있음');
            }
        }
    } else {
        console.log('폼을 찾을 수 없음');
    }
}

// 브라우저 콘솔에서 실행: testFormValidation()