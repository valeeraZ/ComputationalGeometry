package visualization;

import algorithms.DefaultTeam;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keymaps implements KeyListener {
    private RootPanel rootPanel;
    private int nbPoints;

    public Keymaps(RootPanel rootPanel, int nbPoints) {
        this.rootPanel = rootPanel;
        this.nbPoints = nbPoints;
    }

    public void keyPressed(KeyEvent arg0) {
    }

    public void keyReleased(KeyEvent arg0) {
    }

    public void keyTyped(KeyEvent event) {
        ArrayList<ArrayList<Point>> kmeans;
        long t;
        switch(event.getKeyChar()) {
            case 'c':
                t = System.currentTimeMillis();
                kmeans = (new DefaultTeam()).calculKMeans(this.rootPanel.getPoints());
                t = System.currentTimeMillis() - t;
                this.rootPanel.addClusterAndT(kmeans, t);
            case 'd':
            case 'e':
            case 'f':
            case 'i':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            default:
                break;
            case 'h':
                this.rootPanel.shiftLeftAll();
                break;
            case 'j':
                this.rootPanel.shiftUpAll();
                break;
            case 'k':
                this.rootPanel.shiftDownAll();
                break;
            case 'l':
                this.rootPanel.shiftRightAll();
                break;
            case 'r':
                try {
                    RandomPointsGenerator.generate(this.nbPoints);
                    KMeansViewer.readFile();
                    this.rootPanel.refresh();
                } catch (Exception ignored) {
                }
        }

    }
}
