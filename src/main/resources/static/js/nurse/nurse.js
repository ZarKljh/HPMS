// =======================
// 통합 스크립트 (유효성 + 팝업 + 삭제)
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
        if (!field) return;
        const error = field.parentNode.querySelector('.error-message');
        if (error) error.remove();
        field.style.borderColor = '';
    }

    // ----------- 필드 유효성 -----------
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
            case 'tel': case 'emgcCntc':
                if(!value){ showError(field,'전화번호를 입력해주세요.'); isValid=false; }
                else if(!isHyphenPhoneNumber(value)){ showError(field,'전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)'); isValid=false; }
                break;
            case 'email':
                if(!value){ showError(field,'이메일을 입력해주세요.'); isValid=false; }
                else if(!emailCheck(value)){ showError(field,'유효하지 않은 이메일 주소입니다.'); isValid=false; }
                break;
            case 'pcd':
                if(!value){ showError(field,'우편번호를 입력해주세요.'); isValid=false; }
                else if(!/^\d{5,6}$/.test(value)){ showError(field,'우편번호는 5~6자리 숫자로 입력해주세요.'); isValid=false; }
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
                else if(value.length > 20){ showError(field,'자격증 번호는 20자 이하로 입력해주세요.'); isValid=false; }
                break;
            case 'gradDate': case 'dateOfBirth': case 'hireDate':
                if(!value){ showError(field,'날짜를 입력해주세요.'); isValid=false; }
                else if(!/^\d{8}$/.test(value)){ showError(field,'YYYYMMDD 형식으로 입력해주세요.'); isValid=false; }
                break;
            case 'pictureFile':
                const fileInput = document.getElementById('pictureFile');
                const existingPicture = document.querySelector('input[name="existingPicture"]');
                const hasNewFile = fileInput && fileInput.files.length>0;
                const hasExistingPicture = existingPicture && existingPicture.value;
                if(!hasNewFile && !hasExistingPicture){ showError(fileInput||field,'사진을 선택해주세요.'); isValid=false; }
                else if(hasNewFile){
                    const file = fileInput.files[0];
                    const maxSize = 5*1024*1024;
                    const allowed = ['image/jpeg','image/jpg','image/png','image/gif','image/webp'];
                    if(file.size>maxSize){ showError(fileInput,'파일 크기는 5MB 이하입니다.'); isValid=false; }
                    else if(!allowed.includes(file.type)){ showError(fileInput,'지원하지 않는 파일 형식입니다.'); isValid=false; }
                }
                break;
            case 'ms':
                const hiddenInput = document.querySelector('input[name="ms"]');
                if (!hiddenInput || !hiddenInput.value) {
                    showError(hiddenInput, '병역을 선택해주세요.');
                    isValid = false;
                }
                break;
            case 'natn':
                if(!value){ showError(field,'국적을 선택해주세요.'); isValid=false; }
                break;
        }
        return isValid;
    }

    // ----------- 폼 전체 유효성 -----------
    function validateForm(form){
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

    // ----------- 제출 버튼 이벤트 -----------
    document.querySelectorAll('form').forEach(form=>{
        const submitBtn = Array.from(form.querySelectorAll('button, input[type="submit"]'))
            .find(btn => /등록|다음|저장/.test(btn.textContent));
        if(submitBtn){
            submitBtn.type='button';
            submitBtn.addEventListener('click', function(){
                if(validateForm(form)) form.submit();
                else{
                    const firstError=form.querySelector('.error-message');
                    if(firstError) firstError.scrollIntoView({behavior:'smooth', block:'center'});
                    alert('입력 정보를 다시 확인해주세요.');
                }
            });
        }
    });

    // ----------- 사진 미리보기 -----------
    const fileInput = document.getElementById("pictureFile");
    const previewImg = document.getElementById("picturePreview");
    const existingPicture = document.querySelector("input[name='existingPicture']");
    if (fileInput){
        fileInput.addEventListener("change", function(event){
            const file = event.target.files[0];
            if(!file) return;
            const maxSize = 5*1024*1024;
            const allowed = ["image/jpeg","image/png","image/jpg","image/gif","image/webp"];
            if(file.size>maxSize){ alert("파일 크기는 5MB 이하만 업로드 가능합니다."); fileInput.value=""; return; }
            if(!allowed.includes(file.type)){ alert("허용되지 않는 파일 형식입니다."); fileInput.value=""; return; }
            const reader = new FileReader();
            reader.onload=function(e){ if(previewImg) previewImg.src=e.target.result; }
            reader.readAsDataURL(file);
            if(existingPicture) existingPicture.value="";
        });
    }
    // ----------- function previewImage(event){...} 정의 -----------
    function previewImage(event) {
        const file = event.target.files[0];
        const preview = document.getElementById("picturePreview");
        if (file) {
            preview.src = URL.createObjectURL(file);
            preview.style.display = "block";
        } else {
            preview.src = "";
            preview.style.display = "none";
        }
    }


    // ----------- selectbox 처리 -----------
    document.querySelectorAll(".select-wrapper").forEach(wrapper=>{
        const toggleBtn = wrapper.querySelector(".toggle_btn");
        const options = wrapper.querySelector(".selectbox_option");
        const hiddenInput = wrapper.querySelector("input[type='hidden']");
        toggleBtn.addEventListener("click",()=> options.classList.toggle("hide"));
        options.querySelectorAll(".option-btn").forEach(opt=>{
            opt.addEventListener("click",()=>{
                hiddenInput.value=opt.getAttribute("data-value");
                toggleBtn.textContent=opt.textContent;
                options.classList.add("hide");
            });
        });
        document.addEventListener("click",e=>{ if(!wrapper.contains(e.target)) options.classList.add("hide"); });
    });

    // ----------- 팝업 기능 -----------
    window.openCountryPopup=function(){
        const w=780,h=620,left=(screen.width-w)/2,top=(screen.height-h)/2;
        window.open('/global/country_form','countryPopup',`width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    };
    window.setCountry=function(iso2,countryKr){
        const disp=document.getElementById('nationalityDisplay'); if(disp) disp.textContent=`${countryKr} (${iso2})`;
        const hidden=document.getElementById('nationalityInput'); if(hidden) hidden.value=`${countryKr} (${iso2})`;
    };
    window.openRoadPopup=function(){
        const w=900,h=640,left=(screen.width-w)/2,top=(screen.height-h)/2;
        window.open('/global/road/popup','roadPopup',`width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    };
    window.setRoad=function(addrStr){
        const addr=document.getElementById('addr1'); if(addr) addr.value=addrStr||'';
    };
    window.openJobPopup=function(){
        const w=700,h=520,left=(screen.width-w)/2,top=(screen.height-h)/2;
        window.open('/global/jobcode/popup','job_code',`width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    };
    window.addEventListener('message',e=>{
        if(e.data?.source!=='jobcode-popup') return;
        const {rankCode,positionCode}=e.data.payload||{};
        const rankInput=document.getElementById('rank'); if(rankInput) rankInput.value=rankCode||'';
        const positionInput=document.getElementById('position'); if(positionInput) positionInput.value=positionCode||'';
    });

    // ----------- 리스트 선택 삭제 -----------
    const checkAll=document.getElementById('checkAll');
    if(checkAll){
        checkAll.addEventListener('change',()=>{
            document.querySelectorAll('.nurse-checkbox').forEach(cb=>cb.checked=checkAll.checked);
        });
    }
    const deleteBtn=document.getElementById('deleteSelectedBtn');
    const deleteForm=document.getElementById('deleteForm');
    if(deleteBtn && deleteForm){
        deleteBtn.addEventListener('click',()=>{
            const checked=document.querySelectorAll('.nurse-checkbox:checked');
            if(checked.length===0){ alert('삭제할 항목을 선택해주세요.'); return; }
            if(confirm(`선택한 ${checked.length}개 항목을 삭제하시겠습니까?`)){
                deleteForm.innerHTML='';
                const csrfMeta=document.querySelector('meta[name="_csrf"]');
                const csrfHeader=csrfMeta?csrfMeta.getAttribute('content'):'';
                const csrfInput=document.createElement('input');
                csrfInput.type='hidden'; csrfInput.name='_csrf'; csrfInput.value=csrfHeader;
                deleteForm.appendChild(csrfInput);
                checked.forEach(cb=>{
                    const input=document.createElement('input');
                    input.type='hidden'; input.name='checkedIds'; input.value=cb.value;
                    deleteForm.appendChild(input);
                });
                deleteForm.submit();
            }
        });
    }
});