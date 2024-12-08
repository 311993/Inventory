package demos.graphicaldemo;

import java.util.ArrayList;

import demos.graphicaldemo.DemoModel.InvIndices;
import demos.graphicaldemo.DemoModel.ItemRestrictionException;

/**
 * The controller of the graphical inventory demo model and view creator.
 *
 * @author David Stuckey
 */
public class DemoController {

    /** The model. */
    private DemoModel model;

    /** The view creator. */
    private DemoView view;

    /** The graphics buffer width and height. */
    private static final int WIDTH = 256, HEIGHT = 224;

    /** The size of a single grid cell on the display. */
    private static final int CELLSIZE = 16;

    /** The y-coordinate at which to start the 2nd row of the display. */
    private static final int ROW2_Y = 128;

    /** Horizontal and verical item text offset. */
    private static final int ITEM_OFFSET_X = 24, ITEM_OFFSET_Y = 44;

    /** The horizontal offset of the cursor from the item text. */
    private static final int CURSOR_OFFSET = 12;

    /**
     * Creates a new DemoController.
     *
     * @param model
     *            the model
     * @param view
     *            the view creator
     */
    public DemoController(DemoModel model, DemoView view) {
        this.model = model;
        this.view = view;

        this.updateView();
    }

    /** A Button Event Response: Select a position for item movement. */
    public void buttonA() {

        if (!this.model.isInMessage()) {
            try {
                this.model.selectPosMove();
            } catch (ItemRestrictionException e) {
                this.model.sendMessage("CANNOT EQUIP THERE");
            }
        } else {
            this.model.clearMessage();
        }

        this.updateView();
    }

    /** B Button Event Response: Unselect Position. */
    public void buttonB() {

        if (!this.model.isInMessage()) {
            this.model.unselectPos();
        } else {
            this.model.clearMessage();
        }

        this.updateView();
    }

    /** X Button Event Response: Select a position for stack splitting. */
    public void buttonX() {

        if (!this.model.isInMessage()) {
            try {
                this.model.selectPosSplit();
                if (this.model.getSavedPos() >= 0) {
                    this.model.sendMessage("Split Count: ");
                }
            } catch (ItemRestrictionException e) {
                this.model.sendMessage("CANNOT SPLIT THERE");
            }
        } else {
            this.model.clearMessage();
        }

        this.updateView();
    }

    /** Y Button Event Response: Autosend. */
    public void buttonY() {

        if (!this.model.isInMessage()) {
            this.model.autoSend();
        } else {
            this.model.clearMessage();
        }

        this.updateView();
    }

    /** Up Arrow Event Response: Move Cursor Up. */
    public void up() {
        if (!this.model.isInMessage()) {
            switch (this.model.getCurrentInv()) {
                case GENERAL:
                case USABLE:
                case HANDS:
                    this.model.moveCursor(false);
                    break;

                case ARMOR:
                    this.model.changeInv(InvIndices.HANDS);
                    this.model.moveCursor(true);
                    break;

                case RELICS:
                    if (this.model.getCursorPos() == 0) {
                        this.model.changeInv(InvIndices.ARMOR);
                    } else {
                        this.model.moveCursor(false);
                    }
                    break;

                default: //Will not occur
            }
        } else {
            this.model.incrementCount();
        }
        this.updateView();
    }

    /** Down Arrow Event Response: Move Cursor Down. */
    public void down() {

        if (!this.model.isInMessage()) {
            switch (this.model.getCurrentInv()) {
                case GENERAL:
                case USABLE:
                case RELICS:
                    this.model.moveCursor(true);
                    break;

                case ARMOR:
                    this.model.changeInv(InvIndices.RELICS);
                    break;

                case HANDS:
                    if (this.model.getCursorPos() == 1) {
                        this.model.changeInv(InvIndices.ARMOR);
                    } else {
                        this.model.moveCursor(true);
                    }
                    break;

                default: //Will not occur
            }
        } else {
            this.model.decrementCount();
        }
        this.updateView();
    }

    /** Left Arrow Event Response: Move Cursor Left. */
    public void left() {

        if (!this.model.isInMessage()) {
            if (!this.model.getCurrentInv().equals(InvIndices.GENERAL)) {
                this.model.changeInv(InvIndices.GENERAL);
            }
        }

        this.updateView();
    }

    /** Right Arrow Event Response: Move Cursor Right. */
    public void right() {

        if (!this.model.isInMessage()) {
            if (this.model.getCurrentInv().equals(InvIndices.GENERAL)) {

                if (this.model.getCursorPos() < this.model
                        .getItemLabels(InvIndices.GENERAL).size() / 2) {

                    this.model.changeInv(InvIndices.HANDS);

                } else {
                    this.model.changeInv(InvIndices.USABLE);
                }
            }
        }

        this.updateView();
    }

    /**
     * Updates the client view to match the current state of the model. That is,
     * it repaints the canvas based on the model's contents.
     */
    private void updateView() {

        //General Box
        this.view.placeBox("General", 0, 0, WIDTH / CELLSIZE / 2,
                HEIGHT / CELLSIZE - 2);

        ArrayList<String> names = this.model.getItemLabels(InvIndices.GENERAL);
        this.view.placeItems(names, ITEM_OFFSET_X, ITEM_OFFSET_Y);

        //Equipment Box
        this.view.placeBox("Equipment", WIDTH / CELLSIZE / 2, 0,
                WIDTH / CELLSIZE / 2, (HEIGHT - ROW2_Y) / CELLSIZE);

        names = this.model.getItemLabels(InvIndices.HANDS);
        this.view.placeItems(names, WIDTH / 2 + ITEM_OFFSET_X, ITEM_OFFSET_Y);

        names = this.model.getItemLabels(InvIndices.ARMOR);
        this.view.placeItems(names, WIDTH / 2 + ITEM_OFFSET_X,
                ITEM_OFFSET_Y + CELLSIZE * 2);

        names = this.model.getItemLabels(InvIndices.RELICS);
        this.view.placeItems(names, WIDTH / 2 + ITEM_OFFSET_X,
                ITEM_OFFSET_Y + CELLSIZE * (2 + 1));

        //Usables Box
        this.view.placeBox("Usable", WIDTH / CELLSIZE / 2, ROW2_Y / CELLSIZE,
                WIDTH / CELLSIZE / 2, (HEIGHT - ROW2_Y) / CELLSIZE - 2);

        names = this.model.getItemLabels(InvIndices.USABLE);
        this.view.placeItems(names, WIDTH / 2 + ITEM_OFFSET_X,
                ROW2_Y + ITEM_OFFSET_Y);

        //Current Cursor
        int x = WIDTH / 2 + ITEM_OFFSET_X - CURSOR_OFFSET;
        int y = ITEM_OFFSET_Y;

        switch (this.model.getCurrentInv()) {
            case GENERAL:
                x = ITEM_OFFSET_X - CURSOR_OFFSET;
            case HANDS:
                break;
            case USABLE:
                y = ROW2_Y + ITEM_OFFSET_Y;
                break;
            case RELICS:
                y += CELLSIZE;
            case ARMOR:
                y += 2 * CELLSIZE;
            default:
        }

        y += CELLSIZE * this.model.getCursorPos();

        this.view.placeCursor(x, y);

        //Saved Cursor

        if (this.model.getSavedPos() >= 0) {
            x = WIDTH / 2 + ITEM_OFFSET_X - CURSOR_OFFSET;
            y = ITEM_OFFSET_Y;

            switch (this.model.getSavedInv()) {
                case GENERAL:
                    x = ITEM_OFFSET_X - CURSOR_OFFSET;
                case HANDS:
                    break;
                case USABLE:
                    y = ROW2_Y + ITEM_OFFSET_Y;
                    break;
                case RELICS:
                    y += CELLSIZE;
                case ARMOR:
                    y += 2 * CELLSIZE;
                default:
            }

            y += CELLSIZE * this.model.getSavedPos();

            this.view.placeCursor(x, y);

        }

        if (this.model.isInMessage()) {
            this.view.placeBox(this.model.getMessage(), 0,
                    HEIGHT / CELLSIZE / 2 - 1, WIDTH / CELLSIZE, 0);
        }

        //Update canvas
        this.view.render();
    }
}
