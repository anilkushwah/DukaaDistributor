package com.dollop.distributor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utils;
import com.dollop.distributor.adapter.ManageMemberAdapter;
import com.dollop.distributor.model.ManageMemberModel;

import java.util.ArrayList;

public class ManageMemberActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_memberList;
    FrameLayout member_add;
    ImageView memberback;

    ManageMemberAdapter manageMemberAdapter;
    ArrayList<ManageMemberModel> memberModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_member);

        member_add  = findViewById(R.id.member_add);
        memberback  = findViewById(R.id.memberback);
        rv_memberList  = findViewById(R.id.rv_memberList);

        memberList();



        member_add.setOnClickListener(this);
        memberback.setOnClickListener(this);

    }

    private void memberList() {

        ManageMemberModel model  = new ManageMemberModel();
        model.setName("Jack Harisson");
        model.setRole("Owner");
        model.setAccess("Full Access");
        memberModels.add(model);

        ManageMemberModel model1  = new ManageMemberModel();
        model1.setName("Rega");
        model1.setRole("Worker");
        model1.setAccess("Partial Access");
        memberModels.add(model1);


        ManageMemberModel model2  = new ManageMemberModel();
        model2.setName("Golddy");
        model2.setRole("Assistant");
        model2.setAccess("Partial Access");
        memberModels.add(model2);


        ManageMemberModel model3  = new ManageMemberModel();
        model3.setName("Jimmy");
        model3.setRole("Worker");
        model3.setAccess("Full Access");
        memberModels.add(model3);


        ManageMemberModel model4  = new ManageMemberModel();
        model4.setName("Jack Harisson");
        model4.setRole("Owner");
        model4.setAccess("Full Access");
        memberModels.add(model4);


        ManageMemberModel model5  = new ManageMemberModel();
        model5.setName("Jack Harisson");
        model5.setRole("Owner");
        model5.setAccess("partial Access");
        memberModels.add(model5);

        ManageMemberModel model6  = new ManageMemberModel();
        model6.setName("Jack Harisson");
        model6.setRole("Owner");
        model6.setAccess("partial Access");
        memberModels.add(model6);

        ManageMemberModel model7  = new ManageMemberModel();
        model7.setName("Jack Harisson");
        model7.setRole("Worker");
        model7.setAccess("Full Access");
        memberModels.add(model7);


        rv_memberList.setLayoutManager(new LinearLayoutManager(ManageMemberActivity.this));
        manageMemberAdapter = new ManageMemberAdapter(ManageMemberActivity.this,memberModels);
        rv_memberList.setAdapter(manageMemberAdapter);
    }

    @Override
    public void onClick(View v) {

        if(v ==member_add){
            Utils.I(ManageMemberActivity.this,CreateSubMamberActivity.class,null);
        }

        else  if(v ==memberback){
            finish();
        }
    }
}