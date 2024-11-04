package test;

public class Main {

    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        ConvolutionTest.runTests();
    }
}
