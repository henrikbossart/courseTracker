package gruppe087.coursetracker;

import android.app.Activity;
import android.content.ClipData;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static android.R.attr.data;
import static android.R.attr.resource;
import static android.R.id.list;

/**
 * Created by petercbu on 14.03.2017.
 */

public class SelectListAdapter<E> extends BaseAdapter {

    Activity activity;
    ArrayList<String> listItems;
    HashSet<Integer> hiddenPositions = new HashSet<Integer>();
    HashSet<Position> selected;
    Resources res;



    public SelectListAdapter(Activity activity, ArrayList<String> listItems, HashSet<Position> selected, Resources res){
        this.activity = activity;
        this.listItems = listItems;
        this.selected = selected;
        this.res = res;
    }

    @Override
    public int getCount() {
        return listItems.size() - hiddenPositions.size();
    }

    @Override
    public Object getItem(int position) {
            ArrayList<Integer> sortedList = new ArrayList<Integer>(hiddenPositions);
            Collections.sort(sortedList);
            for(Integer hiddenIndex : sortedList) {
                if(hiddenIndex <= position) {
                    position = position + 1;
                }
            }
            return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        ArrayList<Integer> sortedList = new ArrayList<Integer>(hiddenPositions);
        Collections.sort(sortedList);
        for(Integer hiddenIndex : sortedList) {
            if(hiddenIndex <= position) {
                position = position + 1;
            }
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // The following small snippet of code ensures
        // that we skip data from all the hidden positions
        // and use the updated position to fetch the
        // correct data from the listItems ArrayList.

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).
                    inflate(R.layout.list_text_view, arg2, false);
        }
        String currentItem = (String) getItem(position);
        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.text_view_txt);


        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem);

        long id = getItemId(position);
        if (isSelected((int) id)){
            convertView.setBackgroundColor(res.getColor(R.color.gray));
        } else {
            convertView.setBackgroundColor(res.getColor(R.color.white));
        }


        // returns the view for the current row
        return convertView;

    }

    public void addHiddenPosition(int pos){
        hiddenPositions.add(pos);
        notifyDataSetChanged();
    }

    public void removeHiddenPosition(int pos){
        hiddenPositions.remove(Integer.valueOf(pos));
        notifyDataSetChanged();
    }

    public boolean isHidden(int pos){
        if (hiddenPositions.contains(new Position(pos))){
            return true;
        } else {
            return false;
        }
    }

    public void updateSelected(HashSet<Position> selected){
        this.selected = selected;
    }

    private boolean isSelected(int pos){
        if (selected.contains(new Position(pos))){
            return true;
        } else {
            return false;
        }
    }


}
