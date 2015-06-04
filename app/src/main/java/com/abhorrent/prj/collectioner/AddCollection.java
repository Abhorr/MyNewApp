package com.abhorrent.prj.collectioner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.abhorrent.prj.collectioner.Adapters.BoxAdapter;
import com.abhorrent.prj.collectioner.Items.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCollection extends Activity{

    ArrayList<MenuItem> mis = new ArrayList<MenuItem>();
    BoxAdapter boxAdapter;

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
        String[] texts = { "Collection Item's Name", "Photo/Image", "Description","Location","Link"};
        boolean[] checked = { true, false, false, false, false};
        int[] imgs = {  R.drawable.ic_action_edit,
                R.drawable.ic_action_new_picture,
                R.drawable.ic_action_edit,
                R.drawable.ic_action_map,
                R.drawable.ic_action_web_site};

        for (int i=0;i<texts.length;i++)
            mis.add(new MenuItem(texts[i],checked[i],imgs[i]));
    }

    public void showResult(View v) {
        String result = "Selected options:";
        for (MenuItem mi : boxAdapter.getBox()) {
            if (mi.checked)
                result += "\n" + mi.title;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

}
