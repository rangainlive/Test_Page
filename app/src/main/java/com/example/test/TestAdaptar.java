package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdaptar extends RecyclerView.Adapter<TestAdaptar.MyViewHolder>{

    private final Context context;
    private final List<Test> testList;

    public TestAdaptar(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.test_view, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 40, 0, 0);
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final int testId = testList.get(position).getId();
        final String testName = testList.get(position).getTest_name();
        final int nTest = testList.get(position).getNo_of_test();

        holder.testId.setText(String.valueOf(testId));
        holder.testName.setText(testName);
        holder.noOfTest.setText(String.valueOf(nTest));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, testpage.class);
                intent.putExtra("id",testId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView testName, testId, noOfTest;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            testId = itemView.findViewById(R.id.testId);
            testName = itemView.findViewById(R.id.testName);
            noOfTest = itemView.findViewById(R.id.noOfTest);
        }
    }
}
