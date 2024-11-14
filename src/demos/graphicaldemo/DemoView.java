package demos.graphicaldemo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class DemoView extends JFrame implements KeyListener {

    public Canvas canvas;

    /** The screen dimensions. */
    private final int WIDTH, HEIGHT;
    private DemoController controller;

    /** The input key states. */
    private boolean keyUp, keyDown, keyLeft, keyRight, keyA, keyB, keyX, keyY;

    public DemoView() {
        super("Graphical Inventory Demo");

        this.WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth();
        this.HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize()
                .getHeight();

        this.setSize(this.WIDTH, this.HEIGHT);

        this.canvas = new DemoCanvas(this.WIDTH, this.HEIGHT);

        this.getContentPane().add(this.canvas);

        this.getContentPane().setBackground(Color.BLACK);

        this.setFocusable(true);
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void registerObserver(DemoController controller) {
        this.controller = controller;
    }
}
