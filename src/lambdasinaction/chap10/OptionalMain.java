package lambdasinaction.chap10;

import java.util.*;

public class OptionalMain {

	public static void main(String[] args) {
		Insurance insurance = new Insurance();
		insurance.setName("insurance name");
		Optional<Insurance> insuranceOpt = Optional.of(insurance);

		Car car = new Car();
		car.setInsurance(insuranceOpt);
		Optional<Car> carOpt = Optional.of(car);

		Person person = new Person();
		person.setCar(carOpt);
		Optional<Person> personOpt = Optional.of(person);

		System.out.println(getCarInsuranceName(personOpt));
	}

	private static String getCarInsuranceName(Optional<Person> person) {
		return person.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
	}
}
