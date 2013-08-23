package kpk.dev.CalendarGrid.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import kpk.dev.CalendarGrid.util.LogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krasimir.karamazov
 * Date: 8/23/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarGridView extends ViewGroup {

    public static final int NUM_COLUMNS = 7;
    public static final int NUM_ROWS = 6;
    private Adapter mAdapter;
    private SparseArray<List<View>> typedViewsCache = new SparseArray<List<View>>();
    private final DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            refreshViewsFromAdapter();
        }

        @Override
        public void onInvalidated() {
           removeAllViews();
        }
    };

    private void refreshViewsFromAdapter() {
        SparseArray<List<View>> typedViewsCopy = typedViewsCache;
        typedViewsCache = new SparseArray<List<View>>();
        removeAllViews();
        View convertView;
        int type;
        for(int i = 0; i < mAdapter.getCount(); i++) {
            type = mAdapter.getItemViewType(i);
            convertView = shiftCachedViewOfType(type, typedViewsCopy);
            convertView = mAdapter.getView(i, convertView, this);
            addToTypesMap(type, convertView, typedViewsCache);
            addView(convertView, i);
        }
    }

    public CalendarGridView(Context context) {
        super(context);
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int itemWidth = this.getMeasuredWidth() / NUM_COLUMNS;
        int itemHeight = this.getMeasuredHeight() / NUM_ROWS;
        int row = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);

            if(i % NUM_COLUMNS == 0) {
                row++;
            }
            v.setTop((itemHeight * row) - itemHeight);
            v.setLeft(left + itemWidth * (i % NUM_COLUMNS));
            v.setRight(v.getLeft() + itemWidth);
            v.setBottom(v.getTop() + itemHeight);
        }
    }

    public void setAdapter(Adapter adapter) {
        if(mAdapter != null) {
            mAdapter.unregisterDataSetObserver(observer);
        }
        mAdapter = adapter;
        if(mAdapter != null) {
            mAdapter.registerDataSetObserver(observer);
        }
        initViewsFromAdapter();
    }

    private void initViewsFromAdapter() {
        typedViewsCache.clear();
        removeAllViews();
        View view;
        if(mAdapter != null) {
            for( int i = 0; i < mAdapter.getCount(); i++) {
                view = mAdapter.getView(i, null, this);
                addToTypesMap(mAdapter.getItemViewType(i), view, typedViewsCache);
                addView(view, i);
            }
        }
    }

    private void addToTypesMap(int itemViewType, View view, SparseArray<List<View>> typedViewsCache) {
        List<View> singleTypes = typedViewsCache.get(itemViewType);
        if(singleTypes == null) {
            singleTypes= new ArrayList<View>();
            typedViewsCache.put(itemViewType, singleTypes);
        }
        singleTypes.add(view);
    }

    private static View shiftCachedViewOfType(int type, SparseArray<List<View>> typedViewsCache) {
        List<View> singleTypeViews = typedViewsCache.get(type);
        if(singleTypeViews != null) {
            if(singleTypeViews.size() > 0) {
                return singleTypeViews.remove(0);
            }
        }
        return null;
    }

    public Adapter getAdapter() {
        return mAdapter;
    }
}
