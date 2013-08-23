package kpk.dev.CalendarGrid.widget.util;

import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.models.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/23/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarUtils {
    private Calendar mCalendar;
    private int mFirstDayOfWeek;
    private int mDaysInCurrentMonth;
    private int mDaysInPrevMonth;
    private int NUM_ROWS_TO_FILL = 6;
    private List<Date> mData;
    public CalendarUtils(int year, int month, int firstDayOfWeek) {
        if(firstDayOfWeek < Calendar.SUNDAY || firstDayOfWeek > Calendar.SATURDAY) {
            throw new IllegalStateException("The starting day must be between 1 and 7");
        }
        mData = new ArrayList<Date>();

        mFirstDayOfWeek = firstDayOfWeek;
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.setFirstDayOfWeek(firstDayOfWeek);
        mCalendar.set(Calendar.HOUR, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        recalculateDays();
    }

    public void nextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculateDays();
    }

    public void previousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculateDays();
    }

    public void recalculateDays() {
        mData.clear();
        mDaysInCurrentMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        mCalendar.add(Calendar.MONTH, -1);
        mDaysInPrevMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        mCalendar.add(Calendar.MONTH, 1);
        int numDaysInWeek = mCalendar.getActualMaximum(Calendar.DAY_OF_WEEK);
        int totalCells = numDaysInWeek * NUM_ROWS_TO_FILL;
        int prevMonthOffset = Math.abs(mFirstDayOfWeek - mCalendar.get(Calendar.DAY_OF_WEEK)) - 1 ;
        mData = new ArrayList<Date>();
        int daysToFillInTheBeginning = mDaysInPrevMonth - prevMonthOffset + 1;
        int endingFill = 0;
        int counter = 0;
        while(counter < prevMonthOffset) {
            Date date = new Date();
            date.setDate(daysToFillInTheBeginning);

            int year;
            if(mCalendar.get(Calendar.MONTH) == Calendar.JANUARY) {
                year = mCalendar.get(Calendar.YEAR) - 1;
                date.setMonth(Calendar.DECEMBER);
            }else{
                year = mCalendar.get(Calendar.YEAR);
                date.setMonth(mCalendar.get(Calendar.MONTH) - 1);
            }
            date.setYear(year);

            date.setFromPrevMonth(true);
            mData.add(date);
            daysToFillInTheBeginning++;
            counter++;
        }

        for(int i = 1; i <= totalCells - prevMonthOffset; i++) {

            Date date = new Date();
            int month;
            int year;

            date.setYear(mCalendar.get(Calendar.YEAR));

            if(i > mDaysInCurrentMonth) {
                month = mCalendar.get(Calendar.MONTH) + 1;
                if(mCalendar.get(Calendar.MONTH) == Calendar.DECEMBER){
                    year = mCalendar.get(Calendar.YEAR) + 1;
                }else{
                    year = mCalendar.get(Calendar.YEAR);
                }
                date.setFromNextMonth(true);
                date.setDate(endingFill + 1);
                endingFill++;
            }else{
                month = mCalendar.get(Calendar.MONTH);
                year = mCalendar.get(Calendar.YEAR);
                date.setFromNextMonth(false);
                date.setFromPrevMonth(false);
                date.setDate(i);
            }
            date.setMonth(month);
            date.setYear(year);
            mData.add(date);

        }
    }

    public List<Date> getItems() {
        return mData;
    }

    public Calendar getCalendar(){
        return mCalendar;
    }
}
