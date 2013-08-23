package kpk.dev.CalendarGrid.widget.models;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/23/13
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Date {
    private int mDate;
    private boolean mFromPrevMonth;
    private boolean mFromNextMonth;
    private int mMonth;
    private int mYear;
    private long mTime;

    public void setTime(long time) {
        this.mTime = time;
    }

    public long getTime() {
        return mTime;
    }

    public void setDate(int date) {
        this.mDate = date;
    }

    public int getDate() {
        return mDate;
    }

    public void setYear(int year) {
        this.mYear = year;
    }

    public int getYear() {
        return mYear;
    }

    public void setMonth(int month) {
        this.mMonth = month;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setFromPrevMonth(boolean fromPrevMonth) {
        mFromPrevMonth = fromPrevMonth;
    }

    public boolean getFromPrevMonth() {
        return mFromPrevMonth;
    }

    public void setFromNextMonth(boolean fromNextMonth) {
        mFromNextMonth = fromNextMonth;
    }

    public boolean getFromNextMonth() {
        return mFromNextMonth;
    }
}
