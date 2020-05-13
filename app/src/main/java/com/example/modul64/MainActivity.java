package com.example.modul64;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextName;
    private EditText editTextDesg; //definisi jurusan
    private EditText editTextSal; //definisi email

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi dari View
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg); //jurusan
        editTextSal = (EditText) findViewById(R.id.editTextSalary); //email

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }


    //Dibawah ini merupakan perintah untuk Menambahkan mahasiswa (CREATE)
    private void addEmployee(){

        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim(); //jurusan
        final String sal = editTextSal.getText().toString().trim(); //email

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() { //Thread sebelum tugas di eksekusi.
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) { //thread sesudah dieksekusi
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) { //Langkah ini menjalankan komputasi latar belakang,
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,name);
                params.put(konfigurasi.KEY_EMP_POSISI,desg); //konfigurasi jurusan
                params.put(konfigurasi.KEY_EMP_GAJIH,sal); //konifgurasi email

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee(); //fungsi add mahasiswa
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addEmployee(); //add mahasiswa
        }

        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaPgw.class));
        }
    }
}