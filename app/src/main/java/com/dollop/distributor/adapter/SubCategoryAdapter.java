package com.dollop.distributor.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.distributor.Activity.CreateOfferActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.model.CategoryModel;
import com.dollop.distributor.model.SubCategoryModel;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.AddproductViewHolder> implements Filterable {
    Context context;
    private ArrayList<SubCategoryModel> categotyNameList = new ArrayList<>();

    private ArrayList<SubCategoryModel> mListData;
    Dialog dialog ;

    public SubCategoryAdapter(Context context, ArrayList<SubCategoryModel> mList, Dialog dialog1) {
        this.context = context;
        this.categotyNameList = mList;
        this.mListData = mList;
        this.dialog = dialog1;


    }

    @NonNull
    @Override
    public AddproductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_adapter_layout, null);
        return new AddproductViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final AddproductViewHolder holder, final int position) {
        final SubCategoryModel mStateRespone = categotyNameList.get(position);
        holder.country_tv.setText(mStateRespone.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // CreateOfferActivity.category_id = mStateRespone.getName();
                CreateOfferActivity.category_id = mStateRespone.getId();
                CreateOfferActivity.category_name = mStateRespone.getName();
                CreateOfferActivity.category_type = "sub_category";
                ((CreateOfferActivity)context).setCategoryName();
                dialog.dismiss();


            }
        });
    }

    @Override
    public int getItemCount() {
        return categotyNameList.size();
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
                    categotyNameList = mListData;
                } else {
                    ArrayList<SubCategoryModel> filteredList = new ArrayList<>();
                    for (SubCategoryModel row : mListData) {

                        if (row.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            filteredList.add(row);

                    }

                    categotyNameList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = categotyNameList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categotyNameList = (ArrayList<SubCategoryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}




