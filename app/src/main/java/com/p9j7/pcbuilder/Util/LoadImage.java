package com.p9j7.pcbuilder.Util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.p9j7.pcbuilder.R;

public class LoadImage {
    public static void glideClrcle(Context context, String urlString, ImageView imageView) {
        Glide.with(context)
                .load(urlString)
//                .bitmapTransform(new CropCircleTransformation(context))
//                .format()
                .placeholder(R.drawable.ic_add)
                .error(R.drawable.ic_error_black_24dp)
                .signature(new ObjectKey(System.currentTimeMillis()))
                //不使用缓存的图片
                .into(imageView);
    }
}
