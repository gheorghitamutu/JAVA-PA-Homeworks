package lab07.model;

class Action {
    private String message;

    synchronized void setMessage(String message) {
        this.message = message;
        notify();
    }

    synchronized String getMessage() {
        while (message == null) {
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
        notify();
    }
}
