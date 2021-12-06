package visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

@SuppressWarnings("unchecked")
public class DisplayPanel extends JPanel {
    private static final long serialVersionUID = -1401707925288150149L;
    private int xModifier;
    private int yModifier;
    private Graphics2D g2d;
    private ArrayList<Point> points;
    private long time;
    private double totalDistance;
    private ArrayList<Property> properties;
    private ArrayList<ArrayList<Point>> clusters;
    private static final Color[] sevenColors;

    public DisplayPanel() {
        this.setPreferredSize(new Dimension(1500, 1000));
        this.xModifier = 10;
        this.yModifier = 10;
        this.points = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.clusters = new ArrayList<>();
        this.time = -1L;
        this.totalDistance = -1.0D;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D)g.create();
        this.g2d.setFont(new Font(this.g2d.getFont().getName(), 1, 18));
        this.g2d.drawString("Clavier:", 5, 20);
        this.g2d.drawString("- 'r': refresh the plan of points", 15, 40);
        this.g2d.drawString("- 'c': calculate K-means", 15, 60);
        this.g2d.drawString("- 'h', 'j', 'k', 'l': move the plan of points", 15, 80);
        this.g2d.setColor(Color.BLUE);
        this.g2d.setStroke(new BasicStroke(6.0F, 1, 1));

        int x;
        int y;
        int i;
        for(i = 0; i < this.points.size(); ++i) {
            if ((x = (int) this.points.get(i).getX() + this.xModifier) >= 10 && (y = (int) this.points.get(i).getY() + this.yModifier) >= 10) {
                this.g2d.drawLine(x, y, x, y);
            }
        }

        if (this.clusters.size() > 0) {
            for(i = 0; i < this.clusters.size(); ++i) {
                if (i < 7) {
                    this.g2d.setColor(sevenColors[i]);
                } else {
                    this.g2d.setColor(new Color(i % 256, i % 256, i % 256));
                }

                this.g2d.setStroke(new BasicStroke(6.0F, 1, 1));

                for(int j = 0; j < this.clusters.get(i).size(); ++j) {
                    if ((x = (int)((this.clusters.get(i)).get(j)).getX() + this.xModifier) >= 10 && (y = (int)((this.clusters.get(i)).get(j)).getY() + this.yModifier) >= 10) {
                        this.g2d.drawLine(x, y, x, y);
                    }
                }

                this.g2d.drawLine((int) this.properties.get(i).centerX - 5 + this.xModifier, (int) this.properties.get(i).centerY - 5 + this.yModifier, (int) this.properties.get(i).centerX + 5 + this.xModifier, (int) this.properties.get(i).centerY + 5 + this.yModifier);
                this.g2d.drawLine((int) this.properties.get(i).centerX - 5 + this.xModifier, (int) this.properties.get(i).centerY + 5 + this.yModifier, (int) this.properties.get(i).centerX + 5 + this.xModifier, (int) this.properties.get(i).centerY - 5 + this.yModifier);
            }
        }

        this.g2d.setStroke(new BasicStroke(4.0F, 1, 1));

        if (this.time != -1L) {
            this.g2d.setColor(Color.BLACK);
            this.g2d.setFont(new Font(this.g2d.getFont().getName(), 1, 32));
            this.g2d.drawString("Time of calculation " + this.time + " ms", 20, 150);
        }

        if (this.totalDistance != -1.0D) {
            this.g2d.setColor(Color.BLACK);
            this.g2d.setFont(new Font(this.g2d.getFont().getName(), 1, 32));
            this.g2d.drawString("Total distance: " + (long) this.totalDistance, 20, 200);
        }

        if (this.clusters.size() > 0) {
            this.g2d.setColor(Color.BLACK);
            this.g2d.setFont(new Font(this.g2d.getFont().getName(), 1, 18));

            for(i = 0; i < this.clusters.size(); ++i) {
                this.g2d.drawString("Cluster #" + i + " (population " + this.properties.get(i).population + "): total distance to the bary center " + (long) this.properties.get(i).totalDistance, 20, 250 + i * 20);
            }
        }

        this.time = -1L;
    }

    public void drawPoints(ArrayList<Point> points) {
        this.clusters.clear();
        this.points = points;
        this.repaint();
    }

    public void shiftLeftAll() {
        this.xModifier -= 10;
        this.repaint();
    }

    public void shiftUpAll() {
        this.yModifier -= 10;
        this.repaint();
    }

    public void shiftDownAll() {
        this.yModifier += 10;
        this.repaint();
    }

    public void shiftRightAll() {
        this.xModifier += 10;
        this.repaint();
    }

    public void refresh() {
        this.time = -1L;
        this.totalDistance = -1.0D;
        this.repaint();
    }

    public ArrayList<Point> getPoints() {
        return (ArrayList<Point>) this.points.clone();
    }

    private double crossProd(double ux, double uy, double vx, double vy) {
        return ux * vy - uy * vx;
    }

    private double dotProd(double ux, double uy, double vx, double vy) {
        return ux * vx + uy * vy;
    }

    public void addClusterAndT(ArrayList<ArrayList<Point>> cluster, long t) {
        this.clusters.clear();
        this.clusters = (ArrayList)cluster.clone();
        this.time = t;
        this.properties = this.computeProperties(cluster);
        this.totalDistance = this.sumDist(this.properties);
        this.repaint();
    }

    private double sumDist(ArrayList<Property> as) {
        return as.stream().mapToDouble(a -> a.totalDistance).sum();
    }

    private double[] mean(ArrayList<Point> points) {
        double[] mean = new double[]{0.0D, 0.0D};

        Point p;
        for(Iterator<Point> iterator = points.iterator(); iterator.hasNext(); mean[1] += (double)p.y / (double)points.size()) {
            p = iterator.next();
            mean[0] += (double)p.x / (double)points.size();
        }

        return mean;
    }

    private double distanceFromC(ArrayList<Point> points, double[] center) {
        double d;
        Point c = new Point();
        c.setLocation(center[0], center[1]);
        d = points.stream().mapToDouble(c::distance).sum();
        return d;
    }

    private ArrayList<Property> computeProperties(ArrayList<ArrayList<Point>> cluster) {
        ArrayList<Property> attributs = new ArrayList<>();
        double[] center = new double[2];

        for (ArrayList<Point> c : cluster) {
            center = this.mean(c);
            double d = this.distanceFromC(c, center);
            Property a = new Property(d, center[0], center[1], c.size());
            attributs.add(a);
        }

        return attributs;
    }

    static {
        sevenColors = new Color[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.LIGHT_GRAY, Color.BLUE};
    }
}
