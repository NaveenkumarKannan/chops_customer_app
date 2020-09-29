package com.chops.app.adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chops.app.R;
import com.chops.app.activity.ItemDetailsActivity;
import com.chops.app.model.Productlist;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chops.app.retrofit.APIClient.Base_URL;


public class PopularHomeAdapter extends RecyclerView.Adapter<PopularHomeAdapter.ViewHolder> {
    List<Productlist> listdata;
    Context context;

    public PopularHomeAdapter(List<Productlist> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.popular_homeitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Productlist myListData = listdata.get(position);
        holder.txtTitel.setText(myListData.getName());
        Glide.with(context).load(Base_URL + "/" + myListData.getImage()).placeholder(R.drawable.slider).into(holder.imageView);

        holder.lvlCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ItemDetailsActivity.class).putExtra("MyClass", listdata.get(position)));
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
        @BindView(R.id.txt_titel)
        TextView txtTitel;

        @BindView(R.id.lvl_category)
        LinearLayout lvlCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}