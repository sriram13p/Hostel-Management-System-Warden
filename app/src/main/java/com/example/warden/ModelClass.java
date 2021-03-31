package com.example.warden;

import android.view.View;

public class ModelClass {
    private String sid,reason,eintime,eouttime,correct,wrong,call;


    ModelClass(String sid, String reason, String eintime, String eouttime, String correct, String wrong, String call)
    {
        this.sid=sid;
        this.reason=reason;
        this.eintime=eintime;
        this.eouttime=eouttime;
        this.correct=correct;
        this.wrong=wrong;
        this.call=call;
    }

    public String getSid() {
        return sid;
    }

    public String getReason() {
        return reason;
    }

    public String getEintime() {
        return eintime;
    }

    public String getEouttime() {
        return eouttime;
    }

    public String getCorrect() {
        return correct;
    }

    public String getWrong() {
        return wrong;
    }

    public String getCall() {
        return call;
    }
}
