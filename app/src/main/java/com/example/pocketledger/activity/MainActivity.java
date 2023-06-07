package com.example.pocketledger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pocketledger.dataclass.Bill;
import com.example.pocketledger.databaseclass.BillDataManager;
import com.example.pocketledger.adapter.BillListAdapter;
import com.example.pocketledger.R;
import com.example.pocketledger.view.SlideRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }
  public   void init(){
        BillDataManager billDataManager = new BillDataManager(this);
        ArrayList<Bill> billList = billDataManager.getAllBills();

        TextView hint = findViewById(R.id.hint);
        if (billList.isEmpty()){
            hint.setText("目前还没有账单哦!");
        }else
        {
            hint.setText("");

        }

// 初始化 RecyclerView
      SlideRecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // 添加账单对象到 billList

        // 初始化适配器
        BillListAdapter adapter = new BillListAdapter(this, billList);
        recyclerView.setAdapter(adapter);

        Button add = findViewById(R.id.floatingButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBillActivity.class));
            }
        });

        Button statistics = findViewById(R.id.floatingButton1);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BillStatisticsActivity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}