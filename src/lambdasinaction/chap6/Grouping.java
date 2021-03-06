package lambdasinaction.chap6;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static lambdasinaction.chap6.Dish.menu;

public class Grouping {

	enum CaloricLevel {
		DIET, NORMAL, FAT
	};

	public static void main(String... args) {
		System.out.println("Dishes grouped by type: " + groupDishesByType());
		System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
		System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
		System.out.println("Count dishes in groups: " + countDishesInGroups());
		System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
		System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOptionals());
		System.out.println("Sum calories by type: " + sumCaloriesByType());
		System.out.println("Caloric levels by type: " + caloricLevelsByType());
	}

	private static Map<Dish.Type, List<Dish>> groupDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType));
	}

	private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
		return menu.stream().collect(groupingBy(dish -> {
			if (dish.getCalories() <= 400)
				return CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}));
	}

	private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
		return menu.stream().collect(groupingBy(Dish::getType, groupingBy((Dish dish) -> {
			if (dish.getCalories() <= 400)
				return CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		})));
	}

	private static Map<Dish.Type, Long> countDishesInGroups() {
		return menu.stream().collect(groupingBy(Dish::getType, Collectors.counting()));
	}

	private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType,
				Collectors.reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
	}

	private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
		return menu.stream().collect(groupingBy(Dish::getType,
				collectingAndThen(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2), Optional::get)));
	}

	private static Map<Dish.Type, Integer> sumCaloriesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
	}

	private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
		return menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
			if (dish.getCalories() <= 400)
				return CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}, toSet())));
	}
}
