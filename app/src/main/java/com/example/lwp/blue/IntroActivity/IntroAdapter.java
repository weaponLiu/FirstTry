package com.example.lwp.blue.IntroActivity;

/**
 * Created by WP on 2018/4/11.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lwp.blue.R;

import java.util.List;

/**
 * Created by WP on 2018/4/11.
 */
public class IntroAdapter extends ArrayAdapter<Intro> {
    private int resourceId;
    public IntroAdapter(Context context, int resource, List<Intro> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Intro intro = getItem(position)
                ;
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView introName = (TextView)view.findViewById(R.id.intro_item_name);
        TextView introIntent1 = (TextView)view.findViewById(R.id.intro_item_intent1);
        TextView introIntent2 = (TextView)view.findViewById(R.id.intro_item_intent2);
        introName.setText(intro.getName());
        introIntent1.setText(intro.getIntentOne());
        introIntent2.setText(intro.getIntentTwo());

        return view;
    }
}
