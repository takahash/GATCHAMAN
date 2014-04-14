package com.example.gatchaman_test;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import jp.co.yahoo.android.maps.GeoPoint;
import jp.co.yahoo.android.maps.MapActivity;
import jp.co.yahoo.android.maps.MapView;
import jp.co.yahoo.android.maps.MyLocationOverlay;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.gatchaman_test.YolpApiBase.YolpSearchListener;

public class MapTestActivity extends MapActivity implements YolpSearchListener, LocationListener {
	
	private double lat,lon;
	private SendPlace m_sendplace; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MapView mapView = new MapView(this,"dj0zaiZpPUxOZEpncld6aDJLSCZzPWNvbnN1bWVyc2VjcmV0Jng9ZTc-");
		//MapController c = mapView.getMapController();
		//c.setCenter(new GeoPoint(35665721, 139731006));
		//c.setZoom(1);
		//setContentView(mapView);
		
		Log.d("MapTestActivity", "onCreate");

		// LocationManagerを取得
		LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
		mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0,this);
		Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// Criteriaオブジェクトを生成
		//Criteria criteria = new Criteria();
		// Accuracyを指定(低精度)
		//criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// PowerRequirementを指定(低消費電力)
		//criteria.setPowerRequirement(Criteria.POWER_LOW);
		// ロケーションプロバイダの取得
		//String provider = mLocationManager.getBestProvider(criteria, true);
		
		// 取得したロケーションプロバイダを表示
		//TextView tv_provider = (TextView) findViewById(R.id.Provider);
		//tv_provider.setText("Provider: "+provider);
		// LocationListenerを登録
		//mLocationManager.requestLocationUpdates(provider, 0, 0, this);
		
		//MyLocationOverlayインスタンス作成
		//MyLocationOverlay mylocationOverlay = new MyLocationOverlay(getApplicationContext(),mapView);
		//mylocationOverlay.enableMyLocation();
		//GeoPoint place = mylocationOverlay.getMyLocation();
		//doPostメソッドで自身の位置情報を１分毎にURL宛に送信
		//例えば、座標（35665721, 139731006)とか
		//doPost(place);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	//n分毎にPOSTを送信するメソッド
	//宛先は"http://panpan.lolipop.jp/red_impulse/update.php"
	public void doPost(double lat, double lon ){
		//double lat = place.getLatitude();
		//double lon = place.getLongitude();
		
		m_sendplace = new SendPlace(this);
		m_sendplace.setLat(String.valueOf(lat));
		m_sendplace.setLon(String.valueOf(lon));
		m_sendplace.setId("0002");
		m_sendplace.executeAsync(this, true);
	}

	@Override
	public boolean endYolpSearch(YolpApiBase api, Object results) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.d("MapTestActivity", "onLocationChanged");
		lat=location.getLatitude();
		lon=location.getLongitude();
		doPost(lat,lon);
		//json解析
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
}
	
