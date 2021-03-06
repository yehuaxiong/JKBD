package cn.ucai.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.Result;
import cn.ucai.jkbd.utils.OkHttpUtils;
import cn.ucai.jkbd.utils.ResultUtils;

/**
 * Created by LEO on 2017/6/30.
 */

public class ExamDao implements IExamDao{
    @Override
    public void loadExamInfo() {
        OkHttpUtils<Exam> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(Exam.class)
                .execute(new OkHttpUtils.OnCompleteListener<Exam>(){

                    @Override
                    public void onSuccess(Exam exam) {

                        Log.e("main","result=------"+exam);

                        ExamApplication.getInstance().setExam(exam);

                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM)
                                                    .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM)
                                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadQuestionLists() {
        OkHttpUtils<String> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils.url(uri)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>(){
                    @Override
                    public void onSuccess(String jsonStr) {
                        boolean success=false;
                        Result result= ResultUtils.getListResultFromJson(jsonStr);
                        if(result!=null&&result.getError_code()==0){
                            List<Question> list=result.getResult();
                            if(list!=null&&list.size()>0){
//                                mExamList=list;
                                ExamApplication.getInstance().setQuestionList(list);
                                success=true;
                            }
                        }
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_QUESTION)
                                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,success));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);

                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_QUESTION)
                                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }
}
