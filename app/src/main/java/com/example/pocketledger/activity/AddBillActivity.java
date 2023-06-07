package com.example.pocketledger.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketledger.dataclass.Bill;
import com.example.pocketledger.databaseclass.BillDataManager;
import com.example.pocketledger.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddBillActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        initDatePicker();
        initSpinner();
        init();


    }

    private void initSpinner() {

        spinner = findViewById(R.id.spinner);

// 创建一个包含选项的数组
        String[] options = {"收入", "支出"};

// 创建 ArrayAdapter 并设置数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);

// 设置下拉列表的样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// 将适配器设置到 Spinner 上
        spinner.setAdapter(adapter);
    }

    private void initDatePicker() {
        datePicker = findViewById(R.id.datePicker);

// 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

// 设置 DatePicker 的默认日期
        datePicker.init(year, month, dayOfMonth, null);
    }

    private void init(){
        Button addButton = findViewById(R.id.bn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取账单描述的值
                EditText descriptionEditText = findViewById(R.id.et_projectname);
                String description = descriptionEditText.getText().toString();

                // 获取金额的值
                EditText amountEditText = findViewById(R.id.et_amount);
                String amount = amountEditText.getText().toString();
                if (description.equals("")||amount.equals(""))
                {
                    Toast.makeText(AddBillActivity.this,"金额不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    double amountValue = Double.parseDouble(amount);


                    // 获取类别的值
                    Spinner categorySpinner = findViewById(R.id.spinner);
                    String category = categorySpinner.getSelectedItem().toString();
                    String categoryId = "";
                    if (category.equals("支出")) {
                        categoryId = "0";
                    } else if (category.equals("收入")) {
                        categoryId = "1";
                    }

                    // 获取时间的值
                    DatePicker datePicker = findViewById(R.id.datePicker);
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int dayOfMonth = datePicker.getDayOfMonth();

                    // 创建 Calendar 对象，用于存储时间值
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth, 0, 0, 0); // 设置时间部分为0
                    Date date = calendar.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = dateFormat.format(date);


                    // 调用 insertBill() 方法，将数据存储到数据库中
                    BillDataManager billDataManager = new BillDataManager(AddBillActivity.this);
                    billDataManager.insertBill(new Bill(0, description, amountValue, dateString, categoryId));
                    Log.d("dasdasdas", "添加成功");

                    descriptionEditText.setText("");
                    amountEditText.setText("");
                    Toast.makeText(AddBillActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}