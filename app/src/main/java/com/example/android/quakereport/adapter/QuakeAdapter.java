package com.example.android.quakereport.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.android.quakereport.R;
import com.example.android.quakereport.model.Earthquake;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

public class QuakeAdapter extends BaseAdapter {

    Activity activity;
    public static  ArrayList<Earthquake> data;
    private static final String LOCATION_SEPARATOR = " of ";


    public QuakeAdapter(Activity activity, ArrayList<Earthquake> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {

        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(activity, magnitudeColorResourceId);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root = LayoutInflater.from(activity).inflate(R.layout.quake_details , null , false);

        String originalLocation = data.get(i).getLocation();
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = activity.getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        final TextView magnitudeView = (TextView) root.findViewById(R.id.magnitude);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(data.get(i).getMgnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        final TextView primaryLocationView = root.findViewById(R.id.primary_location);
        final TextView locationOffsetView = root.findViewById(R.id.location_offset);
        final TextView quakeTime = root.findViewById(R.id.time);
        final TextView quakeDate = root.findViewById(R.id.date);
        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(data.get(i).getMTimeInMilliseconds());
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(data.get(i).getMgnitude());

        magnitudeView.setText(formattedMagnitude);
        quakeDate.setText(formattedDate);
        quakeTime.setText(formattedTime);
        primaryLocationView.setText(primaryLocation);
        locationOffsetView.setText(locationOffset);
        return root;

    }
}
