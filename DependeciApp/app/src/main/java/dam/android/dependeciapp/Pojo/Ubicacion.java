package dam.android.dependeciapp.Pojo;

import android.database.Cursor;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Tiodor on 20/02/2018.
 */

public class Ubicacion implements Serializable {

    private int ubicacionId;
    private double latitud;
    private double longitud;
    private String direccion;

    public Ubicacion(Cursor cursor){
        ubicacionId = cursor.getInt(0);
        latitud = cursor.getDouble(1);
        longitud = cursor.getDouble(2);
        direccion = cursor.getString(3);
    }

    public int getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(int ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
