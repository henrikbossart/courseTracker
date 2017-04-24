package gruppe087.coursetracker;

import android.support.annotation.NonNull;

/**
 * Created by petercbu on 24.04.2017.
 */

public class Position implements Comparable {
    final int position;

    public Position(int i){
        this.position = i;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return this.position;
    }

    public int getValue() {
        return position;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.hashCode() - o.hashCode();
    }
}
