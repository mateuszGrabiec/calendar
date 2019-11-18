package net.usermd.mgrabiec.jee.calendar.model;

import java.util.Comparator;

public class TimeComparator
        implements Comparator<Task> {
    public int compare(Task event, Task event1) {
        return event.getStartTime().compareTo(event1.getStartTime());
    }
}
