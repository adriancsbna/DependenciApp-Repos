package dam.android.dependeciapp.AsyncTasks;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by adria on 08/02/2018.
 */

public class CreaConexion extends AsyncTask<String,Void,Connection> {

    @Override
    protected Connection doInBackground(String... strings) {
        try {
            //Registrando el Driver
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            String jdbcUrl = "jdbc:mysql://149.202.8.230:3306/proyecto1";
            //Conectando
            Properties pc = new Properties();
            pc.put("user", "grupo1");
            pc.put("password", "jatk1");
            Connection con = DriverManager.getConnection(jdbcUrl, pc);
            return con;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
