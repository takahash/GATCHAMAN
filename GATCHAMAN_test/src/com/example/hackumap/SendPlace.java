package com.example.hackumap;
import android.content.Context;
public class SendPlace extends YolpApiBase {
	public SendPlace(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setUri("http://panpan.lolipop.jp/red_impulse/update.php");
	}
	// 緯度パラメータの設定
	public void setLat(String lat) {
		param.put("ido", lat);
	}
	// 経度パラメータの指定
	public void setLon(String lon) {
		param.put("keido", lon);
	}
	public void setId(String id){
		param.put("id",id);
	}
	@Override
	protected void analyze() {
	}

}