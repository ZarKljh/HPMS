   // ✅ 현재 페이지 테이블의 체크박스만 대상으로 제한
   const $selectAll = document.getElementById('selectAll');
   const itemSelector = 'tbody input[type="checkbox"][name="ids"]';
   const getItems = () => document.querySelectorAll(itemSelector);

   function setAll(checked) { getItems().forEach(cb => cb.checked = checked); }

   function syncMaster() {
     const items = getItems();
     const total = items.length;
     const checked = [...items].filter(cb => cb.checked).length;
     $selectAll.checked = (total > 0 && checked === total);
     $selectAll.indeterminate = (checked > 0 && checked < total); // 부분 선택 표시
   }

   // ✅ 전체선택 클릭 시: 부분 선택 상태면 "전부 해제"로 동작하게
   $selectAll.addEventListener('click', (e) => {
     const items = getItems();
     const total = items.length;
     const checked = [...items].filter(cb => cb.checked).length;

     if ($selectAll.indeterminate || (checked > 0 && checked < total)) {
       // 부분 선택 상태 → 한 번에 전부 해제
       e.preventDefault(); // 기본 토글 막고 직접 처리
       $selectAll.indeterminate = false;
       $selectAll.checked = false;
       setAll(false);
     }
     // 그 외에는 change 핸들러가 처리 (전부 선택/전부 해제)
   });

   // ✅ 전체선택 change: 체크 여부로 전부 선택/해제
   $selectAll.addEventListener('change', () => {
     setAll($selectAll.checked);
   });

   // ✅ 개별 체크 변화에 따라 마스터 상태 동기화
   document.addEventListener('change', (e) => {
     if (e.target.matches(itemSelector)) syncMaster();
   });

   // 초기 동기화
   syncMaster();

   // 👇 기존 다중삭제 버튼 JS는 유지하되, 선택자만 더 안전하게
   document.getElementById('deleteSelected').addEventListener('click', function() {
     const ids = [...document.querySelectorAll(itemSelector)]
       .filter(cb => cb.checked)
       .map(cb => cb.value);

     if (!ids.length) { alert('삭제할 항목을 선택하세요.'); return; }
     if (!confirm(ids.length + '건을 삭제하시겠습니까?')) return;

     const form = document.createElement('form');
     form.method = 'post';
     form.action = '/doctor/deleteSelected';

     ids.forEach(id => {
       const input = document.createElement('input');
       input.type = 'hidden';
       input.name = 'ids';
       input.value = id;
       form.appendChild(input);
     });

     // 🔐 CSRF hidden 추가(레이아웃 head에 메타가 있다고 가정)
     const csrfParam = document.querySelector('meta[name="_csrf_parameter"]').content;
     const csrfToken = document.querySelector('meta[name="_csrf"]').content;
     const csrfInput = document.createElement('input');
     csrfInput.type = 'hidden';
     csrfInput.name = csrfParam;
     csrfInput.value = csrfToken;
     form.appendChild(csrfInput);

     document.body.appendChild(form);
     form.submit();
   });

    // 국적 팝업 열기
    function openCountryPopup() {
      const w = 780, h = 620;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/country_form', 'countryPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 국적 선택 콜백 (팝업에서 window.opener.setCountry 호출)
    function setCountry(iso2, countryKr) {
      const disp = document.getElementById('nationalityDisplay');
      if (disp) disp.textContent = `${countryKr} (${iso2})`;

      const hidden = document.getElementById('nationalityInput');
      if (hidden) hidden.value = `${countryKr} (${iso2})`;
    }

    // 도로명 주소 팝업
    function openRoadPopup() {
      const w = 900, h = 640;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/road/popup', 'roadPopup',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 도로명 선택 콜백
    function setRoad(addrStr /*, roadCode, emdSeqNo */) {
      const addr = document.getElementById('addr1');
      if (addr) addr.value = addrStr || '';
    }

    // 직급/직책 팝업
    function openJobPopup() {
      const w = 700, h = 520;
      const left = (screen.width - w) / 2;
      const top = (screen.height - h) / 2;
      window.open('/global/jobcode/popup', 'job_code',
        `width=${w},height=${h},left=${left},top=${top},resizable=yes,scrollbars=yes`);
    }

    // 팝업 -> 본창 메시지 처리
    window.addEventListener('message', (e) => {
      if (e.data?.source !== 'jobcode-popup') return;
      const { rankCode, positionCode } = e.data.payload || {};

      const rankInput = document.getElementById('rank');
      if (rankInput) rankInput.value = rankCode ?? '';

      const positionInput = document.getElementById('position');
      if (positionInput) positionInput.value = positionCode ?? '';
    });

        function choose(iso2, countryKr) {
          if (window.opener && !window.opener.closed && typeof window.opener.setCountry === 'function') {
            window.opener.setCountry(iso2, countryKr);
          }
          window.close();
        }

            function makeOptionText(item) {
              const code = item.code ?? '';
              const kor  = item.korName ?? '';
              const eng  = item.engName ?? '';
              return `[${code}] ${kor} / ${eng}`;
            }
            async function fetchJson(url) {
              const res = await fetch(url);
              if (!res.ok) throw new Error(`${url} ${res.status}`);
              return res.json();
            }

            // 직책(선택)
            fetchJson('/global/jobcode/positions')
              .then(list => {
                const sel = document.getElementById('positionSelect');
                sel.innerHTML = '';
                (list || []).forEach(item => {
                  const opt = document.createElement('option');
                  // 부모로는 한국어명 전달 (VARCHAR(4) 대응)
                  opt.value = item.korName;
                  opt.dataset.kor = item.korName;
                  opt.textContent = makeOptionText(item);
                  sel.appendChild(opt);
                });
              })
              .catch(err => console.warn('직책 로드 실패:', err));

            // 직급(선택)
            fetchJson('/global/jobcode/ranks')
              .then(list => {
                const sel = document.getElementById('rankSelect');
                sel.innerHTML = '';
                (list || []).forEach(item => {
                  const opt = document.createElement('option');
                  opt.value = item.korName;
                  opt.dataset.kor = item.korName;
                  opt.textContent = makeOptionText(item);
                  sel.appendChild(opt);
                });
              })
              .catch(err => console.warn('직급 로드 실패:', err));

            function applySelection() {
              const rankOpt = document.getElementById('rankSelect').selectedOptions[0];
              const posOpt  = document.getElementById('positionSelect').selectedOptions[0];

              // 선택 안 했으면 빈 문자열로 전달
              const rankKor     = rankOpt ? (rankOpt.dataset.kor || rankOpt.value) : '';
              const positionKor = posOpt  ? (posOpt.dataset.kor  || posOpt.value)  : '';

              window.opener?.postMessage({
                source: 'jobcode-popup',
                payload: { rankCode: rankKor, positionCode: positionKor }
              }, '*');

              window.close();
            }

                let currentPage = 0, totalPages = 0, prevPage = 0, nextPage = 0, pageSize = 10;


                function load(page) {
                  const kw = document.getElementById('kw').value || '';
                  fetch(`/global/road/search?kw=${encodeURIComponent(kw)}&page=${page}&size=${pageSize}`)
                    .then(r => r.json())
                    .then(data => {
                      currentPage = data.number;
                      totalPages  = data.totalPages;
                      prevPage = Math.max(0, currentPage - 1);
                      nextPage = Math.min(totalPages - 1, currentPage + 1);

                      document.getElementById('prev').disabled = currentPage === 0;
                      document.getElementById('next').disabled = currentPage >= totalPages - 1 || totalPages === 0;
                      document.getElementById('pageInfo').textContent = `${totalPages ? (currentPage+1) : 0}/${totalPages}`;

                      const tbody = document.getElementById('rows');
                      tbody.innerHTML = '';
                      if (data.content.length === 0) {
                        tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;">결과가 없습니다.</td></tr>`;
                        return;
                      }
                      data.content.forEach(a => {
                        const addrStr = [a.sido_kor, a.sgg_kor, a.emd_kor, a.road_name_kor]
                                         .filter(Boolean).join(' ');
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                          <td>${a.sido_kor ?? ''}</td>
                          <td>${a.sgg_kor ?? ''}</td>
                          <td>${a.emd_kor ?? ''}</td>
                          <td>${a.road_name_kor ?? ''}</td>
                          <td>${a.road_code ?? ''}</td>
                          <td><button type="button">선택</button></td>`;
                        tr.ondblclick = () => choose(addrStr, a);
                        tr.querySelector('button').onclick = () => choose(addrStr, a);
                        tbody.appendChild(tr);
                      });
                    })
                    .catch(e => {
                      alert('검색 중 오류가 발생했습니다.');
                      console.error(e);
                    });
                }

                function go(p) { load(p); }

                function choose(addrStr, a) {
                  if (window.opener && !window.opener.closed) {
                    // 부모에 값만 전달 (부모가 저장 여부 결정)
                    window.opener.setRoad(addrStr, a.road_code || '', a.emd_seq_no || '');
                  }
                  window.close();
                }

    // 전역 사용을 위해 노출
    window.openCountryPopup = openCountryPopup;
    window.setCountry = setCountry;
    window.openRoadPopup = openRoadPopup;
    window.setRoad = setRoad;
    window.openJobPopup = openJobPopup;

