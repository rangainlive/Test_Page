package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import java.util.Locale;

public class testMainpage extends AppCompatActivity {

    private static final String TEST_URL = "http://192.168.43.47/MyApi/questions.php";
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;

    private TextView chronometer;
    private CountDownTimer mCountDownTimer;
    private ToggleButton btStart;
    private  boolean isPlaying = false;
    private int min, s_id;

    private RecyclerView question_layout;
    private QuestionAdaptar questionAdaptar;
    private LinearLayout mDotLayout;
    private List<Questions> questionsList;
    private TextView[] mDots;

    private Button mNext;
    private Button mPrev;
    private int myCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mainpage);
        //Slider informations
        question_layout = (RecyclerView) findViewById(R.id.question_layout);
        mDotLayout = (LinearLayout) findViewById(R.id.dotLayout);

        mNext = (Button) findViewById(R.id.next_btn);
        mPrev = (Button) findViewById(R.id.prev_btn);

        questionsList = new ArrayList<>();
        question_layout.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        question_layout.setLayoutManager(gridLayoutManager);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCurrentPage+1 == mDots.length) {
                }
            }
        });

        loadQuestions();

        //Timer
        chronometer = findViewById(R.id.timerView);
        btStart = findViewById(R.id.play_btn);

        //get data from the previous page
        Intent intent = getIntent();
        min = intent.getIntExtra("time", -1);
        s_id = intent.getIntExtra("seriesId", -1);
        long millisInput = min*60000;
        setTime(millisInput);

        btStart.setText(null);
        btStart.setTextOn(null);
        btStart.setTextOff(null);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying) {
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

    }

    private void loadQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray que = new JSONArray(response);
                    for(int i=0; i<que.length();i++) {
                        JSONObject queObject = que.getJSONObject(i);
                        int id = queObject.getInt("id");
                        String question = queObject.getString("question");
                        String option1 = queObject.getString("option1");
                        String option2 = queObject.getString("option2");
                        String option3 = queObject.getString("option3");
                        String option4 = queObject.getString("option4");
                        String ans = queObject.getString("ans");
                        int series_id = queObject.getInt("series_id");

                        if(series_id == s_id){
                            Questions QES = new Questions(id, question, option1, option2, option3, option4, ans, series_id);
                            questionsList.add(QES);
                        }
                    }
                    questionAdaptar = new QuestionAdaptar(testMainpage.this, questionsList);
                    question_layout.setAdapter(questionAdaptar);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(testMainpage.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[200];
        mDotLayout.removeAllViews();
        for(int i=0; i<mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.background));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            myCurrentPage = position;
            if(position == 0) {
                mNext.setEnabled(true);
                mPrev.setEnabled(false);
                mNext.setText("Next");
                mPrev.setText("");
                mPrev.setVisibility(View.INVISIBLE);
            }
            else if(position == mDots.length-1){
                mNext.setText("Finish");
                mNext.setEnabled(true);
                mPrev.setEnabled(true);
                mPrev.setVisibility(View.VISIBLE);
            }
            else {
                mNext.setEnabled(true);
                mPrev.setEnabled(true);
                mPrev.setVisibility(View.VISIBLE);
                mNext.setText("Skip");
                mPrev.setText("Prev");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }
    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                isPlaying = false;
            }
        }.start();
        isPlaying = true;
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis/1000)/3600;
        int minutes = (int) ((mTimeLeftInMillis/1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis/1000) % 60;
        String timeLeftFormatted;
        if(hours>0) {
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        chronometer.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        isPlaying = false;
    }
}