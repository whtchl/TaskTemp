package com.example.tasktemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity1 extends AppCompatActivity {
    Button btnOpenActivity2,btn_activity1;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        editText = findViewById(R.id.tv_activity1);

        btnOpenActivity2 = findViewById(R.id.btn_openActivity2);
        btn_activity1 = findViewById(R.id.btn_activity1);
        btn_activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入栈
                moveTaskToBack(true);
            }
        });
        btnOpenActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TaskActivity2.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i("tchl","activity1: call onResume");
        Intent intent = getIntent();
        String str = intent.getStringExtra("value");
        String minappid = intent.getStringExtra(SingleInstanceActivity.minappid);
        editText.setText(str);
        JUtils.getSharedPreference().edit().putString(SingleInstanceActivity.a1,System.currentTimeMillis()+"").apply();
        JUtils.getSharedPreference().edit().putString(SingleInstanceActivity.a1MinappId,minappid).apply();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("tchl","activity1: call onNewInent");
        setIntent(intent);
        String str = intent.getStringExtra("value");
        editText.setText(str);
    }
}
