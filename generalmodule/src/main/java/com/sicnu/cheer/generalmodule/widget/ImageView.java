package com.sicnu.cheer.generalmodule.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.sctf.mobile.generalmodule.R;
import com.sicnu.cheer.generalmodule.util.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cheer on 2016/2/24.
 */
public class ImageView extends android.widget.ImageView {
    private boolean round;
    private Object flag;
    private int defaultImageResourceId;

    public ImageView(Context context) {
        super(context);
    }

    public ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultImageResourceId(attrs);
    }

    public ImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDefaultImageResourceId(attrs);
    }

    private void setDefaultImageResourceId(AttributeSet attrs) {
        int resourceId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        if (resourceId > 0) {
            this.defaultImageResourceId = resourceId;
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.defaultImageResourceId = resId;
    }

    /**
     * 是否显示圆形图片
     *
     * @param round
     */
    public void setRound(boolean round) {
        this.round = round;
    }

    /**
     * 设置imageURL
     *
     * @param url
     */
    public void setImageURL(String url) {
        setImageURL(url, false);
    }

    /**
     * 是否以圆形显示图片
     *
     * @param url
     * @param isRound
     */
    public void setImageURL(String url, boolean isRound) {
        this.round = isRound;
        if (getTag() != null) {
            flag = getTag();
        }
        if (defaultImageResourceId == 0) {
            setBackgroundColor(getResources().getColor(R.color.gray));
        } else {
            setImageResource(defaultImageResourceId);
        }
        new AsyncTask<String, String, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    String url = strings[0];
                    String imagePath = saveImageFile(url);

                    Bitmap bitmap = getBitmap(imagePath, url);
                    if (round && bitmap != null) {
                        bitmap = ImageUtils.getRoundedCornerBitmap(bitmap);
                    }
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * 缓存图片
             *
             * @param url 下载图片的url
             * @return 返回缓存后图片的路径
             */
            private String saveImageFile(String url) {
                String filePath = null;
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    String fileName = "";
                    if (url.indexOf("=") > 0) {
                        fileName = url.substring(url.lastIndexOf("=") + 1);
                    } else if (url.indexOf("/") > 0) {
                        fileName = url.substring(url.lastIndexOf("/") + 1);
                    } else if (url.length() > 10) {
                        fileName = url.substring(url.length() - 10);
                    } else {
                        fileName = url;
                    }
                    if (fileName.length() > 0) {
                        File imagePath = getContext().getExternalFilesDir("image");
                        File imageFile = new File(imagePath, fileName);
                        if (!imageFile.exists()) {


                            fileOutputStream = new FileOutputStream(imageFile);
                            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            inputStream = conn.getInputStream();
                            byte[] bytes = new byte[1024];
                            int len = 0;
                            while ((len = inputStream.read(bytes)) != -1) {
                                fileOutputStream.write(bytes, 0, len);
                            }
                            filePath = imageFile.getAbsolutePath();


                        } else {
                            filePath = imageFile.getAbsolutePath();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return filePath;
            }


            private Bitmap getBitmap(String imageName, String url) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(imageName, options);
                    int width = options.outWidth;
                    int height = options.outHeight;
                    options.inJustDecodeBounds = false;
                    DisplayMetrics metrics = new DisplayMetrics();
                    ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
                    int screenWidth = metrics.widthPixels / 4;
                    int screenHeight = metrics.heightPixels;
                    int scaleSize = 0;
                    scaleSize = width / screenWidth;
                    if (scaleSize < height / screenHeight) {
                        scaleSize = height / screenHeight;
                    }
                    if (scaleSize < 1) {
                        scaleSize = 1;
                    }
                    options.inSampleSize = scaleSize;
                    bitmap = BitmapFactory.decodeFile(imageName, options);
                    if (bitmap == null) {
                        File file = new File(imageName);
                        if (file.exists()) {
                            file.delete();
                            String imagePath = saveImageFile(url);
                            bitmap = getBitmap(imagePath, url);
                        }
                    }
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                try {
                    if (getTag() == flag) {
                        if (bitmap != null) {
                            setBackgroundColor(getResources().getColor(R.color.full_transparent));
                            setImageBitmap(bitmap);
                        } else if (defaultImageResourceId > 0) {
                            setBackgroundColor(getResources().getColor(R.color.full_transparent));
                            setImageResource(defaultImageResourceId);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute(url);
    }

    /**
     * 设置imageURL并以圆形显示
     *
     * @param url
     */
    public void setRoundImageURL(String url) {
        setImageURL(url, true);
    }

}
