package com.example.hackumap;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Recieve extends YolpApiBase {
	private ArrayList<RecieveData> m_reData;

	public Recieve(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setUri("http://panpan.lolipop.jp/red_impulse/apitest.php");
	}

	public void setId(String id){
		param.put("id",id);
	}
	
	@Override
	protected void analyze() {
		JSONObject result =  (JSONObject) getResult();
	    JSONArray arrayResult = result.optJSONArray("data");
	    m_reData = new ArrayList<RecieveData>();
	    for(int i=0;i<arrayResult.length();i++){
	    	RecieveData item = new RecieveData();	    	
	    	JSONObject data = arrayResult.optJSONObject(i);
	    	item.setName(data.optString("name"));
	    	item.setLat(data.optString("ido"));
	    	item.setLon(data.optString("keido"));
	    	m_reData.add(item);
	    }
	    
	}
	
	public ArrayList<RecieveData> getData(){
		return m_reData;
	}
	
}