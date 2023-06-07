package com.example.pocketledger.databaseclass;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;

import com.example.pocketledger.dataclass.Bill;
import com.example.pocketledger.dataclass.BillComment;

import java.util.ArrayList;

public class BillDataManager {
    private Context context;

    public BillDataManager(Context context) {
        this.context = context;
    }

    public ArrayList<Bill> getAllBills() {

        ArrayList<Bill> billList = new ArrayList<>();

        // 获取可读的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询账单表中的数据
        Cursor cursor = db.rawQuery("SELECT id,project_name,amount,entry_time,category FROM bills ", null);

        // 遍历结果集并将数据存储到Bill对象中
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String projectName = cursor.getString(cursor.getColumnIndex("project_name"));
                @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String entryTime = cursor.getString(cursor.getColumnIndex("entry_time"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                Bill bill = new Bill(id, projectName, amount, entryTime, category);
                billList.add(bill);
            } while (cursor.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor.close();
        db.close();

        return billList;
    }

    public void insertBill(Bill bill) {
        // 获取可写的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 创建要插入的数据
        ContentValues values = new ContentValues();
        values.put("project_name", bill.getProjectName());
        values.put("amount", bill.getAmount());
        values.put("entry_time", bill.getEntryTime());
        values.put("category", bill.getCategory());

        // 插入数据到账单表
        long insertedId = db.insert("bills", null, values);

        // 关闭数据库连接
        db.close();
    }

    public void insertComment(int id, BillComment billData) {
        // 获取可写的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 创建要更新的数据
        ContentValues values = new ContentValues();
        values.put("photo", billData.getPhoto());
        values.put("comment", billData.getComment());

        // 更新指定id的数据
        db.update("bills", values, "id=?", new String[]{String.valueOf(id)});

        // 关闭数据库连接
        db.close();
    }

    public BillComment getCommentById(int id) {
        BillComment billComment = null;

        // 获取可读的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 执行查询
        String query = "SELECT photo, comment FROM bills WHERE id=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        AbstractWindowedCursor abstractCursor = (AbstractWindowedCursor) cursor;

        CursorWindow newWindow = null; // 创建一个新的 CursorWindow，设置更大的容量
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            newWindow = new CursorWindow("test", 5000000);
        }
        abstractCursor.setWindow(newWindow);

        if (abstractCursor.moveToFirst()) {
            // 解析数据
            byte[] photo = abstractCursor.getBlob(abstractCursor.getColumnIndex("photo"));
            String comment = abstractCursor.getString(abstractCursor.getColumnIndex("comment"));

            // 创建 BillComment 对象
            billComment = new BillComment(photo, comment);
        }

        cursor.close();


        return billComment;
    }

    public void deleteBillById(int id) {
        // 获取可写的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行删除操作
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("bills", whereClause, whereArgs);

        // 关闭数据库连接
        db.close();
    }

    public ArrayList<Bill> getBillsByDay(String date) {
        ArrayList<Bill> billList = new ArrayList<>();

        // 获取可读的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 执行查询
        String query = "SELECT id, project_name, amount, entry_time, category FROM bills WHERE entry_time = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // 遍历结果集并将数据存储到Bill对象中
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String projectName = cursor.getString(cursor.getColumnIndex("project_name"));
                @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String entryTime = cursor.getString(cursor.getColumnIndex("entry_time"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                Bill bill = new Bill(id, projectName, amount, entryTime, category);
                billList.add(bill);
            } while (cursor.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor.close();
        db.close();

        return billList;
    }


    public ArrayList<Bill> getBillsByMonth(String date) {
        ArrayList<Bill> billList = new ArrayList<>();

        // 获取可读的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 执行查询
        String query = "SELECT id, project_name, amount, entry_time, category FROM bills WHERE entry_time LIKE ?";
        if (date != null && date.length() >= 7) {
            String[] selectionArgs = {date.substring(0, 7) + "%"};
            // 继续处理其他逻辑

            Cursor cursor = db.rawQuery(query, selectionArgs);

            // 遍历结果集并将数据存储到Bill对象中
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String projectName = cursor.getString(cursor.getColumnIndex("project_name"));
                    @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                    @SuppressLint("Range") String entryTime = cursor.getString(cursor.getColumnIndex("entry_time"));
                    @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                    Bill bill = new Bill(id, projectName, amount, entryTime, category);
                    billList.add(bill);
                } while (cursor.moveToNext());
            }

            // 关闭游标和数据库连接
            cursor.close();
            db.close();
        }

        return billList;
    }

    public ArrayList<Bill> getBillsByYear(String date) {
        ArrayList<Bill> billList = new ArrayList<>();

        // 获取可读的数据库实例
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 执行查询
        String query = "SELECT id, project_name, amount, entry_time, category FROM bills WHERE entry_time LIKE ?";
        String[] selectionArgs = {date.substring(0, 4) + "%"};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // 遍历结果集并将数据存储到Bill对象中
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String projectName = cursor.getString(cursor.getColumnIndex("project_name"));
                @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String entryTime = cursor.getString(cursor.getColumnIndex("entry_time"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                Bill bill = new Bill(id, projectName, amount, entryTime, category);
                billList.add(bill);
            } while (cursor.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor.close();
        db.close();

        return billList;
    }


}
