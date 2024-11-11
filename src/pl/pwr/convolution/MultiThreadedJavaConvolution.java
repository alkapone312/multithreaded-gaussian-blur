package pl.pwr.convolution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedJavaConvolution implements Convolution {
    private final int numThreads;

    public MultiThreadedJavaConvolution(int numThreads) {
        this.numThreads = numThreads;
    }

    @Override
    public Double[][] calculateConvolution(Double[][] matrix, Double[][] filter) {
        int matrixHeight = matrix.length;
        int matrixWidth = matrix[0].length;
        Double[][] output = new Double[matrixHeight][matrixWidth];

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            for (int i = 0; i < numThreads; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    int startRow = (matrixHeight * threadId) / numThreads;
                    int endRow = (matrixHeight * (threadId + 1)) / numThreads;
                    applyConvolution(matrix, filter, output, startRow, endRow);
                });
            }
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Wątki przerwane przed ukończeniem.");
            }
        }

        return output;
    }

    private void applyConvolution(Double[][] matrix, Double[][] filter, Double[][] output, int startRow, int endRow) {
        int filterHeight = filter.length;
        int filterWidth = filter[0].length;
        int matrixHeight = matrix.length;
        int matrixWidth = matrix[0].length;

        for (int y = startRow; y < endRow; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                double sum = 0.0;
                for (int ky = 0; ky < filterHeight; ky++) {
                    for (int kx = 0; kx < filterWidth; kx++) {
                        double matrixValue = 0.0;
                        int matY = y + ky - filterHeight / 2;
                        int matX = x + kx - filterWidth / 2;
                        if (matX >= 0 && matX < matrixWidth && matY >= 0 && matY < matrixHeight) {
                            matrixValue = matrix[matY][matX];
                        }
                        sum += filter[ky][kx] * matrixValue;
                    }
                }
                output[y][x] = sum;
            }
        }
    }
}
