package cn.ucai.jkbd.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.biz.ExamBiz;
import cn.ucai.jkbd.biz.IExamBiz;

/**
 * Created by LEO on 2017/6/29.
 */

public class RandomTest extends Activity {
    private TextView subject,limitTime,questionCount,tv_loading;
    private TextView tv_id,tv_question,q1,q2,q3,q4;
    private ImageButton img_pic;
    private LinearLayout linearLayout;
    private ProgressBar pb_loading;

    Exam exam;
    List<Question> questionList;

    IExamBiz iExamBiz;

    boolean isExam=false;
    boolean isQustion=false;

    boolean isExamReceiver=false;
    boolean isQustionReceiver=false;

    LoadExamBroadcast loadExamBroadcast;
    LoadQuestionBroadcast loadQuestionBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        loadExamBroadcast=new LoadExamBroadcast();
        loadQuestionBroadcast=new LoadQuestionBroadcast();
        setListener();

        initView();

        loadDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadExamBroadcast!=null){
            unregisterReceiver(loadExamBroadcast);
        }
        if(loadQuestionBroadcast!=null){
            unregisterReceiver(loadQuestionBroadcast);
        }
    }

    private void setListener() {
        registerReceiver(loadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM));
        registerReceiver(loadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_QUESTION));
    }

    private  void loadDate(){
        iExamBiz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                iExamBiz.ExamStart();
            }
        }).start();
    }

    private void initData(){
        if(isExamReceiver&&isQustionReceiver){
            if(isExam&&isQustion){
                linearLayout.setVisibility(View.GONE);

                exam= ExamApplication.getInstance().getExam();
                if(exam!=null){
                    subject.setText(""+exam.getSubjectTitle());
                    limitTime.setText(""+exam.getLimitTime());
                    questionCount.setText(""+exam.getQuestionCount());
                }else{
                    Log.e("exam","exam为空！");
                }

                questionList=ExamApplication.getInstance().getQuestionList();
                if(questionList!=null){
                    tv_id.setText(questionList.get(0).getId()+".");
                    tv_question.setText(""+questionList.get(0).getQuestion());
                    q1.setText(""+questionList.get(0).getItem1());
                    q2.setText(""+questionList.get(0).getItem2());
                    q3.setText(""+questionList.get(0).getItem3());
                    q4.setText(""+questionList.get(0).getItem4());
                    Picasso.with(this).load(questionList.get(0).getUrl()).into(img_pic);
                }else{
                    Log.e("onCreate:question","questionList为空！");
                }
            }else{
                pb_loading.setVisibility(View.GONE);
                tv_loading.setText("加载失败，点击屏幕重新加载！");
            }
        }

    }

    private void initView(){
        subject= (TextView) findViewById(R.id.subject);
        limitTime= (TextView) findViewById(R.id.limitTime);
        questionCount= (TextView) findViewById(R.id.questionCount);

        tv_id= (TextView) findViewById(R.id.tv_id);
        tv_question= (TextView) findViewById(R.id.tv_question);
        q1= (TextView) findViewById(R.id.tv_A);
        q2= (TextView) findViewById(R.id.tv_B);
        q3= (TextView) findViewById(R.id.tv_C);
        q4= (TextView) findViewById(R.id.tv_D);
        img_pic= (ImageButton) findViewById(R.id.img_pic);

        linearLayout= (LinearLayout) findViewById(R.id.ll_loading);
        tv_loading= (TextView) findViewById(R.id.tv_loading);
        pb_loading= (ProgressBar) findViewById(R.id.pb_loading);
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            if(isSuccess){
                isExam=true;
            }
            isExamReceiver=true;
            initData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            if(isSuccess){
                isQustion=true;
            }
            isQustionReceiver=true;
            initData();
        }
    }
}
