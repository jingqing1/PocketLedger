package com.example.pocketledger.databaseclass;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {private static final String DATABASE_NAME = "bills.db";
    private static final int DATABASE_VERSION = 3;

    // 构造函数
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建账单表
        String createTableQuery = "CREATE TABLE IF NOT EXISTS bills ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "project_name TEXT,"
                + "amount REAL,"
                + "entry_time TEXT,"
                + "category TEXT,"
                + "photo BLOB,"
                + "comment TEXT,"
                + "is_favorite INTEGER DEFAULT 0" // 添加收藏标识字段，默认值为0
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在数据库升级时的操作，可以考虑备份数据并重新创建表
        if (oldVersion < 2) {
            // 备份原始数据
            db.execSQL("ALTER TABLE bills RENAME TO bills_temp");

            // 创建新表
            onCreate(db);

            // 将原始数据复制到新表
            db.execSQL("INSERT INTO bills (id, project_name, amount, entry_time, category, is_favorite) " +
                    "SELECT id, project_name, amount, entry_time, category, 0 FROM bills_temp"); // 添加默认值0

            // 删除临时表
            db.execSQL("DROP TABLE bills_temp");
        }
    }


}