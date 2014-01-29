package com.bpmco.tramitefacil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpmco.tramitefacil.DTO.Contexto;
import com.bpmco.tramitefacil.DTO.Elemento;
import com.bpmco.tramitefacil.DTO.Registro;
import com.bpmco.tramitefacil.DTO.Respuesta;
import com.bpmco.tramitefacil.Database.DatabaseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_form21 extends Activity implements View.OnClickListener {


    LinearLayout lytppal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form21);

        PilaActividades.getInstance().addActividades(this);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();

        lytppal = (LinearLayout)findViewById(R.id.lytppal);
        lytppal.setOnClickListener(this);

        String tramite = contexto.getTraId();

        if(tramite == null || tramite.equals("")){
            Intent i = new Intent(Activity_form21.this, Activity_listadoPqr.class);
            startActivityForResult(i, 1);
        }


        TextView titulo = (TextView)findViewById(R.id.txtcabecera);
        titulo.setText(this.getString(R.string.title_activity_activity_form21));

        RadioGroup rg = (RadioGroup) findViewById(R.id.rbarriovereda);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Spinner spinner = (Spinner)findViewById(R.id.spbarriosvereda);
                ArrayList<String> spinnerArray = new ArrayList<String>();
                switch(checkedId)
                {
                    case R.id.barrio:
                        spinnerArray = new ArrayList( Arrays.asList(getResources().getStringArray(R.array.barrios)));
                        ArrayAdapter<String> adapterBarrio = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, spinnerArray);
                        spinner.setAdapter(adapterBarrio);
                        break;
                    case R.id.vereda:
                        spinnerArray = new ArrayList( Arrays.asList(getResources().getStringArray(R.array.veredas)));
                        ArrayAdapter<String> adapterVereda = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, spinnerArray);
                        spinner.setAdapter(adapterVereda);
                        break;
                }
            }
        });

        ImageButton btnAtras = (ImageButton)findViewById(R.id.btnAtrasIco);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos(true);
                finish();
            }
        });

        ImageButton btnHome = (ImageButton)findViewById(R.id.btnHomeIco);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_form21.this, Activity_listadoPqr.class);
                startActivity(i);
            }
        });

        Button botonSiguiente = (Button)findViewById(R.id.btnSiguiente);
        String lblNext =
                this.getString(R.string.label_boton_siguiente
                        , "2"
                        , Integer.parseInt(contexto.getMaxPage())+1);
        botonSiguiente.setText(lblNext);
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guardarDatos(false))
                {
                    Intent i = new Intent(getApplicationContext(), Activity_Adjuntar.class);
                    startActivityForResult(i, 1);
                }
            }
        });

        cargarDatosGuardados();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            guardarDatos(true);
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        if(view == lytppal) {
            hideSoftKeyboard();
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    private void cargarDatosGuardados() {

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();


        if(!contexto.getTraId().equals("")){

            String tramite = contexto.getTraId();
            String idRegistro = contexto.getRegId();

            List<Elemento> listaElementos = handler.getHandlerElemento().getElementos("0", tramite, idRegistro);

            for(int i =0;i<listaElementos.size();i++)
            {
                String id = listaElementos.get(i).getId();
                String valor = listaElementos.get(i).getRepuesta();


                if(id.equals("19")){
                    EditText txtLugar = (EditText)findViewById(R.id.txtlugar);
                    txtLugar.setText(valor);
                }

                if(id.equals("20")){

                    RadioButton btnBarrio = (RadioButton)findViewById(R.id.barrio);
                    RadioButton btnVereda = (RadioButton)findViewById(R.id.vereda);

                    if(btnBarrio.getText().equals(valor)){  btnBarrio.setChecked(true); }
                    if(btnVereda.getText().equals(valor)){  btnVereda.setChecked(true); }
                }

                if(id.equals("21")){
                    Spinner spn = (Spinner)findViewById(R.id.spbarriosvereda);
                    ArrayAdapter myAdap = (ArrayAdapter)spn.getAdapter();
                    int spinnerPosition = myAdap.getPosition(valor);
                    spn.setSelection(spinnerPosition);
                }


                if(id.equals("22")){
                    EditText motivo = (EditText)findViewById(R.id.txtMotivo);
                    motivo.setText(valor);
                }
            }

        }
    }

    public boolean guardarDatos(Boolean back)
    {

        EditText txtMotivo = (EditText)findViewById(R.id.txtMotivo);
        EditText txtLugar = (EditText)findViewById(R.id.txtlugar);

        RadioGroup rg = (RadioGroup)findViewById(R.id.rbarriovereda);
        int selected = rg.getCheckedRadioButtonId();
        RadioButton radioSelected = (RadioButton)findViewById(selected);

        Spinner spRadioVereda = (Spinner)findViewById(R.id.spbarriosvereda);

        String sbarrioVereda = spRadioVereda.getSelectedItem().toString();
        String rbarrioVereda = radioSelected.getText().toString();
        String lugarProblema = txtLugar.getText().toString();
        String Motivo = txtMotivo.getText().toString();

        if(!back)
        {
            if(lugarProblema.equals("") || Motivo.equals("") || sbarrioVereda.equals("Seleccione")){
                Toast.makeText(this, "Llene todos los datos faltantes", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();

        handler.getHandlerElementoRespuesta().checkPageData("19,20,21,22,23");

        Registro registro = new Registro();
        registro.setTraId(Integer.parseInt(contexto.getTraId()));
        registro.setCiuId(Integer.parseInt(contexto.getCiuId()));

        String idRegistro = contexto.getRegId();

        if(idRegistro == null || idRegistro.equals("")){
            idRegistro = handler.getHandlerRegistro().createRegistro(registro);//Crear Registro
            contexto.setRegId(idRegistro);
            handler.getHandlerContexto().guardarContexto(contexto);
        }

        Respuesta respuesta = new Respuesta();

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("19"));
        respuesta.setResValor(lugarProblema);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("20"));
        respuesta.setResValor(rbarrioVereda);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("21"));
        respuesta.setResValor(sbarrioVereda);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);


        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("22"));
        respuesta.setResValor(Motivo);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_form21, menu);
        return true;
    }
    
}
