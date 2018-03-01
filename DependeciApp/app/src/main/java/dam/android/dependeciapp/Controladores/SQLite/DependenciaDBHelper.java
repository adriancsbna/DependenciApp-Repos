package dam.android.dependeciapp.Controladores.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tiodor on 22/01/2018.
 */

public class DependenciaDBHelper {

    public static class RecordatoriosDBHelper extends SQLiteOpenHelper{

        private static RecordatoriosDBHelper instanceDBHelper;

        public static synchronized RecordatoriosDBHelper getInstance(Context context) {
            if (instanceDBHelper == null) {
                instanceDBHelper = new RecordatoriosDBHelper(context.getApplicationContext());
            }
            return instanceDBHelper;
        }

        private RecordatoriosDBHelper(Context context) {
            super(context, DependenciaDBContract.DB_NAME, null, DependenciaDBContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DependenciaDBContract.RecordatoriosDBContract.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DependenciaDBContract.RecordatoriosDBContract.DELETE_TABLE);

            onCreate(db);
        }

    }

    public static class UbicacionesDBHelper extends SQLiteOpenHelper {

        private static UbicacionesDBHelper instanceDBHelper;

        public static synchronized UbicacionesDBHelper getInstance(Context context){
            if(instanceDBHelper == null){
                instanceDBHelper = new UbicacionesDBHelper(context.getApplicationContext());
            }
            return instanceDBHelper;
        }

        private UbicacionesDBHelper(Context context){
            super(context, DependenciaDBContract.DB_NAME, null, DependenciaDBContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DependenciaDBContract.UbicacionesDBContract.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DependenciaDBContract.UbicacionesDBContract.DELETE_TABLE);

            onCreate(db);
        }

    }

    public static class UsuarioDBHelper extends SQLiteOpenHelper {

        private static UsuarioDBHelper instanceDBHelper;

        public static synchronized UsuarioDBHelper getInstance(Context context){
            if(instanceDBHelper == null){
                instanceDBHelper = new UsuarioDBHelper(context.getApplicationContext());
            }
            return instanceDBHelper;
        }

        private UsuarioDBHelper(Context context){
            super(context, DependenciaDBContract.DB_NAME, null, DependenciaDBContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DependenciaDBContract.UsuarioDBContract.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DependenciaDBContract.UsuarioDBContract.DELETE_TABLE);
            onCreate(db);
        }


    }
}
