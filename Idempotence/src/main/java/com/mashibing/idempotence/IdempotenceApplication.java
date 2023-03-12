package com.mashibing.idempotence;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xcy
 * @date 2023/3/11 - 8:30
 */
@SpringBootApplication
@MapperScan("com.mashibing.idempotence.mapper")
public class IdempotenceApplication {
	public static void main(String[] args) {
		SpringApplication.run(IdempotenceApplication.class, args);
	}
}
