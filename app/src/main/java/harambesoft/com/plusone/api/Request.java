package harambesoft.com.plusone.api;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import harambesoft.com.plusone.PlusOne;

/**
 * Created by isa on 11.12.2016.
 */

class POSTData extends HashMap<String, String> {}

public class Request {
    public enum RequestType {
        GET,
        POST
    }

    public interface RequestFinishedHandler {
        void onRequestFinished(String result);
    }

    private static class RequestParams {
        String url;
        POSTData postData;
        RequestType requestType;
        RequestFinishedHandler handler;

        RequestParams(String url, RequestType requestType, RequestFinishedHandler handler) {
            this.url = url;
            this.requestType = requestType;
            this.handler = handler;
        }

        RequestParams(String url, POSTData postData, RequestFinishedHandler handler) {
            this(url, RequestType.POST, handler);
            this.postData = postData;
        }
    }

    private static class MakeRequest extends AsyncTask<RequestParams, Integer, String> {
        RequestFinishedHandler handler;

        protected String doInBackground(RequestParams... params) {
            this.handler = params[0].handler;
            String url = params[0].url;
            RequestType requestType = params[0].requestType;

            StringBuilder response = new StringBuilder();


            try {
                HttpURLConnection con = connection(url, requestType == RequestType.POST ? "POST":"GET");

                // Add api token to header
                String apiToken = PlusOne.settings().getString("api_token", "");
                if (!apiToken.isEmpty())
                    con.setRequestProperty("Authorization",  "Bearer " + apiToken);

                if (requestType == RequestType.POST) {
                    POSTData postData = params[0].postData;
                    String urlParameters = getPostDataString(postData);

                    // Send post request
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();
                }

                //FIXME: look for response code
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

    public static void get(String url, RequestFinishedHandler handler) throws IOException {
        new MakeRequest().execute(new RequestParams(url, RequestType.GET, handler));
    }

    public static void post(String url, POSTData postData, RequestFinishedHandler handler) throws IOException {
        new MakeRequest().execute(new RequestParams(url, postData, handler));
    }

    private static HttpURLConnection connection(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        return con;
    }
}
