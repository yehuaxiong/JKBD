package cn.ucai.jkbd.bean;

import java.util.List;

/**
 * Created by LEO on 2017/6/28.
 */

public class Result {
    private int error_code;
    private String reason;
    private List<Question> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }


    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }
}
