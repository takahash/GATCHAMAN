package com.example.hackumap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.util.Log;

/**
 * YOLP API用の親クラス
 * 基本パラメータとYDFの分離だけ定義する
 *
 */
public class YolpApiBase {
    /** URIビルダー */
    protected Builder uriBuilder;

    /** Errorオブジェクト */
    private YolpError error;

	/** コンテキスト */
    private Context context;

    /** Feature */
    protected JSONArray feature;

    /** Dictionary */
    protected JSONObject dictionary;

    /** ResultInfo */
    private JSONObject resultInfo;

    /** result */
    private Object objResult;

    /** パラメータ */
    protected HashMap<String, String> param;

    /** スレッド実行通知用のリスナ */
    private YolpSearchListener m_listener;

    /** 別スレッド実行時に「実行中」ダイアログを出すか */
    private boolean bWaitDialog = false;

    /** スレッド実行クラス */
    private SearchThread objAsync = null;

    /** 取得結果件数 */
    protected int count = 0;

    /** 取得結果最大件数*/
    private int total = 0;

    /** パラーメータをまとめてセット */
    private HashMap<String, String> params;

    public YolpApiBase(Context context) {
    	this.uriBuilder = new Builder();
    	param = new HashMap<String, String>();
    	param.put("output", "json");
    	this.context = context;
    }

    /**
     * 解析結果の取得系
     * @return
     */
	public JSONArray getFeature() {
		return feature;
	}


	public void setFeature(JSONArray feature) {
		this.feature = feature;
	}


	public JSONObject getDictionary() {
		return dictionary;
	}


	public void setDictionary(JSONObject dictionary) {
		this.dictionary = dictionary;
	}


	public JSONObject getResultInfo() {
		return resultInfo;
	}


	public void setResultInfo(JSONObject resultInfo) {
		this.resultInfo = resultInfo;
	}


	/**
	 * パラメータセット系
	 * @return
	 */
	public void setAppid(String appid) {
		param.put("appid",appid);
	}

	public void setStart(int start) {
		param.put("start", Integer.toString(start));
	}

	public void setResultCount(int results) {
		param.put("results", Integer.toString(results));
	}

	/**
	 * 実行(スレッドなし)
	 */
	public void execute() {
        this.request();
	}

	/**
	 * 実行(スレッドあり)
	 */
	public void executeAsync(YolpSearchListener listener, boolean bWaitDialog){
		m_listener = listener;
		this.bWaitDialog = bWaitDialog;
		objAsync = new SearchThread();
		objAsync.execute("");
	}

	public void cancel(boolean bCancel){
		if(objAsync != null){
			objAsync.cancel(bCancel);
			objAsync = null;
			m_listener = null;
		}
	}

    /**
     * リクエスト
     * @return 結果文字列
     */
    public String request() {
    	for(String key : param.keySet()){
    		this.setParameter(key, param.get(key));
    	}

    	if(params != null){
    		for(String key : params.keySet()){
        		this.setParameter(key, params.get(key));
        	}
    	}

        String uri = Uri.decode(this.uriBuilder.build().toString());
        HttpClient objApi = new HttpClient();
        String sJson = objApi.doGetString(uri);

    	analyzeYDF(sJson);

    	analyze();

        return sJson;
    }

	/**
     * URLセット
     * @param uri
     */
    public void setUri(String uri) {

        try {
            URL url = new URL(uri);

            this.uriBuilder.scheme(url.getProtocol());
            this.uriBuilder.authority(url.getHost());
            this.uriBuilder.path(url.getPath());

        } catch (MalformedURLException e) {
        	e.printStackTrace();
        }
    }

    /**
     * パラメータセット
     * @param key
     * @param value
     */
    public void setParameter(String key, String value) {
        if (value != null && !value.equals("")) {
            this.uriBuilder.appendQueryParameter(key, value);
        }
    }

    /**
     * パラメータセット
     * @param key
     * @param value
     */
    public void setParameter(String key, int value) {
        this.uriBuilder.appendQueryParameter(key, Integer.toString(value));
    }


    /*
     * 独自解析メソッド
     * ※継承先で実装する
     */
    protected void analyze() {
	}

    /**
     * YOLP解析
     * ※jsonをFeature,ResultInfo,Dictionaryにばらすとこまでやる
     */
    protected void analyzeYDF(String sJson) {
        JSONObject ydfObject = null;

        try{
            ydfObject = new JSONObject(sJson);
        }catch(Exception e){
            YolpError error = new YolpError();
            error.setCode("500");
            error.setMessage("予期せぬエラーです");
            setError(error);
            return;
        }

        this.resultInfo = ydfObject.optJSONObject("ResultInfo");

        // resultinfoがなければエラー
        if(this.resultInfo == null){
            analyzeError(ydfObject);
            return;
        }
        setCount(this.resultInfo.optInt("Count"));
        setTotal(this.resultInfo.optInt("Total"));

        // featureとdictionaryは単体の場合に別処理
        this.feature = ydfObject.optJSONArray("Feature");
        if(this.feature == null && ydfObject.has("Feature")){
            this.feature = new JSONArray();
            this.feature.put(ydfObject.optJSONObject("Feature"));
        }
        if(this.feature == null){
        	return;
        }

        this.dictionary = ydfObject.optJSONObject("Dictionary");
    }

    /**
     * エラー解析
     */
    protected void analyzeError(JSONObject ydfObject){
        if(ydfObject != null && !ydfObject.isNull("Code")){
            // エラーがあれば保持
            YolpError error = new YolpError();

            error.setCode(ydfObject.optString("Code"));
            error.setMessage(ydfObject.optString("Message"));
            setError(error);
        }
    }

    public YolpError getError() {
		return error;
	}

	public void setError(YolpError error) {
		this.error = error;
	}

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


    public void setResult(Object objResult) {
		this.objResult = objResult;
	}

	public Object getResult() {
		return objResult;
	}


	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	protected class SearchThread extends AsyncTask<String,Void,Object>{
    	private ProgressDialog m_progDialog;

	    @Override
	    protected void onPreExecute() {
	    	// ダイアログ表示指定ありなら表示
	    	if(bWaitDialog){
	    		// クルクルスタート
	    		m_progDialog = new ProgressDialog(context);
	    		m_progDialog.setMessage("通信中");
	    		m_progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    		m_progDialog.show();
	    	}
	    }

		@Override
		protected Object doInBackground(String...param) {
			YolpApiBase.this.execute();
			return YolpApiBase.this.getResult();
		}

		@Override
		protected void onPostExecute(Object obj) {
			if(m_progDialog != null && m_progDialog.isShowing()){
				m_progDialog.dismiss();
		  	}
			m_listener.endYolpSearch(YolpApiBase.this, obj);
		}

		@Override
		protected void onCancelled() {
			if(m_progDialog != null && m_progDialog.isShowing()){
				m_progDialog.dismiss();
		  	}
		}
    }

	/*
	 * 完了通知用のリスナ
	 * ※resultsで何が返るかは継承先の実装次第
	 */
	public static interface YolpSearchListener{
		abstract boolean endYolpSearch(YolpApiBase api, Object results);
	}

}
