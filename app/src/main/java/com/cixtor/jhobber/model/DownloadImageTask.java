package com.cixtor.jhobber.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.cixtor.jhobber.activity.Main;

import java.io.InputStream;
import java.net.ConnectException;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private Main parent;
    private ImageView bmImage;

    public DownloadImageTask(Main activity, ImageView bmImage) {
        this.parent = activity;
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;

        try {
            InputStream in = new java.net.URL(url).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (ConnectException e) {
            parent.alert(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
