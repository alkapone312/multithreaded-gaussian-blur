package pl.pwr.convolution;

public class SingleThreadConvolution implements Convolution {
    @Override
    public Double[][] calculateConvolution(Double[][] matrix, Double[][] filter) {
        int matrixHeight = matrix.length;
        int matrixWidth = matrix[0].length;
        int filterHeight = filter.length;
        int filterWidth = filter[0].length;

        Double[][] output = new Double[matrixHeight][matrixWidth];

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                double sum = 0.0;
                for (int ky = 0; ky < filterHeight; ky++) {
                    for (int kx = 0; kx < filterWidth; kx++) {
                        double matrixValue = 0.0;
                        int matY = y + ky - filterHeight / 2;
                        int matX = x + kx - filterWidth / 2;
                        if(matX >= 0 && matX < matrixWidth && matY >= 0 && matY < matrixHeight) {
                            matrixValue = matrix[matY][matX];
                        }

                        sum += filter[ky][kx] * matrixValue;
                    }
                }
                output[y][x] = sum;
            }
        }

        return output;
    }
}
