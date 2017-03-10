package com.dota.pearl17;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by INDIAN on 09/03/2017.
 */

public abstract class DemoViewHolder extends RecyclerView.ViewHolder {


    private final ImageView play;
    public TextView title;


    public DemoViewHolder(View view) {
        super(view);


        this.title = (TextView) view.findViewById(R.id.title);
        this.play= (ImageView) view.findViewById(R.id.overflow);

    }


}
