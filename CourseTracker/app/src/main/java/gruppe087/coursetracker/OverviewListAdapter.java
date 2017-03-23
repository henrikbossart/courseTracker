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
import java.util.Date;
import java.util.List;

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
        String time = details[3];
        Date dNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(dNow);
        if (lectureMissed(courseID, time, date)) {
            tv.setTextColor(getContext().getResources().getColor(R.color.gray));
        } else {
            tv.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        return view;
    }

    public boolean lectureMissed(String courseID, String time, String date) {
        lectureAdapter = new LectureAdapter(getContext());
        lectureAdapter.open();
        time = time + ":00";
        ArrayList<String> lecture = lectureAdapter.getSingleEntry(courseID, time, date);
        int missed = Integer.parseInt(lecture.get(4));
        Boolean lectureMissed;
        if (missed < 1) {
            lectureMissed = false;
        } else {
            lectureMissed = true;
        }
        return lectureMissed;
    }
}
