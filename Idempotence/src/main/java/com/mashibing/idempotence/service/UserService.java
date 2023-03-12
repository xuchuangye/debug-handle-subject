package com.mashibing.idempotence.service;

import com.mashibing.idempotence.entity.User;

/**
 * @author xcy
 * @date 2023/3/11 - 8:34
 */

public interface UserService {

	public void addError(User user);
	public void addCorrect(User user);
}
