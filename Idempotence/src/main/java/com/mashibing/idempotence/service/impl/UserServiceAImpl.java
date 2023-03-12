package com.mashibing.idempotence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashibing.idempotence.entity.User;
import com.mashibing.idempotence.mapper.UserMapper;
import com.mashibing.idempotence.service.UserServiceA;
import com.mashibing.idempotence.service.UserServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 测试事务的链路传播
 *
 * @author xcy
 * @date 2023/3/11 - 14:37
 */
@Service
public class UserServiceAImpl implements UserServiceA {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserServiceB userServiceB;


	/**
	 * 默认的事务链路是传播到其它方法的
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void a() {
		//错误的示例
		//正确的示例是不应该将其它方法抛出的异常进行捕获
		//如果进行捕获，那么抛出异常的方法所在的事务需要进行回滚，而捕获异常的方法所在的事务需要进行提交
		//最终导致事务既没有提交，也没有回滚，而是报错了
		//因为此时提交了已经被标记为异常的事务
		//抛出org.springframework.transaction.UnexpectedRollbackException
		/*try {
			userServiceB.b();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		User user = new User();
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
				.eq(User::getIdCard, "1000");
		User user1 = userMapper.selectOne(wrapper);
		user1.setName("zhangsan");
		LocalDateTime now = LocalDateTime.now();
		user1.setGmtModified(now);
		userMapper.updateById(user1);
		//调用其他系统接口
		//userServiceB.b();
		new Thread(() -> userServiceB.b()).start();

	}

	private void b() {
		User user = new User();

		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
				.eq(User::getIdCard, "1001");
		User user1 = userMapper.selectOne(wrapper);
		user1.setName("zhangsan");
		userMapper.updateById(user1);
	}
}
