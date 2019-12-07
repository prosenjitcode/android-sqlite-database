package com.bengalitutorial.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.bengalitutorial.sqliteexample.database.MyDb;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MyDb myDb;
    private SimpleCursorAdapter adapter;
    private SQLiteDatabase database;
    private Cursor mCursor;

    private ListView listView;
    private ImageView mAdd;
    private EditText fistname,lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fistname = (EditText)findViewById(R.id.fname);
        lastname = (EditText)findViewById(R.id.lname);
        mAdd = (ImageView)findViewById(R.id.imageView);
        mAdd.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(this);



        myDb = new MyDb(this);




    }

    @Override
    protected void onResume() {
        super.onResume();
        database = myDb.getWritableDatabase();

        String[] projection = new String[]{"_id",MyDb.FIRST_NAME,MyDb.LAST_NAME,MyDb._DATE};
        mCursor = database.query(MyDb.TABLE_NAME,projection,null,null,null,null,null);

        String[] headePprojection = new String[]{MyDb.FIRST_NAME,MyDb.LAST_NAME,MyDb._DATE};

        adapter = new SimpleCursorAdapter(this,R.layout.item_list,mCursor,headePprojection,
                new int[]{R.id.textView1,R.id.textView2,R.id.textView3});

        listView.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
        mCursor.close();
    }

    @Override
    public void onClick(View view) {

        if (!TextUtils.isEmpty(fistname.getText().toString()) && !TextUtils.isEmpty(lastname.getText().toString())) {
            myDb.insertData(database, fistname.getText().toString(), lastname.getText().toString());
            mCursor.requery();
            adapter.notifyDataSetChanged();

            fistname.setText(null);
            lastname.setText(null);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        mCursor.moveToPosition(position);

        String rowId = mCursor.getString(0);

        database.delete(MyDb.TABLE_NAME,"_id=?",new String[]{rowId});
        mCursor.requery();
        adapter.notifyDataSetChanged();
    }
}
