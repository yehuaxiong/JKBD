package cn.ucai.jkbd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private TextView tv_id,tv_question,q1,q2,q3,q4,tv_time;
    private ImageButton img_pic;
    private LinearLayout linearLayout,ll_C,ll_D;
    private ProgressBar pb_loading;
    private RadioButton cb_A,cb_B,cb_C,cb_D;
    private RadioButton[] rb=new RadioButton[4];

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

                    initTimer(exam);
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

                    initRadioButton();

                    String userAnswer=question.getUserAnswer();
                    if(userAnswer!=null&&!userAnswer.equals("")){
                        int a=Integer.parseInt(userAnswer)-1;
                        rb[a].setChecked(true);
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

    private void initExam(){
        exam= ExamApplication.getInstance().getExam();
        if(exam!=null){
            subject.setText(""+exam.getSubjectTitle());
            limitTime.setText(""+exam.getLimitTime());
            questionCount.setText(""+exam.getQuestionCount());
        }else{
            Log.e("initExam:exam","exam为空！");
        }
    }

    private void initQuestion(){
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

            initRadioButton();

            String userAnswer=question.getUserAnswer();
            if(userAnswer!=null&&!userAnswer.equals("")){
                int a=Integer.parseInt(userAnswer)-1;
                rb[a].setChecked(true);
            }

        }else{
            Log.e("initQuestion:question","questionList为空！");
        }
    }

    private void initTimer(Exam exam) {
        int sumTime=exam.getLimitTime()*60*1000;
//        int sumTime=1*60*1000;

        final long overTime=sumTime+System.currentTimeMillis();
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l=overTime-System.currentTimeMillis();
                final long min=l/1000/60;
                final long sec=l/1000%60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText("剩余时间:"+min+"分"+sec+"秒");
                    }
                });
            }
        },0,1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        },sumTime);

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
        tv_time= (TextView) findViewById(R.id.tv_time);
        img_pic= (ImageButton) findViewById(R.id.img_pic);

        linearLayout= (LinearLayout) findViewById(R.id.ll_loading);
        tv_loading= (TextView) findViewById(R.id.tv_loading);
        pb_loading= (ProgressBar) findViewById(R.id.pb_loading);

        ll_C= (LinearLayout) findViewById(R.id.ll_C);
        ll_D= (LinearLayout) findViewById(R.id.ll_D);

        cb_A= (RadioButton) findViewById(R.id.cb_A);
        cb_B= (RadioButton) findViewById(R.id.cb_B);
        cb_C= (RadioButton) findViewById(R.id.cb_C);
        cb_D= (RadioButton) findViewById(R.id.cb_D);
        rb[0]=cb_A;
        rb[1]=cb_B;
        rb[2]=cb_C;
        rb[3]=cb_D;

        cb_A.setOnClickListener(listener);
        cb_B.setOnClickListener(listener);
        cb_C.setOnClickListener(listener);
        cb_D.setOnClickListener(listener);
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           v.getId();
            for(int i=0;i<4;i++){
                if(rb[i].getId()!=v.getId()){
                    rb[i].setChecked(false);
                }
            }
        }
    };


    public void pre(View view) {
        setUserAnswer();
        iExamBiz.Pre();
        //initData();
        initExam();
        initQuestion();
    }

    public void next(View view) {
        setUserAnswer();
        iExamBiz.Next();
        //initData();
        initExam();
        initQuestion();
    }

    public void initRadioButton(){
        cb_A.setChecked(false);
        cb_B.setChecked(false);
        cb_C.setChecked(false);
        cb_D.setChecked(false);
    }

    public void setUserAnswer(){
        for(int i=0;i<rb.length;i++){
            if(rb[i].isChecked()){
                iExamBiz.getQuestion().setUserAnswer(String.valueOf(i+1));
                return;
            }
        }
    }

    public void commit(View view) {
        setUserAnswer();
        int score=iExamBiz.commit();
        View inflate=View.inflate(this,R.layout.layout_result,null);
        TextView tvResult= (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n"+score+"分！");

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.create().show();
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
