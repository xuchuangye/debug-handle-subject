package com.mashibing.productionaccidentcases.service;

import com.mashibing.productionaccidentcases.entity.User;

/**
 * @author xcy
 * @date 2023/3/11 - 8:34
 */

public interface UserService {

	public void addError(User user);
	public void addCorrect(User user);
}
