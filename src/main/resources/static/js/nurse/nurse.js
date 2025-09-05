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

const showLicenseFormBtn = document.getElementById('showLicenseFormBtn');
    const licenseForm = document.getElementById('licenseForm');

    showLicenseFormBtn.addEventListener('click', function() {
        // 토글 방식: 숨겨져 있으면 보이게, 보이면 숨기기
        if (licenseForm.style.display === 'none') {
            licenseForm.style.display = 'flex'; // form이 row g-2라 flex가 적합
        } else {
            licenseForm.style.display = 'none';
        }
    });