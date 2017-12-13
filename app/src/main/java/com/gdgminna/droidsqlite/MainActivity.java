package com.gdgminna.droidsqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    FloatingActionButton fab;
    EditText editName,editSurname,editMark,editid;
    Button viewall,viewUpdate,deleteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editName = findViewById(R.id.editText_Name);
        editSurname = findViewById(R.id.editText_Surname);
        editMark = findViewById(R.id.editText_Mark);
        editid=findViewById(R.id.editTex_ID);
        viewall=findViewById(R.id.button);
        viewUpdate=findViewById(R.id.button2);
        deleteData =findViewById(R.id.button3);
        fab = findViewById(R.id.fab);
        addData();
        ViewRecord();
        updateData();
        DeleteRecord();

    }

    public void DeleteRecord(){
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editid.getText().toString());
                if(deletedRows>0){
                    Snackbar.make(v, "Data Deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    Snackbar.make(v, "Data not Deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
    }
    public void updateData(){
        viewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editid.getText().toString(),editName.getText().toString(),editSurname.getText().toString(),editMark.getText().toString());
                if(isUpdated==true){
                    Snackbar.make(v, "Data Updated", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    Snackbar.make(v, "Data not Updated", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
    }
    public void addData(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isInserted = myDb.insertData(editName.getText().toString(),editSurname.getText().toString(),editMark.getText().toString());
               if(isInserted=true){
                   Snackbar.make(view, "Data Inserted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               }else{
                   Snackbar.make(view, "Data not Inserted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
    public void ViewRecord(){
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  myDb.getAllData();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("id: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Surname: "+res.getString(2)+"\n");
                    buffer.append("Mark: "+res.getString(3)+"\n\n");
                }

                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
