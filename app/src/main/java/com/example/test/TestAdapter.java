package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder>{

    private Context context;
    private List<Test> testList;

    public TestAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.test_view, parent, false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.test_view, null);
        //View rootView = LayoutInflater.from(context).inflate(R.layout.test_view, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 40, 0, 0);
        //rootView.setLayoutParams(lp);
        view.setLayoutParams(lp);
        //return new RecyclerViewHolder(rootView);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Test test = testList.get(position);
        holder.testName.setText(test.getTest_name());
        holder.testId.setText(String.valueOf(test.getId()));
        holder.noOfTest.setText(String.valueOf(test.getNo_of_test()));

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), testpage.class);
                intent.putExtra("id", String.valueOf(testId));
                //itemView.getContext().startActivity(new Intent(itemView.getContext(), testpage.class));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView testName, testId, noOfTest;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            testId = itemView.findViewById(R.id.testId);
            testName = itemView.findViewById(R.id.testName);
            noOfTest = itemView.findViewById(R.id.noOfTest);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), testpage.class);
                    intent.putExtra("id", String.valueOf(testId));
                    //itemView.getContext().startActivity(new Intent(itemView.getContext(), testpage.class));
                }
            });
        }
    }
}
