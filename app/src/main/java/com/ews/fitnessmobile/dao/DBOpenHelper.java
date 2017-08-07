package com.ews.fitnessmobile.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ews.fitnessmobile.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wallace on 25/07/17.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String BD_NAME = "fitness.db";
    private static final int VERSION_BD = 1;

    private Context ctx;

    public DBOpenHelper(Context context) {
        super(context, BD_NAME, null, VERSION_BD);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        readAndExecuteSQLScript(db, ctx, R.raw.db_create);
        readAndExecuteSQLScript(db, ctx, R.raw.init_data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for ( int i = oldVersion; i < newVersion; ++i) {
            String migrationFileName = String.format("from_%d_to_%d", i, (i + 1));
            log("Looking for migration file: " + migrationFileName);
            int migrationFileResId = ctx.getResources()
                    .getIdentifier(migrationFileName, "raw", ctx.getPackageName());
            if (migrationFileResId != 0) {
                // execute script
                log("Found, executing");
                readAndExecuteSQLScript(db, ctx, migrationFileResId);
            } else {
                log("Not found!");
            }
            //readAndExecuteSQLScript(db, ctx, R.raw.db_create);
            //readAndExecuteSQLScript(db, ctx, R.raw.init_data);
        }
    }


    private void readAndExecuteSQLScript(SQLiteDatabase db, Context ctx, Integer sqlScriptResId) {
        Resources res = ctx.getResources();
        try {
            InputStream is = res.openRawResource(sqlScriptResId);
            BufferedReader reader = new BufferedReader (new InputStreamReader(is));
            executeSQLScript(db, reader);
            reader.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException( "Não foi possivel ler o arquivo SQLite" , e);
        }
    }

    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader) throws
            IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null ) {
            statement.append(line);
            statement.append( " \n " );
            if (line.endsWith( ";" )) {
                String toExec = statement.toString();
                log( "Executing script: " + toExec);
                db.execSQL(toExec);
                statement = new StringBuilder();
            }
        }
    }

    private void log(String msg) {
        Log.d(DBOpenHelper.class.getSimpleName(), msg);
    }

}
