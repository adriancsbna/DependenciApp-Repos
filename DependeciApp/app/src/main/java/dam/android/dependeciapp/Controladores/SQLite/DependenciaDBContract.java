package dam.android.dependeciapp.Controladores.SQLite;

/**
 * Created by Tiodor on 22/01/2018.
 */

public final class DependenciaDBContract {

    public static final String DB_NAME = "DEPENDECIA.DB";

    public static final int DB_VERSION = 1;

    public static class RecordatoriosDBContract {

        public static final String TABLE_NAME = "RECORDATORIOS";
        public static final String _ID = "_id";
        public static final String TITULO = "titulo";
        public static final String CONTENIDO = "contenido";
        public static final String FECHA = "fecha";
        public static final String HORA = "hora";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + RecordatoriosDBContract.TABLE_NAME
                + " ("
                + RecordatoriosDBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RecordatoriosDBContract.TITULO + " TEXT NOT NULL, "
                + RecordatoriosDBContract.CONTENIDO + " REAL NOT NULL, "
                + RecordatoriosDBContract.FECHA + " TEXT NOT NULL, "
                + RecordatoriosDBContract.HORA + " TEXT NOT NULL "
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + RecordatoriosDBContract.TABLE_NAME;
   }

    public static class UbicacionesDBContract {

        public static final String TABLE_NAME = "UBICACIONES";

        public static final String _ID = "_id";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String DIRECCION = "direccion";


        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + UbicacionesDBContract.TABLE_NAME
                + " ("
                + UbicacionesDBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UbicacionesDBContract.LATITUD + " REAL NOT NULL, "
                + UbicacionesDBContract.LONGITUD + " REAL NOT NULL, "
                + UbicacionesDBContract.DIRECCION + " TEXT NOT NULL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + UbicacionesDBContract.TABLE_NAME;


    }

    public static class UsuarioDBContract {

        public static final String TABLE_NAME = "usuario";

        public static final String _ID = "_id";
        public static final String DNI = "dni";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDOS = "apellidos";
        public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
        public static final String GENERO = "genero";
        public static final String TIPO_DEPENDIENTE = "tipo_dependiente";
        public static final String FECHA_ALTA = "fecha_alta";
        public static final String PASS = "pass";


        public static final String CREATE_TABLE = "CREATE TABLE " + UsuarioDBContract.TABLE_NAME
                + " ("
                + UsuarioDBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsuarioDBContract.DNI + " TEXT NOT NULL, "
                + UsuarioDBContract.NOMBRE + " TEXT NOT NULL, "
                + UsuarioDBContract.APELLIDOS + " TEXT NOT NULL, "
                + UsuarioDBContract.FECHA_NACIMIENTO + " TEXT NOT NULL, "
                + UsuarioDBContract.GENERO + " TEXT NOT NULL, "
                + UsuarioDBContract.TIPO_DEPENDIENTE + " TEXT NOT NULL, "
                + UsuarioDBContract.FECHA_ALTA + " TEXT NOT NULL, "
                + UsuarioDBContract.PASS+" TEXT NOT NULL "
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + UsuarioDBContract.TABLE_NAME;
        public static final String EMPTY_TABLE ="DELETE FROM "+TABLE_NAME;
    }
}
