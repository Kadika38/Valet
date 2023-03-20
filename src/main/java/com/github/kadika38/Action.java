public class Action extends MenuObject {
    private Runnable action;

    Action(Runnable action) {
        this.action = action;
    }

    public void run() {
        action.run();
    }
}
