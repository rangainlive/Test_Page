package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class QuestionAdaptar extends RecyclerView.Adapter<QuestionAdaptar.MyViewHolder> {
    Context context;
    private final List<Questions> questionsList;

    public QuestionAdaptar(Context context, List<Questions> questionsList) {
        this.context = context;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_layout, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 0, 10, 0);
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String QUESTION = questionsList.get(position).getQuestion();
        final String OPTION1 = questionsList.get(position).getOption1();
        final String OPTION2 = questionsList.get(position).getOption2();
        final String OPTION3 = questionsList.get(position).getOption3();
        final String OPTION4 = questionsList.get(position).getOption4();

        holder.question_text.setText(QUESTION);
        holder.option1.setText(OPTION1);
        holder.option2.setText(OPTION2);
        holder.option3.setText(OPTION3);
        holder.option4.setText(OPTION4);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question_text;
        Button option1, option2, option3, option4;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            question_text = itemView.findViewById(R.id.question_text);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);
        }
    }
}
