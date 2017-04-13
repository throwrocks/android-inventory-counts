package rocks.athrow.android_inventory_counts.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Utilities
 * Created by joselopez1 on 4/13/2017.
 */

public final class Utilities {

    private Utilities() {
        throw new AssertionError("No Utilities instances for you!");
    } // suppress constructor


    @SuppressWarnings("SameParameterValue")
    public static void showToast(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public static String getCountDisplay(int countType){
            if ( countType == 0 ){
                return null;
            }
            String countTypeDisplay = null;
            switch (countType){
                case 1:
                    countTypeDisplay = "1st";
                    break;
                case 2:
                    countTypeDisplay = "2nd";
                    break;
                case 3:
                    countTypeDisplay = "3rd";
                    break;
            }
            return countTypeDisplay + " Count";
    }

}