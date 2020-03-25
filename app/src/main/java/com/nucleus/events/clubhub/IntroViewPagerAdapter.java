package com.nucleus.events.clubhub;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mcontext;
    List<Screen_item> mListScreen;

    public IntroViewPagerAdapter(Context mcontext, List<Screen_item> mListScreen) {
        this.mcontext = mcontext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutscreen = inflater.inflate(R.layout.layout_screen, null);

        ImageView imageslider = layoutscreen.findViewById(R.id.img1);
        TextView s_title = layoutscreen.findViewById(R.id.title_ob);
        TextView s_desc = layoutscreen.findViewById(R.id.desc_ob);

        s_title.setText(mListScreen.get(position).getStitle());
        s_desc.setText(mListScreen.get(position).getSdescription());
        imageslider.setImageResource(mListScreen.get(position).getScreenimg());

        container.addView(layoutscreen);
        return layoutscreen;

    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeViewInLayout((View) object);

    }
}
