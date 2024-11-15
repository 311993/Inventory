package demos.graphicaldemo;

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
    private DemoCanvas canvas;

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
        this.canvas.addKeyListener(this);

        this.getContentPane().add(this.canvas);

        this.getContentPane().setBackground(Color.BLACK);

        this.setFocusable(true);
        this.requestFocus();
        this.setUndecorated(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** Places the item text in the canvas. */
    public void placeItems() {
        for (int i = 0; i < 11; i++) {
            this.canvas.drawText("!ABCD WXYZ", 32, 44 + i * 16);

            if (i < 5 || i > 7) {
                this.canvas.drawText("0123456789", 160, 44 + i * 16);
            }
        }
    }

    /** Places the menu boxes in the canvas. */
    public void placeBoxes() {
        this.canvas.drawBox(0, 0, 8, 2);
        this.canvas.drawText(" General", 32, 12);
        this.canvas.drawBox(0, 2, 8, 12);

        this.canvas.drawBox(8, 0, 8, 2);
        this.canvas.drawText("Equipment", 160, 12);
        this.canvas.drawBox(8, 2, 8, 6);

        this.canvas.drawBox(8, 8, 8, 2);
        this.canvas.drawText(" Usables", 160, 140);
        this.canvas.drawBox(8, 10, 8, 4);
    }

    /**
     * Places the cursor in the canvas at (x,y).
     *
     * @param x
     *            the x position at which to place the cursor
     * @param y
     *            the y position at which to place the cursor
     */
    public void placeCursor(int x, int y) {
        this.canvas.drawText("2", x, y);
    }

    /** Updates the canvas with any graphical changes. */
    public void render() {
        this.canvas.paint(this.canvas.getGraphics());
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void keyTyped(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {

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
