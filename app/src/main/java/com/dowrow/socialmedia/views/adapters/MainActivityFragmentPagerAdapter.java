package com.dowrow.socialmedia.views.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.views.fragments.GlobalFeedFragment;
import com.dowrow.socialmedia.views.fragments.HomeFeedFragment;
import com.dowrow.socialmedia.views.fragments.SelfProfileFragment;
import com.dowrow.socialmedia.views.fragments.UserSearchFragment;

public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private int[] imageResId = {
            R.drawable.ic_home,
            R.drawable.ic_global_feed,
            R.drawable.ic_search,
            R.drawable.ic_self_profile,
    };

    public MainActivityFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageResId.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFeedFragment.newInstance();
            case 1:
                return GlobalFeedFragment.newInstance();
            case 2:
                return UserSearchFragment.newInstance();
            case 3:
                return SelfProfileFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

}