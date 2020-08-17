package com.dollop.distributor.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.dollop.distributor.Activity.StockQuantityActivity;
import com.dollop.distributor.R;
import com.dollop.distributor.UtilityTools.Utility;
import com.dollop.distributor.UtilityTools.Utils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;


public class DashboardFragment extends Fragment  {


    private static final int MAX_X_VALUE = 5;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String STACK_1_LABEL = "Stack 1";
    private static final String STACK_2_LABEL = "Stack 2";
    private static final String STACK_3_LABEL = "Stack 3";
    private static final String SET_LABEL = "Set ABC";
    //private BarChart chart;

    TextView tv_view_all;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tv_view_all = root.findViewById(R.id.tv_view_all);
        tv_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.I(getContext(),StockQuantityActivity.class,null);
            }
        });
//////////////////////
        ////// -1-////
  /*      BarChart chart = root.findViewById(R.id.barchart);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));


        ArrayList year = new ArrayList();
        year.add("MON");
        year.add("THE");
        year.add("WED");
        year.add("THU");
        year.add("FRI");
        year.add("SAT");
        year.add("SUN");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "");
      //  chart.animateY(5000);
        BarData data = new BarData( year, bardataset);
     //   bardataset.setBarSpacePercent(3f);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bardataset.setColors(ColorTemplate.createColors(new int[]{R.color.colorPrimaryDark}));
        chart.setData(data);*/
  ///////////


        AnyChartView anyChartView = root.findViewById(R.id.any_chart);
      //  anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("MON", 110430));
        data.add(new ValueDataEntry("TUE", 80540));
        data.add(new ValueDataEntry("WED", 102610));
        data.add(new ValueDataEntry("THU", 130430));
        data.add(new ValueDataEntry("FRI", 1000));
        data.add(new ValueDataEntry("SAT", 13760));
        data.add(new ValueDataEntry("SUN", 102610));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
    .format();

                /*.format("${%Value}{groupsSeparator: }");*/

    //    cartesian.animation(true);
       // cartesian.title("Top 10 Cosmetic Products by Revenue");

       // cartesian.yScale().minimum(0d);
/*        cartesian.yAxis(0).labels().format();*/
       // cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

       // cartesian.xAxis(0).title("Product");
      //  cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);
//////////


/*        BarChart barChart = (BarChart) root.findViewById(R.id.bar_chart_vertical);
        barChart.setBarMaxValue(100);


        //Add single bar
        BarChartModel barChartModel = new BarChartModel();
        barChartModel.setBarValue(50);
        barChartModel.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel.setBarTag(null); //You can set your own tag to bar model
        barChartModel.setBarText("10");
        barChart.addBar(barChartModel);



        BarChartModel barChartModel1 = new BarChartModel();
        barChartModel1.setBarValue(30);
        barChartModel1.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel1.setBarTag(null); //You can set your own tag to bar model
        barChartModel1.setBarText("20");
        barChart.addBar(barChartModel1);

        BarChartModel barChartModel2 = new BarChartModel();
        barChartModel2.setBarValue(40);
        barChartModel2.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel2.setBarTag(null); //You can set your own tag to bar model
        barChartModel2.setBarText("30");
        barChart.addBar(barChartModel2);

        BarChartModel barChartModel3 = new BarChartModel();
        barChartModel3.setBarValue(60);
        barChartModel3.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel3.setBarTag(null); //You can set your own tag to bar model
        barChartModel3.setBarText("40");
        barChart.addBar(barChartModel3);

        BarChartModel barChartModel4 = new BarChartModel();
        barChartModel4.setBarValue(20);
        barChartModel4.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel4.setBarTag(null); //You can set your own tag to bar model
        barChartModel4.setBarText("50");
        barChart.addBar(barChartModel4);

        BarChartModel barChartModel5 = new BarChartModel();
        barChartModel5.setBarValue(80);
        barChartModel5.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel5.setBarTag(null); //You can set your own tag to bar model
        barChartModel5.setBarText("60");
        barChart.addBar(barChartModel5);


        BarChartModel barChartModel6 = new BarChartModel();
        barChartModel6.setBarValue(25);
        barChartModel6.setBarColor(Color.parseColor("#9C27B0"));
        barChartModel6.setBarTag(null); //You can set your own tag to bar model
        barChartModel6.setBarText("70");
        barChart.addBar(barChartModel6);*/






        return root;
    }

}
