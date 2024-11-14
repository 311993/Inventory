package demos.graphicaldemo;

public class DemoMain {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        DemoModel model = new DemoModel();
        DemoView view = new DemoView();
        DemoController controller = new DemoController(model, view);

        view.registerObserver(controller);
    }

}
