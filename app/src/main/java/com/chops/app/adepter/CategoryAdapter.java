package com.chops.app.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chops.app.R;
import com.chops.app.fragment.ItemCategoryFragment;
import com.chops.app.model.Catlist;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chops.app.retrofit.APIClient.Base_URL;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Catlist> listdata;
    Context context;

    public CategoryAdapter(List<Catlist> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Catlist myListData = listdata.get(position);
        holder.txtTitle.setText(myListData.getTitle() + "(" + myListData.getmCount() + ")");
        Glide.with(context).load(Base_URL + "/" + myListData.getImg()).placeholder(R.drawable.slider).into(holder.imageView);
        holder.lvlCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_frame, new ItemCategoryFragment().newInstance(position, "")).addToBackStack(null).commit();

            }
        });
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.lvl_category)
        LinearLayout lvlCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}