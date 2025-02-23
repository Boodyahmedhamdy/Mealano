package io.github.boodyahmedhamdy.mealano.utils.ui;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;

public class DatePickerUtils {

    public static void showDatePicker(FragmentActivity activity, CustomClickListener<String> listener) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Get today's date in UTC
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long today = calendar.getTimeInMillis();

        // Get the date for next 7 days
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        long nextWeek = calendar.getTimeInMillis();

        // Calendar constraints to limit selection to the next 7 days
        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now()) // Disable past dates
                .setStart(today)  // Set the minimum date to today
                .setEnd(nextWeek) // Set the maximum date to 7 days from today
                .build();

        // Build the Material DatePicker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setSelection(today)  // Default selection is today
                .setCalendarConstraints(constraints) // Apply date constraints
                .build();

        // Show the DatePicker
        datePicker.show(fragmentManager, "DATE_PICKER");

        // Handle date selection
        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String selectedDate = sdf.format(new Date(selection));

            listener.onClick(selectedDate);
            Toast.makeText(activity, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });
    }

}
