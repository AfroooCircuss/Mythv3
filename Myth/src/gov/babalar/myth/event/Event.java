package gov.babalar.myth.event;

public class Event {
    private boolean cancelled = false;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean isIt) {
        this.cancelled = isIt;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public static class StateEvent extends Event {
        private boolean pre = true;

        public boolean isPre() { return pre;}
        public boolean isPost() { return !pre;}
        public void setPost() { pre = false; }
    }

}

