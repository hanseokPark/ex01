package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO{
	

	@Autowired
	SqlSession session;
	
	private static final String namespace = "com.dgit.mapper.UserMapper";


	@Override
	public UserVO login(UserVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".login", vo);
	}

}
