package cn.ucai.jkbd.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
    private LinearLayout linearLayout,ll_C,ll_D;
    private ProgressBar pb_loading;
    private CheckBox cb_C,cb_D;

    Exam exam;
    Question question;
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

        iExamBiz=new ExamBiz();
        loadData();
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

    private  void loadData(){
        linearLayout.setEnabled(false);
        pb_loading.setVisibility(View.VISIBLE);
        tv_loading.setText("Loading...");
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

                question=iExamBiz.getQuestion();
                if(question!=null){
                    tv_id.setText(iExamBiz.getIndex()+1+".");
                    tv_question.setText(""+question.getQuestion());
                    q1.setText(""+question.getItem1());
                    q2.setText(""+question.getItem2());
                    q3.setText(""+question.getItem3());
                    q4.setText(""+question.getItem4());

                    ll_C.setVisibility(question.getItem3().equals("")?View.GONE:View.VISIBLE);
                    ll_D.setVisibility(question.getItem4().equals("")?View.GONE:View.VISIBLE);
                    cb_C.setVisibility(question.getItem3().equals("")?View.GONE:View.VISIBLE);
                    cb_D.setVisibility(question.getItem4().equals("")?View.GONE:View.VISIBLE);

                    if(question.getUrl()!=null&&!question.getUrl().equals("")){
                        img_pic.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(question.getUrl()).into(img_pic);
                    }else{
                        img_pic.setVisibility(View.GONE);
                    }

                }else{
                    Log.e("onCreate:question","questionList为空！");
                }
            }else{
                linearLayout.setEnabled(true);
                pb_loading.setVisibility(View.GONE);
                tv_loading.setText("加载失败，点击屏幕重新加载！");

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                });
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

        ll_C= (LinearLayout) findViewById(R.id.ll_C);
        ll_D= (LinearLayout) findViewById(R.id.ll_D);
        cb_C= (CheckBox) findViewById(R.id.cb_C);
        cb_D= (CheckBox) findViewById(R.id.cb_D);
    }

    public void pre(View view) {
        iExamBiz.Pre();
        initData();
    }

    public void next(View view) {
        iExamBiz.Next();
        initData();
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
