
    function submitForm(event) {
        event.preventDefault(); // 링크 기본 동작 막기
        document.getElementById('registrationForm').submit(); // 폼 전송
    }

    function submitDeleteForm(id, page, size) {
        if (!confirm('정말 삭제하시겠습니까?')) {
            return; // 사용자가 취소하면 아무 동작 없음
        }

        const form = document.getElementById('deleteForm');
        form.querySelector('input[name="id"]').value = id;
        form.querySelector('input[name="page"]').value = page;
        form.querySelector('input[name="size"]').value = size;
        form.submit();
    }