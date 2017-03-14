package com.dota.pearl17;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

public class SponsorsActivity extends AppCompatActivity {

    RecyclerView sponsorRecycler;
    private SponsAdapter mAdapter;
    Typeface custom_font_bold;
    boolean loadInternal=false;
    private ArrayList<String> sponsor_url = new ArrayList<>();
    private ArrayList<String> sponsor_title = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        custom_font_bold = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condblack.otf");

        Picasso.with(this)
                .load(R.drawable.sponsors_frame)
                .fit()
                .into((ImageView) findViewById(R.id.bg_sponsors));

        sponsorRecycler = (RecyclerView) findViewById(R.id.sponsor_recycler);
        mAdapter = new SponsAdapter(this);
        sponsorRecycler.setAdapter(mAdapter);
        sponsorRecycler.setLayoutManager(new LinearLayoutManager(this));

        updateSponsorURLs();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("scrollTo",6);
        startActivity(i);
        finish();
    }

    void updateSponsorURLs() {

        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("success") == 1) {
//                        Toast.makeText(SponsorsActivity.this, "Loaded successfully", Toast.LENGTH_SHORT).show();
                        //update all events

                        try {
                            JSONArray array = new JSONObject(s).getJSONArray("data");
                            for (int j = 0; j < array.length(); j++) {
                                JSONObject obj = array.getJSONObject(j);
                                // {"name":"ACT","title":"Title","image":"http:\/\/bits-arena.com\/sponsors\/img\/act.png"}
                                sponsor_url.add(obj.getString("image"));
                                sponsor_title.add(obj.getString("name"));
                                mAdapter.notifyItemInserted(sponsor_url.size() - 1);
                            }
//                            Log.v("Sponsors", s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("onResponse","success 0");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //internet problem
                Log.i("ErrorResponse","Internet Issue");
                // Load from internal in this case. Add filenames to spons_title
                loadInternal = true;
                File directory = new ContextWrapper(SponsorsActivity.this).getDir("imageDir", Context.MODE_PRIVATE);
                if(directory.exists()){
                    for(File f : directory.listFiles()) {
                        String fileNameWithExt = f.getName();
                        //since ext is always .png for us
                        String withoutExt = fileNameWithExt.substring(0,fileNameWithExt.length()-4);
                        Log.i("filename",withoutExt);
                        sponsor_title.add(withoutExt);
                        mAdapter.notifyItemInserted(sponsor_title.size()-1);
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "getSponsors");
                //Log.e("Sent", params.toString());
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(request);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageButton;
        TextView tv;

        MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.sponsor_title);
            imageButton = (ImageView) itemView.findViewById(R.id.main_events);
        }
    }

    LayoutInflater inflater;

    private class SponsAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Context context;

        SponsAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.sponsors_row, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.tv.setText(sponsor_title.get(position));
            holder.tv.setTypeface(custom_font_bold);
            final int finalpos = position;

            holder.imageButton.requestLayout();

            if(loadInternal){
                String path = new ContextWrapper(SponsorsActivity.this).getDir("imageDir",Context.MODE_PRIVATE).getAbsolutePath();
                File local = new File(path, sponsor_title.get(position) + ".png");

                //since internal loading due to volley fail, local will always exist
                Picasso.with(SponsorsActivity.this)
                        .load(local)
                        .fit()
                        .centerInside()
                        .into(holder.imageButton);

            }
            else{
                // Following method calls load image from url into the ImageView

                Picasso.with(context)
                        .load(sponsor_url.get(position)) //This is the image url
                        .fit()
                        .centerInside()
                        .into(holder.imageButton, new Callback() {
                            @Override
                            public void onSuccess() {
                                // Image has been successfully loaded
                                // Save to internal memory
                                Log.i("Callback","onSuccess");
                                saveToInternalSorage(((BitmapDrawable) holder.imageButton.getDrawable()).getBitmap(), sponsor_title.get(finalpos));
                            }

                            @Override
                            public void onError() {
                                // Image download unsuccessful
                                // Cancel pending requests, load from internal if present

                                Log.i("Callback","onError");
                                //Picasso.with(SponsorsActivity.this).cancelRequest(holder.imageButton);
                                //String path = new ContextWrapper(SponsorsActivity.this).getDir("imageDir",Context.MODE_PRIVATE).getAbsolutePath();

                                //File local = new File(path, sponsor_title.get(holder.getAdapterPosition()) + ".png");
                            }
                        });
            }
        }

        @Override
        public int getItemCount() {
            return sponsor_title.size();
        }
    }

    private void saveToInternalSorage(Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name + ".png");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("Save unsuccessful", e.toString());
            e.printStackTrace();
        }
    }

}

