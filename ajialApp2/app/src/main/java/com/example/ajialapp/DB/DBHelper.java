package com.example.ajialapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ajialapp.modle.Employee;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    static SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, Employee.DB_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Employee.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ Employee.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertEmployee(String id ,String name , String title , String password ){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Employee.COL_ID, id);
        contentValues.put(Employee.COL_NAME, name);
        contentValues.put(Employee.COL_TITLE, title);
        contentValues.put(Employee.COL_PASSWORD, password);
        return database.insert(Employee.TABLE_NAME, null, contentValues) > 0;
    }

    public static boolean updateEmployee(String OldID , String id ,String name , String title , String password){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Employee.COL_ID, id);
        contentValues.put(Employee.COL_NAME, name);
        contentValues.put(Employee.COL_TITLE, title);
        contentValues.put(Employee.COL_PASSWORD, password);
        return database.update(Employee.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(OldID)}) > 0;
    }
    public boolean deleteEmployee(String id) {
        return database.delete(Employee.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }



    public ArrayList<Employee> getEmployee(){
        ArrayList<Employee> employeeS= new ArrayList<>();
        String sqlQuery = "select * from employee order by id desc";
        Cursor cursor = database.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getString(cursor.getColumnIndex(Employee.COL_ID)));
                employee.setName(cursor.getString(cursor.getColumnIndex(Employee.COL_NAME)));
                employee.setTitle(cursor.getString(cursor.getColumnIndex(Employee.COL_TITLE)));
                employee.setPassword(cursor.getString(cursor.getColumnIndex(Employee.COL_PASSWORD)));
                employeeS.add(employee);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return employeeS;
    }
}
