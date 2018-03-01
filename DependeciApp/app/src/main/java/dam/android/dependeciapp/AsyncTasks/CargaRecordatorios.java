package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.Pojo.Usuario;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 16/02/2018.
 */

public class CargaRecordatorios extends AsyncTask<Integer, Void, Boolean> implements Comparator<Recordatorio> {

    private List<Recordatorio> recordatorioList;
    private Context context;
    private int idUsuario;
    private final int CONNECTION_TIMEOUT = 15000;
    private final int READ_TIMEOUT = 10000;

    public CargaRecordatorios(List<Recordatorio> lista, Context context) {
        recordatorioList = lista;
        this.context = context;
        this.idUsuario = idUsuario;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean result = false;
        int id = integers[0];
        HttpURLConnection urlCon = null;
        try {
            URL ul = new URL("http://adriancsbna.hopto.org/Scripts/obten_recordatorios.php?id=" + id);
            urlCon = (HttpURLConnection) ul.openConnection();
            urlCon.setRequestProperty("Connection", "close");
            urlCon.setConnectTimeout(CONNECTION_TIMEOUT);
            urlCon.setReadTimeout(READ_TIMEOUT);

            if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(context);
                db.vaciaTabla();
                String resultStream = Conexion.readStream(urlCon.getInputStream());
                JSONObject json = new JSONObject(resultStream);

                //JSONObject json2 = json.getJSONObject("personas");
                JSONArray jArray = json.getJSONArray("recordatorios");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject recordJsonSucio = jArray.getJSONObject(i);
                    JSONObject recordJson = recordJsonSucio.getJSONObject("recordatorio");
                    int idRecordatorio = recordJson.getInt("_id");
                    String titulo = recordJson.getString("titulo");
                    String contenido = recordJson.getString("contenido");
                    String fechaHora = recordJson.getString("fecha");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fecha = format.parse(fechaHora);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fecha);

                    fechaHora = calendar.getTime().toString();

                    //Partimos el String para trabjar con el
                    String[] fechaHoraArray = fechaHora.split(" ");
                    String[] horaArray = fechaHoraArray[3].split(":");

                    String hora = horaArray[0] + ":" + horaArray[1];
                    //creamos el recordatorio
                    Recordatorio r = new Recordatorio(idRecordatorio, titulo, contenido, hora, calendar.getTime());

                    //Establecemos una fecha para poder compararlos luego para ordenar la lista
                    db.insert(String.valueOf(r.id), r.titulo, r.content, fechaHora, r.hora);

                    recordatorioList.add(r);
                }
                Collections.sort(recordatorioList, this);


                result = true;

            } else {
                Log.i("URL", "ErrorCode: " + urlCon.getResponseCode());
            }

        } catch (IOException e) {
            Log.i("IOException", e.getMessage());
        } catch (JSONException e) {
            Log.i("JSONException", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (urlCon != null) urlCon.disconnect();
        }
        return result;
    }


    @Override
    public int compare(Recordatorio o1, Recordatorio o2) {
        if (o1.getFecha().before(o2.getFecha())) {
            return -1;
        } else if (o1.getFecha().after(o2.getFecha())) {
            return 1;
        } else {
            return 0;
        }
    }


}
