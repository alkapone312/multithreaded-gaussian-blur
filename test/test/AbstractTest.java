package test;

public abstract class AbstractTest {
    public static void assertTrue(boolean expression, String testMessage) {
        if(expression) {
            System.out.println("\033[42m\033[37m✓ " + testMessage + "\033[0m");
        } else {
            System.out.println("\033[41m\033[37m✗ " + testMessage + "\033[0m");
        }
    }
}
