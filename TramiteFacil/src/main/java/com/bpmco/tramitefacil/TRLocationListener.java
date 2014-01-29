package com.bpmco.tramitefacil;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bpmco.tramitefacil.DTO.Contexto;
import com.bpmco.tramitefacil.Database.DatabaseHandler;

/**
 * Created by jgonzalez on 18/10/13.
 * http://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android
 */
public class TRLocationListener extends AsyncTask<Object, Void, Void> implements LocationListener {
    OnAsyncResult onAsyncResult;
    private DatabaseHandler manejador = null;
    private Context context;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected Void doInBackground(Object... params) {
        start((Context)params[0]);
        return null;
    }

    public interface OnAsyncResult {
        public abstract void onResultSuccess(int resultCode, String message);
        public abstract void onResultFail(int resultCode, String message);
    }

    public void setOnResultListener(OnAsyncResult onAsyncResult) {
        if (onAsyncResult != null) {
            this.onAsyncResult = onAsyncResult;
        }
    }

    protected void onProgressUpdate() {
    }

    protected void onPostExecute() {
    }

    public  TRLocationListener(){

    }

    public void start(Context context){
        try{
            manejador = DatabaseHandler.getInstance();

            this.context = context;
            locationManager = (LocationManager)this.context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);

            // Initialize the location fields
            if (location != null) {
                onLocationChanged(location);
            }
        }catch (Exception e){
            e.printStackTrace();
            onAsyncResult.onResultFail(1, e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Contexto contexto = manejador.getHandlerContexto().getContexto();
        contexto.setGeo(location.getLatitude()+","+location.getLongitude()+":"+location.getAccuracy());
        manejador.getHandlerContexto().guardarContexto(contexto);
        onAsyncResult.onResultSuccess(0, location.getLatitude()+","+location.getLongitude()+":"+location.getAccuracy());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
