package com.example.sqlapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName,editPhone,editMail,editId;
    Button btnAdd;
    Button btnShow;
    Button btnUpdate;
    Button btnDelete;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB =  new DatabaseHelper(this);
        editName = (EditText)findViewById(R.id.editName);
        editPhone = (EditText)findViewById(R.id.editPhone);
        editMail = (EditText)findViewById(R.id.editMail);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnShow = (Button)findViewById(R.id.btnShow);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        editId = (EditText)findViewById(R.id.editId);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        imageView = (ImageView)findViewById(R.id.imageView2);
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Integer deletedRows = myDB.deleteData(editId.getText().toString());
              if(deletedRows > 0)
                  Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
              else
                  Toast.makeText(MainActivity.this,"Data Is Not Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(editId.getText().toString(),editName.getText().toString(),editPhone.getText().toString(),editMail.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Is Not Updated",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AddData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           boolean isInserted = myDB.insertData(editName.getText().toString(),
                      editPhone.getText().toString(),
                      editMail.getText().toString());
           if(isInserted == true)
               Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
           else
               Toast.makeText(MainActivity.this,"Data Is Not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void ViewAll(){
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          Cursor res = myDB.getAllData();
          if(res.getCount() == 0){
              //show message
              showMessage("Error", "Nothing found");

              return;
          }
          StringBuffer buffer = new StringBuffer();
          while (res.moveToNext()){
              buffer.append("ID :"+ res.getString(0)+"\n");
              buffer.append("Name :"+ res.getString(1)+"\n");
              buffer.append("Phone :"+ res.getString(2)+"\n");
              buffer.append("Email :"+ res.getString(3)+"\n\n");
          }
          //show all data
            showMessage("Data",buffer.toString());
            }
        });

    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
