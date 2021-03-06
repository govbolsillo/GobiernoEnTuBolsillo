package com.bpmco.tramitefacil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.Wsdl2Code.WebServices.pqrdService.Syncronize;
import com.bpmco.tramitefacil.DTO.Contexto;
import com.bpmco.tramitefacil.Database.DatabaseHandler;
import com.datosabiertoscolombia.WS.BarrioVeredaAsync;
import com.datosabiertoscolombia.WS.EPSIPSAsync;

public class Activity_Config extends Activity implements View.OnClickListener {
    private DatabaseHandler manejador;
    private Contexto contexto;

    public TextView txtActividadTitulo;
    public ImageButton btnAtras;
    public ImageButton btnHome;
    //public Button btnMisPQR;
    //public Button btnAcerca;
    FooterTab tab = null;


    ImageView btnBarrioVeredaSync = null;
    ImageView btnEPSIPSSync = null;

    BarrioVeredaAsync syncBarrioVereda;
    EPSIPSAsync syncEPSIPS;
    ProgressDialog syncProgress;

    Syncronize sync;
    TextView lblFecha;
    ImageView btnSync = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        prepareNavigationButtons();

        PilaActividades.getInstance().addActividades(this);

        tab = new FooterTab(this);

        btnSync = (ImageView)findViewById(R.id.btnSync);
        lblFecha = (TextView)findViewById(R.id.lblFechaSinc);


        Button btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSyncProgress(getString(R.string.message_progress_sync_barriosveredas));
                syncBarrioVereda = new BarrioVeredaAsync();
                syncBarrioVereda.setOnResultListener(resBarrioVeredaAsync);
                syncBarrioVereda.execute(getApplicationContext());
                syncEPSIPS = new EPSIPSAsync();
                syncEPSIPS.setOnResultListener(resEPSIPSAsync);
                syncEPSIPS.execute(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Sincronizado", Toast.LENGTH_LONG).show();
            }

        });


        try{
            manejador = DatabaseHandler.getInstance();
            contexto = manejador.getHandlerContexto().getContexto();

            viewLastSync();

            RadioButton rbWIFI = (RadioButton) findViewById(R.id.conn_wifi);
            RadioButton rbMovil = (RadioButton) findViewById(R.id.conn_movil);

            if(contexto.getConexionType().equals(Contexto.CONN_TYPE.WIFI.toString()))
                rbWIFI.setChecked(true);
            else
                rbMovil.setChecked(true);

            RadioGroup rgConn = (RadioGroup) findViewById(R.id.conectividad);
            rgConn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.conn_wifi:
                            contexto.setConexionType(Contexto.CONN_TYPE.WIFI.toString());
                            break;
                        case R.id.conn_movil:
                            contexto.setConexionType(Contexto.CONN_TYPE.WIFI_MOVIL.toString());
                            break;
                    }
                    manejador.getHandlerContexto().guardarContexto(contexto);
                    Toast.makeText(getApplicationContext(),getString(R.string.msj_conf_conexion), 2000).show();
                }
            });

        }catch (Exception e){
            //TODO: strings
            Toast.makeText(this, "No se Puede Crear o Abrir la Base de Datos", 2000).show();
            e.printStackTrace();
        }
        btnSync.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity__config, menu);
        return true;
    }

    private void prepareNavigationButtons() {
        txtActividadTitulo = (TextView)findViewById(R.id.txtcabecera);
        btnAtras = (ImageButton)findViewById(R.id.btnAtrasIco);
        btnHome = (ImageButton)findViewById(R.id.btnHomeIco);

        btnAtras.setVisibility(View.GONE);
        btnHome.setVisibility(View.GONE);

        //btnMisPQR = (Button)findViewById(R.id.btnMisPqr);
        //btnAcerca = (Button)findViewById(R.id.btnAcercaDe);

        //btnBarrioVeredaSync = (ImageView)findViewById(R.id.btnBarrioVeredaSync);
        //btnEPSIPSSync = (ImageView)findViewById(R.id.btnEPSIPSSync);

        txtActividadTitulo.setText(getString(R.string.title_activity_activity__config));
        btnAtras.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        //btnMisPQR.setOnClickListener(this);
        //btnAcerca.setOnClickListener(this);

        //btnBarrioVeredaSync.setOnClickListener(this);
        //btnEPSIPSSync.setOnClickListener(this);
    }

    private void viewLastSync() {
        contexto = manejador.getHandlerContexto().getContexto();
        String ultimaSync = Utilidades.date2string(contexto.getLastSync(), "dd/MM/yyyy HH:mm:ss");
        lblFecha.setText(ultimaSync);
    }

    Syncronize.OnAsyncResult asynResult = new Syncronize.OnAsyncResult() {
        @Override
        public void onResultSuccess(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    syncProgress.cancel();
                    viewLastSync();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onResultFail(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    syncProgress.cancel();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    @Override
    public void onClick(View view) {
        if(view == btnAtras || view == btnHome){
            finish();
        }
        /*else if(view == btnMisPQR){
            Intent i = new Intent(Activity_Config.this, Activity_MisPqr.class);
            startActivity(i);
        }
        else if(view == btnAcerca){
            Intent i = new Intent(Activity_Config.this, Activity_AcercaDe.class);
            startActivity(i);
        }*/
        else if(view == btnSync){
            sync = new Syncronize();
            sync.setOnResultListener(asynResult);
            sync.execute(this.getApplicationContext());
            viewSynProgress();
        }else if(view == btnBarrioVeredaSync){
            syncBarrioVereda = new BarrioVeredaAsync();
            syncBarrioVereda.setOnResultListener(resBarrioVeredaAsync);
            syncBarrioVereda.execute(this.getApplicationContext());
            viewSyncProgress(this.getString(R.string.message_progress_sync_barriosveredas));
        }
        else if(view == btnEPSIPSSync){
            syncEPSIPS = new EPSIPSAsync();
            syncEPSIPS.setOnResultListener(resEPSIPSAsync);
            syncEPSIPS.execute(this.getApplicationContext());
            viewSyncProgress(this.getString(R.string.message_progress_sync_epsips));
        }
    }


    public void viewSynProgress(){
        syncProgress = new ProgressDialog(Activity_Config.this);
        syncProgress.setTitle(this.getString(R.string.title_progress_sync));
        syncProgress.setMessage(this.getString(R.string.message_progress_sync));
        syncProgress.setIndeterminate(true);
        syncProgress.setCancelable(false);
        syncProgress.show();
    }

    public void viewSyncProgress(String message){
        syncProgress = new ProgressDialog(Activity_Config.this);
        syncProgress.setTitle(this.getString(R.string.title_progress_sync));
        syncProgress.setMessage(message);
        syncProgress.setIndeterminate(true);
        syncProgress.setCancelable(false);
        syncProgress.show();
    }

    BarrioVeredaAsync.OnAsyncResult resBarrioVeredaAsync = new BarrioVeredaAsync.OnAsyncResult() {
        @Override
        public void onResultSuccess(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    syncProgress.cancel();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onResultFail(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    syncProgress.cancel();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    EPSIPSAsync.OnAsyncResult resEPSIPSAsync = new EPSIPSAsync.OnAsyncResult() {
        @Override
        public void onResultSuccess(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    syncProgress.cancel();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onResultFail(final int resultCode, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    syncProgress.cancel();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    
}
