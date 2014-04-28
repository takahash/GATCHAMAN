package com.example.hackumap;

import java.util.ArrayList;

import org.json.*;

import com.example.hackumap.R.id;
import com.example.hackumap.YolpApiBase.YolpSearchListener;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import jp.co.yahoo.android.maps.*;

public class Main extends Activity implements YolpSearchListener,
		LocationListener {
	double lat, lon;
	private SendPlace m_sendplace;
	private Recieve2 m_recieve;
	public Map map = null;
	public boolean autoRefresh = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Refresh();
		map = new Map(this);
		Log.d("MapTestActivity", "onCreate");
		// LocationManagerを取得
		LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				0, 0, this);
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, this);
		mLocationManager.requestLocationUpdates(
				LocationManager.PASSIVE_PROVIDER, 0, 0, this);
		Location location = mLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		setContentView(R.layout.activity_map);
		FrameLayout mainFrame = (FrameLayout) findViewById(id.mainFrame);
		mainFrame.addView(map.mapView, 0);

	}

	public void doPost(double lat, double lon) {
		// double lat = place.getLatitude();
		// double lon = place.getLongitude();
		m_sendplace = new SendPlace(this);
		m_sendplace.setLat(String.valueOf(lat));
		m_sendplace.setLon(String.valueOf(lon));
		m_sendplace.setId("0001");
		m_sendplace.executeAsync(this, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.gMode:
			// ここに処理を書く
			Log.v("gmode:", "緊急召集");
			kousin();
			return true;
		case R.id.autoRefreshOff:
			// ここに処理を書く
			Log.v("onOff", "同期無効");
			autoRefresh = false;
			return true;
		case R.id.autoRefreshOn:
			// ここに処理を書く
			Log.v("onOff", "同期無効");
			autoRefresh = true;
			return true;
		case R.id.refresh:
			// ここに処理を書く
			Log.v("refresh", "手動同期");
			this.Refresh();
			return true;

		}
		return false;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		// メニューアイテムを取得
		MenuItem menu0 = (MenuItem) menu.findItem(R.id.autoRefreshOn);
		MenuItem menu1 = (MenuItem) menu.findItem(R.id.autoRefreshOff);

		if (this.autoRefresh == true) {
			// menu0を表示
			menu0.setVisible(false);
			// menu1を非表示
			menu1.setVisible(true);
		} else if (this.autoRefresh == false) {
			// menu0を非表示
			menu0.setVisible(true);
			// menu1を表示
			menu1.setVisible(false);
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		map.mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		map.mapView.onPause();
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("MapTestActivity", "onLocationChanged");
		lat = location.getLatitude();
		lon = location.getLongitude();
		doPost(lat, lon);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean endYolpSearch(YolpApiBase api, Object results) {
		// TODO Auto-generated method stub
		return false;
	}

	public void Refresh() {
		m_recieve = new Recieve2();
		Log.v("Recieved", "aa");
	}
	public void kousin(){
		ArrayList<RecieveData> arrayData=this.m_recieve.getData();
		System.out.println(arrayData);
		int[] user={id.user1,id.user2,id.user3,id.user4,id.user5};
		int[] imgid={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
		int[] placeId ={};
		map.mapView.removeOverlayAll();
			for(int i=0;i<arrayData.size();i++){
			RecieveData data = arrayData.get(i);
			String ido = data.getLat();
			String keido = data.getLon();
			String name = data.getName();
String place = data.getPlace();
			double p1 = Double.valueOf(ido)*1E6;
			double p2 = Double.valueOf(keido)*1E6;
			Log.v(Double.toString(p1), Double.toString(p2));
			GeoPoint pin = new GeoPoint((int)p1,(int)p2);
			PinOverlay pinOverlay = new PinOverlay(PinOverlay.PIN_VIOLET);
            pinOverlay.addPoint(pin, null);

            
            final MyLocationOverlay MyLoc = new MyLocationOverlay(this.getApplicationContext(), map.mapView );
            MyLoc.enableMyLocation();
            MapController c = map.mapView.getMapController();
            c.setCenter( new GeoPoint(35665721, 139731006)); //初期表示の地図を指定
            c.setZoom(3); //初期表示の縮尺を指定

             //現在地追従
             //MyLocationOverlayインスタンス作成
             //位置が更新されると、地図の位置も変わるよう設定
             MyLoc.runOnFirstFix( new Runnable(){
                    public void run() {
                          if (map.mapView.getMapController() != null) {
                                 //現在位置を取得
                                GeoPoint p = MyLoc.getMyLocation();
                                 //地図移動
                                 map.mapView.getMapController().animateTo(p);
                         }
                   }
            });

            PopupOverlay popupOverlay = new PopupOverlay(){
            @Override
                public void onTap(OverlayItem item){
                }
             };

            map.mapView.getOverlays().add(popupOverlay);
            pinOverlay.setOnFocusChangeListener(popupOverlay);
            pinOverlay.addPoint(pin, name,ido+keido);
            map.mapView.getOverlays().add(MyLoc );


            
            map.mapView.getOverlays().add(pinOverlay);	
            LinearLayout ll = (LinearLayout)findViewById(user[i]);
			TextView textView = (TextView) ll.findViewById(id.userName);
			textView.setText(data.getName());
			ImageView img = (ImageView) ll.findViewById(id.img);
			img.setImageResource(imgid[i]);
			TextView placeText = (TextView) ll.findViewById(id.userLocate);
			placeText.setText(data.getPlace());
			System.out.println(data.getPlace());
			
		}
	}
}
