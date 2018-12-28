package com.cp.admob_sdk.hook;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by PengChen on 2018/12/26.
 */

public class FaceBookLinearLayout extends LinearLayout {

    private static float mAlpha = 0.0f;

    public static void setmAlpha(float _alpha) {
        mAlpha = _alpha;
    }

    public FaceBookLinearLayout(Context context) {
        super(context);
        setAlpha(mAlpha);
    }

    public FaceBookLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAlpha(mAlpha);
    }

    public FaceBookLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAlpha(mAlpha);
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
