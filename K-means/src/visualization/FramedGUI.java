package visualization;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

public class FramedGUI extends JFrame {
    private static final long serialVersionUID = 599149216192397088L;
    protected RootPanel rootPanel;

    public FramedGUI(int width, int height, String title, int nbPoints) {
        super(title);
        this.setDefaultCloseOperation(3);
        this.rootPanel = new RootPanel();
        this.getContentPane().add(this.rootPanel);
        this.addKeyListener(new Keymaps(this.rootPanel, nbPoints));
        if (width >= 100 && height >= 100) {
            this.setSize(new Dimension(width, height));
        } else {
            this.pack();
        }

        this.setVisible(true);
        synchronized(Variables.lock) {
            Variables.lock.notify();
        }
    }

    public void drawPoints(ArrayList<Point> points) {
        this.rootPanel.drawPoints(points);
    }
}
