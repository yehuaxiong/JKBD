package cn.ucai.jkbd.bean;

import java.util.List;

/**
 * Created by LEO on 2017/6/28.
 */

public class Result {
    private int error_code;
    private String reason;
    private List<Exam> exams;

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


    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
