package uk.co.nikush.tasktacular.asyctasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class SyncHandler extends AsyncTask<String, Void, String>
{
    /**
     * Initate synchronisation.
     */
    public void synchronise()
    {
        execute("http://10.0.2.2/tasktacular/");
    }
    
    /**
     * Make the http request.
     */
    @Override
    protected String doInBackground(String... urls)
    {
        return makeRequest(urls[0]);
    }
    
    /**
     * Process the http response.
     */
    @Override
    protected void onPostExecute(String result)
    {
        try
        {
            // convert result string into JSON object
            JSONArray jsonArray = new JSONArray(result);
            
            // loop over each child object in the root array
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d("JSON", jsonObject.getString("data"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Structure and send the http request.
     * 
     * @param   url     api url
     * @return  response as a JSON string
     */
    private String makeRequest(String url)
    {
        StringBuilder sb = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        
        try
        {
            // json data to be sent in the request
            JSONObject json = new JSONObject();
            try
            {
                json.put("name", "Nikush");
                json.put("age", 21);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            
            // insert json data in the 'json' key
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("json", json.toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            
            // execute the request
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            
            // read response text into string
            if (statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content)); 
                String line;
                
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
            }
            else
            {
                Log.e("JSON", "Failed to download file");
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return sb.toString();
    }
}