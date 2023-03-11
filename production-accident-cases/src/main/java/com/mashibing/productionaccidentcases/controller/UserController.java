package com.mashibing.productionaccidentcases.controller;

import com.mashibing.productionaccidentcases.entity.User;
import com.mashibing.productionaccidentcases.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/3/11 - 8:34
 */
@RestController
public class UserController {

	@Autowired
	RedissonClient redissonClient;
	@Autowired
	UserService userService;

	@RequestMapping("/add")
	public void addUser(String idCard) {
		User user = new User();
		user.setName("张三" + idCard);
		user.setIdCard(idCard);
		String key = "key";
		RLock lock = redissonClient.getLock(key);
		lock.lock();
		try {
			userService.addError(user);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}
