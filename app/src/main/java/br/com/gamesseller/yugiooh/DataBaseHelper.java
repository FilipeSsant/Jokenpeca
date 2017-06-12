package br.com.gamesseller.yugiooh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ecobiel on 21/03/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String NOME_BANCO = "jokenpo.db";
    private static final int VERSAO = 1;

    public DataBaseHelper(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table tbl_elementos(" +
                "idElemento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomeElemento TEXT," +
                "imagemElemento INTEGER);";

        db.execSQL(sql);

        sql = "insert or replace into tbl_elementos(nomeElemento, imagemElemento)" +
                "values ('Pedrilson', "+ R.drawable.pedrilson +")";

        db.execSQL(sql);

        sql = "insert or replace into tbl_elementos(nomeElemento, imagemElemento)" +
                "values ('Papeldesu', "+ R.drawable.papeldesu +")";

        db.execSQL(sql);

        sql = "insert or replace into tbl_elementos(nomeElemento, imagemElemento)" +
                "values ('Peçoura', "+ R.drawable.pessoura +")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS tbl_elementos");
        onCreate(db);
    }
}
