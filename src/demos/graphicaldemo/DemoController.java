package demos.graphicaldemo;

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

    public void buttonA() {

        try {
            this.model.selectPosMove();
        } catch (ItemRestrictionException e) {
            //TODO:Notify user somehow
        }

        this.updateView();
    }

    public void buttonB() {

        this.model.unselectPos();

        this.updateView();
    }

    public void up() {

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

        this.updateView();
    }

    public void down() {

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

        this.updateView();
    }

    public void left() {
        if (!this.model.getCurrentInv().equals(InvIndices.GENERAL)) {
            this.model.changeInv(InvIndices.GENERAL);
        }

        this.updateView();
    }

    public void right() {

        if (this.model.getCurrentInv().equals(InvIndices.GENERAL)) {

            if (this.model.getCursorPos() < this.model
                    .getItemNames(InvIndices.GENERAL).size() / 2) {

                this.model.changeInv(InvIndices.HANDS);

            } else {
                this.model.changeInv(InvIndices.USABLE);
            }
        }

        this.updateView();
    }

    private void updateView() {

        this.view.placeBoxes();
        this.view.placeItems();

        int x = 148, y = 44 + 16 * this.model.getCursorPos();

        switch (this.model.getCurrentInv()) {
            case GENERAL:
                x = 20;
            case HANDS:
                break;
            case USABLE:
                y += 80;
            case RELICS:
                y += 16;
            case ARMOR:
                y += 32;

        }
        System.out.println(y);
        this.view.placeCursor(x, y);

        this.view.render();
    }
}
