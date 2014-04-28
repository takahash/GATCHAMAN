package com.example.hackumap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.hackumap.RecieveData;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
public class Recieve2 implements Runnable{
	private ArrayList<RecieveData> m_reData;
private static final String URL = "http://panpan.lolipop.jp/red_impulse/apitest.php";
public Recieve2() {
Thread thread = new Thread(this);
thread.start(); 
}
@Override
public void run() { 
try{
HttpUriRequest httpGet = new HttpGet(URL); 
DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
HttpEntity entity = httpResponse.getEntity();
JSONArray object = new JSONArray(EntityUtils.toString(entity));

//Jacksonのマッパーを生成。
ObjectMapper mapper = new ObjectMapper();
String json = object.toString();

m_reData = new ArrayList<RecieveData>();
for(int i=0;i<object.length();i++){
	RecieveData item = new RecieveData();	    	
	JSONObject data = (JSONObject) object.get(i);
	item.setName(data.getString("name"));
	item.setLat(data.getString("ido"));
	item.setLon(data.getString("keido"));
	item.setPlace(data.getString("place"));
	m_reData.add(item);
	//System.out.println(m_reData.get(0).getName());
}

}catch(IOException e){
System.out.println("通信失敗");
}catch(JSONException e){
System.out.println("JSON取得に失敗");
} 
}
public ArrayList<RecieveData> getData(){
	return this.m_reData;
}
}