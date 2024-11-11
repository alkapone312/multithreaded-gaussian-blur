package test;

import pl.pwr.convolution.Convolution;
import pl.pwr.convolution.SingleThreadConvolution;

import java.util.Objects;

public class ConvolutionTest extends AbstractTest {
    public static void runTests() {
        testThatJavaConvolutionHaveCorrectValuesForSquareMatrix(new SingleThreadConvolution());
        testThatJavaConvolutionHaveCorrectValuesForNonSquareMatrix(new SingleThreadConvolution());
    }

    public static void testThatJavaConvolutionHaveCorrectValuesForSquareMatrix(Convolution conv) {
        Double[][] result = conv.calculateConvolution(new Double[][]{
            new Double[]{1.0,2.0,3.0},
            new Double[]{4.0,5.0,6.0},
            new Double[]{7.0,8.0,9.0}
        },
        new Double[][]{
            new Double[]{0.0,1.0,0.0},
            new Double[]{0.0,1.0,0.0},
            new Double[]{1.0,0.0,1.0}
        });

        Double[][] expected = new Double[][]{
            new Double[]{6.0,12.0,8.0},
            new Double[]{13.0,23.0,17.0},
            new Double[]{11.0,13.0,15.0}
        };

        boolean isOk = true;
        for(int i = 0 ; i < result.length; i++) {
            for(int j = 0 ; j < result[0].length; j++) {
                if (!Objects.equals(result[i][j], expected[i][j])) {
                    isOk = false;
                    break;
                }
            }
        }

        assertTrue(isOk, "testThatJavaConvolutionHaveCorrectValuesForSquareMatrix");
    }

    public static void testThatJavaConvolutionHaveCorrectValuesForNonSquareMatrix(Convolution conv) {
        Double[][] result = conv.calculateConvolution(new Double[][]{
                new Double[]{1.0,2.0,3.0,1.0},
                new Double[]{4.0,5.0,6.0,1.0},
                new Double[]{7.0,8.0,9.0,1.0}
            },
            new Double[][]{
                new Double[]{0.0,1.0,0.0},
                new Double[]{0.0,1.0,0.0},
                new Double[]{1.0,0.0,1.0}
            });

        Double[][] expected = new Double[][]{
            new Double[]{6.0,12.0,9.0,7.0},
            new Double[]{13.0,23.0,18.0,11.0},
            new Double[]{11.0,13.0,15.0,2.0}
        };

        boolean isOk = true;
        for(int i = 0 ; i < result.length; i++) {
            for(int j = 0 ; j < result[0].length; j++) {
                if (!Objects.equals(result[i][j], expected[i][j])) {
                    isOk = false;
                    break;
                }
            }
        }

        assertTrue(isOk, "testThatJavaConvolutionHaveCorrectValuesForNonSquareMatrix");
    }
}
