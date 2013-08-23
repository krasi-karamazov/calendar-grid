package kpk.dev.CalendarGrid.util;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/23/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
import android.util.Log;
/**
 * Created by kkaramazov on 6/6/13.
 */
public class LogHelper {

    public static String LOG_TAG;
    public static boolean DEBUG;

    public static void d(String msg){
        if(DEBUG){
            Log.d(LOG_TAG, msg);
        }
    }

    public static void e(String msg){
        if(DEBUG){
            Log.e(LOG_TAG, msg);
        }
    }

    public static void w(String msg){
        if(DEBUG){
            Log.w(LOG_TAG, msg);
        }
    }

    public static void i(String msg){
        if(DEBUG){
            Log.i(LOG_TAG, msg);
        }
    }
}
