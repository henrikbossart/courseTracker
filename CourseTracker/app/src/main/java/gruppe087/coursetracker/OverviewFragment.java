package gruppe087.coursetracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;


public class OverviewFragment extends Fragment {

    String currentDate = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());

    public String getCurrentDate(){
        return currentDate; //Comes out in this format: 20.mar.2017 (example)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }


}
