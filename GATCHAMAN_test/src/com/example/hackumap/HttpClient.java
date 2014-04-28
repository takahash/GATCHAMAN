package com.example.hackumap;

import java.io.IOException;
import java.io.InputStream;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpClient {
	private DefaultHttpClient client=null;
	/**
	*初期化
	*@param				:サーバーパス
	*@return				:なし
	*@exception			:なし
	*/
	public HttpClient(){
	}
	public String https(String url){
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		//SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
		return "";
	}
	public InputStream loadImage(String wkurl){
		HttpGet method=new HttpGet(wkurl);
		try{
			HttpParams httpParms = new BasicHttpParams();
		    httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 30000);
		    httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, 30000);
		    DefaultHttpClient client = new DefaultHttpClient(httpParms);
			HttpResponse res=client.execute(method);
			HttpEntity entity=res.getEntity();
			InputStream is=entity.getContent();
			return is;
		}catch (IOException e) {
			method.abort();
			return null;
		}
	}

	public String doPostString( String url, String params ){
		try{
	        HttpPost method = new HttpPost( url );
			HttpParams httpParms = new BasicHttpParams();
		    httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 30000);
		    httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, 30000);
	        client = new DefaultHttpClient(httpParms);

	        // POST データの設定
	        StringEntity paramEntity = new StringEntity( params );
	        paramEntity.setChunked( false );
	        paramEntity.setContentType( "application/x-www-form-urlencoded" );
	        method.setEntity( paramEntity );
	        HttpResponse response = client.execute( method );
	        int status = response.getStatusLine().getStatusCode();
	        if (status != HttpStatus.SC_OK )
	            throw new Exception( "" );
	        return EntityUtils.toString( response.getEntity(), "UTF-8" );
	    }catch ( Exception e ){
	        return null;
	    }
	}

	public String doPostString( String url, String params,String cookie ){
		try{
	        HttpPost method = new HttpPost( url );
			HttpParams httpParms = new BasicHttpParams();
		    httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 30000);
		    httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, 30000);
	        client = new DefaultHttpClient(httpParms);

	        // POST データの設定
	        StringEntity paramEntity = new StringEntity( params );
	        paramEntity.setChunked( false );
	        paramEntity.setContentType( "application/x-www-form-urlencoded" );
	        method.setHeader( "Connection", "Keep-Alive" );
	        method.setHeader( "Cookie", cookie);
	        method.setEntity( paramEntity );
	        HttpResponse response = client.execute( method );
	        int status = response.getStatusLine().getStatusCode();
	        if ( status != HttpStatus.SC_OK )
	            throw new Exception( "" );
	        return EntityUtils.toString( response.getEntity(), "UTF-8" );
	    }catch ( Exception e ){
	        return null;
	    }
	}

	public String doGetString( String url ){
	    try{
	        HttpGet method = new HttpGet( url );
	        HttpParams httpParms = new BasicHttpParams();
	        httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 30000);
	        httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, 30000);
	        client = new DefaultHttpClient(httpParms);

	        // ヘッダを設定する
	        method.setHeader( "Connection", "Keep-Alive" );

	        HttpResponse response = client.execute( method );

	        int status = response.getStatusLine().getStatusCode();
	        if ( status != HttpStatus.SC_OK )
	            throw new Exception("");

	        return EntityUtils.toString( response.getEntity(), "UTF-8" );
	    }catch ( Exception e ){
	        return null;
	    }
	}

	 public String doGetString( String url ,String cookie){
	    try{
	        HttpGet method = new HttpGet( url );
	        HttpParams httpParms = new BasicHttpParams();
	        httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 30000);
	        httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, 30000);
	        client = new DefaultHttpClient(httpParms);

	        // ヘッダを設定する
	        method.setHeader( "Cookie", cookie );

	        HttpResponse response = client.execute( method );
	        int status = response.getStatusLine().getStatusCode();
	        if ( status != HttpStatus.SC_OK )
	            throw new Exception( "" );

	        return EntityUtils.toString( response.getEntity(), "UTF-8" );
	    }catch ( Exception e )
	    {
	        return null;
	    }
	}

	public InputStream doGet(String url ,String header, String data){
		HttpGet method=new HttpGet(url);
		try {
			HttpParams httpParms = new BasicHttpParams();
		    httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 30000);
		    httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, 30000);
		    DefaultHttpClient client = new DefaultHttpClient(httpParms);
	        // ヘッダを設定する
	        method.setHeader( header, data );
			HttpResponse res=client.execute(method);
			HttpEntity entity=res.getEntity();
			InputStream is=entity.getContent();
			return is;
		} catch (IOException e) {
			method.abort();
			return null;
		}
	}

	/**
	*クローズ処理
	*@param				:なし
	*@return			:ｴﾗｰ:1 成功:0
	*@exception		:なし
	*/
	public int close(){
		if( client == null ) return 0;

		try {
			client = null;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
			return 1;
		}
		return 0;
	}
}
