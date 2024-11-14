package demos.graphicaldemo;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DemoCanvas extends Canvas {

    /** The screen dimensions. */
    private final int WIDTH, HEIGHT;

    /** Ratio of buffer size to display on screen. */
    private final int ratio;

    /** The buffer dimensions. */
    private static final int SIM_WIDTH = 256, SIM_HEIGHT = 224;

    private BufferedImage buffer;

    private Graphics g;

    public DemoCanvas(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;

        this.setSize(this.WIDTH, this.HEIGHT);
        this.setVisible(true);

        this.buffer = new BufferedImage(SIM_WIDTH, SIM_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        this.g = this.buffer.getGraphics();

        if (this.WIDTH / SIM_WIDTH > this.HEIGHT / SIM_HEIGHT) {
            this.ratio = (int) Math.floor(this.HEIGHT / SIM_HEIGHT);
        } else {
            this.ratio = (int) Math.floor(this.WIDTH / SIM_WIDTH);
        }
    }

    @Override
    public void paint(Graphics window) {

        window.drawImage(this.buffer, (this.WIDTH - SIM_WIDTH * this.ratio) / 2,
                (this.HEIGHT - SIM_HEIGHT * this.ratio) / 2,
                SIM_WIDTH * this.ratio, SIM_HEIGHT * this.ratio, null);
    }
}
