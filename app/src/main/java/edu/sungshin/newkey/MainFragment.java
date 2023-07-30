package edu.sungshin.newkey;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {

    CatPoliticFragment politicFragment;
    CatEntertainmentFragment entertainmentFragment;
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        politicFragment=new CatPoliticFragment();
        entertainmentFragment=new CatEntertainmentFragment();

        //toolbar=rootView.findViewById(R.id.toolbar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);

        getChildFragmentManager().beginTransaction().replace(R.id.container,politicFragment).commit();

        tabs=rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정치"));
        tabs.addTab(tabs.newTab().setText("연예"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();

                if(position==0){
                    getChildFragmentManager().beginTransaction().replace(R.id.container,politicFragment).commit();
                }else if(position==1){
                    getChildFragmentManager().beginTransaction().replace(R.id.container,entertainmentFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;

    }
}