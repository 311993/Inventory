package demos.graphicaldemo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DemoCanvas extends Canvas {

    /** The screen dimensions. */
    private final int WIDTH, HEIGHT;

    /** Ratio of buffer size to display on screen. */
    private final int ratio;

    /** The buffer dimensions. */
    private static final int SIM_WIDTH = 256, SIM_HEIGHT = 224;

    /** The screen buffer. */
    private BufferedImage buffer;

    /** The graphics context of the screen buffer. */
    private Graphics g;

    /**
     * Construct a new DemoCanvas.
     *
     * @param w
     *            the width
     * @param h
     *            the height
     */
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

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void paint(Graphics window) {

        this.drawBox(0, 0, 8, 14);
        this.drawBox(8, 0, 8, 6);
        this.drawBox(8, 6, 8, 6);
        this.drawBox(8, 12, 8, 2);

        window.drawImage(this.buffer, (this.WIDTH - SIM_WIDTH * this.ratio) / 2,
                (this.HEIGHT - SIM_HEIGHT * this.ratio) / 2,
                SIM_WIDTH * this.ratio, SIM_HEIGHT * this.ratio, null);
    }

    /**
     * Draws a menu box at (16*xCell, 16*yCell) with width 16*wCells, and height
     * 16*hCells. A cell is a 16x16 region in a grid from the origin.
     *
     * @param xCell
     *            the x 'cell' to draw at
     * @param yCell
     *            the y 'cell' to draw at
     * @param wCells
     *            the width in 'cells'
     * @param hCells
     *            the width in 'cells'
     */
    private void drawBox(int xCell, int yCell, int wCells, int hCells) {

        final int cellSize = 16;
        final int cornerSize = cellSize / 4;
        final int maxByte = 255;
        final int blueScale = (-8 * 14) / hCells;
        final int whiteScale = (12 * 14) / hCells;

        int x = xCell * cellSize;
        int y = yCell * cellSize;
        int w = wCells * cellSize;
        int h = hCells * cellSize;

        int bordersIn = 0;

        //Bars
        for (int i = 0; i < hCells; i++) {

            if (hCells > 2) {
                this.g.setColor(new Color((hCells - i) * whiteScale,
                        (hCells - i) * whiteScale, maxByte + i * blueScale));
            } else {
                this.g.setColor(new Color(hCells * whiteScale / 2,
                        hCells * whiteScale / 2, maxByte + blueScale));
            }
            this.g.fillRect(x, y + i * cellSize, w, cellSize);
        }

        this.g.setColor(Color.WHITE);

        //White border
        this.g.drawRoundRect(x + bordersIn, y + bordersIn,
                w - bordersIn * 2 - 1, h - bordersIn * 2 - 1, cellSize / 2,
                cellSize / 2);
        bordersIn++;
        this.g.drawRoundRect(x + bordersIn, y + bordersIn,
                w - bordersIn * 2 - 1, h - bordersIn * 2 - 1, cellSize / 2,
                cellSize / 2);
        bordersIn++;

        //White corners
        this.g.fillRect(x + 1, y + 1, cornerSize, cornerSize);
        this.g.fillRect(x + w - cornerSize - 1, y + 1, cornerSize, cornerSize);
        this.g.fillRect(x + 1, y + h - cornerSize - 1, cornerSize, cornerSize);
        this.g.fillRect(x + w - cornerSize - 1, y + h - cornerSize - 1,
                cornerSize, cornerSize);

        this.g.setColor(Color.BLACK);

        //Black corners
        this.g.drawLine(x + cornerSize - 1, y + cornerSize + 1,
                x + cornerSize + 1, y + cornerSize - 1);
        this.g.drawLine(x + w - cornerSize, y + cornerSize + 1,
                x + w - cornerSize - 2, y + cornerSize - 1);
        this.g.drawLine(x + cornerSize - 1, y + h - cornerSize - 2,
                x + cornerSize + 1, y + h - cornerSize);
        this.g.drawLine(x + w - cornerSize, y + h - cornerSize - 2,
                x + w - cornerSize - 2, y + h - cornerSize);

        //Black border
        this.g.drawRoundRect(x + bordersIn, y + bordersIn,
                w - bordersIn * 2 - 1, h - bordersIn * 2 - 1, cellSize / 2,
                cellSize / 2);
        bordersIn++;
        this.g.drawRoundRect(x + bordersIn, y + bordersIn,
                w - bordersIn * 2 - 1, h - bordersIn * 2 - 1, cellSize / 2,
                cellSize / 2);
        bordersIn++;
    }
}
