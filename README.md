<h2>병원인력관리시스템 HPMS</h2>
<img alt="hpms title" src="https://github.com/user-attachments/assets/ac7d3810-c0c6-41af-b130-3ac3f78d666f"/>

<ul>
  <li><span>배포 URL: </span><a href="https://localhost:8080/">https://localhost:8080/</a></li>
</ul>
<br>
<br>
<h3>프로젝트 소개</h3>
<ul>
  <li>HPMS는 병원인력 관리를 위해 만들어진 게시판입니다</li>
  <li>병원인력을 의사, 간호사, 환자, 시스템사용자 4개의 도메인으로 구분하였습니다</li>
  <li>각 도메인별로 인력관리 게시판을 기본기능으로 구현하였습니다</li>
  <li>각 도메인별로 필요한 기능과 칼럼을 추가하였습니다</li>
</ul>
<br>
<h3>팀원구성</h3>
<table>
  <thead>
    <th>이승운</th>
    <th>이창훈</th>
    <th>서수한</th>
    <th>서지선</th>
  </thead>
  <tbody>
    <tr>
      <td><img width="420" height="400" alt="image" src="https://github.com/user-attachments/assets/7c252039-7f20-4998-80db-48f321853cc4" /></td>
      <td><img width="420" height="400" alt="image" src="https://github.com/user-attachments/assets/c2d7b8d2-fce3-45aa-8c36-5386aa84d097" /></td>
      <td><img width="420" height="400" alt="image" src="https://github.com/user-attachments/assets/01bffddd-5e48-420e-a7c4-3a6f12dfc682" /></td>
      <td><img width="420" height="400" alt="image" src="https://github.com/user-attachments/assets/72596b8d-3436-4731-a72f-d9af06cc9d19" /></td>
     
    </tr>
    <tr>
      <td>@ZarKljh</td>
      <td>@whenyouwerelittle</td>
      <td>@jiseon723</td>
      <td>@kaeng2258</td>
    </tr>
  </tbody>
</table>
<br>

<h3>1.개발환경</h3>
<ul>
  <li>운영체제: window</li>
  <li>IDE: intellij community Edition</li>
  <li>데이터베이스: MariaDB</li>
  <li>빌드 툴: Gradle</li>
  <li>버전관리: GitHub</li>
  <li>DB편집툴: DBeaver</li>
  <li>협업툴: Figma, Figjam, GoogleDrive</li>
  <li>배포환경: local</li>
  <li>디자인: Figma</li>
</ul>
<br>
<h3>2.기술스택</h3>
<ul>
  <li>
    <span>🖥️:Front-end: </span>
    <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
    <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
    <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
    <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
    </li>
  <li>
    <span>📚:Back-end: </span>
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  </li>
  <li>
    <span>💾:DataBase: </span>
    <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
  </li>
</ul>
<br>
<h3>3.Dependencies</h3>
<ul>
  <li>SpringBoot DevTools</li>
  <li>Lombok</li>
  <li>Spring Data JPA</li>
  <li>MariaDB</li>
  <li>Spring Security</li>
  <li>Thymeleaf</li>
</ul>

<h3>4.DB테이블설계</h3>
<ul>
  <li>COUNTRY_CODE</li>
  <li>DOC_POSITION</li>
  <li>DOC_RANK</li>
  <li>HOSPITAL</li>
  <li>HOSPITAL_ADMINISTRATION_DEPT</li>
  <li>HOSPITAL_ADMINISTRATION_TASK</li>
  <li>LICENSE</li>
  <li>MIDICAL_DEPARTMENT</li>
  <li>NURSE_HOSPITAL</li>
  <li>NURSE_HISTORY</li>
  <li>NURSE_INFORMATION</li>
  <li>NURSE_POSITION</li>
  <li>ROAD_NAME</li>
  <li>TBL_DOCTOR_DTL</li>
  <li>TBL_DOCTOR_H</li>
  <li>TBL_DOCTOR_M</li>
  <li>TBL_PATIENT_DTL</li>
  <li>TBL_PATIENT_H</li>
  <li>TBL_PATIENT_M</li>
  <li>TBL_REL_PERSONNEL_DTL</li>
  <li>TBL_REL_PERSONNEL_H</li>
  <li>TBL_REL_PERSONNEL_M</li>
  <li>TBL_SITE_USER</li>
</ul>
<br>
<h3>5.프로젝트구조</h3>
<br>
<h3>6.역할분담</h3>

<h4>🛃:이승운</h4>
<ul>
  <li>환자 도메인 담당 
    <ul>
      <li>UI: 리스트화면, 상세화면, 환자정보수정화면</li>
      <li>기능: 다중조건검색기능, 로그인&회원가입 기능, 권한별 페이지 접근제한 기능</li>
    </ul>
  </li>
</ul>
<h4>🏢:이창훈</h4>
<ul>
  <li>시스템관리자 도메인 담당, 전반적인 시스템 설계 
    <ul>
      <li>UI: 리스트화면, 상세화면, 시스템관리자 정보수정화면, 로고이미지 제작</li>
      <li>기능: 페이징기능, 등록건수 표시기능, 표시건수 선택기능, 복수선택 삭제기능</li>
    </ul>
  </li>
</ul>
<h4>👩‍⚕️:서지선</h4>
<ul>
  <li>간호사 도메인 담당, 전체 UI/UX 디자인담당
    <ul>
      <li>UI: 리스트화면, 상세화면, 간호사정보수정화면, 간호사정보이력화면</li>
      <li>공통컴포넌트: 로그인화면, 회원가입화면, 네비게이션, 메인화면</li>
      <li>기능: 자격증복수등록기능, 사진url등록기능, 검색기능</li>
    </ul>
  </li>
</ul>
<h4>👨‍⚕️:서수한</h4>
<ul>
  <li>의사 도메인 담당, 팝업기능담당
    <ul>
      <li>UI: 리스트화면, 상세화면, 의사정보수정화면, 의사정보이력관리화면</li>
      <li>공통컴포넌트: 주소입력/직급직책입력/국가입력 팝업화면</li>
      <li>기능: 도로명주소/국가/직급직책 입력팝업기능, 검색기능, 사진업로드기능</li>
    </ul>
  </li>
</ul>
<h3>7.개발기간 및 작업관리</h3>

<table>
  <theade>
  </theade>
  <tbody>
    <tr>
      <td>전체개발기간</td>
      <td>2025.08.20~2025.09.30</td>
    </tr>
    <tr>
      <td>1주차</td>
      <td>프로젝트 기획 및 테이블 구상</td>
    </tr>
    <tr>
      <td>2주차</td>
      <td>테이블 적합성 검토, 테이블 생성 쿼리문 작성</td>
    </tr>
    <tr>
      <td>3주차</td>
      <td>도메인별 CRUD게시판 구현</td>
    </tr>
    <tr>
      <td>4주차</td>
      <td>개별기능구현, 디자인구상 및 구현시작</td>
    </tr>
    <tr>
      <td>5주차</td>
      <td>디자인조정, 로그인기능 구현</td>
    </tr>
    <tr>
      <td>6주차</td>
      <td>개발종료, 문서정리</td>
    </tr>
  </tbody>  
</table>

<h4>🛠️:작업관리</h4>
<ul>
  <li>GitHub에서 개인별 branch 분리 및 패키지 분리하여 코드 충돌을 최소화 하였습니다. </li>
  <li>회의를 통해 정해진 내용은 문서화 하고 Google Drive를 통해 공유하였습니다</li>
  <li>담당자마다 담당 도메인의 CRUD게시판을 구현하였습니다</li>
  <li>부트스트랩을 이용하여 전체 UI/UX 구현, 세부 디자인은 검토후 문서화하여 개인별 조정</li>
</ul>
<br>

<h3>8.신경쓴부분</h3>
<ul>
  <li>DB 테이블 불변성: 테이블 문서화후 변경금지, Hibernate.ddl-auto=vaildate 설정 </li>
  <li>핵심정보는 메인테이블, 부가정보는 서브테이블 구분</li>
  <li>테이블 생성 쿼리문 직접 작성 및 공유	</li>
  <li>테이블&칼럼, 각종 SQL, 회의내용 모두 문서화 & 공유</li>
  <li>개인 담당 도메인별 CRUD 구현 후 개인별 기능 구현</li>
  <li>AI를 이용한 로고이미지 제작</li>
  <li>도로명주소, 국가코드. 직급직책 등 공용테이블 전자정보프레임워크 DB 컬럼명 참고</li>
</ul>

<h3>9.페이지별 기능</h3>
[메인화면]
[회원가입화면] -- 디자인 수정 필요
[로그인화면]
[로그인후 권한별 메인화면, 네비게이션 변경 확인]
[시스템관리자 list 화면]
[등록건수 표시 및 표시건수 변경기능 시연]
[다중 선택 삭제기능 시연]
[의사 list 화면]
[각종 팝업창 시연]
[간호사 list화면]
[간호사 정보 상세화면]
[간호사 정보 수정화면]
[간호사 자격증 등록 시연]
[간호사 히스토리 화면 시연]
[환자 list화면]
[환자 다중조건 검색 시연]
<h3>10.추후개선목표</h3>

<h3>11.개발후기</h3>















<img width="1024" height="1024" alt="hpms logo" src="https://github.com/user-attachments/assets/c1e15b60-4bef-4ff0-b44c-542a0136eb2e" />
