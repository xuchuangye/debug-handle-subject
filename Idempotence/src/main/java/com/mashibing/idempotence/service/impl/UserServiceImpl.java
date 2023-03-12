package com.mashibing.idempotence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mashibing.idempotence.mapper.UserMapper;
import com.mashibing.idempotence.entity.User;
import com.mashibing.idempotence.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.alibaba.fastjson.JSON;


/**
 * 测试redisson的锁处理幂等性失效
 *
 * @author xcy
 * @date 2023/3/11 - 8:35
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private UserMapper userMapper;

	/**
	 * 接口需要幂等，此处身份证号不允许重复
	 * <p>
	 * 错误的示例
	 *
	 * @param user
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addError(User user) {
		log.info("add user params user:{}", JSON.toJSONString(user));
		Assert.isTrue(StringUtils.isNotBlank(user.getIdCard()), "身份证号不允许null");
		//
		String key = "key".intern();
		RLock lock = redissonClient.getLock(key);
		//幂等性失效了
		//原因：事务没有提交，锁却释放了
		lock.lock();
		try {
			//MySQL的默认级别，可重复读
			//两个线程同时执行到了这里，查询数据库都没有
			//A线程执行查询没有，B线程查询也没有，因为A线程没有提交，这就是幂等性失败的原因
			//锁加错位置了，事务级别搞混了
			LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
					.eq(User::getIdCard, user.getIdCard());
			long count = userMapper.selectCount(wrapper);
			//stop the world
			if (count == 0) {
				userMapper.insert(user);
			}
		} catch (Exception e) {
			log.error("add user error", e);
		} finally {
			lock.unlock();
		}
		System.out.println("并发执行，同时插入两条数据");
	}

	/**
	 * 接口需要幂等，此处身份证号不允许重复
	 * <p>
	 * 正确的示例
	 * <p>
	 * 原因：因为不加事务@Transactional(rollbackFor = Exception.class)时，当事务执行完之后会自动提交
	 * 多线程执行时，是能够互相隔离开的
	 *
	 * @param user
	 */
	@Override
	public void addCorrect(User user) {
		//将事务和锁进行分开
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
				.eq(User::getIdCard, user.getIdCard());
		long count = userMapper.selectCount(wrapper);
		if (count == 0) {
			userMapper.insert(user);
		}
		System.out.println("并发执行，同时插入两条数据");
	}
}
