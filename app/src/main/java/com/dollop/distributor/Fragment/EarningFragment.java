package com.dollop.distributor.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.MarshMallowPermission;
import com.dollop.distributor.UtilityTools.NetworkUtil;
import com.dollop.distributor.UtilityTools.SavedData;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.TotalEarningAdapter;
import com.dollop.distributor.database.UserDataHelper;
import com.dollop.distributor.model.Datum;
import com.dollop.distributor.model.OrderModel;
import com.dollop.distributor.retrofit.ApiClient;
import com.dollop.distributor.retrofit.ApiInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;

public class EarningFragment extends Fragment {

    TextView refresh_data_tv;
    RecyclerView rv_total_earning;
    ArrayList<Datum> totalEarningmodelArrayList = new ArrayList<>();
    TotalEarningAdapter totalEarningAdapter;
    LinearLayout date_to_layout, date_from_layout;
    DatePickerDialog datePickerDialog;
    TextView date_from_tv, date_to_tv;
    int mYear_To = 0;
    int mMonth_To = 0;
    int mDay_To = 0;
    private long fromTimeMils;
    boolean seletectValue = false;
    String excel_URI = "";
    ProgressDialog mProgressDialog;
    TextView total_earning_tv, title_tv;
    Uri path;
    ImageView back_back, download_tv;
    String From="",To="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_earning, container, false);

        initializationView(root);
        totalEarningmodelArrayList = new ArrayList<>();
        EarningMethod("5");
        return root;

    }


    private void EarningMethod(final String status) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("status", status);
        stringStringHashMap.put("from_date", From);
        stringStringHashMap.put("to_date", To);
      //  stringStringHashMap.put("excel", "GenerateExcel");
        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

        }


        Utils.E("checkNewOrder::" + stringStringHashMap);

        Call<OrderModel> call = apiService.getAllOrder(stringStringHashMap);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, retrofit2.Response<OrderModel> response) {
                dialog.dismiss();
                try {
                    OrderModel body = response.body();
                    totalEarningmodelArrayList.clear();

                    if (body.status == 200) {

                        rv_total_earning.getRecycledViewPool().clear();

                        totalEarningmodelArrayList.addAll(body.data);

                        rv_total_earning.setLayoutManager(new LinearLayoutManager(getActivity()));
                        totalEarningAdapter = new TotalEarningAdapter(getActivity(), totalEarningmodelArrayList);
                        rv_total_earning.setAdapter(totalEarningAdapter);
                        totalEarningAdapter.notifyDataSetChanged();
                       // excel_URI = body.ExcelFileName;

                        total_earning_tv.setText(getActivity().getString(R.string.currency_sign)+" " + body.earnTotalAmount);
                    } else {
                        Utils.T(getActivity(), body.message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

    private void EarningMethodExcel(final String status) {
        final Dialog dialog = Utils.initProgressDialog(getActivity());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("status", status);
        stringStringHashMap.put("from_date", "");
        stringStringHashMap.put("to_date", "");
        stringStringHashMap.put("excel", "GenerateExcel");
        if (SavedData.get_AccessType().equals("Distributor")) {
            stringStringHashMap.put("distributor_id", UserDataHelper.getInstance().getList().get(0).getDistributorId());

        } else {
            stringStringHashMap.put("distributor_id", SavedData.get_MainMemberId());

        }


        Utils.E("checkNewOrder::" + stringStringHashMap);

        Call<OrderModel> call = apiService.getAllOrder(stringStringHashMap);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, retrofit2.Response<OrderModel> response) {
                dialog.dismiss();
                try {
                    OrderModel body = response.body();
                    totalEarningmodelArrayList.clear();

                    if (body.status == 200) {
                        Utils.T(getActivity(), body.message);
                    } else {
                        Utils.T(getActivity(), body.message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                dialog.dismiss();

            }
        });

    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(String... strings) {
            Log.v("TAG", "doInBackground() Method invoked ");

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = System.currentTimeMillis() + ".xlsx";  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);
            Log.v("TAG", "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
            Log.v("TAG", "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());


            path = FileProvider.getUriForFile(getActivity(), "com.dollop.distributor.provider", pdfFile);


            try {
                pdfFile.createNewFile();
                Log.v("TAG", "doInBackground() file created" + pdfFile);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAG", "doInBackground() error" + e.getMessage());
                Log.e("TAG", "doInBackground() error" + e.getStackTrace());


            }
            FileDownloader fileDownloader = new FileDownloader();
            fileDownloader.downloadFile(fileUrl, pdfFile);
            Log.v("TAG", "doInBackground() file download completed");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            OpenFileMethod();
        }
    }

    public class FileDownloader {

        private static final String TAG = "FileDownloader";

        private static final int MEGABYTE = 1024 * 1024;

        public void downloadFile(String fileUrl, File directory) {
            try {
                // Log.v(TAG, "downloadFile() invoked ");
                // Log.v(TAG, "downloadFile() fileUrl " + fileUrl);
                // Log.v(TAG, "downloadFile() directory " + directory);

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
                Log.v(TAG, "downloadFile() completed ");
                mProgressDialog.dismiss();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "downloadFile() error" + e.getMessage());
                Log.e(TAG, "downloadFile() error" + e.getStackTrace());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "downloadFile() error" + e.getMessage());
                Log.e(TAG, "downloadFile() error" + e.getStackTrace());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "downloadFile() error" + e.getMessage());
                Log.e(TAG, "downloadFile() error" + e.getStackTrace());
            }
        }
    }

    private void OpenFileMethod() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Download!!!")
                .setMessage("Open Your Excel File...")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent excelIntent = new Intent(Intent.ACTION_VIEW);
                        excelIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        excelIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        excelIntent.setDataAndType(path, "application/vnd.ms-excel");
                        //  excelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        try {
                            startActivity(excelIntent);
                        } catch (ActivityNotFoundException e) {
                            Utils.T(getActivity(), "No Application available to viewExcel");
                            //   Toast.makeText(getActivity(), "No Application available to viewExcel", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();


    }

    private void initializationView(View root) {

        rv_total_earning = root.findViewById(R.id.rv_total_earning);
        refresh_data_tv = root.findViewById(R.id.refresh_data_tv);
        date_to_layout = root.findViewById(R.id.date_to_layout);
        date_from_layout = root.findViewById(R.id.date_from_layout);
        date_from_tv = root.findViewById(R.id.date_from_tv);
        date_to_tv = root.findViewById(R.id.date_to_tv);
        download_tv = root.findViewById(R.id.download_tv);
        total_earning_tv = root.findViewById(R.id.total_earning_tv);
        back_back = root.findViewById(R.id.back_back);
        title_tv = root.findViewById(R.id.title_tv);

        boolean status = NetworkUtil.getConnectivityStatus(getActivity());

        if (status == true) {
        } else {
            Utils.T(getActivity(), "No Internet Connection available. Please try again");
        }

        back_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        refresh_data_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EarningMethod("5");
            }
        });
        download_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* MarshMallowPermission marshMallowPermission = new MarshMallowPermission(getActivity());

                if (marshMallowPermission.checkPermissionForExternalStorage()) {

                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setMessage("Downloading file..");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    new DownloadFile().execute(ApiClient.BASE_URL + excel_URI, "");
                } else {
                    marshMallowPermission.requestPermissionForExternalStorage();
                }*/
                EarningMethodExcel("5");

            }
        });
        date_to_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seletectValue) {
                    datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth);
                                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                                    String dateString = format.format(calendar.getTime());
                                    date_to_tv.setText(dateString);
                                    To=dateString;

                                    totalEarningmodelArrayList.clear();
                                    rv_total_earning.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    totalEarningAdapter = new TotalEarningAdapter(getActivity(), totalEarningmodelArrayList);
                                    rv_total_earning.setAdapter(totalEarningAdapter);
                                    totalEarningAdapter.notifyDataSetChanged();

                                    EarningMethod("5");

                                }
                            }, mYear_To, mMonth_To, mDay_To);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(fromTimeMils);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                } else {
                    Utils.T(getActivity(), "Seletct First Date From");
                    //  Toast.makeText(getActivity(), "Seletct First Date From", Toast.LENGTH_LONG).show();
                }


            }
        });
        date_from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date_to_tv.setText("");
                mYear_To = 0;
                mMonth_To = 0;
                mDay_To = 0;

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                                String dateString = format.format(calendar.getTime());
                                date_from_tv.setText(dateString);
                                From=dateString;
                                fromTimeMils = calendar.getTimeInMillis();

                                mYear_To = calendar.get(Calendar.YEAR);
                                mMonth_To = calendar.get(Calendar.MONTH);
                                mDay_To = calendar.get(Calendar.DAY_OF_MONTH);
                                seletectValue = true;

                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });


    }
}
