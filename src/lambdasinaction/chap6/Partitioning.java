package lambdasinaction.chap6;

import java.util.*;
import java.util.stream.Collectors;

import static lambdasinaction.chap6.Dish.menu;

public class Partitioning {
	public static void main(String... args) {
		System.out.println("Dishes partitioned by vegetarian: " + partitionByVegeterian());
		System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
		System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
	}

	private static Map<Boolean, List<Dish>> partitionByVegeterian() {
		return menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
	}

	private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
		return menu.stream()
				.collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
	}

	private static Object mostCaloricPartitionedByVegetarian() {
		return menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors
				.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
	}
}