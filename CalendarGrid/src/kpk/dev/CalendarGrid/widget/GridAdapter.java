package kpk.dev.CalendarGrid.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import kpk.dev.CalendarGrid.R;
import kpk.dev.CalendarGrid.util.LogHelper;
import kpk.dev.CalendarGrid.widget.models.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/23/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridAdapter extends ArrayAdapter<Date> {

    public GridAdapter(Context context,int layoutId,  List<Date> data) {
        super(context, layoutId, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TextView cellView = (TextView) convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = (TextView) inflater.inflate(R.layout.calendar_item, null);
        }

        if(isToday(getItem(position))){
            cellView.setBackground(getContext().getResources().getDrawable(R.drawable.calendar_item_backgorund_selected));
            cellView.setTextColor(Color.WHITE);
        }else if(getItem(position).getFromNextMonth() || getItem(position).getFromPrevMonth()){
            cellView.setBackground(getContext().getResources().getDrawable(R.drawable.calendar_item_backgorund_not_in_month));
            cellView.setTextColor(Color.BLACK);
        }else{
            cellView.setBackground(getContext().getResources().getDrawable(R.drawable.calendar_item_background_normal));
            cellView.setTextColor(Color.BLACK);
        }

        cellView.setText(getItem(position).getDate() + "");
        cellView.setOnClickListener(getOnClickListener(position));
        return cellView;
    }

    private boolean isToday(Date item) {
        Calendar cal = Calendar.getInstance();
        boolean isToday = false;
        if(item.getDate() == cal.get(Calendar.DAY_OF_MONTH) && item.getMonth() == cal.get(Calendar.MONTH) && item.getYear() == cal.get(Calendar.YEAR)) {
            isToday = true;
        }

        return isToday;
    }

    private View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LogHelper.d(getItem(position).getDate() + "/" + getItem(position).getMonth() + "/" + getItem(position).getYear());
            }
        };
    }
}
