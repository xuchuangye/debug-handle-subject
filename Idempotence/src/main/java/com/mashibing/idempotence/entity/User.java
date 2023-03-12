package com.mashibing.idempotence.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/3/11 - 8:36
 */
@Data
public class User {
	private Long id;

	private String name;

	private String idCard;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;
}
