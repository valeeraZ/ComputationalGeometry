package visualization;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class KMeansViewer {
    private static int width = 0;
    private static int height = 0;
    private static String title = "K-means";
    private static String filename = "input.points";
    private static FramedGUI framedGUI;
    private static final int nbPoints = 1000;

    public KMeansViewer() {
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                KMeansViewer.framedGUI = new FramedGUI(KMeansViewer.width, KMeansViewer.height, KMeansViewer.title, KMeansViewer.nbPoints);
            }
        });
        synchronized(Variables.lock) {
            try {
                Variables.lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        readFile();
    }

    public static void readFile() {
        ArrayList<Point> points = new ArrayList<>();

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

            try {
                String line;
                while((line = input.readLine()) != null) {
                    String[] coordinates = line.split("\\s+");
                    points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
                }

                framedGUI.drawPoints(points);
                synchronized(Variables.lock2) {
                    Variables.lock2.notify();
                }
            } catch (IOException e) {
                System.err.println("Exception: interrupted I/O.");
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println("I/O exception: unable to close " + filename);
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found.");
        }

    }
}