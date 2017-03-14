package lambdasinaction.appd;

import java.util.function.Function;

public class Lambda {
    private static Function<Object, String> f = obj -> obj.toString();
    
    public static void main(String [] args)
    {
    	System.out.println(f.apply(new Lambda()));
    }
}