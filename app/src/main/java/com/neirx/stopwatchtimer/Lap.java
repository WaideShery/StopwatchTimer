package com.neirx.stopwatchtimer;

public class Lap {
    public int getStopwatchNum() {
        return stopwatchNum;
    }

    public int getTimeNum() {
        return timeNum;
    }

    public String getTimeTotal() {
        return timeTotal;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    private int stopwatchNum;
    private int timeNum;
    private String timeTotal;
    private String timeDifference;

    public Lap(int stopwatchNum, int timeNum, String timeTotal, String timeDifference) {
        this.stopwatchNum = stopwatchNum;
        this.timeNum = timeNum;
        this.timeTotal = timeTotal;
        this.timeDifference = timeDifference;
    }


}
