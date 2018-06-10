package com.dgit.persistence;

import com.dgit.domain.UserVO;

public interface UserDAO {
	public UserVO login(UserVO vo) throws Exception;
	
}
