package com.dgit.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/Web-inf/spring/**/*.xml"})
public class BoardDAOTest {
	
	@Autowired
	BoardDAO dao;
	
	//@Test
	public void testCreate() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("title test");
		vo.setContent("content test");
		vo.setWriter("user00");
		
		dao.create(vo);
	}
	//@Test
	public void read() throws Exception{
		
		BoardVO boardvo = dao.read(1);
		
		System.out.println(boardvo);
	}
	
	//@Test
	public void update() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("title test1");
		vo.setContent("content test1");
		vo.setWriter("user00");
		
		dao.update(vo);
		
	}
	
	//@Test
	public void delete() throws Exception{
		dao.delete(3);
	}
	
	//@Test
	public void readAll() throws Exception{
		List<BoardVO> vo = dao.listAll();
		System.out.println(vo);
	}
	//@Test
	public void testListPage() throws Exception{
		dao.listPage(1);
		
	}
	
	//@Test
	public void testListCriteria() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(1);
		
		
		dao.listCriteria(cri);
		
	}
	
	//@Test
	public int testtotalCount() throws Exception{
		return dao.totalCount();
	}
	
	@Test
	public void testlistSearch() throws Exception{
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);
		cri.setSearchType("t");
		cri.setKeyword("테스트");
		dao.listSearch(cri);
	}
}








