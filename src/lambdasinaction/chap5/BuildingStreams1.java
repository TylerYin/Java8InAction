package lambdasinaction.chap5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class BuildingStreams1 {
	public static void main(String[] args) throws IOException {
		// 有限流，其中range方法是不包含结束值的，rangeClosed方法是包含返回值的
		IntStream.rangeClosed(1, 20).filter(n -> n % 2 == 0).forEach(System.out::println);
		LongStream.range(1, 10).forEach(System.out::println);

		// 构建流
		// 1.由值创建流 Stream.of
		Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);

		// 2.由数组创建流 Arrays.stream
		int[] numbers = { 2, 3, 5 };
		System.out.println(Arrays.stream(numbers).sum());

		// 3.由文件创建流
		long uniqueWords = Files.lines(Paths.get("src/lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
				.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
		System.out.println("There are " + uniqueWords + " unique words in data.txt");

		// 4.由函数创建无限流，一般情况下需要通过limit方法进行范围限制 Stream.iterate和Stream.generate
		Stream.iterate(0, n -> n + 4).limit(5).forEach(System.out::println);
		IntStream.generate(() -> 6).limit(5).forEach(System.out::println);
	}
}