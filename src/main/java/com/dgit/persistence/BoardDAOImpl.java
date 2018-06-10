package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	SqlSession session;
	
	private static final String namespace = "com.dgit.mapper.BoardMapper";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".create", vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".read",bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".update", vo);
	}

	@Override
	public void delete(int dno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace + ".delete", dno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".readAll");
	}
	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		// TODO Auto-generated method stub
		page = (page-1)*10;
		return session.selectList(namespace + ".listPage", page);
	}
	
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub	
		return session.selectList(namespace + ".listCriteria", cri);
	}
	
	@Override
	public int totalCount() throws Exception {
		// TODO Auto-generated method stub	
		return session.selectOne(namespace + ".totalCount");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".listSearch", cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".totalSearchCount" ,cri) ;
	}

	@Override
	public void updateReplyCnt(int bno, int amount) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
		session.update(namespace + ".updateReplyCnt", map);
	}

	@Override
	public int updateViewCnt(int bno) throws Exception {
		return session.update(namespace + ".updateViewCnt", bno);
		
	}

	@Override
	public void addAttach(String fullName) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".addAttach", fullName);
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".getAttach", bno);
	}

	@Override
	public void deleteAttach(int bno, String fullName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullName", fullName);
		session.delete(namespace + ".deleteAttach", map);
	}

	@Override
	public void modaddAttach(String fullName, int bno) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullName", fullName);		
		session.insert(namespace + ".modaddAttach", map);
	}

	@Override
	public void deleteImgAttach(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace + ".deleteImgAttach", bno);
	}
	
}
