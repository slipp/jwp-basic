package core.di;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class MyCalendarTest {
    @Test
    public void getHour() throws Exception {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 11);
        MyCalendar calendar = new MyCalendar(now);
        assertEquals(new Hour(11), calendar.getHour());
    }
}
