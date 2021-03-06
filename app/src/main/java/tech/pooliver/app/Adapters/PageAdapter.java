package tech.pooliver.app.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import tech.pooliver.app.Fragments.tab1;
import tech.pooliver.app.Fragments.tab2;
import tech.pooliver.app.Fragments.tab3;

public class PageAdapter extends FragmentPagerAdapter {

    int tabCount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new tab1();
            case 1:
                return new tab2();
            case 2:
                return new tab3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
