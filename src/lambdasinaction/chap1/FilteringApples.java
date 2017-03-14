package lambdasinaction.chap1;

import java.util.*;
import java.util.function.Predicate;

public class FilteringApples {

	private static Predicate<Apple> isGreenApple = new Predicate<Apple>() {
		@Override
		public boolean test(Apple t) {
			return "green".equals(t.getColor());
		}
	};

	private static Predicate<Apple> isHeavyApple = new Predicate<Apple>() {
		@Override
		public boolean test(Apple t) {
			return t.getWeight() > 150;
		}
	};

	public static void main(String... args) {
		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

		List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
		System.out.println(greenApples);
		List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
		System.out.println(heavyApples);

		List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
		System.out.println(greenApples2);
		List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
		System.out.println(heavyApples2);

		List<Apple> weirdApples = filterApples(inventory,
				(Apple a) -> a.getWeight() < 80 || "green".equals(a.getColor()));
		System.out.println(weirdApples);
		
		inventory.stream().filter(apple->"green".equals(apple.getColor()));
	}

	// public static List<Apple> filterGreenApples(List<Apple> inventory) {
	// List<Apple> result = new ArrayList<>();
	// for (Apple apple : inventory) {
	// if ("green".equals(apple.getColor())) {
	// result.add(apple);
	// }
	// }
	// return result;
	// }
	//
	// public static List<Apple> filterHeavyApples(List<Apple> inventory) {
	// List<Apple> result = new ArrayList<>();
	// for (Apple apple : inventory) {
	// if (apple.getWeight() > 150) {
	// result.add(apple);
	// }
	// }
	// return result;
	// }

	public static boolean isGreenApple(Apple apple) {
		return "green".equals(apple.getColor());
	}

	public static boolean isHeavyApple(Apple apple) {
		return apple.getWeight() > 150;
	}

	public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color) {
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
