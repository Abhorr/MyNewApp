package com.abhorrent.prj.collectioner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhorrent.prj.collectioner.DB.DB;

public class StartActivityOld extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting);


        final Button addGallery = (Button) findViewById(R.id.add_collection);
        addGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCollection.class);
                startActivity(intent);
            }
        });

        final Button chooseGallery = (Button) findViewById(R.id.choose_collection);
        chooseGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No Actions are set here", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
