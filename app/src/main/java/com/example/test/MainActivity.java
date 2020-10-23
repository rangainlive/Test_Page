package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private static final String TEST_URL = "http://192.168.43.47/MyApi/api.php";
    RecyclerView recyclerView;
    TestAdapter testAdapter;
    List<Test> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.testListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTests();
    }

    private void loadTests() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray tests = new JSONArray(response);
                    for(int i=0; i<tests.length(); i++) {
                        JSONObject testObject = tests.getJSONObject(i);

                        int id = testObject.getInt("id");
                        String test_name = testObject.getString("test_name");
                        int no_of_test = testObject.getInt("no_of_test");

                        Test test = new Test(id, test_name, no_of_test);
                        testList.add(test);
                    }
                    testAdapter = new TestAdapter(MainActivity.this, testList);
                    recyclerView.setAdapter(testAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}