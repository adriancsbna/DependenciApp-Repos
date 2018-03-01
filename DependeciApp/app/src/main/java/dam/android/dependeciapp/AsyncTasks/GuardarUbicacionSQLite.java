package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;

/**
 * Created by Tiodor on 20/02/2018.
 */

public class GuardarUbicacionSQLite extends AsyncTask{

    private Context applicationContext;
    private LatLng locationLatLng;
    private String addressLine;

    public GuardarUbicacionSQLite(Context context, LatLng latLng, String address){
        applicationContext = context;
        locationLatLng = latLng;
        addressLine = address;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        saveCurrentLocationInSQLite();

        return null;
    }

    private void saveCurrentLocationInSQLite() {
        DependenciaDBManager.UbicacionesDBManager db = new DependenciaDBManager.UbicacionesDBManager(applicationContext);
        Cursor cursor = db.getRows();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                db.update(1, locationLatLng.latitude, locationLatLng.longitude, addressLine);
            } else {
                db.insert(locationLatLng.latitude, locationLatLng.longitude, addressLine);
            }
        } else {
            Log.e("NULL_LOCATION_CURSOR","El cursor del DB Manager de ubicaciones es nulo, la aplicación no consigue acceso a la base de datos interna");
        }
    }
}
