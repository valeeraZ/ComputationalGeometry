package visualization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class RandomPointsGenerator {
    private static String filename = "input.points";
    private static int numberOfPoints = 10000;
    private static int maxWidth = 1400;
    private static int maxHeight = 900;
    private static int radius = 140;

    public RandomPointsGenerator() {
    }

    public static double distanceToCenter(int x, int y) {
        return Math.min(Math.min(Math.min(Math.sqrt(Math.pow((double)(x - maxWidth / 2), 2.0D) + Math.pow((double)(y - maxHeight / 2), 2.0D)), Math.sqrt(Math.pow((double)x - 2.5D * (double)maxWidth / 6.0D, 2.0D) + Math.pow((double)(y - 2 * maxHeight / 6), 2.0D))), Math.min(Math.sqrt(Math.pow((double)(x - 4 * maxWidth / 6), 2.0D) + Math.pow((double)(y - 2 * maxHeight / 6), 2.0D)), Math.sqrt(Math.pow((double)(x - 2 * maxWidth / 6), 2.0D) + Math.pow((double)(y - 4 * maxHeight / 6), 2.0D)))), Math.sqrt(Math.pow((double)(x - 4 * maxWidth / 6), 2.0D) + Math.pow((double)(y - 4 * maxHeight / 6), 2.0D)));
    }

    public static void main(String[] args) {
        try {
            PrintStream output = new PrintStream(new FileOutputStream(filename));
            Random generator = new Random();

            for(int i = 0; i < numberOfPoints; ++i) {
                int x;
                int y;
                do {
                    x = generator.nextInt(maxWidth);
                    y = generator.nextInt(maxHeight);
                } while(!(distanceToCenter(x, y) < (double)radius * 1.4D) && (!(distanceToCenter(x, y) < (double)radius * 1.6D) || generator.nextInt(5) != 1) && (!(distanceToCenter(x, y) < (double)radius * 1.8D) || generator.nextInt(10) != 1) && (maxHeight / 5 >= x || x >= 4 * maxHeight / 5 || maxHeight / 5 >= y || y >= 4 * maxHeight / 5 || generator.nextInt(100) != 1));

                output.println(Integer.toString(x) + " " + Integer.toString(y));
            }

            output.close();
        } catch (FileNotFoundException e) {
            System.err.println("I/O exception: unable to create " + filename);
        }

    }

    public static void generate(int nbPoints) {
        try {
            PrintStream output = new PrintStream(new FileOutputStream(filename));
            Random generator = new Random();

            for(int i = 0; i < nbPoints; ++i) {
                int x;
                int y;
                do {
                    x = generator.nextInt(maxWidth);
                    y = generator.nextInt(maxHeight);
                } while(!(distanceToCenter(x, y) < (double)radius * 1.4D) && (!(distanceToCenter(x, y) < (double)radius * 1.6D) || generator.nextInt(5) != 1) && (!(distanceToCenter(x, y) < (double)radius * 1.8D) || generator.nextInt(10) != 1) && (maxHeight / 5 >= x || x >= 4 * maxHeight / 5 || maxHeight / 5 >= y || y >= 4 * maxHeight / 5 || generator.nextInt(100) != 1));

                output.println(Integer.toString(x) + " " + Integer.toString(y));
            }

            output.close();
        } catch (FileNotFoundException e) {
            System.err.println("I/O exception: unable to create " + filename);
        }

    }
}