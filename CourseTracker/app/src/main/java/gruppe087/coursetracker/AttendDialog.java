package gruppe087.coursetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by petercbu on 03.04.2017.
 */

public class AttendDialog extends DialogFragment {

    UserLectureAdapter userLectureAdapter;
    SharedPreferences settings;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        userLectureAdapter = new UserLectureAdapter(getActivity().getApplicationContext(), settings.getString("username", "default"));
        Bundle args = getArguments();
        int courseID = args.getInt("courseID");
        String time = args.getString("time");
        final int lectureID = args.getInt("lectureID");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attendance")
                .setMessage("Did you attend the class in " + courseID + "\nat" + time + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        userLectureAdapter.setMissed(lectureID, 0);
                        userLectureAdapter.setAsked(lectureID, 1);
                        notifyToTarget(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                userLectureAdapter.setMissed(lectureID, 1);
                                userLectureAdapter.setAsked(lectureID, 1);
                                notifyToTarget(Activity.RESULT_CANCELED);
                            }
                        });
        return builder.create();
    }

    private void notifyToTarget(int code) {
        Fragment targetFragment = getTargetFragment();
        if (targetFragment != null) {
            targetFragment.onActivityResult(getTargetRequestCode(), code, null);
        }
    }
}
