package cn.ucai.jkbd;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cn.ucai.jkbd.activity.MainActivity;
import cn.ucai.jkbd.activity.RandomTest;
import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.utils.OkHttpUtils;

/**
 * Created by LEO on 2017/6/30.
 */

public class ExamApplication extends Application {
    Exam exam;
    List<Question> questionList;
    public static ExamApplication instance;

    public Exam getExam() {
        OkHttpUtils<Exam> utils=new OkHttpUtils<>(this);
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(Exam.class)
                .execute(new OkHttpUtils.OnCompleteListener<Exam>(){

                    @Override
                    public void onSuccess(Exam exam) {

                        Log.e("main","result=------"+exam);
                        ExamApplication.this.exam=exam;
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });

        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public static ExamApplication getInstance() {
        return instance;
    }

    public void setInstance(ExamApplication instance) {
        this.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        getExam();
    }

}
