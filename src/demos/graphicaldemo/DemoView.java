package demos.graphicaldemo;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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
        UP, DOWN, LEFT, RIGHT, A, B, X, Y
    }

    /** KeyCodes for key events. */
    private static final int[] KEYCODES = { 38, 40, 37, 39, 86, 67, 88, 90 };

    /** Vertical header text offset. */
    private static final int HEADER_OFFSET = 12;

    /** The size of a single grid cell on the display. */
    private static final int CELLSIZE = 16;

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

    /**
     * Displays a vertical list of item names on the canvas at {@code (x,y)}.
     *
     * @param names
     *            the list of item names to display
     * @param x
     *            the x position to display the names at
     * @param y
     *            the y position to display the names at
     */
    public void placeItems(ArrayList<String> names, int x, int y) {

        for (int i = 0; i < names.size(); i++) {
            this.canvas.drawText(names.get(i), x, y + i * CELLSIZE);
        }
    }

    /**
     * Places a menu box in the canvas with label {@code header} at
     * {@code (x,y)}. The box be {@code width}cells wide and will have a 2 cell
     * tall header block over a column block {@code length} cells tall.
     *
     * @param header
     *            the header label for the box
     * @param colX
     *            the x position of the box's column in cells
     * @param headerY
     *            the y position of the column's header block in cells
     * @param width
     *            the width of the box
     * @param length
     *            the vertical length of the box's column
     */
    public void placeBox(String header, int colX, int headerY, int width,
            int length) {

        final int headerX = (CELLSIZE * width - 8 * header.length()) / 2;

        this.canvas.drawBox(colX, headerY, width, 2);
        this.canvas.drawText(header, colX * CELLSIZE + headerX,
                headerY * CELLSIZE + HEADER_OFFSET);
        if (length > 0) {
            this.canvas.drawBox(colX, headerY + 2, width, length);
        }
    }

    /**
     * Places the cursor in the canvas at {@code (x,y)}.
     *
     * @param x
     *            the x position at which to place the cursor
     * @param y
     *            the y position at which to place the cursor
     */
    public void placeCursor(int x, int y) {
        this.canvas.drawText("\"", x, y);
    }

    /** Updates the canvas with any graphical changes. */
    public void render() {
        this.canvas.paint(this.canvas.getGraphics());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Do nothing
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
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

                case X:
                    this.controller.buttonX();
                    break;

                case Y:
                    this.controller.buttonY();
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
