package com.dollop.distributor.database;

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

import com.dollop.distributor.Activity.CreateAccountActivity;
import com.dollop.distributor.Activity.EditProfileFragment;
import com.dollop.distributor.Fragment.ProductChildFragment;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.adapter.OfferAdapter;
import com.dollop.distributor.model.CityResponse;
import com.dollop.distributor.model.OfferResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.AddproductViewHolder> implements Filterable {
    Context context;
    ArrayList<CityResponse> mStateRespones;
    private ArrayList<CityResponse> mListData;
    Dialog mDialog;
    String incomimg_type;

    public CityAdapter(Context context, ArrayList<CityResponse> mList, Dialog dialog, String type) {
        this.context = context;
        this.mStateRespones = mList;
        this.mListData = mList;
        this.mDialog = dialog;
        this.incomimg_type = type;

    }

    @NonNull
    @Override
    public AddproductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.scity_adapter_layout, null);
        return new AddproductViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final AddproductViewHolder holder, final int position) {
        final CityResponse mStateRespone = mStateRespones.get(position);
        holder.country_tv.setText(mStateRespone.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.dismiss();
                if (incomimg_type.equals("creatAccount")) {
                    ((CreateAccountActivity) context).setCityList(mStateRespone.name, mStateRespone.id);
                } else {
                    ((EditProfileFragment) context).setCityList(mStateRespone.name,mStateRespone.id );
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mStateRespones.size();
    }


    public class AddproductViewHolder extends RecyclerView.ViewHolder {
        TextView country_tv;

        public AddproductViewHolder(@NonNull View itemView) {
            super(itemView);

            country_tv = itemView.findViewById(R.id.country_tv);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mStateRespones = mListData;
                } else {
                    ArrayList<CityResponse> filteredList = new ArrayList<>();
                    for (CityResponse row : mListData) {

                        if (row.name.toLowerCase().contains(charSequence.toString().toLowerCase()))
                            filteredList.add(row);

                    }

                    mStateRespones = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mStateRespones;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mStateRespones = (ArrayList<CityResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}


