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
import cn.ucai.jkbd.bean.Result;
import cn.ucai.jkbd.biz.ExamBiz;
import cn.ucai.jkbd.biz.IExamBiz;
import cn.ucai.jkbd.utils.OkHttpUtils;
import cn.ucai.jkbd.utils.ResultUtils;

/**
 * Created by LEO on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_EXAM="load_exam";
    public static String LOAD_QUESTION="load_question";
    public static String LOAD_DATA_SUCCESS="load_data_success";

    Exam exam;
    List<Question> questionList;
    public static ExamApplication instance;

    IExamBiz iExamBiz;


//    public ExamApplication() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                iExamBiz=new ExamBiz();
//                iExamBiz.ExamStart();
//            }
//        }).start();
//    }

    public Exam getExam() {
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
