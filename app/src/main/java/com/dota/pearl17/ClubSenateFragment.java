package com.dota.pearl17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SHREEDA on 11-03-2017.
 */

public class ClubSenateFragment extends Fragment {
    ContactsAdapter contactAdapter;
    RecyclerView recyclerView;
    ArrayList<Contacts> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dummy_fragment, container, false);
    }

    public ClubSenateFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        contactAdapter = new ContactsAdapter(getActivity());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter.setArrayList(data);
        feedData();

    }

    private void feedData() {
        Contacts temp1 = new Contacts("KARRAN PANDEY", "Music", "73811818733", R.drawable.karran);
        Contacts temp2 = new Contacts("BHAVYA GUPTA", "Photog", "9553795222", R.drawable.bhavya);
        Contacts temp3_1 = new Contacts("SHARAD HOTHA", "Journal", "8187077212", R.drawable.sharad);
        Contacts temp3 = new Contacts("SUMIRAN MITTAL", "ELAS", "8096063146", R.drawable.sumiran);
        Contacts temp4 = new Contacts("SHIVANI MAHADDALKAR", "Dance", "9542979151", R.drawable.shivani);
        Contacts temp5 = new Contacts("SAMHITHA GADDAMANUGU", "Shades", "8096120328", R.drawable.samhitha);
        Contacts temp6 = new Contacts("ADITYA MATHUR", "Dramatics", "9542980965", R.drawable.mathur);
        Contacts temp7 = new Contacts("SHUBHAM JAIN", "Quiz", "9553317418", R.drawable.shubham);
        Contacts temp8 = new Contacts("RAJIV KRISHNA", "Design", "8331880623", R.drawable.rajiv);
        Contacts temp9 = new Contacts("DHRUV KULSHRESHTHA", "VFx", "9640022571", R.drawable.dhruv);
        Contacts temp10 = new Contacts("ANUJ SAXENA", "Movie", "9553339766", R.drawable.anuj);
        Contacts temp11 = new Contacts("NIMISHA AGARWAL", "Embryo", "7989944250", R.drawable.nimisha);
        Contacts temp12 = new Contacts("MANAN AGARWAL", "Hindi Tarang", "9912252372", R.drawable.manan);


        data.add(temp1);
        data.add(temp2);
        data.add(temp3_1);
        data.add(temp3);
        data.add(temp4);
        data.add(temp5);
        data.add(temp6);
        data.add(temp7);
        data.add(temp8);
        data.add(temp9);
        data.add(temp10);
        data.add(temp11);
        data.add(temp12);


        contactAdapter.notifyItemRangeInserted(0, data.size() - 1);
    }

}
