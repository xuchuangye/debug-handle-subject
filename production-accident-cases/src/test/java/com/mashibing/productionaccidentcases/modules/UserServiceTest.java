package com.mashibing.productionaccidentcases.modules;

import com.mashibing.productionaccidentcases.ProductionAccidentCasesTest;
import com.mashibing.productionaccidentcases.entity.User;
import com.mashibing.productionaccidentcases.mapper.UserMapper;
import com.mashibing.productionaccidentcases.service.UserService;
import com.mashibing.productionaccidentcases.service.UserServiceA;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/3/11 - 9:34
 */
@Slf4j
public class UserServiceTest extends ProductionAccidentCasesTest {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private RedissonClient redissonClient;

	@Test
	public void addErrorTest() {
		User user = new User();

		user.setName("张三");
		user.setIdCard("1000");

		LocalDateTime now = LocalDateTime.now();
		user.setGmtCreate(now);
		user.setGmtModified(now);
		//错误的示例
		userService.addError(user);
	}

	@Test
	public void addCorrectTest() {
		User user = new User();

		user.setName("张三");
		user.setIdCard("1000");

		LocalDateTime now = LocalDateTime.now();
		user.setGmtCreate(now);
		user.setGmtModified(now);
		//正确的示例

		String key = "key".intern();
		RLock lock = redissonClient.getLock(key);
		//幂等性失效了
		//原因：事务没有提交，锁却释放了
		lock.lock();
		try {
			userService.addCorrect(user);
		} catch (Exception e) {
			log.error("add user error", e);
		} finally {
			lock.unlock();
		}
	}


	@Autowired
	private UserServiceA userServiceA;

	@Test
	public void testTransaction() {
		userServiceA.a();
	}
}
