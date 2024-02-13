interface State {
    public void timeout(Context context);
    public void pedestrianWaiting(Context context);
    public void print();
}

class GreenState implements State {

    private boolean isPedestrianWaiting;

    public GreenState() {
        isPedestrianWaiting = false;
    }

    public void timeout(Context context) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isPedestrianWaiting) {
            context.setState(new YellowState());
        } else {
            context.setState(new GreenIntState());
        }
    }

    public void pedestrianWaiting(Context context) {
        isPedestrianWaiting = true;
    }

    public void print() {
        Action.DONT_WALK.print();
        Action.GREEN.print();
    }
}

class GreenIntState implements State {

    public void timeout(Context context) {

    }

    public void pedestrianWaiting(Context context) {
        context.setState(new YellowState());
    }

    public void print() {

    }
}

class YellowState implements State {

    public void timeout(Context context) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setState(new WalkState());
    }

    public void pedestrianWaiting(Context context) {
    
    }

    public void print() {
        Action.YELLOW.print();
    }
}

class WalkState implements State {

    public void timeout(Context context) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setState(new FlashState());
    }

    public void pedestrianWaiting(Context context) {

    }

    public void print() {
        Action.RED.print();
        Action.WALK.print();
    }
}

class FlashState implements State {
    private int pedestrianFlashCtr;
    private static final int pedestrianFlashCtrMax = 7;

    public FlashState() {
        pedestrianFlashCtr = pedestrianFlashCtrMax;
    }

    public void timeout(Context context) {
        while (pedestrianFlashCtr > 0) {
            if ((pedestrianFlashCtr & 1) == 0) {
                Action.DONT_WALK.print();
            } else {
                Action.BLANK.print();
            }
            try {
                Thread.sleep(2000 / pedestrianFlashCtrMax);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pedestrianFlashCtr--;
        }
        context.setState(new GreenState());
    }

    public void pedestrianWaiting(Context context) {

    }

    public void print() {

    }
}
