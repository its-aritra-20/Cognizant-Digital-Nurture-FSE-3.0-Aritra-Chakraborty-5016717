// SingletonTest.java
public class SingletonTest {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Check if both instances are the same
        System.out.println(logger1 == logger2); // true

        // Log a message using both instances
        logger1.log("Hello from logger1!");
        logger2.log("Hello from logger2!");
    }
}
