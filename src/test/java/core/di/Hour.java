package core.di;

public class Hour {
    private int hour;

    public Hour(int hour) {
        this.hour = hour;
    }

    public String getMessage() {
        if (hour < 12) {
            return "오전";
        }
        return "오후";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hour other = (Hour) obj;
        if (hour != other.hour)
            return false;
        return true;
    }
}
