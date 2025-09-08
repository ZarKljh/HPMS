$('.navbar-toggle').click(function() {
	let a = $('.menubox_2').hasClass('active');

	if(a) {
		$('.menubox_2').removeClass('active');
	} else {
		$('.menubox_2').addClass('active');
	}
});

const delete_elements = document.getElementsByClassName("delete");
    Array.from(delete_elements).forEach(function(element) {
        element.addEventListener('click', function() {
            if (confirm("정말로 삭제하시겠습니까?")) {
                location.href = this.dataset.uri;
            };
        });
    });

document.addEventListener("DOMContentLoaded", function() {
    const showBtn = document.getElementById("showLicenseFormBtn");
    const cancelBtn = document.getElementById("cancelLicenseFormBtn");
    const form = document.getElementById("newLicenseForm");
    const noMsg = document.getElementById("noLicenseMsg");
    const btnBox = document.getElementById("addLicenseBtnBox");

    // 자격증 추가 버튼 클릭 시
    showBtn.addEventListener("click", function() {
        form.style.display = "block";     // 폼 보여주기
        btnBox.style.display = "none";    // 버튼 숨기기
        if (noMsg) noMsg.style.display = "none";  // 메시지 숨기기
        form.scrollIntoView({ behavior: "smooth", block: "start" }); // 스크롤 이동
    });

    // 취소 버튼 클릭 시
    cancelBtn.addEventListener("click", function() {
        form.style.display = "none";      // 폼 숨기기
        btnBox.style.display = "block";   // 버튼 복원
        if (noMsg) noMsg.style.display = "block"; // 메시지 복원
        btnBox.scrollIntoView({ behavior: "smooth", block: "center" }); // 스크롤 이동
    });
});

//검색 스크립트 추가
const page_elements = document.getElementsByClassName("page-link");
Array.from(page_elements).forEach(function(element) {
    element.addEventListener('click', function() {
        document.getElementById('page').value = this.dataset.page;
        document.getElementById('searchForm').submit();
    });
});
const btn_search = document.getElementById("btn_search");
btn_search.addEventListener('click', function() {
    document.getElementById('kw').value = document.getElementById('search_kw').value;
    document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
    document.getElementById('searchForm').submit();
});