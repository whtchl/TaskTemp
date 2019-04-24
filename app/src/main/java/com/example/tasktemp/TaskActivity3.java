package com.example.tasktemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity3 extends AppCompatActivity {
    Button btn_activity3;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        editText = findViewById(R.id.tv_activity3);
        btn_activity3 = findViewById(R.id.btn_activity3);
        btn_activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(false);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("tchl","activity3: call onResume");
        JUtils.getSharedPreference().edit().putString(SingleInstanceActivity.a3,System.currentTimeMillis()+"").apply();
        Intent intent = getIntent();
        String str = intent.getStringExtra("value");
        String minappid = intent.getStringExtra(SingleInstanceActivity.minappid);
        editText.setText(str);

        JUtils.getSharedPreference().edit().putString(SingleInstanceActivity.a3MinappId,minappid).apply();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("tchl","activity3: call onNewInent");
        setIntent(intent);
        String str = intent.getStringExtra("value");
        editText.setText(str);
    }
}
