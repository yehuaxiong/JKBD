package cn.ucai.jkbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;import android.view.View;
import android.widget.Toast;

import cn.ucai.jkbd.R;

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
