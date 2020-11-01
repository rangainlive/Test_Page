package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class testpage extends AppCompatActivity {
    private static final String TEST_URL = "http://192.168.43.47/MyApi/test_series.php";
    RecyclerView recyclerView;
    private int id;
    //TextView testHead;
    TestSeriesAdaptar testSeriesAdaptar;
    List<TestSeries> testSeriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpage);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        testSeriesList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.testseriesView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadtestSeries();
    }

    private void loadtestSeries() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray tests = new JSONArray(response);
                    for(int i=0; i<tests.length();i++) {
                        JSONObject testObject = tests.getJSONObject(i);

                        int seriesId = testObject.getInt("seriesId");
                        String seriesName = testObject.getString("seriesName");
                        int questions = testObject.getInt("questions");
                        int marks = testObject.getInt("marks");
                        int time = testObject.getInt("time");
                        String date = testObject.getString("date");
                        int testId = testObject.getInt("testId");
                        String physics = testObject.getString("physics");
                        String che = testObject.getString("che");
                        String biology = testObject.getString("biology");

                        if(id == testId){
                            TestSeries testSeries = new TestSeries(seriesId, seriesName, questions, marks, time, date, testId, physics, che, biology);
                            testSeriesList.add(testSeries);
                        }
                    }
                    testSeriesAdaptar = new TestSeriesAdaptar(testpage.this, testSeriesList);
                    recyclerView.setAdapter(testSeriesAdaptar);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(testpage.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}