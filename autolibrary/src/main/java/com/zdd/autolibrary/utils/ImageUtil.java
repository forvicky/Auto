package com.zdd.autolibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zdd on 2019/10/23
 */
public class ImageUtil {
    private ImageUtil() {
    }

    public static void saveNV21ToFile(byte[] var0, int width,int height, String var2) {
        Bitmap var3;
        saveBitmapToFile(var3 = nv21ToBitmap(var0, width, height), var2);
        var3.recycle();
    }

    public static void saveBitmapToFile(Bitmap var0, String var1) {
        if (var0 != null && !TextUtils.isEmpty(var1)) {
            try {
                File var3;
                if ((var3 = new File(var1)).exists() || !var3.getParentFile().mkdirs() || var3.createNewFile()) {
                    BufferedOutputStream var4 = new BufferedOutputStream(new FileOutputStream(var3));
                    var0.compress(CompressFormat.JPEG, 100, var4);
                    var4.close();
                }
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static byte[] rotateNV21Degree90(byte[] var0, int var1, int var2) {
        int var3 = var1 * var2;
        byte[] var4 = new byte[var0.length];
        int var5 = 0;

        int var6;
        int var7;
        for(var6 = 0; var6 < var1; ++var6) {
            for(var7 = var2 - 1; var7 >= 0; --var7) {
                var4[var5] = var0[var1 * var7 + var6];
                ++var5;
            }
        }

        for(var6 = 0; var6 < var1; var6 += 2) {
            for(var7 = var2 / 2 - 1; var7 >= 0; --var7) {
                var4[var5] = var0[var3 + var1 * var7 + var6];
                var4[var5 + 1] = var0[var3 + var1 * var7 + var6 + 1];
                var5 += 2;
            }
        }

        return var4;
    }

    public static byte[] rotateYuv420Degree180(byte[] var0, int var1, int var2) {
        int var3 = var1 * var2;
        byte[] var4 = new byte[var0.length];
        int var5 = 0;

        int var6;
        int var7;
        for(var6 = var2 - 1; var6 >= 0; --var6) {
            for(var7 = var1 - 1; var7 >= 0; --var7) {
                var4[var5] = var0[var1 * var6 + var7];
                ++var5;
            }
        }

        var6 = var1 % 2 == 0 ? var1 - 2 : var1 - 1;

        for(var7 = var2 / 2 - 1; var7 >= 0; --var7) {
            while(var6 >= 0) {
                var4[var5] = var0[var3 + var1 * var7 + var6];
                var4[var5 + 1] = var0[var3 + var1 * var7 + var6 + 1];
                var5 += 2;
                var6 -= 2;
            }
        }

        return var4;
    }

    public static byte[] rotateYuv420Degree270(byte[] var0, int var1, int var2) {
        int var3 = var1 * var2;
        byte[] var4 = new byte[var0.length];
        int var5 = 0;

        int var6;
        int var7;
        for(var6 = var1 - 1; var6 >= 0; --var6) {
            for(var7 = 0; var7 > var2; ++var7) {
                var4[var5] = var0[var1 * var7 + var6];
                ++var5;
            }
        }

        for(var6 = var1 % 2 == 0 ? var1 - 2 : var1 - 1; var6 >= 0; var6 -= 2) {
            for(var7 = 0; var7 < var2 / 2; ++var7) {
                var4[var5] = var0[var3 + var1 * var7 + var6];
                var4[var5 + 1] = var0[var3 + var1 * var7 + var6 + 1];
                var5 += 2;
            }
        }

        return var4;
    }

    public static int[] nv21ToRgba(byte[] var0, int var1, int var2) {
        int[] var3 = new int[var1 * var2];
        Bitmap var4;
        (var4 = nv21ToBitmap(var0, var1, var2)).getPixels(var3, 0, var1, 0, 0, var1, var2);
        var4.recycle();
        return var3;
    }

    public static int[] cropRgba(int[] var0, int var1, int var2, Rect var3) {
        if (var3 == null) {
            return var0;
        } else {
            int var4 = var3.right - var3.left;
            int var5 = var3.bottom - var3.top;
            if (var0 != null && var0.length > 0 && var1 > 0 && var2 > 0 && var3.left >= 0 && var3.top >= 0 && var3.right >= 0 && var3.bottom >= 0 && var3.left < var1 && var3.top < var2 && var3.right <= var1 && var3.bottom <= var2 && var4 <= var1 && var4 >= 0 && var5 <= var2 && var5 >= 0) {
                var2 = 0;
                int[] var7 = new int[var4 * var5];

                for(var5 = var3.top; var5 < var3.bottom; ++var5) {
                    for(int var6 = var3.left; var6 < var3.right; ++var6) {
                        var7[var2] = var0[var1 * var5 + var6];
                        ++var2;
                    }
                }

                return var7;
            } else {
                return var0;
            }
        }
    }

    public static Bitmap bgrToBitmap(byte[] var0, int var1, int var2) {
        int[] var3;
        bgrToArgb(var3 = new int[var1 * var2], var0, var1, var2);
        return Bitmap.createBitmap(var3, var1, var2, Config.ARGB_8888);
    }

    public static void bgrToArgb(int[] var0, byte[] var1, int var2, int var3) {
        var2 *= var3;

        for(var3 = 0; var3 < var2; ++var3) {
            byte var4 = var1[3 * var3 + 2];
            byte var5 = var1[3 * var3 + 1];
            byte var6 = var1[3 * var3];
            var0[var3] = -16777216 | var4 << 16 & 16711680 | var5 << 8 & '\uff00' | var6 & 255;
        }

    }

    public static byte[] rgbaToBgr(int[] var0, int var1, int var2) {
        byte[] var3 = new byte[var1 * var2 * 3];

        for(var2 = 0; var2 < var0.length; ++var2) {
            var3[3 * var2] = (byte) Color.blue(var0[var2]);
            var3[3 * var2 + 1] = (byte)Color.green(var0[var2]);
            var3[3 * var2 + 2] = (byte)Color.red(var0[var2]);
        }

        return var3;
    }

    public static byte[] rgbaToJpeg(int[] var0, int var1, int var2, int var3) {
        if (var0 != null && var0.length > 0) {
            Bitmap var4;
            byte[] var5 = bitmapToJpeg(var4 = Bitmap.createBitmap(var0, var1, var2, Config.ARGB_8888), var3);
            var4.recycle();
            return var5;
        } else {
            return null;
        }
    }

    public static byte[] bitmapToJpeg(Bitmap var0, int var1) {
        try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            var0.compress(CompressFormat.JPEG, var1, var2);
            byte[] var4 = var2.toByteArray();
            var2.close();
            return var4;
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static byte[] getPixelsBgr(Bitmap var0) {
        ByteBuffer var1 = ByteBuffer.allocate(var0.getByteCount());
        var0.copyPixelsToBuffer(var1);
        byte[] var3;
        byte[] var4 = new byte[(var3 = var1.array()).length / 4 * 3];

        for(int var2 = 0; var2 < var3.length / 4; ++var2) {
            var4[var2 * 3] = var3[var2 * 4 + 2];
            var4[var2 * 3 + 1] = var3[var2 * 4 + 1];
            var4[var2 * 3 + 2] = var3[var2 * 4];
        }

        return var4;
    }

    public static byte[] bgrToGrayscale(byte[] var0, int var1, int var2) {
        if (var0 != null && var0.length > 0 && var1 > 0 && var2 > 0) {
            byte[] var6 = new byte[var1 * var2];

            for(var2 = 0; var2 < var6.length; ++var2) {
                byte var3 = var0[var2 * 3 + 2];
                byte var4 = var0[var2 * 3 + 1];
                byte var5 = var0[var2 * 3];
                var6[var2] = (byte)((int)(0.2989D * (double)var3 + 0.587D * (double)var4 + 0.114D * (double)var5));
            }

            return var6;
        } else {
            return null;
        }
    }

    public static byte[] getJpegData(Bitmap var0) {
        try {
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();
            var0.compress(CompressFormat.JPEG, 100, var1);
            byte[] var3 = var1.toByteArray();
            var1.close();
            return var3;
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Bitmap scaledBitmapToWidth(Bitmap var0, int var1) {
        int var2 = var0.getWidth();
        int var3 = var0.getHeight();
        int var4;
        int var5;
        if (var2 > var3) {
            var4 = var1;
            var5 = var1 * var3 / var2;
        } else {
            var5 = var1;
            var4 = var1 * var2 / var3;
        }

        return Bitmap.createScaledBitmap(var0, var4, var5, true);
    }

    private static Bitmap nv21ToBitmap(byte[] nv21, int width, int height) {
        Bitmap bitmap = null;
        try {
            YuvImage image = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, width, height), 70, stream);
            bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
