package com.example.android.quakereport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.quakereport.adapter.QuakeAdapter;
import com.example.android.quakereport.app.AppController;
import com.example.android.quakereport.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    public static final String LOG_TAG = "jehad";
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    ArrayList<Earthquake> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        showDialog();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USGS_REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(LOG_TAG,response.toString());
                        try {
                            JSONArray quakesArray = response.getJSONArray("features");

                            for(int i = 0 ; i < quakesArray.length() ; i++){
                                JSONObject earthquake_element = quakesArray.getJSONObject(i);
                                JSONObject properties = earthquake_element.getJSONObject("properties");
                                double magnitude = properties.getDouble("mag");
                                String location = properties.getString("place");
                                Long time = properties.getLong("time");
                                // Extract the value for the key called "url"
                                String url = properties.getString("url");

                                Earthquake earthquake = new Earthquake(magnitude , location , time , url );
                                data.add(earthquake);

                            }
                        } catch (JSONException e) {
                            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
                            hideDialog();
                        }

                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, error.getMessage());
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(objectRequest);

        // Find a reference to the {@link ListView} in the layout
        final ListView earthquakeListView = (ListView) findViewById(R.id.list);
        // Create a new {@link ArrayAdapter} of earthquakes
        final QuakeAdapter quakeAdapter =new QuakeAdapter(this , data);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(quakeAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(QuakeAdapter.data.get(i).getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

}

    public void showDialog() {
        progressDialog = new ProgressDialog(EarthquakeActivity.this);
        progressDialog.setMessage("Data is Loading ....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}

