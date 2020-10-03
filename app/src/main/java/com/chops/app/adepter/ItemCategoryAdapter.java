package com.chops.app.adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chops.app.R;
import com.chops.app.activity.ItemDetailsActivity;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.Productlist;
import com.chops.app.utils.MySpannable;
import com.chops.app.utils.SessionManager;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chops.app.retrofit.APIClient.Base_URL;


public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ViewHolder> {

    List<Productlist> listdata;
    Context context;
    final int[] count = {0};
    DatabaseHelper helper;
    SessionManager sessionManager;

    public ItemCategoryAdapter(List<Productlist> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.subcategory_item, parent, false);
        helper = new DatabaseHelper(context);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Productlist produc = listdata.get(position);
        holder.txtDprice.setPaintFlags(holder.txtDprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context).load(Base_URL + "/" + produc.getImage()).placeholder(R.drawable.slider).into(holder.imageView);
        holder.txtTitle.setText("" + produc.getName());
        holder.txtDesc.setText("" + produc.getSdesc());

        holder.txtGross.setText("" + produc.getGross());
        holder.txtPicTitel.setText("" + produc.getTypes());
        holder.txtPic.setText("" + produc.getPipack());
        holder.txtPrice.setText(sessionManager.getStringData(SessionManager.CURRNCY) + " " + produc.getPrice());
        holder.txtDprice.setText(sessionManager.getStringData(SessionManager.CURRNCY) + " " + produc.getDiscount());

        makeTextViewResizable(holder.txtDesc, 3, "See More", true);
        holder.txtNett.setText("KG :");
        holder.txtNet.setText("" + produc.getNet());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, ItemDetailsActivity.class).putExtra("MyClass", listdata.get(position)));
            }
        });
        int qrt = helper.getCard(produc.getId(), produc.getCid());
        if (qrt != -1) {
            count[0] = qrt;
            holder.txtCount.setText("" + count[0]);
            holder.txtCount.setVisibility(View.VISIBLE);

        } else {
            holder.txtCount.setText("0");

        }
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtCount.setVisibility(View.VISIBLE);
                holder.imgMinus.setVisibility(View.VISIBLE);
                count[0] = Integer.parseInt(holder.txtCount.getText().toString());
                count[0] = count[0] + 1;
                holder.txtCount.setText("" + count[0]);
                produc.setContity(count[0]);
                Log.e("INsert", "--> " + helper.insertData(produc));
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0] = Integer.parseInt(holder.txtCount.getText().toString());
                count[0] = count[0] - 1;
                if (count[0] <= 0) {
                    holder.txtCount.setText("0");
                    helper.deleteRData(produc.getId(), String.valueOf(produc.getCid()));
                } else {
                    holder.txtCount.setVisibility(View.VISIBLE);
                    holder.txtCount.setText("" + count[0]);
                    produc.setContity(count[0]);
                    Log.e("INsert", "--> " + helper.insertData(produc));
                }

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
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.txt_net)
        TextView txtNet;
        @BindView(R.id.txt_nett)
        TextView txtNett;
        @BindView(R.id.txt_gross)
        TextView txtGross;
        @BindView(R.id.txt_pic_titel)
        TextView txtPicTitel;
        @BindView(R.id.txt_pic)
        TextView txtPic;
        @BindView(R.id.img_minus)
        ImageView imgMinus;
        @BindView(R.id.img_add)
        ImageView imgAdd;
        @BindView(R.id.txt_price)
        TextView txtPrice;
        @BindView(R.id.txt_dprice)
        TextView txtDprice;
        @BindView(R.id.txt_count)
        TextView txtCount;
        @BindView(R.id.lvl_category)
        LinearLayout lvlCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    try {
                        int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

}