package com.mahadi.loginapp.Dashboard;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mahadi.loginapp.Login.Userloginsession;
import com.mahadi.loginapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {


    String myJSON;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();
        getData();
    }


    protected void showList() throws JSONException {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("Issues");

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("problem_title");
                //String latitude = c.getString("latitude");
                //String longitude = c.getString("longitude");
                String posted = c.getString("posted_time");
                String details=c.getString("name");
                //String image=c.getString("image");
                String userid=c.getString("submitted_by");
                String status=c.getString("issolve");
                String  location_name=c.getString("locationname");
                if(status.equals("0"))
                {
                    status="Not solved";
                }
                else
                    status="Solved";




                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put("id",id);
                persons.put("problem_title",name);
//                persons.put("latitude",latitude);
//                persons.put("longitude",longitude);
                persons.put("posted_time",posted);
                persons.put("name",details);
                //persons.put("image",image);
                persons.put("submitted_by",userid);
                persons.put("issolve",status);
                persons.put("locationname",location_name);

                personList.add(persons);
            }

            /*ListAdapter adapter = new SimpleAdapter(
                    DashboardActivity.this, personList, R.layout.activity_dashboard,
                    new String[]{"id","problem_title","locationname","posted_time","name","submitted_by","issolve"},
                    new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_locationname,R.id.tv_datetime,R.id.tv_description,R.id.tv_submit,R.id.tv_status}
            );*/

            //list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://54.218.102.254/scarecrow/json.php");


                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                try {
                    showList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


}
