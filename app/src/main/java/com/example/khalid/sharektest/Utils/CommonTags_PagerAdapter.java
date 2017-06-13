package com.example.khalid.sharektest.Utils;

/**
 * Created by Abdelrahman on 6/9/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.khalid.sharektest.commonTags_interest_tab;
import com.example.khalid.sharektest.commonTags_share_tab;

public class CommonTags_PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CommonTags_PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                commonTags_share_tab shareTab = new commonTags_share_tab();
                return shareTab;
            case 1:
                commonTags_interest_tab interestTab = new commonTags_interest_tab();
                return interestTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}