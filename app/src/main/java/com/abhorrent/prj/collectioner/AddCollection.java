package com.abhorrent.prj.collectioner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.abhorrent.prj.collectioner.Adapters.BoxAdapter;
import com.abhorrent.prj.collectioner.DB.DB;
import com.abhorrent.prj.collectioner.Items.MenuItem;

import java.util.ArrayList;

public class AddCollection extends Activity{

    ArrayList<MenuItem> mis = new ArrayList<MenuItem>();
    BoxAdapter boxAdapter;
    public static final String COLLECTION_ITEM_ID = "_item_id";
    String colName="";
    EditText nameET;
    EditText descET;
    boolean isDeclared = false;

    ListView lvSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_collection);
        fillData();
        boxAdapter = new BoxAdapter(this, mis);

        ListView lvMain = (ListView) findViewById(R.id.lvSimple);
        lvMain.setAdapter(boxAdapter);
    }

    void fillData() {
        String[] ids = { "collection_item_name","collection_item_photo","collection_item_description","collection_item_location","collection_item_link"};
        String[] texts = { getString(R.string.collection_item_name),getString(R.string.collection_item_photo),
                getString(R.string.collection_item_description),getString(R.string.collection_item_location),
                getString(R.string.collection_item_link)};
        boolean[] checked = { true, false, false, false, false};
        int[] imgs = {  R.drawable.ic_action_edit,
                R.drawable.ic_action_new_picture,
                R.drawable.ic_action_edit,
                R.drawable.ic_action_map,
                R.drawable.ic_action_web_site};

        for (int i=0;i<texts.length;i++)
            mis.add(new MenuItem(ids[i],texts[i],checked[i],imgs[i]));
    }

    public void showResult(View v) {
        String result = "Selected options:";
        for (MenuItem mi : boxAdapter.getBox()) {
            if (mi.checked)
                result += "\n" + mi.title;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        nameET = (EditText) findViewById(R.id.editNameText);
        descET = (EditText) findViewById(R.id.editDescriptionText);
        colName = nameET.getText().toString();
    }

    public void addNewCollection() {
        if (!colName.isEmpty()) {
            DB db = new DB(getApplicationContext());
            db.open();
            Cursor c = db.getAllData("Collection_List");
            try {
                c.moveToFirst();

                if (c != null) {
                    do {
                        for (int i = 0; i < c.getColumnCount(); i++) {
                            try {
                                if (c.getString(i).equals(colName)) {
                                    isDeclared = true;
                                    break;
                                }
                            } catch (IllegalStateException e) {
                                continue;
                            }
                        }
                        if (isDeclared)
                            break;
                    } while (c.moveToNext());
                }
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            if (isDeclared)
                Toast.makeText(getApplicationContext(), getString(R.string.invalid_collection_name), Toast.LENGTH_SHORT).show();
            else {
                db.addRec("Collection_List",colName, 191);
                db.close();
                c.close();
                isDeclared = false;
                colName = "";
                Intent intent = new Intent(getApplicationContext(), AddCollection.class);
                startActivity(intent);
            }
        }
    }

    public void addCollection(String dbName,String dbDescription,String[] params)
    {

        DB db = new DB(getApplicationContext());
        db.open();

        String DB_CREATE_QUERY =  "create table " + dbName + "(" +
                COLLECTION_ITEM_ID + " integer primary key autoincrement );";
                //COLUMN_IMG + " integer, " +

        for (int i=0;i<params.length;i++)
            switch(params[i]) {
                case "collection_item_name":
                    DB_CREATE_QUERY.replace(" );",", collection_item_name text );");
                    break;
                case "collection_item_photo":
                    DB_CREATE_QUERY.replace(" );",", collection_item_photo text );");
                    break;
                case "collection_item_description":
                    DB_CREATE_QUERY.replace(" );",", collection_item_description text );");
                    break;
                case "collection_item_location":
                    DB_CREATE_QUERY.replace(" );",", collection_item_location text );");
                    break;
                case "collection_item_link":
                    DB_CREATE_QUERY.replace(" );",", collection_item_link text );");
                    break;
            }
        db.createNewTab(DB_CREATE_QUERY);
        db.close();
    }

}
