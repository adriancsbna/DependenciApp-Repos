package dam.android.dependeciapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dam.android.dependeciapp.Controladores.Conexion;

/**
 * Created by adria on 20/02/2018.
 */

public class RecordatorioTerminado extends AsyncTask<Integer, Void, Boolean> {
    private final int CONNECTION_TIMEOUT = 2000;
    private final int READ_TIMEOUT = 2000;

    @Override
    protected Boolean doInBackground(Integer... integers) {
        HttpURLConnection urlCon = null;
        int idRecordatorio = integers[0];
        try {
            URL ul = new URL("http://casabuenapps.hopto.org/Scripts/tarea_terminada.php?id=" + idRecordatorio);
            urlCon = (HttpURLConnection) ul.openConnection();
            urlCon.setRequestProperty("Connection", "close");
            urlCon.setConnectTimeout(CONNECTION_TIMEOUT);
            urlCon.setReadTimeout(READ_TIMEOUT);

            if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}