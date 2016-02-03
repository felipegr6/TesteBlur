package br.com.fgr.testeblur;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BlankFragment extends Fragment {

    @Bind(R.id.pie_chart)
    PieChartView pieChartView;

    public BlankFragment() {

    }

    public static Fragment newInstance() {

        Fragment fragment = new BlankFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        ButterKnife.bind(this, v);

        pieChartView.setColors(ColorsEnum.DIAMOND.getColors(), ColorsEnum.ALPHA_DIAMOND.getColors());

        return v;

    }

}