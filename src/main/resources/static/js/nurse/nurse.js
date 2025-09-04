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

const addBtn = document.getElementById("addLicenseBtn");
    const cancelBtn = document.getElementById("cancelLicenseBtn");
    const licenseFormBox = document.getElementById("licenseFormBox");

    addBtn.addEventListener("click", () => {
        licenseFormBox.style.display = "block"; // 폼 표시
        addBtn.style.display = "none";          // 버튼 숨김
    });

    cancelBtn.addEventListener("click", () => {
        licenseFormBox.style.display = "none";  // 폼 숨김
        addBtn.style.display = "inline-block";  // 버튼 다시 표시
    });