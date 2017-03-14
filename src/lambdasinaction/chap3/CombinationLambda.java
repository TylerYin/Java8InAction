package lambdasinaction.chap3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class CombinationLambda {

	private BiFunction<Integer, String, Apple> appleFactory = Apple::new;
	private Apple apple1 = appleFactory.apply(70, "red");
	private Apple apple2 = appleFactory.apply(60, "green");
	private Apple apple3 = appleFactory.apply(90, "blue");
	private Apple apple4 = appleFactory.apply(80, "green");

	public static void main(String[] args) {
		CombinationLambda combinationLambda = new CombinationLambda();

		combinationLambda.predicateCombination();
		System.out.println();

		combinationLambda.comparatorCombination();
		System.out.println();

		combinationLambda.functionCombination();
	}

	public void predicateCombination() {
		Predicate<Apple> heavyApple = a -> a.getWeight() > 80;

		Predicate<Apple> redApple = a -> "red".equals(a.getColor());
		Predicate<Apple> notRedApple = redApple.negate();

		Predicate<Apple> heavyAndRedApple = redApple.and(heavyApple);
		Predicate<Apple> heavyOrRedApple = redApple.or(heavyApple);

		System.out.println(redApple.test(apple1));
		System.out.println(notRedApple.test(apple1));
		System.out.println(heavyAndRedApple.test(apple1));
		System.out.println(heavyOrRedApple.test(apple1));
	}

	public void comparatorCombination() {
		List<Apple> appleList = new ArrayList<>();
		appleList.add(apple1);
		appleList.add(apple2);
		appleList.add(apple3);
		appleList.add(apple4);

		Comparator<Apple> weightComparator = Comparator.comparing(Apple::getWeight);
		Comparator<Apple> colorComparator = Comparator.comparing(Apple::getColor);

		System.out.println("=====重量排序======");
		appleList.sort(weightComparator);
		appleList.stream().forEach(System.out::println);

		System.out.println("=====重量逆序排序======");
		appleList.sort(weightComparator.reversed());
		appleList.stream().forEach(System.out::println);

		System.out.println("=====先按照颜色，再按照重量排序======");
		appleList.sort(colorComparator.thenComparing(weightComparator));
		appleList.stream().forEach(System.out::println);
	}

	public void functionCombination() {
		Function<Integer, Integer> f = x -> x + 1;
		Function<Integer, Integer> g = x -> x * 2;

		Function<Integer, Integer> h = f.andThen(g);
		Function<Integer, Integer> k = f.compose(g);

		System.out.println("Function<Integer, Integer> h = " + h.apply(2));
		System.out.println("Function<Integer, Integer> k = " + k.apply(2));
	}

	public class Apple {
		private Integer weight = 0;
		private String color = "";

		public Apple(Integer weight, String color) {
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" + "color='" + color + '\'' + ", weight=" + weight + '}';
		}
	}
}
