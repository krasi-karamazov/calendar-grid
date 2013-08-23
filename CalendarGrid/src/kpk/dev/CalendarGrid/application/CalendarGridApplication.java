package kpk.dev.CalendarGrid.application;

import android.app.Application;
import kpk.dev.CalendarGrid.util.LogHelper;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/23/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarGridApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogHelper.DEBUG = true;
        LogHelper.LOG_TAG = CalendarGridApplication.class.getSimpleName();
    }
}
