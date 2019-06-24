package com.example.ktomoya.rusuapp.helper;

import android.os.AsyncTask;
import com.example.ktomoya.rusuapp.values.Urls;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetTask extends AsyncTask <Void, Void, String> {
    private boolean state;

    public HttpGetTask(boolean state) {
        this.state = state;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... voids) {
        return execGet();
    }

    @Override
    protected void onPostExecute(String string) {
    }

    private String execGet() {
        HttpURLConnection http = null;
        InputStream in = null;
        String src = "";
        try {
            URL url = new URL(getURL(state));
            http = (HttpURLConnection) url.openConnection();
            http.connect();
            in = http.getInputStream();
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read(line);
                if (size <= 0) break;
                src += new String(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (http != null) {
                    http.disconnect();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ignored) {
            }
        }
        return src;
    }

    public static String getURL(boolean state) {
        String url = Urls.Companion.getKeyOpen(state);
        return url;
    }
}

