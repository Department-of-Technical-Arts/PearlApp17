package com.dota.pearl17;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SHREEDA on 11-03-2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    ArrayList<Contacts> arrayList;
    Context context;
    LayoutInflater inflater;

    Typeface goodpro_light, goodpro_condblack;

    int offset = 0;

    public ContactsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        arrayList = new ArrayList<>();
        this.context = context;

        goodpro_light = Typeface.createFromAsset(context.getAssets(),"fonts/goodpro_light.otf");
        goodpro_condblack = Typeface.createFromAsset(context.getAssets(),"fonts/goodpro_condblack.otf");
    }

    public void setArrayList(ArrayList<Contacts> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = (inflater.inflate(R.layout.item_row, viewGroup, false));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onclick",v.toString());
                String num = ((TextView)v.findViewById(R.id.number)).getText().toString();
                Uri number = Uri.parse("tel:" + num);
                Intent intent = new Intent(Intent.ACTION_DIAL, number);
                context.startActivity(intent);
            }
        });
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(arrayList.get(i).getName());
        myViewHolder.designation.setText((arrayList.get(i).getDesignation()));
        myViewHolder.number.setText(arrayList.get(i).getMobile());

        Picasso.with(context)
                .load(arrayList.get(i).getImage()).
                into(myViewHolder.imageView);

        Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator(1.5f));
        animation.setFillAfter(true);
        animation.setStartOffset(offset);
        myViewHolder.itemView.startAnimation(animation);
        offset = offset + 40;
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
        offset = 0;
    }

    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, designation, number;


        //        RelativeLayout email,mobile;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.overflow);
            name = (TextView) itemView.findViewById(R.id.title);
            name.setTypeface(goodpro_light);
            designation = (TextView) itemView.findViewById(R.id.count);
            designation.setTypeface(goodpro_condblack);
            number = (TextView) itemView.findViewById(R.id.number);
            number.setTypeface(goodpro_light);
        }
    }
}
