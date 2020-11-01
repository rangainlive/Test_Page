package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class testsyllabus extends AppCompatActivity {
    private static final String TEST_URL = "http://192.168.43.47/MyApi/test_series.php";
    private int serId, time;
    private Button readIns_btn;

    private TextView tsName, tsName1, tsQuestions, tsMarks, tsTime, phy_syllabus, che_syllabus, bio_syllabus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsyllabus);

        Intent intent = getIntent();
        serId = intent.getIntExtra("seriesId",-1);
        time = intent.getIntExtra("time",-1);

        tsName = findViewById(R.id.tsName);
        tsName1 = findViewById(R.id.tsName1);
        tsQuestions = findViewById(R.id.questions);
        tsMarks = findViewById(R.id.marks);
        tsTime = findViewById(R.id.time);
        phy_syllabus = findViewById(R.id.phy_syllabus);
        che_syllabus = findViewById(R.id.che_syllabus);
        bio_syllabus = findViewById(R.id.bio_syllabus);

        loadtestSeries();
        readIns_btn = findViewById(R.id.readIns_btn);
        readIns_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testsyllabus.this, startTestpage.class);
                intent.putExtra("seriesId", serId);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });

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
                        int testId = testObject.getInt("testId");
                        String physics = testObject.getString("physics");
                        String chemistry = testObject.getString("che");
                        String biology = testObject.getString("biology");

                        if(serId == seriesId) {

                            tsName.setText(seriesName);
                            tsName1.setText(seriesName);
                            tsQuestions.setText(String.valueOf(questions));
                            phy_syllabus.setText(physics);
                            che_syllabus.setText(chemistry);
                            bio_syllabus.setText(biology);
                            tsMarks.setText(String.valueOf(marks));

                            long millisInput = time*60000;
                            int hours = (int) (millisInput/1000)/3600;
                            int minutes = (int) ((millisInput/1000) % 3600) / 60;
                            int seconds = (int) (millisInput/1000) % 60;
                            String timeLeftFormatted;
                            if(hours>0) {
                                timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
                            }
                            else {
                                timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                            }
                            tsTime.setText(timeLeftFormatted);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(testsyllabus.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}