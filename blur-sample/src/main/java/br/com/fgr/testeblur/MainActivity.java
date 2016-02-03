package br.com.fgr.testeblur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.pie_chart)
    PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Log.e("x", String.valueOf(new Helper().sum(5, 7)));

        pieChartView.setColors(ColorsEnum.SAPPHIRE.getColors(), ColorsEnum.ALPHA_SAPPHIRE.getColors());

    }

}