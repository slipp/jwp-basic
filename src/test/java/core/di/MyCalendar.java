package core.di;

import java.util.Calendar;

public class MyCalendar {
    private Calendar calendar;

    public MyCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Hour getHour() {
        return new Hour(calendar.get(Calendar.HOUR_OF_DAY));
    }
}
