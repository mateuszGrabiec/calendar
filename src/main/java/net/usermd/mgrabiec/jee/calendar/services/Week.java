package net.usermd.mgrabiec.jee.calendar.services;

import net.usermd.mgrabiec.jee.calendar.model.Priority;
import net.usermd.mgrabiec.jee.calendar.model.Status;
import net.usermd.mgrabiec.jee.calendar.model.Task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Week {
    private ArrayList<ArrayList<Task>> weekList;

    public boolean addObj(int day, LocalDateTime start, LocalDateTime end, String name , String content, Status status, Priority priority) {
        boolean isAdd = false;
        day--;
        try {
            if (day < 7 && day >= 0 && start.isBefore(end)) {
                if (weekList.get(day).size() == 0) {
                    ArrayList<Task> today = new ArrayList<>();
                    Task obj = new Task(start, end,name, content, status, priority);
                    today.add(obj);
                    today.sort(new TimeComparator());
                    weekList.add(day, today);
                    isAdd = true;
                } else {
                    ArrayList<Task> today = weekList.get(day);
                        Task obj = new Task(start, end, name,content, status, priority);
                        today.add(obj);
                        today.sort(new TimeComparator());
                        isAdd = true;
                }
            }
        } catch (IllegalArgumentException exception) {//
        }

        return isAdd;
    }

    public boolean removeTask(int day, LocalTime startTime) {
        boolean isRemoved = false;
        day--;
        try {
            int index = findTask(day, startTime);
            if (index < this.weekList.get(day).size()) {
                if (this.weekList.get(day).get(index) != null) this.weekList.get(day).remove(index);
                this.weekList.get(day).sort(new TimeComparator());
                isRemoved = true;
            }
        } catch (IndexOutOfBoundsException e) {//
        }
        return isRemoved;
    }

    public Task getTask(int day, LocalTime startTime) {
        day--;
        Task ev = null;
        int index = findTask(day, startTime);
        if (weekList.get(day).size() > index) ev = this.weekList.get(day).get(index);
        return ev;
    }

    private int findTask(int day, LocalTime startTime) {
        int index = 0;
        while (index < this.weekList.get(day).size()) {
            if (this.weekList.get(day).get(index).getStartTime().equals(startTime)) {
                return index;
            }
            index++;
        }
        return index;
    }


    private int findTask(int day, String content) {
        int index = 0;
        while (index < this.weekList.get(day).size()) {
            if (this.weekList.get(day).get(index).getContent().equals(content)) {
                return index;
            }
            index++;
        }
        return index;
    }

    public Week() {
        ArrayList<ArrayList<Task>> week = new ArrayList<ArrayList<Task>>();
        ArrayList<Task> day = new ArrayList<Task>();
        for (int i = 0; i <= 6; i++) week.add(i, day);
        this.weekList = week;
    }
}
