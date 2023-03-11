package com.mashibing.productionaccidentcases;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xcy
 * @date 2023/3/11 - 8:30
 */
@SpringBootApplication
@MapperScan("com.mashibing.productionaccidentcases.mapper")
public class ProductionAccidentCasesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductionAccidentCasesApplication.class, args);
	}
}
