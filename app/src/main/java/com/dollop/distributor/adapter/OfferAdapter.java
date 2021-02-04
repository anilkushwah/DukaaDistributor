package com.dollop.distributor.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Fragment.ProductChildFragment;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.model.OfferResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.AddproductViewHolder> implements Filterable {
    Context context;
    private ArrayList<OfferResponse> mOfferResponseArrayList = new ArrayList<>();
    private ArrayList<OfferResponse> mList = new ArrayList<>();
    String type;
    Dialog dialog;

    public OfferAdapter(Context context, ArrayList<OfferResponse> mList, String type, Dialog mDialog) {
        this.context = context;
        this.mOfferResponseArrayList = mList;
        this.mList = mList;
        this.type = type;
        this.dialog = mDialog;
    }

    @NonNull
    @Override
    public AddproductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.offer_adapter_layout, null);
        return new AddproductViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final AddproductViewHolder holder, final int position) {

        final OfferResponse mStateRespone = mOfferResponseArrayList.get(position);
        holder.name.setText( mStateRespone.getTitle());
        if (mStateRespone.getType().equals("FLAT")) {
            holder.category_tv.setText(mStateRespone.getType() + " " + context.getString(R.string.currency_sign) +
                    mStateRespone.getDiscountAmount()+" On "+mStateRespone.getCategoryType());

        } else {
            holder.category_tv.setText(mStateRespone.getType() + " " + mStateRespone.getDiscountPercent() +" On "+mStateRespone.getCategoryType());
        }

        holder.discription_tv.setText(mStateRespone.getDescription());
        holder.percent_tv.setText("");
        holder.type_tv.setText(mStateRespone.getType());
        if (mStateRespone.getImage() != null) {
            Picasso.get().load(Const.URL.HOST_URL + mStateRespone.getImage()).error(R.drawable.ic_businessman).into(holder.image);
        }

        holder.valid_from_tv.setText("Valid From: " + mStateRespone.getValidFrom());
        holder.valid_to_tv.setText("Valid To: " + mStateRespone.getValidTo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("addProduct")) {
                    ProductChildFragment.offername = mStateRespone.getTitle();
                    ProductChildFragment.offer_id = mStateRespone.getId();
                    ProductChildFragment.setOffername();
                    dialog.dismiss();
                }
            }
        });
//

    }

    @Override
    public int getItemCount() {
        return mOfferResponseArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mOfferResponseArrayList = mList;
                } else {
                    ArrayList<OfferResponse> filteredList = new ArrayList<>();
                    for (OfferResponse row : mList) {

                        if (row.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            filteredList.add(row);

                    }

                    mOfferResponseArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mOfferResponseArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mOfferResponseArrayList = (ArrayList<OfferResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class AddproductViewHolder extends RecyclerView.ViewHolder {

        private TextView name, discription_tv, category_tv, valid_to_tv, percent_tv;
        TextView valid_from_tv, type_tv;
        private ImageView image;


        public AddproductViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            discription_tv = itemView.findViewById(R.id.discription_tv);

            category_tv = itemView.findViewById(R.id.category_tv);
            valid_to_tv = itemView.findViewById(R.id.valid_to_tv);
            percent_tv = itemView.findViewById(R.id.percent_tv);
            valid_from_tv = itemView.findViewById(R.id.valid_from_tv);
            type_tv = itemView.findViewById(R.id.type_tv);

        }
    }

}




