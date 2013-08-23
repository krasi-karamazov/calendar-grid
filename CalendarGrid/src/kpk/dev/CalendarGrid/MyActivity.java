package kpk.dev.CalendarGrid;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.calendarproviderbackport.models.Event;
import com.example.calendarproviderbackport.provider.CalendarProvider;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.CalendarGridView;
import kpk.dev.CalendarGrid.widget.GridAdapter;
import kpk.dev.CalendarGrid.widget.models.Date;
import kpk.dev.CalendarGrid.widget.util.CalendarUtils;

import java.util.*;

public class MyActivity extends Activity {

    private CalendarUtils mCalendarUtils;
    private CalendarGridView mGrid;
    private List<Date> mData;
    private GridAdapter mAdapter;
    private CalendarProvider mProvider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Calendar calendar = Calendar.getInstance();

        mCalendarUtils = new CalendarUtils(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Calendar.SUNDAY);
        Button prevButton = (Button)findViewById(R.id.prev_button);
        Button nextButton = (Button)findViewById(R.id.next_button);
        prevButton.setOnClickListener(getOnClickListener());
        nextButton.setOnClickListener(getOnClickListener());
        mGrid = (CalendarGridView)findViewById(R.id.calendar_grid);
        mData = mCalendarUtils.getItems();
        mAdapter = new GridAdapter(this, android.R.layout.simple_list_item_1, mData);
        mGrid.setAdapter(mAdapter);
        mProvider = new CalendarProvider(this);


        List<Event> evs = new ArrayList<Event>();
        Map<Long, String> calendars = mProvider.getCalendars();
        Set<Map.Entry<Long, String>> set = calendars.entrySet();
        Iterator<Map.Entry<Long, String>> iter = set.iterator();

        while(iter.hasNext()) {
            Long key = iter.next().getKey();
            evs.addAll(mProvider.getEventsInCalendar(key));
        }


        LogHelper.d("Events num: " + evs.size());
    }

    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch(view.getId()) {
                    case R.id.next_button:
                        mCalendarUtils.nextMonth();
                        break;
                    case R.id.prev_button:
                        mCalendarUtils.previousMonth();
                        break;
                }
                mData.clear();
                mData.addAll(mCalendarUtils.getItems());
                mAdapter.notifyDataSetChanged();
            }
        };
    }
}
