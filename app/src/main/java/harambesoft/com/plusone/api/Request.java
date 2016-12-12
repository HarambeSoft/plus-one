package harambesoft.com.plusone.api;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by isa on 11.12.2016.
 */

class POSTData extends HashMap<String, String> {}

public class Request {
    public interface RequestFinishedHandler {
        public void onRequestFinished(String result);
    }

    private static class PostRequestParams {
        String url;
        POSTData postData;
        RequestFinishedHandler handler;

        PostRequestParams(String url, POSTData postData, RequestFinishedHandler handler) {
            this.url = url;
            this.postData = postData;
            this.handler = handler;
        }
    }

    private static class MakePostRequest extends AsyncTask<PostRequestParams, Integer, String> {
        RequestFinishedHandler handler;

        protected String doInBackground(PostRequestParams... params) {
            this.handler = params[0].handler;
            String url = params[0].url;
            POSTData postData = params[0].postData;

            StringBuilder response = new StringBuilder();


            try {
                HttpURLConnection con = (HttpURLConnection) connection(url, "POST");
                String urlParameters = getPostDataString(postData);

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                /*int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);*/

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response.toString();
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            handler.onRequestFinished(result);
        }
    }



    public static String get(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) connection(url, "GET");

        /*int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);*/

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        return response.toString();
    }

    public static void post(String url, POSTData postData, RequestFinishedHandler handler) throws IOException {
        new MakePostRequest().execute(new PostRequestParams(url, postData, handler));
    }

    // http://stackoverflow.com/a/29561084
    private static String getPostDataString(POSTData params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static HttpURLConnection connection(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(method);
        //con.setRequestProperty("Content-Type", "");
        //con.setRequestProperty("Authorization", "");

        return con;
    }
}
