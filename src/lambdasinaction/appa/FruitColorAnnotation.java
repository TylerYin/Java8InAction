package lambdasinaction.appa;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FruitColorAnnotation {
	
    public enum Color{ BULE,RED,GREEN};
    
    Color fruitColor() default Color.GREEN;
}
