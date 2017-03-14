package com.dota.pearl17;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azoft.carousellayoutmanager.CarouselChildSelectionListener;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.squareup.picasso.Picasso;

public class ProShowActivity extends AppCompatActivity {

    String[] titles = new String[]{
            "Lagori",
            "Marnik",
            "Sonu Nigam",
            "Zakir Khan"
    };

    String[] descriptions = new String[]{
            "<b>Date:</b> 17/03/17<br/><b>Desc:</b> lorem ipsum xyz abc <br/><b>Time:</b> 6PM to 7PM<br/><b>Venue:</b> Auditorium",
            "<b>Date:</b> 18/03/17<br/><b>Desc:</b> lorem ipsum xyz abc <br/><b>Time:</b> 9PM to 12AM<br/><b>Venue:</b> Stage 1 Lawns",
            "<b>Date:</b> 19/03/17<br/><b>Desc:</b> lorem ipsum xyz abc <br/><b>Time:</b> 7PM to 10PM<br/><b>Venue:</b> Stage 1 Lawns",
            "<b>Date:</b> 17/03/17<br/><b>Desc:</b> lorem ipsum xyz abc <br/><b>Time:</b> 7PM to 9PM<br/><b>Venue:</b> Auditorium"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_show);

        Picasso.with(ProShowActivity.this)
                .load(R.drawable.proshows_frame)
                .fit()
                .into((ImageView) findViewById(R.id.proshow_bg));

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ProShowActivity.this);
                builder.setMessage(descString)
                        .setCancelable(true)
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
        mRecycler.setAdapter(new ProShowAdapter());
        mRecycler.scrollToPosition(2); // TODO This should be sonu nigam resId's index AT ALL TIMES
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ProShowActivity.this, MainActivity.class);
        i.putExtra("scrollTo",1);
        startActivity(i);
        finish();
    }

    class ProShowAdapter extends RecyclerView.Adapter<ProShowAdapter.ProShowViewHolder> {
        int resId[] = new int[]{
                R.drawable.proshow_lagori,
                R.drawable.proshow_marnik,
                R.drawable.proshow_sonu,
                R.drawable.proshow_zakir
        };

        @Override
        public ProShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pro_show, parent, false));
        }

        @Override
        public void onBindViewHolder(ProShowViewHolder holder, int position) {
            Picasso.with(ProShowActivity.this)
                    .load(resId[position])
                    .fit()
                    .centerInside()
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return resId.length;
        }

        class ProShowViewHolder extends RecyclerView.ViewHolder {
            private ImageView img;

            ProShowViewHolder(View v) {
                super(v);
                img = (ImageView) v.findViewById(R.id.image_pro_show);
            }
        }

    }

}
