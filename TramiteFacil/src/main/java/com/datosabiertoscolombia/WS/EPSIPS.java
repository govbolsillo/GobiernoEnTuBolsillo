package com.datosabiertoscolombia.WS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgonzalez on 15/12/13.
 */
public class EPSIPS {
    private static String url = "http://servicedatosabiertoscolombia.cloudapp.net/v1/Alcaldia_de_Manizales/epsipsmanizales?$format=json";
    private static final String TAG_BARRIOVEREDAS = "d";
    private static final String TAG_PARTITIONKEY = "PartitionKey";
    private static final String TAG_ROWKEY = "RowKey";
    private static final String TAG_CODIGO = "codigo";
    private static final String TAG_TIPO = "tipo";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_DIRECCION = "direccion";
    private static final String TAG_TELEFONO = "telefono";


    public List<com.datosabiertoscolombia.DTO.EPSIPS> sync()
    {
        List<com.datosabiertoscolombia.DTO.EPSIPS> lst = new ArrayList<com.datosabiertoscolombia.DTO.EPSIPS>();

        try {
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            JSONArray barriosveredas = json.getJSONArray(TAG_BARRIOVEREDAS);

            for(int i = 0; i < barriosveredas.length(); i++){
                JSONObject c = barriosveredas.getJSONObject(i);

                com.datosabiertoscolombia.DTO.EPSIPS ei = new com.datosabiertoscolombia.DTO.EPSIPS();
                ei.setPartitionKey(c.getString(TAG_PARTITIONKEY));
                ei.setRowKey(c.getString(TAG_ROWKEY));
                ei.setCodigo(c.getString(TAG_CODIGO));
                ei.setTipo(c.getString(TAG_TIPO));
                ei.setNombre(c.getString(TAG_NOMBRE));
                ei.setDireccion(c.getString(TAG_DIRECCION));
                ei.setTelefono(c.getString(TAG_TELEFONO));

                lst.add(ei);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;
    }
}
