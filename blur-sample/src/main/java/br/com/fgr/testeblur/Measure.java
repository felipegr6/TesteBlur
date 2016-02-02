package br.com.fgr.testeblur;

import android.content.Context;
import android.util.TypedValue;

public class Measure {

    private Measure() {

    }

    public static int getPixelsFromDP(Context context, int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());

    }

}