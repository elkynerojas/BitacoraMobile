package elkinrojas.com.bitacora;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import utilidades.stringsDataBase;

/**
 * Created by Elkin Rojas on 01/02/2018.
 */

public class conexionDataBase extends SQLiteOpenHelper {



    public conexionDataBase(Context context) {
        super(context, stringsDataBase.DB_NAME, null, stringsDataBase.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(stringsDataBase.CREAR_TABLA_DEPENDENCIA);
        db.execSQL(stringsDataBase.CREAR_TABLA_TIPO_REGISTRO);
        db.execSQL(stringsDataBase.CREAR_TABLA_FUNCIONARIO);
        db.execSQL(stringsDataBase.CREAR_TABLA_REGISTRO);
        db.execSQL(stringsDataBase.INSERCION_TABLA_DEPENDENCIA);
        db.execSQL(stringsDataBase.INSERCION_TABLA_TIPO_REGISTRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(stringsDataBase.UPGRADE_TABLA_REGISTRO);
        db.execSQL(stringsDataBase.CREAR_TABLA_FUNCIONARIO);
        db.execSQL(stringsDataBase.UPGRADE_TABLA_TIPO_REGISTRO);
        db.execSQL(stringsDataBase.UPGRADE_TABLA_DEPENDENCIA);
        onCreate(db);
    }
}
