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

import com.dollop.distributor.Activity.CreateAccountActivity;
import com.dollop.distributor.Activity.EditProfileFragment;
import com.dollop.distributor.R;
import com.dollop.distributor.model.StateRespone;

import java.util.ArrayList;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.AddproductViewHolder> implements Filterable {
    Context context;
    ArrayList<StateRespone> mStateRespones;
    private ArrayList<StateRespone> mListData;
    String type = "";
    Dialog mDialog;


    public StateAdapter(Context context, ArrayList<StateRespone> mList, String to, Dialog dialog) {
        this.context = context;
        this.mStateRespones = mList;
        this.mListData = mList;
        this.type = to;
        this.mDialog = dialog;

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
        final StateRespone mStateRespone = mStateRespones.get(position);
        holder.country_tv.setText(mStateRespone.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("EditProfile")){


                  EditProfileFragment.to_state_name = mStateRespone.name;
                    EditProfileFragment.to_state_id = mStateRespone.id;
                    EditProfileFragment.cityToMethod(mStateRespone.id);
                    mDialog.dismiss();

                }else if (type.equals("FROM"))

                    CreateAccountActivity.to_state_name = mStateRespone.name;
                    CreateAccountActivity.to_state_id = mStateRespone.id;
                    CreateAccountActivity.cityToMethod(mStateRespone.id);
                    mDialog.dismiss();



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
                    ArrayList<StateRespone> filteredList = new ArrayList<>();
                    for (StateRespone row : mListData) {

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
                mStateRespones = (ArrayList<StateRespone>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}



