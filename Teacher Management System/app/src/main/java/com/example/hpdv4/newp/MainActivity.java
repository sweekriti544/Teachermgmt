package com.example.hpdv4.newp;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    DatabaseAdapter myDb;
    EditText username, address, faculty, password,id;
    Button signup, viewa,updated,deleted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myDb = new DatabaseAdapter(this);
        id=(EditText) findViewById(R.id.id);
        username = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
        faculty = (EditText) findViewById(R.id.faculty);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signup);
        viewa = (Button) findViewById(R.id.viewa);
        updated=(Button) findViewById(R.id.updated);
        deleted=(Button) findViewById(R.id.deleted);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void reset(View v) {
        id.setText("");
       username.setText("");
        address.setText("");
        faculty.setText(" ");
        password.setText("");
    }

    public void DeleteData(){
        deleted.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows=myDb.deleteData(id.getText().toString());
                        if(deletedRows>0){
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_SHORT).show();
                    }
                }
        );
}
public void UpdateData(){
        updated.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdated=myDb.updateData(id.getText().toString(),username.getText().toString(), address.getText().toString(), faculty.getText().toString(), password.getText().toString());
                        if(isUpdated==true) {
                            Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                    }
        );
}
    public void AddData() {
        signup.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(username.getText().toString(), address.getText().toString(), faculty.getText().toString(), password.getText().toString());
                        if (isInserted == true){
                            reset(view);
                            Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(MainActivity.this, "Failed to insert", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    public void viewAll() {
        viewa.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Cursor res=myDb.getAllData();
                        if(res.getCount()==0){
                          showMessage("No Data","Nothing Found");
                        }
                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("id:"+res.getString(0)+"\n");
                            buffer.append("username:"+res.getString(1)+"\n");
                            buffer.append("address:"+res.getString(2)+"\n");
                            buffer.append("faculty:"+res.getString(3)+"\n");
                            buffer.append("password:"+res.getString(4)+"\n");
                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
builder.setCancelable(true);
builder.setTitle(title);
builder.setMessage(Message);
builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case (R.id.fetch):
                new fetchData().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class fetchData extends AsyncTask<String, Void, JSONArray> {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Starting", Toast.LENGTH_SHORT).show();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray results = null;
            try {
                URL url = new URL("https://api.myjson.com/bins/167jq8");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(">>>>>>");
                    buffer.append(line);
                }
                System.out.println("buffer = " + buffer.toString());
                results = new JSONArray(buffer.toString());
            } catch (Exception ex){
                ex.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return results;
        }

        @Override
        protected void onPostExecute(JSONArray s) {
            progressDialog.dismiss();
            System.out.println("JSONObject = " + s);
            Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_SHORT).show();
        }
    }
}



