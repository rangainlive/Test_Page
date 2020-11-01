package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class TestSeriesAdaptar extends RecyclerView.Adapter<TestSeriesAdaptar.MyViewHolder> {
    private final Context context;
    private final List<TestSeries> testSeriesList;

    public TestSeriesAdaptar(Context context, List<TestSeries> testSeriesList) {
        this.context = context;
        this.testSeriesList = testSeriesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_test, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30, 0,0);
        view.setLayoutParams(layoutParams);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int seriesId = testSeriesList.get(position).getSeriesId();
        final String seriesName = testSeriesList.get(position).getSeriesName();
        final int questions  = testSeriesList.get(position).getQuestions();
        final int marks = testSeriesList.get(position).getMarks();
        final int time = testSeriesList.get(position).getTime();

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

        holder.tsName.setText(seriesName);
        holder.questions.setText(String.valueOf(questions));
        holder.marks.setText(String.valueOf(marks));
        holder.time.setText(timeLeftFormatted);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, testsyllabus.class);
                intent.putExtra("seriesId", seriesId);
                intent.putExtra("time", time);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testSeriesList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tsName, questions, marks, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tsName = itemView.findViewById(R.id.tsName);
            questions = itemView.findViewById(R.id.questions);
            marks = itemView.findViewById(R.id.marks);
            time = itemView.findViewById(R.id.time);
        }
    }
}
