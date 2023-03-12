package com.mashibing.idempotence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashibing.idempotence.entity.User;
import com.mashibing.idempotence.mapper.UserMapper;
import com.mashibing.idempotence.service.UserServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/3/11 - 14:38
 */
@Service
public class UserServiceBImpl implements UserServiceB {
	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void b() {
		User user = new User();
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
				.eq(User::getIdCard, "1000");
		User user1 = userMapper.selectOne(wrapper);
		user1.setName("lisi");

		LocalDateTime now = LocalDateTime.now();
		user1.setGmtModified(now);
		userMapper.updateById(user1);
		System.out.println(1 / 0);
	}
}
