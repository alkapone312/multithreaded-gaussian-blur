package pl.pwr.jk.jni;


public class Main {
    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Usage: conv [bmp_file] [filter_csv] [method (default java)]");
            return;
        }

        Convolution convolution = new JavaConvolution();

        if(args.length == 3) {
            if(args[2].equals("native")) {
                convolution = new NativeConvolution();
            }
        }

        var bmp = new BitmapManager();
        Double[][] out = bmp.readBMPToDoubleArray(args[0]);
        Double[][] filter = new CsvReader().readCSVToDoubleArray(args[1]);
        long time = System.nanoTime();
        out = convolution.calculateConvolution(
            out,
            filter
        );
        System.out.println("Elapsed time:                   " + ((System.nanoTime() - time)/1000) + "us");
        System.out.println("File   name:                    " + args[0]);
        System.out.println("Filter name:                    " + args[1]);
        System.out.println("Convolution calculation method: " + (convolution.getClass().getName().equals(NativeConvolution.class.getName()) ? "native" : "java"));
        System.out.println("Matrix height:                  " + out.length);
        System.out.println("Matrix width:                   " + out[0].length);
        System.out.println("Filter height:                  " + filter.length);
        System.out.println("Filter width:                   " + filter[0].length);
        bmp.saveDoubleArrayToBMP(out, args[0] + ".out");
    }
}
