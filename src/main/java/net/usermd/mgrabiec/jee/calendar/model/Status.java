package net.usermd.mgrabiec.jee.calendar.model;

public enum Status {
    FREE("FREE"),
    DURING("DURING"),
    DONE("DONE");

    private final String displayValue;

    Status(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
