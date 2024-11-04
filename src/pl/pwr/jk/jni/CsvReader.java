package pl.pwr.jk.jni;

import java.io.BufferedReader;
import java.io.FileReader;

public class CsvReader {
    public Double[][] readCSVToDoubleArray(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int rowCount = 0;
            int colCount = 0;

            while ((line = reader.readLine()) != null) {
                rowCount++;
                String[] values = line.split(";");
                if (colCount == 0) {
                    colCount = values.length;
                }
            }

            Double[][] pixelArray = new Double[rowCount][colCount];

            reader.close();
            reader = new BufferedReader(new FileReader(filePath));

            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                for (int col = 0; col < colCount; col++) {
                    pixelArray[row][col] = Double.parseDouble(values[col]);
                }
                row++;
            }

            return pixelArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
