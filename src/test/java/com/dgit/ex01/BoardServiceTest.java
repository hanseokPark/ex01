package com.dgit.ex01;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/Web-inf/spring/**/*.xml"})
public class BoardServiceTest {
	@Autowired
	BoardService service;
	
	//@Test
	public void testRegister() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("title test");
		vo.setContent("content test");
		vo.setWriter("user00");
		
		service.regist(vo);
	}
	//@Test
	public void testread() throws Exception{
		BoardVO vo = service.read(1);
		System.out.println(vo);
	}
	//@Test
	public void testlist() throws Exception{
		List<BoardVO> vo = service.listAll();
		System.out.println(vo);
	}
	//@Test
	public void testmodify() throws Exception{		
		BoardVO vo = new BoardVO();
		vo.setTitle("title test2");
		vo.setContent("content test2");
		vo.setWriter("user00");
		
		service.modify(vo);
	}
	//@Test
	public void testremove() throws Exception{
		service.remove(2);
	}
	
}
