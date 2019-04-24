package com.example.tasktemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity2 extends AppCompatActivity {
    Button btn_activity2;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        editText = findViewById(R.id.tv_activity2);
        btn_activity2 = findViewById(R.id.btn_activity2);
        btn_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入栈
                moveTaskToBack(true);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("tchl","activity2: call onResume");
        JUtils.getSharedPreference().edit().putString(SingleInstanceActivity.a2,System.currentTimeMillis()+"").apply();
        Intent intent = getIntent();
        String str = intent.getStringExtra("value");
        String minappid = intent.getStringExtra(SingleInstanceActivity.minappid);
        editText.setText(str);


        JUtils.getSharedPreference().edit().putString(SingleInstanceActivity.a2MinappId,minappid).apply();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("tchl","activity2: call onNewInent");
        setIntent(intent);
        String str = intent.getStringExtra("value");
        editText.setText(str);
    }

}
