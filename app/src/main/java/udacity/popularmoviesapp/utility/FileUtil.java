package udacity.popularmoviesapp.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.ExifInterface;

import com.kartiks.utility.LoggerGeneral;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import udacity.popularmoviesapp.R;

@SuppressLint("SimpleDateFormat")
public class FileUtil {

    Context context;

    public FileUtil(Context context) {
        this.context = context;
    }

    //related to SD card and database
    public File getChacheDir() {

        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();

        return cacheDir;

    }

    public File getDatabaseDir() {

        File cacheDir = new File("data/data/" + context.getPackageName() + "/");
        if (!cacheDir.exists())
            cacheDir.mkdirs();

        return cacheDir;
    }

    //------------------------------------
    //related to caching
    public Object readObject(String fileName) {

        try {


            File file = new File(getChacheDir(), fileName);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            ois.close();
            return object;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return null;
    }

    public void writeObject(Object object, String fileName) {

        try {

            File file = createNewFileOrOverwrite(getChacheDir(), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();


        } catch (FileNotFoundException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
    }

    public void deleteObject(String fileName) {

        File file = new File(getChacheDir(), fileName);
        if (file.exists()) {

            LoggerGeneral.e("deleting " + fileName + " " + file.delete());
        } else {
            LoggerGeneral.e("not found " + fileName);
        }
    }

    public void deleteAllFiles() {

        File file = getChacheDir();
        for (File c : file.listFiles()) {
            c.delete();
        }
    }

    //------------------------------------
    //related to core file IO
    public File createNewFileOrOverwrite(File dir, String fileName) {

        File f = new File(dir, fileName);
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return f;
    }

    public void deleteFile(File dir, String fileName) {
        File f = new File(dir, fileName);
        if (f.exists()) {
            LoggerGeneral.e("deleting 00" + fileName);
            f.delete();
        } else {
            LoggerGeneral.e("doesnt exist 00" + fileName);
        }
    }


    public void CopyStream(InputStream is, OutputStream os) {

        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }



    //------------------------------------
    //related to bitmaps
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // final int halfHeight = height / 2;
            // final int halfWidth = width / 2;

            final int halfHeight = 70;
            final int halfWidth = 70;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 4;
            }
        }

        return inSampleSize;
    }


//    public static synchronized Bitmap decodeFile(File f, int reqWidth, int
//            reqHeight) {
//
//        try {
//
//            //decode image size
//            BitmapFactory.Options options = new
//                    BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            int sampleBY = calculateInSampleSize(options, reqWidth, reqHeight);
//            //Decode bitmap with inSampleSize set options.inJustDecodeBounds = false;
//            BitmapFactory.Options opt = new BitmapFactory.Options();
//            opt.inSampleSize = sampleBY;
//            opt.inJustDecodeBounds = false;
//
//            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f), null,
//                    options);
//            return
//                    FileUtil.getCorrectOrientationBitmap(f.getAbsolutePath(), bm);
//
//        } catch (FileNotFoundException e) {
//        }
//        return null;
//    }


    public Bitmap decodeFile(File f) {

        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 200;
            int actual_image_width = o.outWidth, actual_image_height = o.outHeight;
            int scale = 1;
            while (true) {
                if (actual_image_width / 2 < REQUIRED_SIZE || actual_image_height / 2 < REQUIRED_SIZE)
                    break;
                actual_image_width /= 2;
                actual_image_height /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

            try {
                ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                // //Log.i("orientation is ",
                // exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                if (orientation.equalsIgnoreCase("6") || orientation.equalsIgnoreCase("5")) {
                    Matrix m = new Matrix();
                    m.setRotate(90);
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

                } else if (orientation.equalsIgnoreCase("3") || orientation.equalsIgnoreCase("2")
                        || orientation.equalsIgnoreCase("4")) {

                    Matrix m = new Matrix();
                    m.setRotate(180);
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

                } else if (orientation.equalsIgnoreCase("8") || orientation.equalsIgnoreCase("7")) {

                    Matrix m = new Matrix();
                    m.setRotate(270);
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bm;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LoggerGeneral.info("returning null");
        return null;
    }



    public Bitmap getCircularBitmap(Bitmap source) {

        int targetWidth = 60;
        int targetHeight = 60;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2), Path.Direction.CCW);

        canvas.clipPath(path);
        // Bitmap sourceBitmap = scaleBitmapImage;

        if (source != null) {

            // LoggerGeneral.info("w "+source.getWidth()+" h"+source.getHeight());
            canvas.drawBitmap(source, new Rect(0, 0, source.getWidth(), source.getHeight()), new Rect(0, 0,
                    targetWidth, targetHeight), null);
        }

		/*
         * canvas.drawBitmap(source, new Rect(0, 0,
		 * source.getWidth(),source.getHeight()), new Rect(0, 0, targetWidth,
		 * targetHeight), null);
		 */
        return targetBitmap;
    }



    public void saveBitmapToFile(Bitmap image, File f) {

        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileOutputStream bos;
        try {
            bos = new FileOutputStream(f);
            image.compress(CompressFormat.JPEG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public int sizeOf(Bitmap data) {

        return data.getRowBytes() * data.getHeight();
    }

    public byte[] getCompressedByteArrayBody(Bitmap bm) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 40, bos);
        byte[] dataimg = bos.toByteArray();
        // ByteArrayBody bab = new ByteArrayBody(dataimg, "ihu_tip_image.jpg");
        // return bab;
        return dataimg;
    }

    public Bitmap getCorrectOrientationBitmap(String imageData, Bitmap bm) {

        try {
            ExifInterface exif = new ExifInterface(imageData);
            // //Log.i("orientation is ",
            // exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (orientation.equalsIgnoreCase("6") || orientation.equalsIgnoreCase("5")) {
                Matrix m = new Matrix();
                m.setRotate(90);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

            } else if (orientation.equalsIgnoreCase("3") || orientation.equalsIgnoreCase("2")
                    || orientation.equalsIgnoreCase("4")) {

                Matrix m = new Matrix();
                m.setRotate(180);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

            } else if (orientation.equalsIgnoreCase("8") || orientation.equalsIgnoreCase("7")) {

                Matrix m = new Matrix();
                m.setRotate(270);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bm;
    }

    public Bitmap transformImage(Bitmap source,int containerWidth,int screenHeight,int noOfPieces){


        int width = source.getWidth();
        int height = source.getHeight();

        int newWidth=containerWidth/noOfPieces;
        int newHeight=(height*newWidth)/width;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(source, 0, 0, width, height, matrix, false);
        source.recycle();
        return  resizedBitmap;
    }

}