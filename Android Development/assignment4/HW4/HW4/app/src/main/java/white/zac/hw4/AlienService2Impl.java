package white.zac.hw4;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AlienService2Impl extends Service {

    private  int requests = 1;
    private String server = "http://javadude.com/aliens";
    private List<UFOReporter> reporters = new ArrayList<>();
    private String content;

    AlienService2.Stub binder = new AlienService2.Stub() {
        public void add(UFOReporter reporter) {
            reporters.add(reporter);
        }
        public void remove(UFOReporter  reporter) {
            reporters.remove(reporter);
        }
    };

    private class RequestThread extends Thread{
        @Override
        public void run() {

            while(reporters.isEmpty()){} // check to make sure there are reporters
            URL url;
            HttpURLConnection connection;
            List<UFOPosition> ships = new ArrayList<>();

            try {
                //make connection
                url = new URL(server + "/" + requests + ".json");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                //while getting good response
                while(connection.getResponseCode() != 404){
                    content = "";

                    //read input
                    InputStream in = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while((line = br.readLine()) != null) {
                        content += line + "\n";
                    }

                    // parse JSON
                    try {
                        JSONArray jsonArray = new JSONArray(content);
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            UFOPosition ufoPosition = new UFOPosition(
                                    jsonObject.optInt("ship"),
                                    jsonObject.optDouble("lat"),
                                    jsonObject.optDouble("lon")
                            );
                            // add to list of ships
                            ships.add(ufoPosition);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // report
                    for(UFOReporter reporter: reporters){
                        try {
                            reporter.report(ships);
                        } catch (RemoteException e) {
                            Log.e(getClass().getSimpleName(), "Could not send report", e);                }
                    }

                    //pause thread for 1 second
                    Thread.sleep(1000);

                    //empty ships list
                    ships.clear();

                    requests++;
                    url = new URL(server + "/" + requests + ".json");
                    System.out.println(url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private RequestThread requestThread;


    @Nullable
    @Override
    public synchronized IBinder onBind(Intent intent) {
        //start polling thread
        requestThread = new RequestThread();
        requestThread.start();
        return binder;
    }





    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d("started service", "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // interrupt polling thread
        super.onDestroy();
        requestThread.interrupt();
        Log.d("destroy service", "onDestroy");
    }
}
