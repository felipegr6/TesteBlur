package br.com.fgr.testeblur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        ButterKnife.bind(this);

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnWithoutFragment)
    public void activityWithoutFragment() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnWithFragment)
    public void activityWithFragment() {
        startActivity(new Intent(this, Main2Activity.class));
    }

}