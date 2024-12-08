package pl.pwr.convolution;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting program...");

        if (args.length < 2) {
            System.out.println("Usage: conv [bmp_file] [filter_csv] [num_threads (optional)]");
            return;
        }

        Convolution convolution;
        int numThreads = 1;

        if (args.length == 3) {
            try {
                numThreads = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of threads. Please provide an integer.");
                return;
            }
        }

        if (numThreads > 1) {
            convolution = new MultiThreadedJavaConvolution(numThreads);
            System.out.println("Using multi-threaded convolution with " + numThreads + " threads.");
        } else {
            convolution = new SingleThreadConvolution();
            System.out.println("Using single-threaded convolution.");
        }

        var bmp = new BitmapManager();
        Double[][] imageMatrix = bmp.readBMPToDoubleArray(args[0]);
        Double[][] filter = new CsvReader().readCSVToDoubleArray(args[1]);

        long startTime = System.nanoTime();
        Double[][] outputMatrix = convolution.calculateConvolution(imageMatrix, filter);
        long elapsedTime = System.nanoTime() - startTime;
        double elapsedTimeMs = elapsedTime / 1_000_000.0;
        System.out.println("Elapsed time:                   " + elapsedTimeMs + " ms");
        System.out.println("Elapsed time:                   " + (elapsedTime / 1000) + "us");
        System.out.println("File   name:                    " + args[0]);
        System.out.println("Filter name:                    " + args[1]);
        System.out.println("Matrix height:                  " + outputMatrix.length);
        System.out.println("Matrix width:                   " + outputMatrix[0].length);
        System.out.println("Filter height:                  " + filter.length);
        System.out.println("Filter width:                   " + filter[0].length);

        bmp.saveDoubleArrayToBMP(outputMatrix, args[0].replace(".bmp", "_out.bmp"));
    }
}
