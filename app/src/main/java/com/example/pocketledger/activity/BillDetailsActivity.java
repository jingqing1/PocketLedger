package com.example.pocketledger.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pocketledger.dataclass.BillComment;
import com.example.pocketledger.databaseclass.BillDataManager;
import com.example.pocketledger.R;

import java.io.ByteArrayOutputStream;

public class BillDetailsActivity extends AppCompatActivity {

    private ImageButton intoPhoto;
   public static Integer id;
    private EditText etComment;
    private BillDataManager billDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        init();
        loadContent();


    }

    private void loadContent() {
        BillComment billComment = billDataManager.getCommentById(id);

        if (billComment.getComment() != null) {
            etComment.setText(billComment.getComment());

        }
        if (billComment.getPhoto() != null){
            byte[] imageBytes = billComment.getPhoto(); // 从数据库中获取的二进制图片数据
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            intoPhoto.setImageBitmap(bitmap);
        }
    }

    private void init() {

        billDataManager = new BillDataManager(BillDetailsActivity.this);

        intoPhoto = findViewById(R.id.intoPhoto);
        intoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
            }
        });

        Button submit = findViewById(R.id.btn_submit);
        etComment = findViewById(R.id.et_comment);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把图片先转化成bitmap格式
                BitmapDrawable drawable = (BitmapDrawable) intoPhoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                //二进制数组输出流
                ByteArrayOutputStream byStream = new ByteArrayOutputStream();
                //将图片压缩成质量为100的PNG格式图片
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byStream);
                //把输出流转换为二进制数组
                byte[] byteArray = byStream.toByteArray();


                String commentString = etComment.getText().toString();


                billDataManager.insertComment(id, new BillComment(byteArray, commentString));

                Toast.makeText(BillDetailsActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });



    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除确认");
        builder.setMessage("你是否要删除这一条账单");

        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 执行删除操作
                billDataManager.deleteBillById(id);
                finish();
            }
        });

        builder.setNegativeButton("取消", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //从相册返回的数据
            if (data != null) {
                //得到图片的全路径
                Uri uri = data.getData();
                intoPhoto.setImageURI(uri);
            }
        }
    }
}