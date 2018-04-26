package com.yjc.photodance.common.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import com.yjc.photodance.MyApplication;

/**
 * Created by Administrator on 2018/4/26/026.
 */

public class BlurUtil {

    private Bitmap mScreenBitmap;

    // Remix Blur
    private void blur(Bitmap bkg, View view, int radius) {
        RenderScript rs = RenderScript.create(MyApplication.getMyApplicationContext());
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, bkg);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bkg);
        view.setBackground(new BitmapDrawable(MyApplication.getMyApplicationContext()
                .getResources(), bkg));
        rs.destroy();
    }
}
