package com.avatarmind.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avatarmind.greendaotest.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private GreenDaoManager manager;
    private EditText etName;
    private EditText etSex;
    private EditText etAge;
    private EditText etScore;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;
    private List<Student> list = new ArrayList<>();
    private MyAdapter myAdapter;
    //用于更新界面数据
    private int currentPosition = -1;
    //用于更新数据库数据
    private long keyId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
        initListeners();
    }

    private void initData() {
        manager = GreenDaoManager.getInstance();
    }

    private void initViews() {
        etName = findViewById(R.id.et_name);
        etSex = findViewById(R.id.et_sex);
        etAge = findViewById(R.id.et_age);
        etScore = findViewById(R.id.et_score);

        btnInsert = findViewById(R.id.btn_insert);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        btnQuery = findViewById(R.id.btn_query);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_show);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(list);
        myAdapter.setListener(new MyAdapter.onClickListener() {
            @Override
            public void clickItem(int position) {
                currentPosition = position;
                //选中的数据进行回显,并设置高亮显示
                Student student = list.get(position);
                keyId = student.getId();
                Log.d(TAG, "click the item::" + student);
                etName.setText(student.getName());
                etSex.setText(student.getSex());
                etAge.setText(student.getAge() + "");
                etScore.setText(student.getScore() + "");

                setSelected(position);
            }
        });
        recyclerView.setAdapter(myAdapter);
    }

    private void initListeners() {
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int age = 0;
        float score = (float) 0.0;
        String name = etName.getText().toString().trim();
        String sex = etSex.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String scoreStr = etScore.getText().toString().trim();

        if (!TextUtils.isEmpty(ageStr)) {
            age = Integer.parseInt(ageStr);
        }

        if (!TextUtils.isEmpty(scoreStr)) {
            score = Float.parseFloat(scoreStr);
        }

        switch (v.getId()) {
            case R.id.btn_insert:
                if (TextUtils.isEmpty(name) &&
                        TextUtils.isEmpty(sex) &&
                        TextUtils.isEmpty(ageStr) &&
                        TextUtils.isEmpty(scoreStr)) {
                    Toast.makeText(MainActivity.this, "the data can not be null!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Student insertData = new Student(null, name, sex, age, score);
                long rowId = manager.insertData(insertData);
                Log.d(TAG, "insert rowId::" + rowId);
                if (rowId >= 0) {//插入成功,记录主键id
                    list.add(insertData);
                    List<Student> allData = manager.queryAllData();
                    Log.d(TAG, "000000insert::" + allData);

                } else {
                    Log.e(TAG, "inset error");
                }
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_delete:
                if (currentPosition == -1) {
                    showToast("请选择待删除项目");
                    return;
                }
                if (keyId == -1) {
                    showToast("数据库中没有可供删除的数据");
                    return;
                }

                manager.deleteData(Long.valueOf(keyId));
                list.remove(currentPosition);
                currentPosition = -1;
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_update:

                if (currentPosition == -1) {
                    showToast("请选择待更新项目");
                    return;
                }
                if (keyId == -1) {
                    showToast("数据库中没有可供更新的数据");
                    return;
                }

                if (TextUtils.isEmpty(name) &&
                        TextUtils.isEmpty(sex) &&
                        TextUtils.isEmpty(ageStr) &&
                        TextUtils.isEmpty(scoreStr)) {
                    showToast("待更新的数据不能为空");
                    return;
                }

                Student updateData = list.get(currentPosition);
                updateData.setName(name);
                updateData.setSex(sex);
                updateData.setAge(age);
                updateData.setScore(score);

                manager.updateData(updateData);
                list.set(currentPosition, updateData);

                myAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_query:
                if (!list.isEmpty()) {
                    list.clear();
                }
                List<Student> allData = manager.queryAllData();
                Log.d(TAG, "query all data::" + allData);
                list.addAll(allData);
                myAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

        initEdit();
        List<Student> allData = manager.queryAllData();
        Log.d(TAG, "操作后的当前数据库数据" + allData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentPosition = -1;
        keyId = -1;
        for (Student student : list) {
            student.setSelected(false);
        }
    }

    /**
     * 设置选中的item，高亮显示
     *
     * @param position
     */
    private void setSelected(int position) {
        for (Student student : list) {
            student.setSelected(false);
        }
        list.get(position).setSelected(true);
        myAdapter.notifyDataSetChanged();
    }

    //弹出toast
    private void showToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    //初始化編輯框
    private void initEdit() {
        etName.setText("");
        etSex.setText("");
        etAge.setText("");
        etScore.setText("");
    }
}
