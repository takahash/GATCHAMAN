package com.example.hackumap;
//import com.example.hackumap.R.id;

import android.app.Activity;

import org.json.*;

/*
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
*/
import jp.co.yahoo.android.maps.*;
public class Map {   
        int[][] p = {{35665721,139731006},{35656824,139545705},{35668431, 139650447}};

        GeoPoint pin1 = new GeoPoint(p [0][0], p [0][1]);
        GeoPoint pin2 = new GeoPoint(p [1][0], p [1][1]);
        GeoPoint pin3 = new GeoPoint(p [2][0], p [2][1]);

        public MapView mapView = null;
        //現在地
        private MyLocationOverlay _overlay ;
        public boolean autoRefresh = true;

        public Map(Activity a) {
              
              mapView = new MapView(a,"dj0zaiZpPUxOZEpncld6aDJLSCZzPWNvbnN1bWVyc2VjcmV0Jng9ZTc-" );
              mapView.setLongPress(true);
              mapView.setMapType(mapView .MapTypeStandard);
               //地図描画

              _overlay = new MyLocationOverlay(a.getApplicationContext(), mapView );
              _overlay.enableMyLocation();
              MapController c = mapView.getMapController();
              c.setCenter( new GeoPoint(35665721, 139731006)); //初期表示の地図を指定
              c.setZoom(3); //初期表示の縮尺を指定

               //現在地追従
               //MyLocationOverlayインスタンス作成
               //位置が更新されると、地図の位置も変わるよう設定
               _overlay.runOnFirstFix( new Runnable(){
                      public void run() {
                            if (mapView .getMapController() != null) {
                                   //現在位置を取得
                                  GeoPoint p = _overlay.getMyLocation();
                                   //地図移動
                                   mapView.getMapController().animateTo(p);
                           }
                     }
              });
               //ピン描画
              PinOverlay pinOverlay = new PinOverlay(PinOverlay.PIN_VIOLET);
              mapView.getOverlays().add(pinOverlay);
              pinOverlay.addPoint(pin1, null);
              pinOverlay.addPoint(pin2, null);
              pinOverlay.addPoint(pin3, null);
              mapView.getOverlays().add(_overlay );
              
              PopupOverlay popupOverlay = new PopupOverlay(){
              @Override
                  public void onTap(OverlayItem item){
                  }
               };
              
               mapView.getOverlays().add(popupOverlay);
               pinOverlay.setOnFocusChangeListener(popupOverlay);
               pinOverlay.addPoint(pin1, "KEN","IDO" );
               pinOverlay.addPoint(pin2, "TAICHI","IDO" );
               pinOverlay.addPoint(pin3, "HARUYA","IDO" );
              
       }
}
