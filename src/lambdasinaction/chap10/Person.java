package lambdasinaction.chap10;

import java.util.*;

public class Person {

	private Optional<Car> car;

	public Optional<Car> getCar() {
		return car;
	}

	public void setCar(Optional<Car> car) {
		this.car = car;
	}
}
