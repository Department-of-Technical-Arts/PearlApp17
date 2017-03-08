package com.dota.pearl17;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SHIVIKA NAGPAL on 08-03-2017.
 */

public class Contact_adapter extends RecyclerView.Adapter <Contact_adapter.My_view_holder>{

    private LayoutInflater inflator;

    public Contact_adapter(Context context, LayoutInflater inflator){
        inflator.from(context);

    }
    @Override
    public My_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflator.inflate(R.layout.contact_recycler_layout,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(My_view_holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class My_view_holder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView imageview;
        public My_view_holder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.fob_name);
            imageview= (ImageView) imageview.findViewById(R.id.fob_image);
        }
    }
}
