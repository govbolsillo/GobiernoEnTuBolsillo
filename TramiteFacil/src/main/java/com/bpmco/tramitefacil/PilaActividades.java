package com.bpmco.tramitefacil;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Cristian Cantillo on 21/11/13.
 */
public class PilaActividades implements Cloneable {
    private static final ArrayList<Activity> actividades = new ArrayList<Activity>();
    private static PilaActividades instance = null;

    public PilaActividades() {}


    private synchronized static void createInstance() {
        instance = new PilaActividades();
    }

    public static PilaActividades getInstance(){
        if(instance == null){
            createInstance();
        }

        return instance;
    }

    public static void addActividades(Activity actividad){
        actividades.add(actividad);
    }

    public static void finalizarActividades(){
        for(int i = 0; i < actividades.size(); i++){
            actividades.get(i).finish();
        }
    }
}
