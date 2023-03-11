package com.mashibing.debug;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试流的断点调试
 *
 * @author xcy
 * @date 2023/3/10 - 17:48
 */
public class TestStreamDebug {
	public static void main(String[] args) {
		int num = 10;
		List<Student> list = new ArrayList<>(num);
		for (int i = 0; i < num; i++) {
			list.add(new Student(i, "data" + i * 10));
		}

		List<Student> students = list.stream()
				.skip(1)
				.limit(100)
				.filter(item -> item.getAge() > 20)
				.filter(item -> item.getAge() < 50)
				.collect(Collectors.toList());
	}
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class Student {
	public int age;
	public String name;
}
