package com.example.pocketledger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pocketledger.databaseclass.dataclass.Bill;
import com.example.pocketledger.databaseclass.BillDataManager;
import com.example.pocketledger.adapter.BillListAdapter;
import com.example.pocketledger.R;
import com.example.pocketledger.in.OnBillDeleteListener;
import com.example.pocketledger.view.SlideRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class BillStatisticsActivity extends AppCompatActivity implements View.OnClickListener, OnBillDeleteListener {

    private int year;
    private int month;
    private int dayOfMonth;
    private double inMoney;
    private double outMoney;
    private ArrayList<Bill> billList;
    private TextView out;
    private TextView in;
    private ListView list;
    private String selectedDate;
    private String selectedMonth;
    private String selectedYear;
    private BillDataManager billDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_statistics);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

//        init();
        comInit();
    }

    private void comInit() {



        calculatedAmount();

        Button btnFilterDay = findViewById(R.id.btn_filter_day);
        Button btnFilterMonth = findViewById(R.id.btn_filter_month);
        Button btnFilterYear = findViewById(R.id.btn_filter_year);

        btnFilterDay.setOnClickListener(this);
        btnFilterMonth.setOnClickListener(this);
        btnFilterYear.setOnClickListener(this);
    }

    @Override
    public void onBillDeleted() {
       calculatedAmount();
    }
    private void calculatedAmount() {
        billDataManager = new BillDataManager(this);
        billList = billDataManager.getAllBills();
        SlideRecyclerView recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // 添加账单对象到 billList

        // 初始化适配器
        BillListAdapter adapter = new BillListAdapter(this, billList);
        adapter.setOnBillDeleteListener(this);
        recyclerView.setAdapter(adapter);
        outMoney = 0.0;
        inMoney = 0.0;


        for (Bill i : billList) {
            if (i.getCategory().equals("0")) {
                outMoney += i.getAmount();
            }
            if (i.getCategory().equals("1")) {
                inMoney += i.getAmount();
            }
        }
        out = findViewById(R.id.tv_out_money);
        out.setText(String.valueOf(outMoney));
        in = findViewById(R.id.tv_in_money);
        in.setText(String.valueOf(inMoney));

    }

    private void init(int id) {
        // 创建一个 Calendar 实例，用于设置 DatePickerDialog 的默认日期


// 创建 DatePickerDialog 对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearIn, int monthIn, int dayOfMonthIn) {
                // 处理用户选择的日期
                // 在这里可以将日期转换为特定的格式，然后使用它进行查询或其他操作
                selectedDate = String.format("%04d-%02d-%02d", yearIn, monthIn + 1, dayOfMonthIn);
                selectedMonth = String.format("%04d-%02d", yearIn, monthIn + 1);
                selectedYear = String.format("%04d", yearIn);
                // 执行相关操作
                year = yearIn;
                month = monthIn;
                dayOfMonth = dayOfMonthIn;

                if (id == R.id.btn_filter_day) {
                    billList = billDataManager.getBillsByDay(selectedDate);
                    calculatedAmount();
                    Log.d("asdasdas",String.valueOf(selectedDate));

                }
                if (id == R.id.btn_filter_month) {
                    billList = billDataManager.getBillsByMonth(selectedMonth);
                    calculatedAmount();

                }
                if (id == R.id.btn_filter_year) {
                    billList = billDataManager.getBillsByYear(selectedYear);
                    calculatedAmount();
                }



            }
        }, year, month, dayOfMonth);

// 显示日期选择对话框
        datePickerDialog.show();

    }

    @Override
    public void onClick(View v) {
        init(v.getId());


    }
}