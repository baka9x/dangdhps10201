package com.danghai.dangdhps10201.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.danghai.dangdhps10201.Adapter.ViewPaperAdapter;
import com.danghai.dangdhps10201.R;
import com.google.android.material.tabs.TabLayout;


public class CategoriesFragment extends Fragment {

    private final String[] catValue = {"Trinh thám", "Học thuật", "Văn học", "Giải trí"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);


        //Tab
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        ViewPaperAdapter pagerAdapter = new ViewPaperAdapter(getChildFragmentManager());


        for (int i= 0; i < catValue.length; i++){
            pagerAdapter.addFragment(new AllFragment(), catValue[i]);

        }
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
