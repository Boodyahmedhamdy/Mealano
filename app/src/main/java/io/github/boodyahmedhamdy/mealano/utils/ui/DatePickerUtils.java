package io.github.boodyahmedhamdy.mealano.utils.ui;

import static com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;

public class DatePickerUtils {
    private static final String TAG = "DatePickerUtils";

    public static void showDatePicker(FragmentActivity activity, CustomClickListener<Long> listener) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Get today's date in UTC
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long today = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_YEAR, 7);
        long nextWeek = calendar.getTimeInMillis();

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(CompositeDateValidator.allOf(Arrays.asList(
                        DateValidatorPointForward.now(),
                        DateValidatorPointBackward.before(nextWeek)
                )))
                .setStart(today)
                .setEnd(nextWeek)
                .build();

        calendar.setTimeInMillis(today);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_a_date)
                .setSelection(today)  // Default selection is today
                .setCalendarConstraints(constraints) // Apply date constraints
                .setInputMode(INPUT_MODE_CALENDAR)
                .build();

        // Show the DatePicker
        datePicker.show(fragmentManager, "DATE_PICKER");

        // Handle date selection
        datePicker.addOnPositiveButtonClickListener(selection -> {
            listener.onClick(selection);

            String selectedDate = formatDateToString(selection);
            Log.i(TAG, "showDatePicker: " + selectedDate);
            Toast.makeText(activity, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });
    }

    public static String formatDateToString(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(date));
    }

}
