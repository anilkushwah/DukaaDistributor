package com.dollop.distributor.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Const;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.ManageMemberModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageMemberActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_memberList;
    FrameLayout member_add;
    ImageView memberback;

    ManageMemberAdapter manageMemberAdapter;
    ArrayList<ManageMemberModel> memberModels = new ArrayList<>();
    TextView member_count_tv;
    Activity activity = ManageMemberActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_member);

        member_add = findViewById(R.id.member_add);
        memberback = findViewById(R.id.memberback);
        rv_memberList = findViewById(R.id.rv_memberList);
        member_count_tv = findViewById(R.id.member_count_tv);

        getmember();

        member_add.setOnClickListener(this);
        memberback.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == member_add) {
            Utils.I_finish(ManageMemberActivity.this, CreateSubMamberActivity.class, null);


        } else if (v == memberback) {
            Utils.I_clear(ManageMemberActivity.this, HomeActivity.class, null);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.I_clear(ManageMemberActivity.this, HomeActivity.class, null);
    }

    private void getmember() {
        final Dialog dialog = Utils.initProgressDialog(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.get_sub_member, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.E("get member::" + response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {

                        String msg = jsonObject.getString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        member_count_tv.setText("" + jsonArray.length() + " Members");
                        memberModels.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ManageMemberModel model = new ManageMemberModel();

                            if (!object.isNull("id")) {
                                model.setId(object.getString("id"));
                            }

                            if (!object.isNull("emp_id")) {
                                model.setEmp_id(object.getString("emp_id"));
                            }
                            if (!object.isNull("name")) {
                                model.setName(object.getString("name"));
                            }
                            if (!object.isNull("email")) {
                                model.setEmail(object.getString("email"));
                            }
                            if (!object.isNull("mobile")) {
                                model.setMobile(object.getString("mobile"));
                            }
                            if (!object.isNull("designation")) {
                                model.setDesignation(object.getString("designation"));
                            }
                            if (!object.isNull("access_type")) {
                                model.setAccess_type(object.getString("access_type"));
                            }
                            if (!object.isNull("image")) {
                                model.setProfile_pic(object.getString("image"));
                            }

                            memberModels.add(model);
                            rv_memberList.setLayoutManager(new LinearLayoutManager(ManageMemberActivity.this));
                            manageMemberAdapter = new ManageMemberAdapter(ManageMemberActivity.this, memberModels);
                            rv_memberList.setAdapter(manageMemberAdapter);

                            // ((HomeFragment)getActivity()).yourPublicMethod();
                        }

                        manageMemberAdapter.notifyDataSetChanged();
                    }else {
                        Utils.T(ManageMemberActivity.this, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (error.getClass().equals(TimeoutError.class)) {
                    String errorMessage = "Request timeout";
                    Utils.T(activity, errorMessage + " No Internet Connection available. Please try again");
                }
            }
        }) {
            @Override
            public String getCacheKey() {
                return super.getCacheKey();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());
                Utils.E("GetNewOrder:params:" + stringStringHashMap);
                return stringStringHashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }


    public class ManageMemberAdapter extends RecyclerView.Adapter<ManageMemberAdapter.MyViewHolder> {
        Context context;
        List<ManageMemberModel> manageMemberModels;
        int alldata;

        public ManageMemberAdapter(Context context, List<ManageMemberModel> manageMemberModels) {
            this.context = context;
            this.manageMemberModels = manageMemberModels;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.managemeber_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final ManageMemberModel currentitem = manageMemberModels.get(position);

            if (alldata == position) {
                holder.lastitem_space.setVisibility(View.VISIBLE);
            } else {
                holder.lastitem_space.setVisibility(View.GONE);
            }

            holder.name.setText(currentitem.getName().toString());
            holder.role.setText(currentitem.getDesignation().toString());

            String access = currentitem.getAccess_type().toString();

            holder.access_type_tv.setText(currentitem.getAccess_type());

            if (access.equals("Full Access")) {
                holder.ll_full_access.setVisibility(View.VISIBLE);
                holder.ll_partial_access.setVisibility(View.GONE);
            } else if (access.equals("Partial Access")) {
                holder.ll_full_access.setVisibility(View.GONE);
                holder.ll_partial_access.setVisibility(View.VISIBLE);
            }
            if (currentitem.getProfile_pic() != null) {
                Picasso.get().load(Const.URL.HOST_URL + currentitem.getProfile_pic()).error(R.drawable.ic_businessman).into(holder.image);
            }

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonalDeatilsActivity.class)
                            .putExtra("type", "manage member")
                            .putExtra("data", currentitem);
                    startActivity(intent);
                }
            });

            holder.detail_LL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonalDeatilsActivity.class)
                            .putExtra("type", "manage member")
                            .putExtra("data", currentitem);
                    startActivity(intent);
                }
            });

            holder.ll_member_menu.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.bf_menu_popup, popup.getMenu());
                    //popup.show();

                    @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popup.getMenu(), v);
                    menuHelper.setForceShowIcon(true);
                    menuHelper.setGravity(Gravity.END);
                    menuHelper.show();


                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();

                            if (R.id.menu_editdetails == id) {
                                // Utils.I(activity, EditSubMemberActivity.class, null);
                                Intent intent = new Intent(context, EditSubMemberActivity.class)
                                        .putExtra("data", currentitem);
                                startActivity(intent);

                            } else if (R.id.menu_delete == id) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Delete");
                                alert.setMessage("Are you sure you want to delete?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        deleteMember(currentitem.getId());
                                        dialog.dismiss();
                                    }
                                });

                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                alert.show();

                            }
                            return true;
                        }
                    });


                } //
            });


        }

        private void deleteMember(final String Id) {
            final Dialog dialog = Utils.initProgressDialog(activity);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.delete_sub_member, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Utils.E("getNewOrder::" + response);
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");

                        if (jsonObject.getInt("status") == 200) {

                            memberModels.clear();
                            rv_memberList.setLayoutManager(new LinearLayoutManager(ManageMemberActivity.this));
                            manageMemberAdapter = new ManageMemberAdapter(ManageMemberActivity.this, memberModels);
                            rv_memberList.setAdapter(manageMemberAdapter);
                            manageMemberAdapter.notifyDataSetChanged();

                            getmember();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    if (error.getClass().equals(TimeoutError.class)) {
                        String errorMessage = "Request timeout";
                        Utils.T(activity, errorMessage + " No Internet Connection available. Please try again");
                    }
                }
            }) {
                @Override
                public String getCacheKey() {
                    return super.getCacheKey();
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("id", Id);
                    Utils.E("memberdelete :params:" + stringStringHashMap);
                    return stringStringHashMap;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(activity);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);


        }

        @Override
        public int getItemCount() {
            alldata = (manageMemberModels.size() - 1);
            return manageMemberModels.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView name, role, access_type_tv;
            private ImageView image;
            LinearLayout ll_member_menu, ll_full_access, ll_partial_access, lastitem_space, detail_LL;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.image);
                role = itemView.findViewById(R.id.role);
                ll_partial_access = itemView.findViewById(R.id.ll_partial_access);
                ll_full_access = itemView.findViewById(R.id.ll_full_access);
                ll_member_menu = itemView.findViewById(R.id.ll_member_menu);
                lastitem_space = itemView.findViewById(R.id.lastitem_space);
                detail_LL = itemView.findViewById(R.id.detail_LL);
                access_type_tv = itemView.findViewById(R.id.access_type_tv);
            }
        }
    }


}