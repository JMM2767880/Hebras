package com.example.hebras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lSegundos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        init();
    }

    private void initComponents() {
        lSegundos = findViewById(R.id.lSegundos);
    }

    private void init(){
        /*Hilo1 h1 = new Hilo1();
        h1.start();*/

        /*CuentaAtrasTask cuentaAtrasTask = new CuentaAtrasTask(lSegundos);
          cuentaAtrasTask.execute();*/

        Thread runnable = new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 5; i >= 0; i--){
                    System.out.println(i + " segundos");

                    final String numeroString = String.valueOf(i);
                    try {
                        Hilo1.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lSegundos.post(new Runnable() {
                        @Override
                        public void run() {
                            lSegundos.setText(numeroString);
                        }
                    });
                }

                Intent intent = new Intent(MainActivity.this, Terminado.class);
                startActivity(intent);
                finish();
            }
        };

        runnable.start();
    }

    private class Hilo1 extends Thread{
        @Override
        public void run() {
            cuentaAtrás();
        }
    }

    private void cuentaAtrás() {
        for (int i = 5; i >= 0; i--){
            System.out.println(i + " segundos");

            String numeroString = String.valueOf(i);
            try {
                Hilo1.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            addSegundo(lSegundos, numeroString);
        }

        Intent intent = new Intent(MainActivity.this, Terminado.class);
        startActivity(intent);
        finish();

    }

    private void addSegundo(final TextView lSegundos, final String segundo) {
        lSegundos.post(new Runnable() {
            @Override
            public void run() {
                lSegundos.setText(segundo + " segundos");
            }
        });
    }

    private class CuentaAtrasTask extends AsyncTask<Integer, Integer, String>{

        private TextView tvSegundos;

        public CuentaAtrasTask(TextView tvSegundos) {
            this.tvSegundos = tvSegundos;
        }

        @Override
        protected String doInBackground(Integer... integers) {

            for (int i = 5; i >= 0; i--){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            System.out.println("values: " + values[0]);
            tvSegundos.setText(String.valueOf(values[0]) + " segundos");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(MainActivity.this, Terminado.class);
            startActivity(intent);
            finish();
        }
    }

}
