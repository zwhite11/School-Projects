package white.zac.hw5;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

import java.util.WeakHashMap;

//class was meant to help with dealing with the due time as a Long...
public class BindingAdapters {
    private static WeakHashMap<TextView, Integer> lastSetValues = new WeakHashMap<>();
    private static WeakHashMap<TextView, Long> lastLongs = new WeakHashMap<>();

    @BindingAdapter("android:text")
    public static void setIntText(TextView textView, int value) {
        String oldValue = textView.getText().toString();
        String newValue = String.valueOf(value);
        if (!oldValue.equals(newValue)) {
            textView.setText(newValue);
            lastSetValues.put(textView, value);
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getIntText(TextView textView) {
        try {
            int value = Integer.parseInt(textView.getText().toString());
            lastSetValues.put(textView, value);
            return value;
        } catch (NumberFormatException e) {
            Integer value = lastSetValues.get(textView);
            if (value != null)
                return value;
            return 0;
        }
    }

    @BindingAdapter("android:text")
    public static void setLongText(TextView textView, long value) {
        String oldValue = textView.getText().toString();
        String newValue = String.valueOf(value);
        if (!oldValue.equals(newValue)) {
            textView.setText(newValue);
            lastLongs.put(textView, value);
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static long getLongText(TextView textView) {
        try {
            Long value = Long.parseLong(textView.getText().toString());
            lastLongs.put(textView, value);
            return value;
        } catch (NumberFormatException e) {
            Long value = lastLongs.get(textView);
            if (value != null)
                return value;
            return 0;
        }
    }

}
