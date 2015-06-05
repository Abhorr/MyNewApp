package com.abhorrent.prj.collectioner.Adapters;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.abhorrent.prj.collectioner.Items.MenuItem;
import com.abhorrent.prj.collectioner.R;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<MenuItem> menuItems;

    public BoxAdapter(Context context, ArrayList<MenuItem> menuItem) {
        ctx = context;
        menuItems = menuItem;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.add_collection_item, parent, false);
        }
        MenuItem mi = getMenuItem(position);
        ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(mi.icon);
        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbChecked);
        cbBuy.setText(mi.title);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(mi.checked);
        return view;
    }

    public MenuItem getMenuItem(int position) {
        return ((MenuItem) getItem(position));
    }

    public ArrayList<MenuItem> getBox() {
        ArrayList<MenuItem> box = new ArrayList<MenuItem>();
        for (MenuItem p : menuItems) {
            if (p.checked)
                box.add(p);
        }
        return box;
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getMenuItem((Integer) buttonView.getTag()).checked = isChecked;
        }
    };
}