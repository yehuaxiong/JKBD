package cn.ucai.jkbd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.Question;

/**
 * Created by LEO on 2017/6/29.
 */

public class RandomTest extends Activity {
    private TextView subject,limitTime,questionCount;

    private TextView tv_id,tv_question,q1,q2,q3,q4;
    private ImageButton img_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

//        Intent intent = this.getIntent();
//        Exam exam=(Exam)intent.getSerializableExtra("exam");
        Exam exam= ExamApplication.getInstance().getExam();
        List<Question> questionList=ExamApplication.getInstance().getQuestionList();


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


//        subject.setText(""+exam.getSubjectTitle());
//        limitTime.setText(""+exam.getLimitTime());
//        questionCount.setText(""+exam.getQuestionCount());

        subject.setText(""+exam.getSubjectTitle());
        limitTime.setText(""+exam.getLimitTime());
        questionCount.setText(""+exam.getQuestionCount());

        tv_id.setText(questionList.get(0).getId()+".");
        tv_question.setText(""+questionList.get(0).getQuestion());
        q1.setText(""+questionList.get(0).getItem1());
        q2.setText(""+questionList.get(0).getItem2());
        q3.setText(""+questionList.get(0).getItem3());
        q4.setText(""+questionList.get(0).getItem4());
        Picasso.with(this).load(questionList.get(0).getUrl()).into(img_pic);

    }
}
