package com.dota.pearl17;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselChildSelectionListener;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.squareup.picasso.Picasso;

public class TalksActivity extends AppCompatActivity {

    String[] titles = new String[]{
            "Anshu Mor",
            "Shashi Tharoor",
            "Kommune",
            "Offbeat"
    };

    String[] descriptions = new String[]{
            "Day: 17/03/17<br/><br/><b>Desc:</b> lorem ipsum xyz abc egfbkjg sbdgk fbgb ;kfbg; kadbg;kbds gk;jbdsg; asdg\n\nTime: 7PM",
            "Day: 17/03/17<br/><br/><b>Desc:</b> lorem ipsum xyz abc egfbkjg sbdgk fbgb ;kfbg; kadbg;kbds gk;jbdsg; asdg\n\nTime: 7PM",
            "Day: 17/03/17<br/><br/><b>Desc:</b> lorem ipsum xyz abc egfbkjg sbdgk fbgb ;kfbg; kadbg;kbds gk;jbdsg; asdg\n\nTime: 7PM",
            "Day: 17/03/17<br/><br/><b>Desc:</b> lorem ipsum xyz abc egfbkjg sbdgk fbgb ;kfbg; kadbg;kbds gk;jbdsg; asdg\n\nTime: 7PM"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);

        ImageView bg = (ImageView) findViewById(R.id.imgV_background);
        Picasso.with(TalksActivity.this)
                .load(R.drawable.talks_frame)
                .fit()
                .into(bg);

        Typeface cubano = Typeface.createFromAsset(getAssets(), "fonts/cubano.otf");
        View showMore = findViewById(R.id.btn_show_more_talks);
        ((TextView)showMore.findViewById(R.id.text_show_more)).setTypeface(cubano);

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ANIM: This needs to have a slide up animation
                startActivity(new Intent(TalksActivity.this, TalksShowMoreActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
            }
        });

        final CarouselLayoutManager mLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        mLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        final RecyclerView mRecycler = (RecyclerView) findViewById(R.id.recycler_pro_show);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setHasFixedSize(true);

        CarouselChildSelectionListener csl = new CarouselChildSelectionListener(mRecycler, mLayoutManager) {
            @Override
            protected void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {
                //open description
                int pos = recyclerView.getChildAdapterPosition(v);
                Spanned descString = Html.fromHtml(descriptions[pos]);
                AlertDialog.Builder builder = new AlertDialog.Builder(TalksActivity.this);
                builder.setMessage(descString)
                        .setCancelable(false)
                        .setTitle(titles[pos])
                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
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
        Intent i = new Intent(TalksActivity.this, MainActivity.class);
        i.putExtra("scrollTo",2);
        startActivity(i);
        finish();
    }

    class TalksAdapter extends RecyclerView.Adapter<TalksAdapter.TalksViewHolder> {

        int resourceId[] = new int[]{
                R.drawable.talk_anshu,
                R.drawable.talk_shashi,
                R.drawable.talk_kommune,
                R.drawable.talk_offbeat
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
                    .centerInside()
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return resourceId.length;
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
