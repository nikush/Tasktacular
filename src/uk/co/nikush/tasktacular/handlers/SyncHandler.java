package uk.co.nikush.tasktacular.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Log;

public class SyncHandler
{
    public void execute(String url)
    {
        new DownloadTextTask().execute(url);        
    }
    
    private String DownloadText(String url)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        
        try
        {
            in = openHttpConnection(url);
        }
        catch (IOException e)
        {
            Log.d("SyncHandler", e.getLocalizedMessage());
            return "";
        }
        
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        
        try
        {
            while ((charRead = isr.read(inputBuffer)) > 0)
            {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new  char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e)
        {
            Log.d("SyncHandler", e.getLocalizedMessage());
            return "";
        }
        
        return str;
    }
    
    private InputStream openHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;
        
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not a HTTP connection.");
        
        try
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            
            if (response == HttpURLConnection.HTTP_OK)
            {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception e)
        {
            Log.d("SyncHandler", e.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        
        return in;
    }
    
    private class DownloadTextTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            return DownloadText(urls[0]);
        }
        
        @Override
        protected void onPostExecute(String result)
        {
            Log.d("SyncHandler", result);
        }
    }
}