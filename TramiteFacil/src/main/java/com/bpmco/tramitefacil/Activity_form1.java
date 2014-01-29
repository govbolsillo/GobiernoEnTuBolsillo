package com.bpmco.tramitefacil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpmco.tramitefacil.DTO.Ciudadano;
import com.bpmco.tramitefacil.DTO.Contexto;
import com.bpmco.tramitefacil.DTO.Elemento;
import com.bpmco.tramitefacil.DTO.Registro;
import com.bpmco.tramitefacil.DTO.Respuesta;
import com.bpmco.tramitefacil.Database.DatabaseHandler;
import com.bpmco.tramitefacil.Database.Validation;
import com.bpmco.tramitefacil.Utilidades;

import org.kobjects.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_form1 extends Activity implements View.OnClickListener {


    LinearLayout lytppal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form1);

        PilaActividades.getInstance().addActividades(this);

        lytppal = (LinearLayout)findViewById(R.id.lytppal);
        lytppal.setOnClickListener(this);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();

        String tramite = contexto.getTraId();

        if(tramite == null || tramite.equals("")){
            Intent i = new Intent(Activity_form1.this, Activity_listadoPqr.class);
            startActivityForResult(i, 1);
        }

        TextView titulo = (TextView)findViewById(R.id.txtcabecera);
        titulo.setText(this.getString(R.string.title_activity_activity_form1));

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

        Button botonSiguiente = (Button)findViewById(R.id.btnSiguiente);
        String lblNext =
                this.getString(R.string.label_boton_siguiente
                        , "2"
                        , Integer.parseInt(contexto.getMaxPage()) + 1);
        botonSiguiente.setText(lblNext);


        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guardarDatos(false))
                {
                    Intent i = new Intent(Activity_form1.this, Activity_form2.class);
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
                Intent i = new Intent(Activity_form1.this, Activity_listadoPqr.class);
                startActivity(i);
            }
        });


        EditText txtEdadAnio = (EditText)findViewById(R.id.txtEdadAnio);

        txtEdadAnio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                EditText text = (EditText)findViewById(R.id.txtEdadAnio);

                if(text.getText().toString().equals("")){
                    return;
                }

                Integer anios = Integer.parseInt(text.getText().toString());

                EditText EditTextMes = (EditText)findViewById(R.id.txtEdadMeses);
                TextView textMes = (TextView)findViewById(R.id.textMes);

                if(anios < 2)
                {
                    EditTextMes.setVisibility(View.VISIBLE);
                    textMes.setVisibility(View.VISIBLE);
                }
                else
                {
                    EditTextMes.setVisibility(View.GONE);
                    textMes.setVisibility(View.GONE);
                }
             }

        });



        Spinner spTipoDoc = (Spinner)findViewById(R.id.spTipoDoc);
        spTipoDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selected = adapterView.getItemAtPosition(i).toString();
                EditText noIden = (EditText)findViewById(R.id.txtIdenti);

                if(selected.equals("Pasaporte") || selected.equals("Cédula de Extranjería") || selected.equals("Registro Civil")){
                    noIden.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else{
                    noIden.setInputType(InputType.TYPE_CLASS_NUMBER);
                }

                //noIden.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        precargarDatosCiudadano();
        cargarDatosGuardados();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            guardarDatos(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cargarDatosGuardados() {

        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();

        if(!contexto.getTraId().equals("")){

            String pagina = "1";
            String tramite = contexto.getTraId();
            String idRegistro = contexto.getRegId();

            List<Elemento> listaElementos = handler.getHandlerElemento().getElementos("0", tramite, idRegistro);

            for(int i =0;i<listaElementos.size();i++)
            {
                String id = listaElementos.get(i).getId();
                String valor = listaElementos.get(i).getRepuesta();

                if(id.equals("1")){
                    EditText lugar = (EditText)findViewById(R.id.txtlugar);
                    lugar.setText(valor);
                }

                if(id.equals("2")){

                    RadioButton btnBarrio = (RadioButton)findViewById(R.id.barrio);
                    RadioButton btnVereda = (RadioButton)findViewById(R.id.vereda);

                    if(btnBarrio.getText().equals(valor)){  btnBarrio.setChecked(true); }
                    if(btnVereda.getText().equals(valor)){  btnVereda.setChecked(true); }
                }

                if(id.equals("3")){
                    Spinner spn = (Spinner)findViewById(R.id.spbarriosvereda);
                    ArrayAdapter myAdap = (ArrayAdapter)spn.getAdapter();
                    int spinnerPosition = myAdap.getPosition(valor);
                    spn.setSelection(spinnerPosition);
                }

                if(id.equals("4")){
                    EditText edadAnio = (EditText)findViewById(R.id.txtEdadAnio);
                    edadAnio.setText(valor);
                }


                if(id.equals("5")){
                    if(valor != null && !valor.isEmpty())
                    {
                        EditText edadMes = (EditText)findViewById(R.id.txtEdadMeses);
                        edadMes.setText(valor);
                        edadMes.setVisibility(View.VISIBLE);
                    }
                }

                if(id.equals("7")){
                    Spinner spn = (Spinner)findViewById(R.id.spTipoDoc);
                    ArrayAdapter myAdap = (ArrayAdapter)spn.getAdapter();
                    int spinnerPosition = myAdap.getPosition(valor);
                    spn.setSelection(spinnerPosition);
                }

                if(id.equals("8")){
                    EditText noIdentif = (EditText)findViewById(R.id.txtIdenti);

                    if(!valor.isEmpty())
                    {
                        noIdentif.setText(valor);
                    }
                }

                if(id.equals("9")){
                    EditText nombreAfectado = (EditText)findViewById(R.id.txtNombreAfectado);
                    nombreAfectado.setText(valor);
                }



            }
        }
    }

    public void precargarDatosCiudadano()
    {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        Ciudadano ciudadano = handler.getHandlerCiudadano().getCiudadano(null);

        EditText txtIdenti = (EditText)findViewById(R.id.txtIdenti);

        if(ciudadano.getDocumento() != null && !ciudadano.getDocumento().isEmpty()){
            txtIdenti.setText(ciudadano.getDocumento());
        }

        EditText txtNombreAfectado = (EditText)findViewById(R.id.txtNombreAfectado);

        if(ciudadano.getNombres() != null){
            txtNombreAfectado.setText(ciudadano.getNombres());
        }

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

    public boolean guardarDatos(Boolean back)
    {
        EditText txtLugar = (EditText)findViewById(R.id.txtlugar);

        RadioGroup rg = (RadioGroup)findViewById(R.id.rbarriovereda);
        int selected = rg.getCheckedRadioButtonId();
        RadioButton radioSelected = (RadioButton)findViewById(selected);

        Spinner spRadioVereda = (Spinner)findViewById(R.id.spbarriosvereda);
        Spinner spTipoDoc = (Spinner)findViewById(R.id.spTipoDoc);

        EditText txtIdenti = (EditText)findViewById(R.id.txtIdenti);

        EditText txtNombreAfectado = (EditText)findViewById(R.id.txtNombreAfectado);
        EditText txtEdadAnio = (EditText)findViewById(R.id.txtEdadAnio);

        String edadMeses = "0";

        if(!txtEdadAnio.getText().toString().equals(""))
        {
            if(Integer.parseInt(txtEdadAnio.getText().toString()) < 2)
            {
                EditText txtEdadMeses = (EditText)findViewById(R.id.txtEdadMeses);
                edadMeses = txtEdadMeses.getText().toString();
            }
        }


        String edadAnio = txtEdadAnio.getText().toString();
        String nombreAfectado = txtNombreAfectado.getText().toString();
        String identificacion = txtIdenti.getText().toString();
        String sTipoDoc = spTipoDoc.getSelectedItem().toString();
        String sbarrioVereda = spRadioVereda.getSelectedItem().toString();
        String rbarrioVereda = radioSelected.getText().toString();
        String lugarProblema = txtLugar.getText().toString();


        //Si no se ha presionado el boton atras
        if(!back)
        {
            if(Integer.parseInt(edadMeses) > 12){
                Toast.makeText(this, "La edad en meses no puede ser mayor a 12", Toast.LENGTH_LONG).show();
                return false;
            }

            if(edadMeses.equals("") || edadAnio.equals("") || nombreAfectado.equals("") || identificacion.equals("")
          || sTipoDoc.equals("") || sbarrioVereda.equals("Seleccione") || rbarrioVereda.equals("") || lugarProblema.equals("")){

                    Toast.makeText(this, "Llene todos los datos faltantes", Toast.LENGTH_LONG).show();
                    return false;
            }

            if(Integer.parseInt(edadAnio) > 120){
                Toast.makeText(this, "La edad no puede ser Mayor a 120", Toast.LENGTH_LONG).show();
                return false;
            }

        }


        DatabaseHandler handler = DatabaseHandler.getInstance();
        Contexto contexto = handler.getHandlerContexto().getContexto();

        handler.getHandlerElementoRespuesta().checkPageData("1,2,3,4,5,6,7,8,9,19");

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
        respuesta.setEleId(String.valueOf("1"));
        respuesta.setResValor(lugarProblema);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("2"));
        respuesta.setResValor(rbarrioVereda);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("3"));
        respuesta.setResValor(sbarrioVereda);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("4"));
        respuesta.setResValor(edadAnio);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        if(!edadMeses.equals("0"))
        {
            respuesta.setRegId(idRegistro);
            respuesta.setEleId(String.valueOf("5"));
            respuesta.setResValor(edadMeses);

            handler.getHandlerElementoRespuesta().createRespuesta(respuesta);
        }

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("7"));
        respuesta.setResValor(sTipoDoc);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("8"));
        respuesta.setResValor(identificacion);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("9"));
        respuesta.setResValor(nombreAfectado);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);


        respuesta.setRegId(idRegistro);
        respuesta.setEleId(String.valueOf("20"));
        respuesta.setResValor(nombreAfectado);

        handler.getHandlerElementoRespuesta().createRespuesta(respuesta);

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_form1, menu);
        return true;
    }
    
}
