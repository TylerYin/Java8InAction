package lambdasinaction.appd;

import java.util.function.Function;

public class InnerClass {
    private static Function<Object, String> f = new Function<Object, String>() {
        @Override
        public String apply(Object obj) {
            return obj.toString();
        }
    };
    
    public static void main(String [] args)
    {
    	System.out.println(f.apply(new InnerClass()));
    }
}