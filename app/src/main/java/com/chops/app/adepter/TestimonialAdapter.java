package com.chops.app.adepter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.chops.app.R;
import com.chops.app.model.Testimonial;

import java.util.List;

public class TestimonialAdapter extends PagerAdapter {
    private Activity activity;
    private List<Testimonial> testimonialList;

    public TestimonialAdapter(Activity activity, List<Testimonial> imagesArray) {
        this.activity = activity;
        this.testimonialList = imagesArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.textview_item, container, false);
        TextView txtMsg = viewItem.findViewById(R.id.txt_msg);
        TextView txt_name = viewItem.findViewById(R.id.txt_name);
        txtMsg.setText("" + testimonialList.get(position).getMessage());
        txt_name.setText("" + testimonialList.get(position).getName());
        ((ViewPager) container).addView(viewItem);
        return viewItem;
    }

    @Override
    public int getCount() {
        return testimonialList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}