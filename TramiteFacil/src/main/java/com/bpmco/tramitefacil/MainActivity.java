package com.bpmco.tramitefacil;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Wsdl2Code.WebServices.pqrdService.Syncronize;

public class MainActivity extends Activity implements View.OnClickListener{
    Syncronize sync;
    TRLocationListener locationListener;

    Button btnIngresar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar = (Button)findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);

        try
        {
            sync = new Syncronize();
            sync.setOnResultListener(asynResult);
            sync.execute(this.getApplicationContext());

            locationListener = new TRLocationListener();
            locationListener.setOnResultListener(locAsyncResult);
            locationListener.execute(this.getApplicationContext());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
    }

    Syncronize.OnAsyncResult asynResult = new Syncronize.OnAsyncResult() {
        @Override
        public void onResultSuccess(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onResultFail(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    TRLocationListener.OnAsyncResult locAsyncResult = new TRLocationListener.OnAsyncResult() {
        @Override
        public void onResultSuccess(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onResultFail(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == btnIngresar){
            Intent i = new Intent(MainActivity.this, Activity_listadoPqr.class);
            startActivity(i);
        }
    }
}
