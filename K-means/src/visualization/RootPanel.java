package visualization;

import java.awt.CardLayout;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class RootPanel extends JPanel {
    private static final long serialVersionUID = -7573425545656207548L;
    protected CardLayout layout = (CardLayout)this.getLayout();
    protected MainPanel mainPanel = new MainPanel();

    public RootPanel() {
        super(new CardLayout());
        this.add(this.mainPanel, "1664");
        this.layout.show(this, "1664");
    }

    public void drawPoints(ArrayList<Point> points) {
        this.mainPanel.drawPoints(points);
    }

    public void shiftLeftAll() {
        this.mainPanel.shiftLeftAll();
    }

    public void shiftUpAll() {
        this.mainPanel.shiftUpAll();
    }

    public void shiftDownAll() {
        this.mainPanel.shiftDownAll();
    }

    public void shiftRightAll() {
        this.mainPanel.shiftRightAll();
    }

    public void refresh() {
        this.mainPanel.refresh();
    }

    public ArrayList<Point> getPoints() {
        return this.mainPanel.getPoints();
    }

    public void addClusterAndT(ArrayList<ArrayList<Point>> cluster, long t) {
        this.mainPanel.addClusterAndT(cluster, t);
    }
}