package net.usermd.mgrabiec.jee.calendar.model;

public enum Priority {
    NONE("NONE"),
    LOW("LOW"),
    MIDDLE("MIDDLE"),
    HIGH("HIGH");

    private final String displayValue;

    private Priority(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
