package demos.graphicaldemo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Canvas for drawing the graphical inventory demo.
 *
 * @author David Stuckey
 */
public class DemoCanvas extends Canvas {

    /** The screen dimensions. */
    private final int width, height;

    /** Ratio of buffer size to display on screen. */
    private final int ratio;

    /** The buffer dimensions. */
    private static final int SIM_WIDTH = 256, SIM_HEIGHT = 224;

    /** The screen buffer. */
    private BufferedImage buffer;

    /** The graphics context of the screen buffer. */
    private Graphics g;

    /** The image to source the font from. */
    private BufferedImage font;

    /** The index of the default character in the font. */
    private static final int DEFAULT_TEXT_INDEX = 38;

    /** The index of the zero character in the font. */
    private static final int NUMBER_TEXT_INDEX = 26;

    /** The special characters used ot encode graphics symbols. */
    private static final String SPECIAL_CHARS = " !\"#$%&'()";

    /**
     * Construct a new DemoCanvas.
     *
     * @param w
     *            the width
     * @param h
     *            the height
     */
    public DemoCanvas(int w, int h) {
        this.width = w;
        this.height = h;

        this.setSize(this.width, this.height);
        this.setVisible(true);

        this.buffer = new BufferedImage(SIM_WIDTH, SIM_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        this.g = this.buffer.getGraphics();

        if (this.width / SIM_WIDTH > this.height / SIM_HEIGHT) {
            this.ratio = (int) Math.floor(this.height / SIM_HEIGHT);
        } else {
            this.ratio = (int) Math.floor(this.width / SIM_WIDTH);
        }

        try {
            this.font = ImageIO
                    .read(new File("src/demos/graphicaldemo/FONT.png"));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void paint(Graphics window) {

        window.drawImage(this.buffer, (this.width - SIM_WIDTH * this.ratio) / 2,
                (this.height - SIM_HEIGHT * this.ratio) / 2,
                SIM_WIDTH * this.ratio, SIM_HEIGHT * this.ratio, null);

        this.drawInstructions(window);
    }

    /**
     * Writes user instructions ouside sim.
     *
     * @param window
     *            - the graphics context to draw on.
     */
    private void drawInstructions(Graphics window) {
        BufferedImage temp = this.buffer;

        this.buffer = new BufferedImage(SIM_WIDTH, SIM_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        this.g = this.buffer.getGraphics();

        final int offsetX = 4, offsetY = 44;
        final int cellSize = 16;
        int line = 0;

        this.drawText("ARROWS MOVE", offsetX, offsetY + line * cellSize);
        line++;
        this.drawText("V SELECT POS", offsetX, offsetY + line * cellSize);
        line++;
        this.drawText("C GO BACK", offsetX, offsetY + line * cellSize);
        line++;
        this.drawText("X SPLIT HERE", offsetX, offsetY + line * cellSize);
        line++;
        this.drawText("Z AUTO SEND", offsetX, offsetY + line * cellSize);

        window.drawImage(this.buffer, (this.width + SIM_WIDTH * this.ratio) / 2,
                (this.height - SIM_HEIGHT * this.ratio) / 2,
                SIM_WIDTH * this.ratio, SIM_HEIGHT * this.ratio, null);

        this.buffer = temp;
        this.g = this.buffer.getGraphics();
    }

    /**
     * Writes the text of {@code msg} at {@code (x,y)} in monospaced 8x8 font.
     * Only alphabetical and numerical characters and spaces will render
     * properly. Characters !"#$%&'() encode special graphical symbols (see
     * below). All other characters will render as unknown text.
     *
     *
     *
     * @param msg
     *            the text to be written
     * @param x
     *            the x postion to write at
     * @param y
     *            the y position to write at
     *
     *            <pre>
     * .
     * Special Characters:
     *      !: UNKNOWN
     *      ": CURSOR
     *      #: BOTTLE
     *      $: ARMOR
     *      %: RING
     *      &: SWORD
     *      ': DIRK
     *      (: SPEAR
     *      ): SHIELD
     * </pre>
     */
    public void drawText(String msg, int x, int y) {

        final int fontSize = 8;

        for (int i = 0; i < msg.length(); i++) {

            int offset = DEFAULT_TEXT_INDEX;

            if (Character.isAlphabetic(msg.charAt(i))) {

                offset = Character.toUpperCase(msg.charAt(i)) - 'A';

            } else if (Character.isDigit(msg.charAt(i))) {

                offset = NUMBER_TEXT_INDEX + msg.charAt(i) - '0';

            } else if (SPECIAL_CHARS.indexOf(msg.charAt(i)) >= 0) {
                offset += SPECIAL_CHARS.indexOf(msg.charAt(i));
            }

            this.g.drawImage(this.font, x + i * fontSize, y,
                    x + (i + 1) * fontSize, y + fontSize,
                    fontSize * (offset % fontSize),
                    fontSize * (offset / fontSize),
                    fontSize * (offset % fontSize + 1),
                    fontSize * (offset / fontSize + 1), null);
        }

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
    public void drawBox(int xCell, int yCell, int wCells, int hCells) {

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
