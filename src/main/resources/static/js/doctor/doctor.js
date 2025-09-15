    // 선택 삭제
  document.getElementById('deleteSelected').addEventListener('click', function() {
    const ids = Array.from(document.querySelectorAll('input[name="ids"]:checked')).map(cb => cb.value);
    if (ids.length === 0) {
      alert('삭제할 항목을 선택하세요.');
      return;
    }
    if (!confirm(ids.length + '건을 삭제하시겠습니까?')) return;

    const form = document.createElement('form');
    form.method = 'post';
    form.action = '/doctor/deleteSelected'; // ✅ 컨트롤러에서 처리
    ids.forEach(id => {
      const input = document.createElement('input');
      input.type = 'hidden';
      input.name = 'ids';
      input.value = id;
      form.appendChild(input);
    });
    document.body.appendChild(form);
    form.submit();
  });
    document.getElementById('selectAll').addEventListener('change', function(e) {
      const checked = e.target.checked;
      document.querySelectorAll('input[name="ids"]').forEach(cb => cb.checked = checked);
    });