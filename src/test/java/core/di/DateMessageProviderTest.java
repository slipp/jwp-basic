package core.di;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class DateMessageProviderTest {
    @Test
    public void 오전() throws Exception {
        DateMessageProvider provider = new DateMessageProvider();
        assertEquals("오전", provider.getDateMessage(createCurrentDate(11)));
    }

    @Test
    public void 오후() throws Exception {
        DateMessageProvider provider = new DateMessageProvider();
        assertEquals("오후", provider.getDateMessage(createCurrentDate(13)));
    }

    private Calendar createCurrentDate(int hourOfDay) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        return now;
    }
}
