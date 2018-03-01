package dam.android.dependeciapp.Pojo;

import android.content.Context;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dam.android.dependeciapp.R;

/**
 * Created by adria on 12/02/2018.
 */

public class Recordatorio implements Serializable {

    public final int id;
    public final String titulo;
    public final String content;
    public final String hora;
    public final Date fecha;

    public Recordatorio(int id, String titulo, String content, String hora,Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.content = content;
        this.hora = hora;
        this.fecha=fecha;
    }

    public Date getFecha() {
        return fecha;
    }
    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getContent() {
        return content;
    }
    public String getHora() {
        return hora;
    }



    public static String obtrenCuando(Context context, Recordatorio recordatorio) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(recordatorio.getFecha());
        //A partir de la fecha, obtenemos si es Hoy o Mañana
        String cuando = obtenHoyoMañana(context, calendar.getTime());
        String fechaHora = calendar.getTime().toString();
        String[] fechaHoraArray = fechaHora.split(" ");
        //Si no fuera ni hoy ni mañana, se pone el nombre del dia de la semana
        if (cuando == null)
            cuando = Recordatorio.obtenDiaTexto(context, fechaHoraArray[0]);
        return cuando;
    }

    private static String obtenDiaTexto(Context context, String day) {
        String[] dias = context.getResources().getStringArray(R.array.dias);
        switch (day) {
            case "Mon":
                return dias[0];
            case "Tue":
                return dias[1];
            case "Wed":
                return dias[2];
            case "Thu":
                return dias[3];
            case "Fry":
                return dias[4];
            case "Sat":
                return dias[5];
            case "Sun":
                return dias[6];
        }
        return day;
    }

    //Introduces una fecha y te devuelve si es hoy o mañana. Si no lo es devuelve Null
    private static String obtenHoyoMañana(Context context, Date toma) {
        Date actual = new Date();
        String[] dias = context.getResources().getStringArray(R.array.dias);
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyy");
        String actualString = dt.format(actual);
        String tomaString = dt.format(toma);
        //Si la fecha es igual a la actual, se devuelve HOY
        if (actualString.equalsIgnoreCase(tomaString))
            return dias[7];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actual);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        actual = calendar.getTime();
        actualString = dt.format(actual);
        //Si es igual a un dia mas al de hoy se devuelve MAÑANA
        if (actualString.equalsIgnoreCase(tomaString))
            return dias[8];
        return null;
    }
}