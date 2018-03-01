package dam.android.dependeciapp.Controladores.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tiodor on 22/01/2018.
 */

public class DependenciaDBManager {

    public static class RecordatoriosDBManager {

        private DependenciaDBHelper.RecordatoriosDBHelper recordatoriosDBHelper;

        public RecordatoriosDBManager(Context context) {
            recordatoriosDBHelper = DependenciaDBHelper.RecordatoriosDBHelper.getInstance(context);
        }

        public void insert(String id,String titulo, String contenido, String fecha, String hora) {
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract._ID,id);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.TITULO, titulo);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.CONTENIDO, contenido);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.FECHA, fecha);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.HORA, hora);

                sqLiteDatabase.insert(DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME, null, contentValues);

                sqLiteDatabase.close();
            }
        }

        public void update(String id, String titulo, String contenido, String fecha, String hora) {
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.TITULO, titulo);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.CONTENIDO, contenido);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.FECHA, fecha);
                contentValues.put(DependenciaDBContract.RecordatoriosDBContract.HORA, hora);

                String tableName = DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME;
                String whereClause = DependenciaDBContract.RecordatoriosDBContract._ID + " = ?";
                String whereArgs[] = {id};

                sqLiteDatabase.update(tableName, contentValues, whereClause, whereArgs);

                sqLiteDatabase.close();
            }
        }

        public void delete(String id) {
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                String tableName = DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME;
                String whereClause = DependenciaDBContract.RecordatoriosDBContract._ID + " = ?";
                String whereArgs[] = {id};

                sqLiteDatabase.delete(tableName, whereClause, whereArgs);

                sqLiteDatabase.close();
            }
        }
        public void createTableIfNotExist(){
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(DependenciaDBContract.RecordatoriosDBContract.CREATE_TABLE);
            sqLiteDatabase.close();

        }
        public void vaciaTabla(){
            SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(DependenciaDBContract.RecordatoriosDBContract.DELETE_TABLE);
            sqLiteDatabase.execSQL(DependenciaDBContract.RecordatoriosDBContract.CREATE_TABLE);
            sqLiteDatabase.close();
        }


        public Cursor getRows() {
            Cursor cursor = null;
            try {
                SQLiteDatabase sqLiteDatabase = recordatoriosDBHelper.getReadableDatabase();

                if (sqLiteDatabase != null) {
                    String[] projection = new String[]{DependenciaDBContract.RecordatoriosDBContract._ID,
                            DependenciaDBContract.RecordatoriosDBContract.TITULO,
                            DependenciaDBContract.RecordatoriosDBContract.CONTENIDO,
                            DependenciaDBContract.RecordatoriosDBContract.FECHA,
                            DependenciaDBContract.RecordatoriosDBContract.HORA};

                    cursor = sqLiteDatabase.query(DependenciaDBContract.RecordatoriosDBContract.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            null);
                }
                return cursor;
            } catch (SQLException ex) {
            return null;
            }
        }
    }

    public static class UbicacionesDBManager {

        private DependenciaDBHelper.UbicacionesDBHelper ubicacionesDBHelper;

        public UbicacionesDBManager(Context context) {
            ubicacionesDBHelper = DependenciaDBHelper.UbicacionesDBHelper.getInstance(context);
        }

        public void insert(double latitud, double longitud, String direccion) {
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(DependenciaDBContract.UbicacionesDBContract.LATITUD, latitud);
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.LONGITUD, longitud);
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.DIRECCION, direccion);

                sqLiteDatabase.insert(DependenciaDBContract.UbicacionesDBContract.TABLE_NAME, null, contentValues);

                sqLiteDatabase.close();
            }
        }

        public void update(int id, double latitud, double longitud, String direccion) {
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.LATITUD, latitud);
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.LONGITUD, longitud);
                contentValues.put(DependenciaDBContract.UbicacionesDBContract.DIRECCION, direccion);

                String tableName = DependenciaDBContract.UbicacionesDBContract.TABLE_NAME;
                String whereClause = DependenciaDBContract.UbicacionesDBContract._ID + " = ?";
                String whereArgs[] = {String.valueOf(id)};

                sqLiteDatabase.update(tableName, contentValues, whereClause, whereArgs);

                sqLiteDatabase.close();
            }
        }

        public void delete(String id) {
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                String tableName = DependenciaDBContract.UbicacionesDBContract.TABLE_NAME;
                String whereClause = DependenciaDBContract.UbicacionesDBContract._ID + " = ?";
                String whereArgs[] = {id};

                sqLiteDatabase.delete(tableName, whereClause, whereArgs);

                sqLiteDatabase.close();
            }
        }

        public Cursor getRows() {
            Cursor cursor = null;

            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getReadableDatabase();

            if (sqLiteDatabase != null) {
                String[] projection = new String[]{DependenciaDBContract.UbicacionesDBContract._ID,
                        DependenciaDBContract.UbicacionesDBContract.LATITUD,
                        DependenciaDBContract.UbicacionesDBContract.LONGITUD,
                        DependenciaDBContract.UbicacionesDBContract.DIRECCION};

                cursor = sqLiteDatabase.query(DependenciaDBContract.UbicacionesDBContract.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }

            return cursor;
        }

        public void createTableIfNotExist(){
            SQLiteDatabase sqLiteDatabase = ubicacionesDBHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(DependenciaDBContract.UbicacionesDBContract.CREATE_TABLE);
            sqLiteDatabase.close();

        }
    }

    public static class UsuarioDBManager {

        private DependenciaDBHelper.UsuarioDBHelper usuarioDBHelper;

        public UsuarioDBManager(Context context) {
            usuarioDBHelper = DependenciaDBHelper.UsuarioDBHelper.getInstance(context);
        }

        public void insert(int id, String dni, String nombre, String apellidos, String fecha_nacimiento, String genero, String tipo_dependiente, String fecha_alta, String pass) {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DependenciaDBContract.UsuarioDBContract._ID, id);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.DNI, dni);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.NOMBRE, nombre);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.APELLIDOS, apellidos);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.FECHA_NACIMIENTO, fecha_nacimiento);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.GENERO, genero);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.TIPO_DEPENDIENTE, tipo_dependiente);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.FECHA_ALTA, fecha_alta);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.PASS, pass);
                sqLiteDatabase.insert(DependenciaDBContract.UsuarioDBContract.TABLE_NAME, null, contentValues);

                sqLiteDatabase.close();
            }
        }

        public void update(int id, String dni, String nombre, String apellidos, String fecha_nacimiento, String genero, String tipo_dependiente, String fecha_alta) {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();
            if (sqLiteDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DependenciaDBContract.UsuarioDBContract.DNI, dni);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.NOMBRE, nombre);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.APELLIDOS, apellidos);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.FECHA_NACIMIENTO, fecha_nacimiento);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.GENERO, genero);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.TIPO_DEPENDIENTE, tipo_dependiente);
                contentValues.put(DependenciaDBContract.UsuarioDBContract.FECHA_ALTA, fecha_alta);

                String tableName = DependenciaDBContract.UsuarioDBContract.TABLE_NAME;
                String whereClause = DependenciaDBContract.UsuarioDBContract._ID + " = ?";
                String whereArgs[] = {String.valueOf(id)};

                sqLiteDatabase.update(tableName, contentValues, whereClause, whereArgs);

                sqLiteDatabase.close();
            }
        }

        public void delete(String id) {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();

            if (sqLiteDatabase != null) {
                String tableName = DependenciaDBContract.UsuarioDBContract.TABLE_NAME;
                String whereClause = DependenciaDBContract.UsuarioDBContract._ID + " = ?";
                String whereArgs[] = {"1"};

                sqLiteDatabase.delete(tableName, null, null);

                sqLiteDatabase.close();
            }
        }

        public void emptyTable() {
            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(DependenciaDBContract.UsuarioDBContract.EMPTY_TABLE);
            sqLiteDatabase.close();
        }

        public Cursor getRows() {
            Cursor cursor = null;

            SQLiteDatabase sqLiteDatabase = usuarioDBHelper.getReadableDatabase();

            if (sqLiteDatabase != null) {
                String[] projection = new String[]{DependenciaDBContract.UsuarioDBContract._ID,
                        DependenciaDBContract.UsuarioDBContract.DNI,
                        DependenciaDBContract.UsuarioDBContract.NOMBRE,
                        DependenciaDBContract.UsuarioDBContract.APELLIDOS,
                        DependenciaDBContract.UsuarioDBContract.FECHA_NACIMIENTO,
                        DependenciaDBContract.UsuarioDBContract.GENERO,
                        DependenciaDBContract.UsuarioDBContract.TIPO_DEPENDIENTE,
                        DependenciaDBContract.UsuarioDBContract.FECHA_ALTA,
                        DependenciaDBContract.UsuarioDBContract.PASS};

                cursor = sqLiteDatabase.query(DependenciaDBContract.UsuarioDBContract.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }

            return cursor;
        }
    }
}

