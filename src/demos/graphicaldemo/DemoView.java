package demos.graphicaldemo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * View creator for the graphical inventory demo.
 *
 * @author David Stuckey
 */
public class DemoView extends JFrame implements KeyListener {

    /** The canvas on which the demo is drawn. */
    private Canvas canvas;

    /** The screen dimensions. */
    private final int width, height;

    /** The controller. */
    private DemoController controller;

    /** Indices for each tracked key in KEYCODES and keys[]. */
    private enum Keys {
        UP, DOWN, LEFT, RIGHT, A, B
    }

    /** KeyCodes for key events. */
    private static final int[] KEYCODES = { 38, 40, 37, 39, 86, 67, 88, 90 };

    /** Create a new DemoView. */
    public DemoView() {
        super("Graphical Inventory Demo");

        this.width = (int) Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth();
        this.height = (int) Toolkit.getDefaultToolkit().getScreenSize()
                .getHeight();

        this.setSize(this.width, this.height);

        this.canvas = new DemoCanvas(this.width, this.height);

        this.getContentPane().add(this.canvas);

        this.getContentPane().setBackground(Color.BLACK);

        this.setFocusable(true);
        this.setUndecorated(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void keyTyped(KeyEvent e) {

        this.canvas.paint(this.getGraphics());

        int key = -1;

        for (int i = 0; i < KEYCODES.length; i++) {
            if (e.getKeyCode() == KEYCODES[i]) {
                key = i;
            }
        }

        if (key >= 0) {
            switch (Keys.values()[key]) {
                case UP:
                    this.controller.up();
                    break;

                case DOWN:
                    this.controller.down();
                    break;

                case LEFT:
                    this.controller.left();
                    break;

                case RIGHT:
                    this.controller.right();
                    break;

                case A:
                    this.controller.buttonA();
                    break;

                case B:
                    this.controller.buttonB();
                    break;

                default:
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Do nothing
    }

    /**
     * Adds the controller to {@code this} as an event observer.
     *
     * @param controller
     *            the controller
     */
    public void registerObserver(DemoController controller) {
        this.controller = controller;
    }
}
