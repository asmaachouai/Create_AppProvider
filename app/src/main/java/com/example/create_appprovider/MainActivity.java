package com.example.create_appprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    private DbHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DbHelper(this, "livres", null, 1);


        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final SQLiteDatabase sqDb = helper.getWritableDatabase();
                final ContentValues values = new ContentValues();
                values.put("livre", "programmez en JAVA");
                values.put("auteur", "JEAN ");
                sqDb.insert("tableau", null, values);
                sqDb.close();



        }});
        display();


    }
    private void display() {
        ArrayList<String> data =new ArrayList<>();
        final SQLiteDatabase sqDb = helper.getReadableDatabase();

        String table = "tableau";
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        final Cursor cursor = ((SQLiteDatabase) sqDb).query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            String livre = cursor.getString(cursor.getColumnIndex("livre"));
            String auteur = cursor.getString(cursor.getColumnIndex("auteur"));
            Toast.makeText(this,"livre"+livre+" \n Auteur :"+auteur,Toast.LENGTH_SHORT).show();
            data.add(livre+"\n"+auteur);
        }
        ((ListView)findViewById(R.id.list)).setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));


        sqDb.close();
    }

}
