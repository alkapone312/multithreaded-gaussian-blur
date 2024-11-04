package pl.pwr.jk.jni;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BitmapManager {
    public Double[][] readBMPToDoubleArray(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));

            int width = image.getWidth();
            int height = image.getHeight();

            Double[][] pixelArray = new Double[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;

                    double grayscale = (0.299 * red + 0.587 * green + 0.114 * blue) / 255.0;

                    pixelArray[y][x] = grayscale;
                }
            }
            return pixelArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveDoubleArrayToBMP(Double[][] pixelArray, String filePath) {
        int height = pixelArray.length;
        int width = pixelArray[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = (int) (pixelArray[y][x] * 255.0);
                int rgb = (grayValue << 16) | (grayValue << 8) | grayValue;
                image.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(image, "bmp", new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
