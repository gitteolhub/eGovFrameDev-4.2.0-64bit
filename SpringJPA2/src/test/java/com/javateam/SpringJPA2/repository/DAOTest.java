package com.javateam.SpringJPA2.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.SpringJPA2.domain.TestVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class DAOTest {
	
	@Autowired
	TestRepository testRepo;
	
	// case-1) 레코드 수 점검
	@Test
	void test() {

		int cnt = (int)testRepo.count();
		log.info("레코드 수 : " + cnt);
		assertEquals(1, cnt);
	} //
	
	@Test
	void test2() {
		
		Optional<TestVO> optionVO = testRepo.findById((long)1);
		assertFalse(optionVO.isEmpty());
		
		TestVO testVO = optionVO.get();
		log.info("testVO : " + testVO); // TestVO(id=1, name=허미미, address=서울)
		
		assertEquals("허미미", testVO.getName());
	}
	
	@Test
	@Transactional
	// @Rollback(true)
	@Rollback(false)
	void testInsert() {
		
		TestVO testVO = new TestVO();		
		testVO.setName("임종훈");
		testVO.setAddress("대한민국");
		
		// 존재하지 않는 최초 레코드로 감지되면 save => insert로 해석 
		TestVO resultVO = testRepo.save(testVO);
		log.info("resultVO : " + resultVO);
		assertEquals(testVO.getName(), resultVO.getName());
	} 
	
	@Test
	@Transactional
	// @Rollback(true)
	@Rollback(false)
	void testUpdateDelete() {
		
		// 기존값 가져오기 : 위의 테스트에서 실제로 생성된 레코드의 아이디 값을 
		// 가져와서 아이디 인자에 대입하여 테스트합니다.
		TestVO testVO = testRepo.findById((long)702).get();
		// 변경 내용 반영
		testVO.setName("임종수");
		testVO.setAddress("프랑스");
		
		// 존재하는 기존의 레코드로 감지되면 save => update로 해석
		TestVO resultVO2 = testRepo.save(testVO);
		log.info("resultVO2 : " + resultVO2);
		assertEquals(testVO.getAddress(), resultVO2.getAddress());
		
		// 삭제
		testRepo.deleteById(resultVO2.getId());
	}
}
