package com.dota.pearl17;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azoft.carousellayoutmanager.CarouselChildSelectionListener;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.squareup.picasso.Picasso;

public class ProShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_show);

        ImageView test = (ImageView) findViewById(R.id.testimg);
        Picasso.with(ProShowActivity.this).load(R.drawable.events_screen_undersat).fit().into(test);
//        final CarouselLayoutManager mLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
//        mLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//        final RecyclerView mRecycler = (RecyclerView) findViewById(R.id.recycler_pro_show);
//        mRecycler.setLayoutManager(mLayoutManager);
//        mRecycler.setHasFixedSize(true);
//        CarouselChildSelectionListener csl = new CarouselChildSelectionListener(mRecycler,mLayoutManager) {
//            @Override
//            protected void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {
//                //do shit
//                Log.i("onClick","central item clicked");
//            }
//
//            @Override
//            protected void onBackItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {
//                //do other shit
//                recyclerView.smoothScrollToPosition(recyclerView.getChildAdapterPosition(v));
//            }
//        };
//        mRecycler.setAdapter(new ProShowAdapter());
//        mRecycler.scrollToPosition(2);
    }

    class ProShowAdapter extends RecyclerView.Adapter<ProShowAdapter.ProShowViewHolder>{

        @Override
        public ProShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pro_show,parent,false));
        }

        @Override
        public void onBindViewHolder(ProShowViewHolder holder, int position) {
            Log.i("onbind","bound pos " + position);
            switch(position){
                case 0:
                    holder.img.setBackgroundColor(0xFF000000);
                    break;
                case 1:
                    holder.img.setBackgroundColor(0xFFFF0000);
                    break;
                case 2:
                    Picasso.with(ProShowActivity.this).load(R.drawable.terpsichore).fit().into(holder.img);
//                    holder.img.setBackgroundColor(0xFFFF00FF);
                    break;
                case 3:
                    holder.img.setBackgroundColor(0xFF0000FF);
                    break;
                case 4:
                    holder.img.setBackgroundColor(0xFF00FFFF);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ProShowViewHolder extends RecyclerView.ViewHolder{
            private ImageView img;
            ProShowViewHolder(View v){
                super(v);
                img = (ImageView) v.findViewById(R.id.image_pro_show);
            }
        }

    }

}
