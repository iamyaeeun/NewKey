package edu.sungshin.newkey;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {

    CatPoliticFragment politicFragment;
    CatEconomicFragment economicFragment;
    CatSocialFragment socialFragment;
    CatLifeFragment lifeFragment;
    CatWorldFragment worldFragment;
    CatItFragment itFragment;
    CatOpinionFragment opinionFragment;
    CatSportFragment sportFragment;
    CatEntertainmentFragment entertainmentFragment;

    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        politicFragment = new CatPoliticFragment();
        economicFragment = new CatEconomicFragment();
        socialFragment = new CatSocialFragment();
        lifeFragment = new CatLifeFragment();
        worldFragment = new CatWorldFragment();
        itFragment = new CatItFragment();
        opinionFragment = new CatOpinionFragment();
        sportFragment = new CatSportFragment();
        entertainmentFragment = new CatEntertainmentFragment();

        getChildFragmentManager().beginTransaction().replace(R.id.container, politicFragment).commit();

        tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정치"));
        tabs.addTab(tabs.newTab().setText("경제"));
        tabs.addTab(tabs.newTab().setText("사회"));
        tabs.addTab(tabs.newTab().setText("생활"));
        tabs.addTab(tabs.newTab().setText("세계"));
        tabs.addTab(tabs.newTab().setText("IT"));
        tabs.addTab(tabs.newTab().setText("오피니언"));
        tabs.addTab(tabs.newTab().setText("스포츠"));
        tabs.addTab(tabs.newTab().setText("연예"));

//        TabLayout.Tab opinionTab = tabs.getTabAt(6);
//
//        TextView customTab1 = new TextView(getContext());
//        customTab1.setText("오피니언");
//        customTab1.setTextSize(7); // 원하는 글자 크기를 설정합니다.
//        customTab1.setTextColor(Color.WHITE); // 원하는 글자 색을 설정합니다.
//        opinionTab.setCustomView(customTab1);
//
//        TabLayout.Tab sportTab = tabs.getTabAt(7);
//
//        TextView customTab2 = new TextView(getContext());
//        customTab2.setText("스포츠");
//        customTab2.setTextSize(7); // 원하는 글자 크기를 설정합니다.
//        customTab2.setTextColor(Color.WHITE); // 원하는 글자 색을 설정합니다.
//        sportTab.setCustomView(customTab2);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position == 0) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, politicFragment).commit();
                } else if (position == 1) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, economicFragment).commit();
                } else if (position == 2) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, socialFragment).commit();
                } else if (position == 3) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, lifeFragment).commit();
                } else if (position == 4) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, worldFragment).commit();
                } else if (position == 5) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, itFragment).commit();
                } else if (position == 6) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, opinionFragment).commit();
                } else if (position == 7) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, sportFragment).commit();
                } else if (position == 8) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, entertainmentFragment).commit();
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
