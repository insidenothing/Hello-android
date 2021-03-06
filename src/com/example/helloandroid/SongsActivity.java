package com.example.helloandroid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
public class SongsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("patrick-code", "json/php/mysql (push via get, pull via json) webservice");
        String result = "";
 	   InputStream is = null;
 	   //the year data to send
 	   ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 	   nameValuePairs.add(new BasicNameValuePair("year","1980"));
 	   //http post
 	   try{
 		   HttpClient httpclient = new DefaultHttpClient();
 	 		  HttpPost httppost = new HttpPost("http://209.251.35.217/getAllPeopleBornAfter.php");
 	 		  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 	 		  HttpResponse response = httpclient.execute(httppost);
 	 		  HttpEntity entity = response.getEntity();
 			is = entity.getContent();
 	   }catch(Exception e){
 	 		  Log.e("patrick-code", "Error in http connection "+e.toString());
 	   }
 	   //convert response to string
 	   try{
 			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
 			StringBuilder sb = new StringBuilder();
 	 		  String line = null;
 	 		  while ((line = reader.readLine()) != null) {
 	 				  sb.append(line + "\n");
 	 		  }
 	 		  is.close();
 	 		  result=sb.toString();
 	   }catch(Exception e){
 	 		  Log.e("patrick-code", "Error converting result "+e.toString());
 	   }
 	   //parse json data
 	   try{
 			  super.onCreate(savedInstanceState);
 		      TextView tv = new TextView(this);
 	 		  JSONArray jArray = new JSONArray(result);
 	 		  for(int i=0;i<jArray.length();i++){
 	 				  JSONObject json_data = jArray.getJSONObject(i);
 	 			      tv.setText("welcome, pulling data: "+json_data.getString("name"));
 	 				  Log.i("patrick-code","id: "+json_data.getInt("id")+
 	 						  ", name: "+json_data.getString("name")+
 	 						  ", sex: "+json_data.getInt("sex")+
 	 						  ", birthyear: "+json_data.getInt("birthyear")
 	 				  );
 	 		  }
 	 		  
 	 		 //setContentView(R.layout.main);
 	 		 //setContentView(R.layout.welcome);
 	 		  
 		      setContentView(tv); 
 	 		    
 		      
 		      
 	
 		    
 			
 	   }catch(JSONException e){
 	 		  Log.e("patrick-code", "Error parsing data "+e.toString());
 	   }        
        
        
        
        
  
    }
}