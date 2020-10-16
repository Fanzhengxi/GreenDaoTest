package com.avatarmind.greendaotest;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avatarmind.greendaotest.bean.Student;

import java.util.List;

/**
 * Created by zhangqun on 18-4-8.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    private onClickListener listener;
    private List<Student> list;
    //构造器
    public MyAdapter(List<Student> list) {
        this.list = list;
        Log.d(TAG, "list::" + list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = list.get(position);
        Log.d(TAG, " student:" + student);
        holder.rootItem.setSelected(student.isSelected());
        holder.id.setText(student.getId() + "");
        holder.name.setText(student.getName());
        holder.sex.setText(student.getSex());
        holder.age.setText(student.getAge() + "");
        holder.score.setText(student.getScore() + "");
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout rootItem;
        private TextView id;
        private TextView name;
        private TextView sex;
        private TextView age;
        private TextView score;

        ViewHolder(View itemView) {
            super(itemView);
            rootItem=itemView.findViewById(R.id.item_root);
            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            sex = itemView.findViewById(R.id.tv_sex);
            age = itemView.findViewById(R.id.tv_age);
            score = itemView.findViewById(R.id.tv_score);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.clickItem((Integer)   v.getTag());
        }
    }

    void setListener(onClickListener listener) {
        this.listener = listener;
    }

    interface onClickListener {
        void clickItem(int position);
    }
}
