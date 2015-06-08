package com.abhorrent.prj.collectioner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abhorrent.prj.collectioner.Adapters.MyCursorLoader;
import com.abhorrent.prj.collectioner.DB.DB;

public class StartingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);

        final Button addGallery = (Button) findViewById(R.id.add_collection);
        addGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCollectionName();
                /*Intent intent = new Intent(getApplicationContext(), AddCollection.class);
                startActivity(intent);*/
            }
        });

        final Button chooseGallery = (Button) findViewById(R.id.choose_collection);
        chooseGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"No Actions are set here",Toast.LENGTH_SHORT).show();
            }
        });


    }
    String etStr="";
    boolean isDeclared = false;

    public void setCollectionName()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        TextView tv = new TextView(this);
        tv.setText(getString(R.string.new_collection_name_title));
        tv.setPadding(20, 20, 20, 20);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(24);

        EditText et = new EditText(this);
        et.setHint(R.string.new_collection_name_edit);
        etStr = getString(R.string.new_collection_name_edit);
        et.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s){
                etStr = s.toString();
            }
            public void beforeTextChanged(CharSequence s,int start, int count, int after){
            }
            public void onTextChanged(CharSequence s,int start, int before, int count)
            {
            }
        });

        layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

        alertDialogBuilder.setView(layout);
        alertDialogBuilder.setCustomTitle(tv);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!etStr.isEmpty()) {
                    DB db = new DB(getApplicationContext());
                    db.open();
                    Cursor c = db.getAllData("Collection_List");
                    try {
                        c.moveToFirst();

                        if (c != null) {
                            do {
                                for (int i = 0; i < c.getColumnCount(); i++) {
                                    try {
                                        if (c.getString(i).equals(etStr)) {
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
                        //db.addRec(etStr, 191);
                        db.close();
                        c.close();
                        isDeclared = false;
                        etStr = "";
                        Intent intent = new Intent(getApplicationContext(), AddCollection.class);
                        startActivity(intent);
                    }
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        try {
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
