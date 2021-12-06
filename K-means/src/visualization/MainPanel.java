package visualization;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    private static final long serialVersionUID = -662473925955493029L;
    protected DisplayPanel displayPanel = new DisplayPanel();

    public MainPanel() {
        super(new BorderLayout());
        this.add(this.displayPanel, "Center");
    }

    public void drawPoints(ArrayList<Point> points) {
        this.displayPanel.drawPoints(points);
    }

    public void shiftLeftAll() {
        this.displayPanel.shiftLeftAll();
    }

    public void shiftUpAll() {
        this.displayPanel.shiftUpAll();
    }

    public void shiftDownAll() {
        this.displayPanel.shiftDownAll();
    }

    public void shiftRightAll() {
        this.displayPanel.shiftRightAll();
    }

    public void refresh() {
        this.displayPanel.refresh();
    }

    public ArrayList<Point> getPoints() {
        return this.displayPanel.getPoints();
    }

    public void addClusterAndT(ArrayList<ArrayList<Point>> cluster, long t) {
        this.displayPanel.addClusterAndT(cluster, t);
    }
}