package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dollop.distributor.R;

public class DeliveryCompletedActivity extends AppCompatActivity {

    ImageView queimage;
    PopupWindow popupWindow;
    RelativeLayout rela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_completed);

        queimage = findViewById(R.id.que_image);
        rela = findViewById(R.id.rela);

        queimage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) DeliveryCompletedActivity.this.getSystemService(DeliveryCompletedActivity.this.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.deliverynumber_popup,null);

                popupWindow = new PopupWindow(customView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                //display the popup window
              //  popupWindow.showAtLocation(rela, Gravity.RIGHT, 0, 0);
                popupWindow.showAtLocation(rela, Gravity.TOP | Gravity.RIGHT, 0, 130);

                RelativeLayout relative_popupkRelativeLayout =   customView.findViewById(R.id.relative_popupk);
                relative_popupkRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });



               /* final Dialog dialog = new Dialog(DeliveryCompletedActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
                dialog.setContentView(R.layout.deliverynumber_popup);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                dialog.setCancelable(true);*/

            }
        });
    }
}
