package com.ews.fitnessmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ews.fitnessmobile.model.Unidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wallace on 27/07/17.
 */
public class UnidadeDAO {

    private DBOpenHelper db;
    private static final String TABLE = "TB_UNIDADE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_CIDADE = "cidade";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_HORARIOFUNCIONAMENTO = "horarioFuncionamento";
    public static final String COLUMN_URL_IMAGEM = "urlImagem";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public UnidadeDAO(Context ctx) {
        this.db = new DBOpenHelper(ctx);
    }

    public String insertOrUpdate(Unidade unidade) {
        SQLiteDatabase database = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, unidade.getNome());
        values.put(COLUMN_CIDADE, unidade.getCidade());
        values.put(COLUMN_ENDERECO, unidade.getEndereco());
        values.put(COLUMN_TELEFONE, unidade.getTelefone());
        values.put(COLUMN_HORARIOFUNCIONAMENTO, unidade.getHorarioFuncionamento());
        values.put(COLUMN_URL_IMAGEM, "");
        values.put(COLUMN_LATITUDE, unidade.getLatitude());
        values.put(COLUMN_LONGITUDE, unidade.getLongitude());
        long result = -1;
        if (unidade.getId() == null) {
             result = database.insert(TABLE, null, values);
        } else {
            result = database.update(TABLE, values, COLUMN_ID + "=?", new String[]{Integer.toString(unidade.getId())});
        }
        database.close();
        return (result > 0) ? "Sucess" : "Error";
    }

    public String delete(Unidade unidade) {
        SQLiteDatabase database = this.db.getWritableDatabase();
        String[] args = {unidade.getId().toString()};
        int result = database.delete(TABLE, "id = ?", args);
        return (result > 0) ? "Sucess" : "Error";
    }

    public List<Unidade> getAll() {
        List<Unidade> unidades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY ID DESC";
        SQLiteDatabase database = this.db.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Unidade unidade = new Unidade();
                unidade.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                unidade.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
                unidade.setCidade(cursor.getString(cursor.getColumnIndex(COLUMN_CIDADE)));
                unidade.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_ENDERECO)));
                unidade.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_ENDERECO)));
                unidade.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE)));
                unidade.setHorarioFuncionamento(cursor.getString(cursor.getColumnIndex(COLUMN_HORARIOFUNCIONAMENTO)));
                unidade.setUrlImagem(cursor.getString(cursor.getColumnIndex(COLUMN_URL_IMAGEM)));
                unidade.setLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)));
                unidade.setLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                unidades.add(unidade);
            } while (cursor.moveToNext());
        }
        return unidades;
    }


}
