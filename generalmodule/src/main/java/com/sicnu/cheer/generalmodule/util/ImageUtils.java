package com.sicnu.cheer.generalmodule.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sicnu.cheer.generalmodule.R;


/**
 * 图片操作工具包
 *
 * @version 1.0
 */
public class ImageUtils {

    /**
     * 获取圆形图片的方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(outBitmap);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            int round = 0;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > height) {
                round = height;
            } else {
                round = width;
            }
            final Rect rect = new Rect(0, 0, round, round);
            final RectF rectF = new RectF(rect);
            final float roundPX = round / 2;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return outBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 显示大图片
     *
     * @param context
     */
    public static void showBigImageDialog(Context context, int layoutResId, String imageUrl, Bitmap bitmap) {
        View view = LayoutInflater.from(context).inflate(layoutResId, null);

        ImageView head = (ImageView) view.findViewById(R.id.user_head);

        //设置图片的高宽
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(head.getLayoutParams());
        layoutParams.height = ScreenUtils.getScreenWidth(context);
        layoutParams.width = ScreenUtils.getScreenWidth(context);
        head.setLayoutParams(layoutParams);
//        userHead.setBackgroundColor(context.getResources().getColor(R.color.full_transparent));
        if (!"".equals(imageUrl)) {
            Glide.with(context).load(Uri.parse(imageUrl)).placeholder(R.drawable.default_user_img).into(head);
        } else if (bitmap != null) {
            head.setImageBitmap(bitmap);
        } else {
            head.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.default_user_img));
        }

        Dialog dialog = new Dialog(context);

        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = ScreenUtils.getScreenWidth(context); //设置宽度
//        lp.height = ScreenUtils.getScreenHeight(context); //设置高度
//        window.setAttributes(lp);


    }

}
