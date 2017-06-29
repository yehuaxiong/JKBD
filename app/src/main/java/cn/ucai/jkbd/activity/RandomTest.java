package cn.ucai.jkbd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.Exam;

/**
 * Created by LEO on 2017/6/29.
 */

public class RandomTest extends Activity {
    private TextView subject,limitTime,questionCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        Intent intent = this.getIntent();
        Exam exam=(Exam)intent.getSerializableExtra("exam");
        subject= (TextView) findViewById(R.id.subject);
        limitTime= (TextView) findViewById(R.id.limitTime);
        questionCount= (TextView) findViewById(R.id.questionCount);

        subject.setText(""+exam.getSubjectTitle());
        limitTime.setText(""+exam.getLimitTime());
        questionCount.setText(""+exam.getQuestionCount());
    }
}
