package com.HPMS.HPMS;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationRepository;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HpmsApplicationTests {

	@Autowired
	private NurseMainRepository nurseMainRepository;

	@Autowired
	private NurseInformationRepository nurseInformationRepository;

	@Test //NurseMain 데이터 저장
	void testJpa01() {
		NurseMain n1 = new NurseMain();
		n1.setDept("피부과");
		n1.setRank("부장");
		n1.setFirstName("지영");
		n1.setLastName("김");
		n1.setMiddleName("");
		n1.setGender("F");
		n1.setDateOfBirth(19960312);
		n1.setHireDate(20250828);
		n1.setSts("재직중");
		n1.setWt("정규직");
		n1.setWriter("김진혁");
		n1.setCreateDate(LocalDateTime.now());
		n1.setModifier("");
		n1.setModifyDate(LocalDateTime.now());
		this.nurseMainRepository.save(n1);

		NurseMain n2 = new NurseMain();
		n2.setDept("산부인과");
		n2.setRank("일반간호사");
		n2.setFirstName("현진");
		n2.setLastName("박");
		n2.setMiddleName("");
		n2.setGender("F");
		n2.setDateOfBirth(19960722);
		n2.setHireDate(20201203);
		n2.setSts("재직중");
		n2.setWt("정규직");
		n2.setWriter("김주현");
		n2.setCreateDate(LocalDateTime.now());
		n2.setModifier("");
		n2.setModifyDate(LocalDateTime.now());
		this.nurseMainRepository.save(n2);
	}

	@Test //findAll
	void testJpa02() {
		List<NurseMain> all = this.nurseMainRepository.findAll();
		assertEquals(2, all.size());

		NurseMain q = all.get(0);
		assertEquals("지영", q.getFirstName());
	}

	@Test //findById
	void testJpa03() {
		Optional<NurseMain> on = this.nurseMainRepository.findById(1);
		if(on.isPresent()) {
			NurseMain n = on.get();
			assertEquals("지영", n.getFirstName());
		}
	}

	@Test //findByFirstName
	void testJpa04() {
		NurseMain n = this.nurseMainRepository.findByFirstName("지영");
		assertEquals(1, n.getId());
	}

	@Test //findByFirstNameAndLastName
	void testJpa05() {
		NurseMain q = this.nurseMainRepository.findByFirstNameAndLastName(
				"지영", "김");
		assertEquals(1, q.getId());
	}

	@Test //findByDeptLike
	void testJpa06() {
		List<NurseMain> nList = this.nurseMainRepository.findByDeptLike("피부%");
		NurseMain n = nList.get(0);
		assertEquals("피부과", n.getDept());
	}

	@Test //NurseMain 데이터 수정하기
	void testJpa07() {
		Optional<NurseMain> on = this.nurseMainRepository.findById(1);
		assertTrue(on.isPresent());
		NurseMain n = on.get();
		n.setRank("과장");
		n.setModifier("김xx");
		n.setModifyDate(LocalDateTime.now());
		this.nurseMainRepository.save(n);
	}

	@Test //NurseMain 데이터 삭제
	void testJpa08() {
		assertEquals(2, this.nurseMainRepository.count());
		Optional<NurseMain> on = this.nurseMainRepository.findById(1);
		assertTrue(on.isPresent());
		NurseMain n = on.get();
		this.nurseMainRepository.delete(n);
		assertEquals(1, this.nurseMainRepository.count());
	}

	@Test //NurseInformation 데이터 저장
	void testJpa09() {
		Optional<NurseMain> on = this.nurseMainRepository.findById(2);
		assertTrue(on.isPresent());
		NurseMain n = on.get();

		NurseInformation i = new NurseInformation();
		i.setFirstName("sujin");
		i.setLastName("kim");
		i.setMiddleName("");
		i.setTel("01012345678");
		i.setEmgcCntc("01023456789");
		i.setEmgcFName("현진");
		i.setEmgcLName("박");
		i.setEmgcMName("");
		i.setEmgcRel("부");
		i.setEmgcNote("평일 오후 6시까지는 연락 못 받음");
		i.setEmail("test@test.com");
		i.setPcd(123456);
		i.setDefAdd("대전 둔산동");
		i.setDetAdd("어딘가");
		i.setRnNo("123456");
		i.setEdbc("xx대학교");
		i.setGradDate(20100230);
		i.setFl("영어, 일본어, 중국어");
		i.setMs("NO");
		i.setNatn("한국");
		i.setDss("N");
		i.setCarr("30개월");
		i.setPicture("https://cdn.woodkorea.co.kr/news/photo/202503/84085_96462_4115.png");
		i.setNote("");
		i.setNurseMain(n);
		this.nurseInformationRepository.save(i);
	}

	@Test //NurseInformation 데이터 조회
	void testJpa10() {
		Optional<NurseInformation> on = this.nurseInformationRepository.findById(1);
		assertTrue(on.isPresent());
		NurseInformation n = on.get();
		assertEquals(2, n.getNurseMain().getId());
	}

	@Transactional
	@Test
	void testJpa11() {
		Optional<NurseMain> on = this.nurseMainRepository.findById(2);
		assertTrue(on.isPresent());
		NurseMain n = on.get();

		NurseInformation nurseInformation = n.getNurseInformation();

		assertEquals(1, nurseInformation.getId());
		assertEquals(2, nurseInformation.getNurseMain().getId());
	}
}
