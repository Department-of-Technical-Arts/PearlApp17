package com.dota.pearl17;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class TalksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);

        ImageView bg = (ImageView) findViewById(R.id.imgV_background);
        Picasso.with(TalksActivity.this)
                .load(R.drawable.talks_frame)
                .fit()
                .into(bg);

        final CarouselLayoutManager mLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        mLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        final RecyclerView mRecycler = (RecyclerView) findViewById(R.id.recycler_pro_show);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setHasFixedSize(true);
        CarouselChildSelectionListener csl = new CarouselChildSelectionListener(mRecycler, mLayoutManager) {
            @Override
            protected void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {
                //open description
                return;
            }

            @Override
            protected void onBackItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {
                //bring that item to center
                recyclerView.smoothScrollToPosition(recyclerView.getChildAdapterPosition(v));
            }
        };
        mRecycler.setAdapter(new TalksAdapter());
        mRecycler.scrollToPosition(1); // TODO Tharoor resId
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TalksActivity.this, MainActivity.class));
        finish();
    }

    class TalksAdapter extends RecyclerView.Adapter<TalksAdapter.TalksViewHolder> {

        int resourceId[] = new int[]{
                R.drawable.talk_anshu,
                R.drawable.talk_shashi
        };

        @Override
        public TalksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TalksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pro_show, parent, false));
        }

        @Override
        public void onBindViewHolder(TalksViewHolder holder, int position) {
            Picasso.with(TalksActivity.this)
                    .load(resourceId[position])
                    .fit()
                    .centerCrop()
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class TalksViewHolder extends RecyclerView.ViewHolder {
            private ImageView img;

            TalksViewHolder(View v) {
                super(v);
                img = (ImageView) v.findViewById(R.id.image_pro_show);
            }
        }

    }

}
