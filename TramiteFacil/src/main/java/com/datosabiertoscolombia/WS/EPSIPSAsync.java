package com.datosabiertoscolombia.WS;

import android.os.AsyncTask;
import android.content.Context;

import com.bpmco.tramitefacil.DTO.Contexto;
import com.bpmco.tramitefacil.DTO.ElementoOpcion;
import com.bpmco.tramitefacil.Database.DatabaseHandler;
import com.bpmco.tramitefacil.R;
import com.emil.android.util.Connectivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgonzalez on 15/12/13.
 */
public class EPSIPSAsync extends AsyncTask<Object, Void, Void> {

    public interface OnAsyncResult {
        public abstract void onResultSuccess(int resultCode, String message);
        public abstract void onResultFail(int resultCode, String message);
    }

    DatabaseHandler manejador = null;
    OnAsyncResult onAsyncResult;
    public void setOnResultListener(OnAsyncResult onAsyncResult) {
        if (onAsyncResult != null) {
            this.onAsyncResult = onAsyncResult;
        }
    }

    @Override
    protected Void  doInBackground(Object... params) {
        Context context = (Context)params[0];
        if (onAsyncResult != null) {
            try
            {
                manejador = DatabaseHandler.getInstance();
                Contexto contexto = manejador.getHandlerContexto().getContexto();

                if(contexto.getConexionType().equals(Contexto.CONN_TYPE.WIFI.toString())
                        && !Connectivity.isConnectedWifi(context)){
                    onAsyncResult.onResultFail(1, context.getString(R.string.message_sync_error_nowifi));
                    return null;
                }

            if(contexto.getConexionType().equals(Contexto.CONN_TYPE.WIFI_MOVIL.toString())
                    && !Connectivity.isConnected(context)){
                onAsyncResult.onResultFail(1
                        , context.getString(R.string.message_sync_error_nomovil));
                return null;
            }


            EPSIPS ei = new EPSIPS();
            List<com.datosabiertoscolombia.DTO.EPSIPS> lst = ei.sync();

            int i = 0;
            List<ElementoOpcion> opciones = new ArrayList<ElementoOpcion>();
            for (com.datosabiertoscolombia.DTO.EPSIPS item : lst)
            {
                ElementoOpcion opcion = new ElementoOpcion();
                opcion.setGrupo(item.getTipo());
                opcion.setValor(item.getNombre());
                opcion.setTexto(item.getNombre());

                opcion.setOrden(i++);
                opciones.add(opcion);
            }
            int recs = 0;
            if(lst.size()>0)
            {
                DatabaseHandler dbHand = DatabaseHandler.getInstance();
                dbHand.getHandlerElementoOpcion().delete("EPS");
                dbHand.getHandlerElementoOpcion().delete("IPS");
                recs = dbHand.getHandlerElementoOpcion().createOptions(opciones);
            }
            onAsyncResult.onResultSuccess(0, "EPS IPS:" + lst.size() + " db:" + recs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            onAsyncResult.onResultFail(2, "Error no definido");
        }
        }
        return null;
    }

    protected void onProgressUpdate() {
    }

    protected void onPostExecute() {
    }
}
