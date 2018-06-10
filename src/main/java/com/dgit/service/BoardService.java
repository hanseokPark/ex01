package com.dgit.service;

import java.util.List;


import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardService {
	
	public void regist(BoardVO vo) throws Exception;
	public BoardVO read(int bno) throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public void modify(BoardVO vo) throws Exception;
	public void remove(int bno) throws Exception;
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	public int totalCount() throws Exception;
	
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	public int totalSearchCount(SearchCriteria cri) throws Exception;
	
	public int boardviewcnt(int bno) throws Exception;
	public void deleteAttach(int bno, String fileName) throws Exception;
	/*public void modaddAttach(String fullName, int bno) throws Exception;*/
	
}
