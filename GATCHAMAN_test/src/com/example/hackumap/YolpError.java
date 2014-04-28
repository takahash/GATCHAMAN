package com.example.hackumap;

import java.io.Serializable;

public class YolpError implements Serializable{
    /**
     * 自動生成のシリアルバージョン
     */
    private static final long serialVersionUID = 432578141893476775L;

    /** エラーコード */
    private String code;

    /** エラーメッセージ */
    private String message;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



}