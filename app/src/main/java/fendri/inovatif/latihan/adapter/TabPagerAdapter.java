package fendri.inovatif.latihan.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private List<String> listTitle = new ArrayList<String>();
    private List<Fragment> listFragment = new ArrayList<Fragment>();

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void fragment(Fragment fragment, String title) {
        listFragment.add(fragment);
        listTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
}
