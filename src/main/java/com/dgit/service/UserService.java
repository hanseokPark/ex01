package com.dgit.service;

import com.dgit.domain.UserVO;

public interface UserService {
	public UserVO login(UserVO vo) throws Exception;
}
