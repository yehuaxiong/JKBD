package cn.ucai.jkbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.exam;
import cn.ucai.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void test(android.view.View view) {
        OkHttpUtils<exam> utils=new OkHttpUtils<>(this);
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(exam.class)
                .execute(new OkHttpUtils.OnCompleteListener<exam>(){

                    @Override
                    public void onSuccess(exam result) {
                        Log.e("main","result="+result);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });


        Intent intent=new Intent(MainActivity.this,RandomTest.class);
        startActivity(intent);
    }

    public void test_all(android.view.View view) {

    }

    public void setting(android.view.View view) {

    }

    public void quit(android.view.View view) {

    }
}
