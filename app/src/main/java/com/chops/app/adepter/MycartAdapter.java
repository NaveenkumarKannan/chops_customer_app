package com.chops.app.adepter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chops.app.R;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.Productlist;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chops.app.fragment.MyCartFragment.txtNoitem;
import static com.chops.app.fragment.MyCartFragment.txtTotals;
import static com.chops.app.retrofit.APIClient.Base_URL;


public class MycartAdapter extends RecyclerView.Adapter<MycartAdapter.ViewHolder> {
    List<Productlist> listdata;
    Context context;
    final int[] count = {0};

    DatabaseHelper helper;
    SessionManager sessionManager;

    public MycartAdapter(List<Productlist> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
        helper = new DatabaseHelper(context);
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.mycart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Productlist myListData = listdata.get(position);

        Glide.with(context).load(Base_URL + "/" + myListData.getImage()).into(holder.imageView);
        holder.txtTitle.setText("" + myListData.getName());

        holder.txtPrice.setText(sessionManager.getStringData(SessionManager.CURRNCY) + " " + myListData.getPrice());
        holder.txtCount.setText("" + myListData.getContity());

        int qrt = helper.getCard(myListData.getId(), myListData.getCid());
        if (qrt != -1) {
            count[0] = qrt;
            holder.txtCount.setText("" + count[0]);
            holder.txtCount.setVisibility(View.VISIBLE);
        } else {
            holder.txtCount.setVisibility(View.INVISIBLE);
            holder.txtCount.setVisibility(View.INVISIBLE);
        }

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.txtCount.setVisibility(View.VISIBLE);
                holder.imgMinus.setVisibility(View.VISIBLE);
                count[0] = Integer.parseInt(holder.txtCount.getText().toString());

                count[0] = count[0] + 1;
                holder.txtCount.setText("" + count[0]);
                myListData.setContity(count[0]);
                Log.e("INsert", "--> " + helper.insertData(myListData));
                total();
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0] = Integer.parseInt(holder.txtCount.getText().toString());
                count[0] = count[0] - 1;
                if (count[0] <= 0) {
                    holder.txtCount.setText("" + count[0]);
                    holder.txtCount.setVisibility(View.INVISIBLE);
                    helper.deleteRData(myListData.getId(), String.valueOf(myListData.getCid()));
                    listdata.remove(myListData);
                    notifyDataSetChanged();
                } else {
                    holder.txtCount.setVisibility(View.VISIBLE);
                    holder.txtCount.setText("" + count[0]);
                    myListData.setContity(count[0]);
                    Log.e("INsert", "--> " + helper.insertData(myListData));
                }
                total();
            }
        });

    }

    public void total() {
        double total = 0;
        for (int i = 0; i < listdata.size(); i++) {
            GetService.skin_type=listdata.get(i).getSkin();
            GetService.pieces_type=listdata.get(i).getPieces();
            Productlist productlist = listdata.get(i);
            double temp = Double.parseDouble(productlist.getPrice()) * productlist.getContity();
            total = total + temp;
        }
        txtTotals.setText(sessionManager.getStringData(SessionManager.CURRNCY) + +total);
        txtNoitem.setText(listdata.size() + " items Added");
        GetService.totalTemp=total;

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
        @BindView(R.id.img_minus)
        ImageView imgMinus;
        @BindView(R.id.txt_count)
        TextView txtCount;
        @BindView(R.id.img_add)
        ImageView imgAdd;
        @BindView(R.id.txt_price)
        TextView txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}