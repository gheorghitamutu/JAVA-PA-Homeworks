package lab07.model;

class Action {
    private String message;
    private boolean pass = true;

    synchronized void setMessage(String message) {
        this.message = message;
        notify();
    }

    synchronized String getMessage() {
        while (message == null && pass) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String rMessage = message;
        this.message = null;
        return rMessage;
    }

    synchronized void pass(){
        this.message = "";
        notify();
    }

    public boolean isPass() {
        return pass;
    }

    void setPass(boolean pass) {
        this.pass = pass;
    }
}
