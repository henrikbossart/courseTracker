package gruppe087.coursetracker;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import static android.R.attr.resource;

/**
 * Created by petercbu on 23.03.2017.
 */

public class OverviewListAdapter<E> extends ArrayAdapter<E> {
    LectureAdapter lectureAdapter;


    public OverviewListAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<E> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tv = (TextView) view.findViewById(R.id.textview);
        String text = (String) tv.getText();
        String[] details = text.split("(\n|\t)");
        String courseID = details[0];
        String time = (details[3] + ":00").substring(0,8);
        Date dNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String now = timeFormat.format(dNow);
        int timeNow = Toolbox.timeToInt(now);
        int lectureTime = Toolbox.timeToInt(time);
        if (lectureTime + 90 < timeNow) {
            tv.setTextColor(getContext().getResources().getColor(R.color.gray));
        } else {
            tv.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        return view;
    }
}
