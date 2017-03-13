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

import java.util.ArrayList;

/**
 * Created by SHREEDA on 11-03-2017.
 */

public class FobFragment extends Fragment{
    ContactsAdapter contactAdapter;
    RecyclerView recyclerView;
    ArrayList<Contacts> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dummy_fragment, container, false);
    }

    public FobFragment() {
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
        Contacts temp1 = new Contacts("KESHAV KEDARNATH", "President", "7997030001", R.drawable.keshav);
        Contacts temp2 = new Contacts("SAMPATH BALINENI", "General Secretary", "7997030002", R.drawable.sampath);
        Contacts temp3 = new Contacts("METTA SAI SIDDHARATHA", "Cultural Secretary", "7997030003", R.drawable.siddhartha);
        Contacts temp4 = new Contacts("MONICA ADUSUMILLI", "Cultural Secretary", "7997030004", R.drawable.monica);
        Contacts temp5 = new Contacts("G.V VIGNESH REDDY", "Sponsorship and Marketing", "7036409444", R.drawable.vignesh);
        Contacts temp6 = new Contacts("ANIRUDH KASUGANTI", "Publicity", "9030330744", R.drawable.anirudh);
        Contacts temp7 = new Contacts("ARJUN DESHMUKH", "Photography", "9912259211", R.drawable.arjun);
        Contacts temp8 = new Contacts("MAYUR BAJAJ", "Hospitality", "9542981940", R.drawable.mayurb);
        Contacts temp9 = new Contacts("K JAIDEEP", "Technical Arts", "7702021928", R.drawable.jaideep);
        Contacts temp10 = new Contacts("SOMESH PARADKAR", "Visual Effects", "8686400884", R.drawable.somesh);
        Contacts temp11 = new Contacts("HARSH CHOPRA", "Lights And Sound", "8096120418", R.drawable.harshc);
        Contacts temp12 = new Contacts("MALLA NAGA POOJITHA REDDY", "Arts n Deco", " 9553329048", R.drawable.poojitha);
        Contacts temp13 = new Contacts("PIPPIRI SACHIN", "Informalz", "9666070735", R.drawable.sachin);
        Contacts temp14 = new Contacts("HITHENDRA SAI ALAM", "Controlz", " 9553336733", R.drawable.hithendra);



        data.add(temp1);
        data.add(temp2);
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
        data.add(temp13);
        data.add(temp14);

        contactAdapter.notifyItemRangeInserted(0, data.size() - 1);
    }
}
