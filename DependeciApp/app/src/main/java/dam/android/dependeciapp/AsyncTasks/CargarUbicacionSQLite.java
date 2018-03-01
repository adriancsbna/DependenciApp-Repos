package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Ubicacion;

/**
 * Created by Tiodor on 20/02/2018.
 */

public class CargarUbicacionSQLite extends AsyncTask<Context,Void,Ubicacion> {

    @Override
    protected Ubicacion doInBackground(Context...contexts) {
        Ubicacion ubicacion = getLastLocationFromSQLite(contexts[0]);

        return ubicacion;
    }

    private Ubicacion getLastLocationFromSQLite(Context context){
        Ubicacion ubicacion = null;
        DependenciaDBManager.UbicacionesDBManager db = new DependenciaDBManager.UbicacionesDBManager(context);

        Cursor cursor = db.getRows();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                ubicacion = new Ubicacion(cursor);
                cursor.close();
            }
        } else {
            Log.e("NULL_LOCATION_CURSOR","El cursor del DB Manager de ubicaciones es nulo, la aplicación no consigue acceso a la base de datos interna");
        }

        return ubicacion;
    }
}
