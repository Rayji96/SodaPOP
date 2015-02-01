package soytea.sodapop;

/**
 * Created by shaw on 15-01-31.
 */
public class Singleton {

    public static Counter counter;


    public static int pricePassive = 20;
    public static int priceMultiply = 50;


    private static Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }



}
