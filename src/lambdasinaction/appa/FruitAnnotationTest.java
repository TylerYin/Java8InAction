package lambdasinaction.appa;

import java.lang.reflect.Field;

public class FruitAnnotationTest {
	public static void main(String[] args) {
		getFruitInfo(Fruit.class);
	}

	private static void getFruitInfo(Class<?> clazz) {

		String strFruitName = " 水果名称：";
		String strFruitColor = " 水果颜色：";
		String strFruitProvicer = "供应商信息：";

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(FruitTypeAnnotation.class)) {
				FruitTypeAnnotation fruitName = (FruitTypeAnnotation) field
						.getAnnotation(FruitTypeAnnotation.class);
				strFruitName = strFruitName + fruitName.value();
				System.out.println(strFruitName);
			} else if (field.isAnnotationPresent(FruitColorAnnotation.class)) {
				FruitColorAnnotation fruitColor = (FruitColorAnnotation) field
						.getAnnotation(FruitColorAnnotation.class);
				strFruitColor = strFruitColor + fruitColor.fruitColor().name().toString();
				System.out.println(strFruitColor);
			} else if (field.isAnnotationPresent(FruitProviderAnnotation.class)) {
				FruitProviderAnnotation fruitProvider = (FruitProviderAnnotation) field
						.getAnnotation(FruitProviderAnnotation.class);
				strFruitProvicer = " 供应商编号：" + fruitProvider.id() + " 供应商名称：" + fruitProvider.name() + " 供应商地址："
						+ fruitProvider.address();
				System.out.println(strFruitProvicer);
			}
		}
	}
}