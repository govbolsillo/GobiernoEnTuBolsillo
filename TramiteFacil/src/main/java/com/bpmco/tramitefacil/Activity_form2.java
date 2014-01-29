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
import com.bpmco.tramitefacil.DTO.Respuesta;
import com.bpmco.tramitefacil.Database.DatabaseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_form2 extends Activity implements View.OnClickListener{


    LinearLayout lytppal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form2);

        PilaActividades.getInstance().addActividades(this);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();

        lytppal = (LinearLayout)findViewById(R.id.lytppal);
        lytppal.setOnClickListener(this);

        String tramite = contexto.getTraId();

        if(tramite.equals("")){
            Intent i = new Intent(Activity_form2.this, Activity_listadoPqr.class);
            startActivityForResult(i, 1);
        }


        TextView titulo = (TextView)findViewById(R.id.txtcabecera);
        titulo.setText(this.getString(R.string.title_activity_activity_form2));

        Button botonSiguiente = (Button)findViewById(R.id.btnSiguiente);
        String lblNext =
                this.getString(R.string.label_boton_siguiente
                        , "3"
                        , Integer.parseInt(contexto.getMaxPage())+1);
        botonSiguiente.setText(lblNext);

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guardarDatos(false))
                {
                    Intent i = new Intent(Activity_form2.this, Activity_Adjuntar.class);
                    startActivityForResult(i, 1);
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
                Intent i = new Intent(Activity_form2.this, Activity_listadoPqr.class);
                startActivity(i);
            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.repsips);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Spinner spinner = (Spinner)findViewById(R.id.spEPSIPS);
                ArrayList<String> spinnerArray = new ArrayList<String>();

                EditText txtEntidadGeneradora = null;
                TextView txtLblEntidad = null;

                switch(checkedId)
                {
                    case R.id.eps:
                        spinnerArray = new ArrayList( Arrays.asList(getResources().getStringArray(R.array.EPS)));
                        ArrayAdapter<String> adapterBarrio = new ArrayAdapter<String>(getApplicationContext(),  R.layout.spinner_text, spinnerArray);
                        spinner.setAdapter(adapterBarrio);

                        txtEntidadGeneradora = (EditText)findViewById(R.id.txtNombreEntidadQueja);
                        txtEntidadGeneradora.setVisibility(View.GONE);
                        txtLblEntidad = (TextView)findViewById(R.id.txtLblEntidad);
                        txtLblEntidad.setVisibility(View.GONE);


                        findViewById(R.id.txtSelEPSIPS).setVisibility(View.VISIBLE);
                        findViewById(R.id.spEPSIPS).setVisibility(View.VISIBLE);

                        break;
                    case R.id.ips:
                        spinnerArray = new ArrayList( Arrays.asList(getResources().getStringArray(R.array.IPS)));
                        ArrayAdapter<String> adapterVereda = new ArrayAdapter<String>(getApplicationContext(),  R.layout.spinner_text, spinnerArray);
                        spinner.setAdapter(adapterVereda);

                        txtEntidadGeneradora = (EditText)findViewById(R.id.txtNombreEntidadQueja);
                        txtEntidadGeneradora.setVisibility(View.GONE);
                        txtLblEntidad = (TextView)findViewById(R.id.txtLblEntidad);
                        txtLblEntidad.setVisibility(View.GONE);

                        findViewById(R.id.txtSelEPSIPS).setVisibility(View.VISIBLE);
                        findViewById(R.id.spEPSIPS).setVisibility(View.VISIBLE);

                        break;
                    case R.id.otro:
                        txtEntidadGeneradora = (EditText)findViewById(R.id.txtNombreEntidadQueja);
                        txtEntidadGeneradora.setVisibility(View.VISIBLE);
                        txtLblEntidad = (TextView)findViewById(R.id.txtLblEntidad);
                        txtLblEntidad.setVisibility(View.VISIBLE);

                        findViewById(R.id.txtSelEPSIPS).setVisibility(View.GONE);
                        findViewById(R.id.spEPSIPS).setVisibility(View.GONE);
                        break;
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

            String pagina = "2";
            String tramite = contexto.getTraId();
            String idRegistro = contexto.getRegId();

            List<Elemento> listaElementos = handler.getHandlerElemento().getElementos("0", tramite, idRegistro);

            for(int i =0;i<listaElementos.size();i++)
            {
                String id = listaElementos.get(i).getId();
                String valor = listaElementos.get(i).getRepuesta();

                if(id.equals("10")){
                    RadioButton btnEPS = (RadioButton)findViewById(R.id.eps);
                    RadioButton btnIPS = (RadioButton)findViewById(R.id.ips);
                    RadioButton btnOTRO = (RadioButton)findViewById(R.id.otro);

                    if(btnEPS.getText().equals(valor)) {  btnEPS.setChecked(true); }
                    if(btnIPS.getText().equals(valor)) {  btnIPS.setChecked(true); }
                    if(btnOTRO.getText().equals(valor)){  btnOTRO.setChecked(true);}
                }

                if(id.equals("11")){
                    Spinner spn = (Spinner)findViewById(R.id.spEPSIPS);
                    ArrayAdapter myAdap = (ArrayAdapter)spn.getAdapter();
                    int spinnerPosition = myAdap.getPosition(valor);
                    spn.setSelection(spinnerPosition);
                }

                if(id.equals("12")){
                    EditText nombreEntidad = (EditText)findViewById(R.id.txtNombreEntidadQueja);
                    nombreEntidad.setText(valor);
                }

                if(id.equals("13")){
                    Spinner spn = (Spinner)findViewById(R.id.spTipoQuejaSalud);
                    ArrayAdapter myAdap = (ArrayAdapter)spn.getAdapter();
                    int spinnerPosition = myAdap.getPosition(valor);
                    spn.setSelection(spinnerPosition);
                }

                if(id.equals("18")){
                    EditText motivo = (EditText)findViewById(R.id.txtMotivo);
                    motivo.setText(valor);
                }
            }

        }
    }

    public boolean guardarDatos(Boolean back)
    {
        RadioGroup rg = (RadioGroup)findViewById(R.id.repsips);
        int selected = rg.getCheckedRadioButtonId();
        RadioButton radioSelected = (RadioButton)findViewById(selected);


        Spinner spEntidad = (Spinner)findViewById(R.id.spEPSIPS);
        EditText txtNombreEntidad = (EditText)findViewById(R.id.txtNombreEntidadQueja);
        Spinner spTipoQueja = (Spinner)findViewById(R.id.spTipoQuejaSalud);
        EditText txtMotivo = (EditText)findViewById(R.id.txtMotivo);

        String motivo = txtMotivo.getText().toString();
        String tipoQueja = spTipoQueja.getSelectedItem().toString();
        String nombreEntidad = txtNombreEntidad.getText().toString();
        String stTipoEntidad = radioSelected.getText().toString();
        String entidad = spEntidad.getSelectedItem().toString();

        RadioButton btnOTRO = (RadioButton)findViewById(R.id.otro);

        if(!back)
        {
            if(btnOTRO.isChecked()){
                if(nombreEntidad.equals("")){
                    Toast.makeText(this, "Ingrese el nombre de la entidad generadora de la queja", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            else
            {

                if(entidad.equals("Seleccione")){
                    Toast.makeText(this, "Seleccion la entidad", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            if(motivo.equals("") || tipoQueja.equals("Seleccione") || stTipoEntidad.equals("Seleccione"))
            {
                Toast.makeText(this, "Llene o seleccione todos los datos faltantes", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();
        String idRegistro = contexto.getRegId();

        handler.getHandlerElementoRespuesta().checkPageData("10,11,12,13,18");

        Respuesta respuesta = new Respuesta();

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("10"));
        respuesta.setResValor(stTipoEntidad);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        if(!btnOTRO.isChecked()){
            respuesta.setRegId(idRegistro);
            respuesta.setEleId(String.valueOf("11"));
            respuesta.setResValor(entidad);

            handler.getHandlerElementoRespuesta().createRespuesta(respuesta);
        }
        else
        {
            respuesta.setRegId(idRegistro);
            respuesta.setEleId(String.valueOf("12"));
            respuesta.setResValor(nombreEntidad);

            handler.getHandlerElementoRespuesta().createRespuesta(respuesta);
        }

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("13"));
        respuesta.setResValor(tipoQueja);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("18"));
        respuesta.setResValor(motivo);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_form2, menu);
        return true;
    }


}
