package pl.pwr.jk.jni;

public class NativeConvolution implements Convolution {
    @Override
    public Double[][] calculateConvolution(Double[][] matrix, Double[][] filter) {
        return nativelyCalculateConvolution(matrix, filter);
    }

    private native Double[][] nativelyCalculateConvolution(Double[][] matrix, Double[][] filter);
}
