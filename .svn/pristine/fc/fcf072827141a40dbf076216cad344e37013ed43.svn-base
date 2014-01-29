package com.bpmco.tramitefacil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cristian Cantillo on 1/01/14.
 */
public class FooterTab implements View.OnClickListener
{
    private Activity a = null;
    Button registro = null;
    Button seguimiento = null;
    Button config = null;
    Button acerca = null;
    String activeAct = "";

    public FooterTab(Activity a) {
        this.a = a;
        registro = (Button)a.findViewById(R.id.tab1);
        seguimiento = (Button)a.findViewById(R.id.tab2);
        config = (Button)a.findViewById(R.id.tab3);
        acerca = (Button)a.findViewById(R.id.tab4);

        registro.setOnClickListener(this);
        seguimiento.setOnClickListener(this);
        config.setOnClickListener(this);
        acerca.setOnClickListener(this);

        activeAct = a.getLocalClassName();
    }

    public void accionFooter(Button btnFooter){
        Intent i = null;

        if(btnFooter.getId() == R.id.tab1 && !activeAct.equals("Activity_listadoPqr")){
            i = new Intent(a, Activity_listadoPqr.class);
        }else  if(btnFooter.getId() == R.id.tab2 && !activeAct.equals("Activity_MisPqr")){
            i = new Intent(a, Activity_MisPqr.class);
        }else  if(btnFooter.getId() == R.id.tab3 && !activeAct.equals("Activity_Config")){
            i = new Intent(a, Activity_Config.class);
        }else if(btnFooter.getId() == R.id.tab4 && !activeAct.equals("Activity_AcercaDe")){
            i = new Intent(a, Activity_AcercaDe.class);
        }

        if(i != null)
            a.startActivity(i);
    }

    @Override
    public void onClick(View view) {
        accionFooter((Button)view);
    }
}
