package com.example.day_04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class WebdemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebdemoApplication.class, args);
//		Dog dog = new Dog();
//
//		Action cat = new Action() {
//			@Override
//			public void eat() {
//				System.out.println("Cat eat Dog");
//			}
//		};
//
//		Action pig = () -> {
//			System.out.println("Pig eat Rabbit");
//		};
//
//		dog.eat();
//		dog.power();
//		cat.eat();
//		pig.eat();

		List<Integer> numbers = List.of(10, 5, 3, 12, 6, 7, 5, 3);

		// Tìm giá trị chẵn
		System.out.print("Giá trị chẵn: ");
		System.out.println(
			numbers.stream()
				.filter(num -> num % 2 == 0)
				.collect(Collectors.toList())
		);

		// Giá trị > 5
		System.out.print("Giá trị > 5: ");
		System.out.println(
			numbers.stream()
			.filter(num -> num > 5)
			.collect(Collectors.toList())
		);

		// Giá trị max
		System.out.print("Giá trị max: ");
			numbers.stream()
			.max(Integer::compare)
			.ifPresent(System.out::print);
		System.out.println();

		// Giá trị min
		System.out.print("Giá trị min: ");
		numbers.stream()
			.min(Integer::compare)
			.ifPresent(System.out::print);
		System.out.println();

		//  TÍnh tổng
		System.out.print("Tính tổng: ");
		System.out.print(numbers.stream()
			.mapToInt(Integer::intValue)
			.sum());
		System.out.println();

		//
		System.out.print("Danh sách không trùng nhau");
		System.out.println(
			numbers.stream()
			.distinct()
				.collect(Collectors.toList())
		);

		//
		System.out.print("5 phần tử đầu: ");
		System.out.println(
			numbers.stream()
			.limit(5)
			.collect(Collectors.toList())
		);

		System.out.print("Phần tử từ 3 -> 5");
		System.out.println(
			numbers.stream()
			.skip(2)
			.limit(5)
			.collect(Collectors.toList())
		);

		System.out.print("Phần tử đầu tiên > 5: ");
		numbers.stream()
			.filter(num -> num > 5)
			.findFirst()
			.ifPresent(System.out::println);

		System.out.print("Kiểm tra list chẵn: ");
		System.out.println(
			numbers.stream()
			.allMatch(num -> num % 2 == 0)
		);

		System.out.print("Kiểm tra list có phần tử lớn hơn 10");
		System.out.println(
			numbers.stream()
			.anyMatch(num -> num > 10)
		);

		System.out.print("Số phần tử > 5: ");
		System.out.println(
			numbers.stream()
				.filter(num -> num > 5)
				.count()
		);

		System.out.println("Nhân đôi các phần tử trong list:" );
		System.out.println(
			numbers.stream()
			.map(num -> num * 2)
			.collect(Collectors.toList())
		);

		System.out.print("Kiểm tra list không chứa giá trị nào bằng 8");
		System.out.println(
			numbers.stream()
			.noneMatch(num -> num == 8)
		);
	}

}
