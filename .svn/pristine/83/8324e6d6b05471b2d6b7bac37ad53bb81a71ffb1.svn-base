package com.datosabiertoscolombia.WS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgonzalez on 15/12/13.
 */
public class BarrioVereda {
    private static String url = "http://servicedatosabiertoscolombia.cloudapp.net/v1/Alcaldia_de_Manizales/barriosyveredasmanizales?$format=json";
    private static final String TAG_BARRIOVEREDAS = "d";
    private static final String TAG_PARTITIONKEY = "PartitionKey";
    private static final String TAG_ROWKEY = "RowKey";
    private static final String TAG_CODIGO = "codigo";
    private static final String TAG_TIPO = "tipo";
    private static final String TAG_NOMBRE = "nombre";


    public List<com.datosabiertoscolombia.DTO.BarrioVereda> sync()
    {
        List<com.datosabiertoscolombia.DTO.BarrioVereda> lst = new ArrayList<com.datosabiertoscolombia.DTO.BarrioVereda>();

        try {
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            JSONArray barriosveredas = json.getJSONArray(TAG_BARRIOVEREDAS);

            for(int i = 0; i < barriosveredas.length(); i++){
                JSONObject c = barriosveredas.getJSONObject(i);

                com.datosabiertoscolombia.DTO.BarrioVereda bv = new com.datosabiertoscolombia.DTO.BarrioVereda();
                bv.setPartitionKey(c.getString(TAG_PARTITIONKEY));
                bv.setRowKey(c.getString(TAG_ROWKEY));
                bv.setCodigo(c.getString(TAG_CODIGO));
                bv.setTipo(c.getString(TAG_TIPO));
                bv.setNombre(c.getString(TAG_NOMBRE));

                lst.add(bv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;
    }
}
