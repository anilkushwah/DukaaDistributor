package com.dollop.distributor.adapter;

/*
public class BreakfastdaAdapter extends RecyclerView.Adapter<BreakfastdaAdapter.MyViewHolder> {
    Context context;
    List<breakfastdaModel> breakfastdaModels = new ArrayList<>();

    int allitemdata;
    String stock;


    public BreakfastdaAdapter(Context context, List<breakfastdaModel> breakfastdaModels) {
        this.context = context;
        this.breakfastdaModels = breakfastdaModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.breakfast_itemlist,null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        breakfastdaModel  current  = breakfastdaModels.get(position);

        holder.bf_name.setText(current.getName());
        holder.bf_amount.setText(current.getAmount());


          stock = current.getStock().toString();

        if(stock.equals("Out of Stock")){
            holder.bf_stock.setText(current.getStock());
            holder.bf_stock.setTextColor(Color.parseColor("#ec1515"));
          //  holder.bf_stock.setTextColor(R.color.colorRed);
        }
        else{
            holder.bf_stock.setText(current.getStock());
            holder.bf_stock.setTextColor(Color.parseColor("#14a809"));
        }

      //  if (allitemdata == position){
       if (position == (getItemCount()-1)){

            holder.free_space_Add_items.setVisibility(View.VISIBLE);
        } else {
            holder.free_space_Add_items.setVisibility(View.GONE);
        }

        holder.bf_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.bf_menu_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem menu_item) {
                        int id = menu_item.getItemId();

                        if (R.id.menu_editdetails == id){
                          ////////
                            final Dialog popdialog = new Dialog(getContext()) ;
                            popdialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
                            popdialog.setContentView(R.layout.add_itempopup);
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(popdialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                            popdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            popdialog.getWindow().setAttributes(lp);
                            // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

                            ImageView additemClose = popdialog.findViewById(R.id.add_item_close);
                            Button btn_add_product = popdialog.findViewById(R.id.btn_add_product);
                            sp_categoty = popdialog.findViewById(R.id.sp_product_category);
                            sp_sub_category= popdialog.findViewById(R.id.sp_product_sub_category);
                            sp_unit_per_case= popdialog.findViewById(R.id.sp_unit_per_case);
                            sp_total_unit= popdialog.findViewById(R.id.sp_total_unit);
                            sp_packsize= popdialog.findViewById(R.id.sp_packsize);

                            add_product_name= popdialog.findViewById(R.id.add_product_name);
                            sku_code= popdialog.findViewById(R.id.sku_code);
                            item_code= popdialog.findViewById(R.id.item_code);
                            price_per_case= popdialog.findViewById(R.id.price_per_Case);
                            inc_vat= popdialog.findViewById(R.id.incl_vat);
                            retail_price= popdialog.findViewById(R.id.retail_price);
                            pro_discription= popdialog.findViewById(R.id.product_discription);
                            btn_add_product= popdialog.findViewById(R.id.btn_add_product);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, categotyNameList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_categoty.setAdapter(arrayAdapter);
                            sp_categoty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String Name = parent.getItemAtPosition(position).toString();

                                    if(position>0){
                                        SubCategoty();}

                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {
                                }
                            });

                            additemClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popdialog.dismiss();
                                }
                            });
                            btn_add_product.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    if (!UserAccount.isEmpty(add_product_name)){
                                        add_product_name.setError("Please enter product name");
                                        add_product_name.requestFocus();
                                    }

                                    else if (!UserAccount.isEmpty(sku_code)){
                                        sku_code.setError("Please enter sku code");
                                        sku_code.requestFocus();
                                    }
                                    else if (!UserAccount.isEmpty(item_code)){
                                        item_code.setError("Please enter item code");
                                        item_code.requestFocus();
                                    }
                                    else if (!UserAccount.isEmpty(price_per_case)){
                                        price_per_case.setError("Please enter price per case");
                                        price_per_case.requestFocus();
                                    }
                                    else if (!UserAccount.isEmpty(inc_vat)){
                                        inc_vat.setError("Please enter factory price");
                                        inc_vat.requestFocus();
                                    }
                                    else if (!UserAccount.isEmpty(retail_price)){
                                        retail_price.setError("Please enter retail price");
                                        retail_price.requestFocus();
                                    }
                                    else if (!UserAccount.isEmpty(pro_discription)){
                                        pro_discription.setError("Please enter discription");
                                        pro_discription.requestFocus();
                                    }

                                    else{
                                        AddProduct();
                                    }
                                }
                            });
                            popdialog.show();

                            /////
                        }

                        else  if (R.id.menu_delete == id){}

                        return true;
                    }
                });

                MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popup.getMenu(), v);
                menuHelper.setForceShowIcon(true);
                menuHelper.setGravity(Gravity.END);
                menuHelper.show();
                //popup.show();

            }


        });



    }

    @Override
    public int getItemCount() {

      //  allitemdata=(breakfastdaModels.size()-1);
        return breakfastdaModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
       private   TextView bf_name,bf_stock,bf_amount;
       ImageView bf_menu;
       LinearLayout free_space_Add_items;
       Switch breakfastswitch;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            free_space_Add_items = itemView.findViewById(R.id.free_space_Add_items);
            bf_name = itemView.findViewById(R.id.bf_name);
            bf_stock = itemView.findViewById(R.id.bf_stock);
            bf_amount = itemView.findViewById(R.id.bf_amount);
            bf_menu = itemView.findViewById(R.id.bf_menu);
        }
    }
}
*/
