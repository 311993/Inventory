package demos.graphicaldemo;

/**
 * Main class for graphical inventory demo.
 *
 * @author David Stuckey
 */
public final class DemoMain {

    /** Private constructor to prevent instantiation. */
    private DemoMain() {
    }

    /**
     * The main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        DemoModel model = new DemoModel();
        DemoView view = new DemoView();
        DemoController controller = new DemoController(model, view);

        view.registerObserver(controller);
    }

}
