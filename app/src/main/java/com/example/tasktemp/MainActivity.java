package com.example.tasktemp;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    Button btnTaskActivity1,btnTaskActivity2,btnTaskActivity3,btnTaskActivity4,btnGetStackInfo,btnTaskActivity5,btnTaskActivity6;
    Map<String,String> mMap = new HashMap<String,String>();
    Map<String, String> resultMap= new HashMap<String,String>();

    Map<String,String> mMapInfo = new HashMap<String,String>();
    Map<String, String> resultMapInfo= new HashMap<String,String>();
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        packageManager=getPackageManager();
        btnTaskActivity1 = findViewById(R.id.btn_taskactivity1);
        btnTaskActivity2 = findViewById(R.id.btn_taskactivity2);
        btnTaskActivity3 = findViewById(R.id.btn_taskactivity3);
        btnTaskActivity4 = findViewById(R.id.btn_taskactivity4);
        btnTaskActivity5 = findViewById(R.id.btn_taskactivity5);
        btnTaskActivity6 = findViewById(R.id.btn_taskactivity6);
        btnGetStackInfo = findViewById(R.id.btn_getStackInfo);
        btnGetStackInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStackInfo();
            }
        });
        btnTaskActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(),TaskActivity1.class);
                intent.putExtra("value","this is from taskActivity1");
                startActivity(intent);*/
                /*Intent intent = new Intent(getApplicationContext(),TaskActivity1.class);
                intent.putExtra("value","this is from taskActivity1");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
                startTaskActivity2( "btnTaskActivity1");
            }
        });

        btnTaskActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(),TaskActivity2.class);
                intent.putExtra("value","this is from taskActivity2");
                startActivity(intent);*/
                startTaskActivity2( "btnTaskActivity2");
            }
        });

        btnTaskActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(),TaskActivity3.class);
                intent.putExtra("value","this is from taskActivity3");
                startActivity(intent);*/
                startTaskActivity2( "btnTaskActivity3");

            }
        });

        btnTaskActivity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTaskActivity2( "btnTaskActivity4");
            }
        });

        btnTaskActivity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTaskActivity2( "btnTaskActivity5");
            }
        });
        btnTaskActivity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTaskActivity2( "btnTaskActivity6");
            }
        });
    }

    //不满三个时，使用闲置的activity.
    //满三个时，判断传入的minappid是否已经存在，如果存在着启动这个activity。  如果不存在，那么使用最早的actvity
    private void startTaskActivity2(String minappid) {
        getStackInfo2();

        if(resultMap != null && resultMap.size()==3){
            //resultMap.get(resultMap.size()-1);
            //Map<String, String> tempMap;
            String className = "";

            //得到三个activity中保存的minappid
            String minaddId1 = JUtils.getSharedPreference().getString(SingleInstanceActivity.a1MinappId,"");
            String minaddId2= JUtils.getSharedPreference().getString(SingleInstanceActivity.a2MinappId,"");
            String minaddId3 = JUtils.getSharedPreference().getString(SingleInstanceActivity.a3MinappId,"");
            if(minaddId1.equals(minappid)){
                Intent intent= new Intent();//packageManager.getLaunchIntentForPackage(className);
                intent.setClassName(getApplicationContext(),SingleInstanceActivity.a1);
                intent.putExtra("value","size=3"+ " 使用的activity；"+ className+"  Btn:"+minappid);
                intent.putExtra(SingleInstanceActivity.minappid,minappid);
                startActivity(intent);
            }

            if(minaddId2.equals(minappid)){
                Intent intent= new Intent();//packageManager.getLaunchIntentForPackage(className);
                intent.setClassName(getApplicationContext(),SingleInstanceActivity.a2);
                intent.putExtra("value","size=3"+ " 使用的activity；"+ SingleInstanceActivity.a2+"  minappid:"+minappid);
                intent.putExtra(SingleInstanceActivity.minappid,minappid);
                startActivity(intent);
            }

            if(minaddId3.equals(minappid)){
                Intent intent= new Intent();//packageManager.getLaunchIntentForPackage(className);
                intent.setClassName(getApplicationContext(),SingleInstanceActivity.a3);
                intent.putExtra("value","size=3"+ " 使用的activity；"+ className+"  Btn:"+minappid);
                intent.putExtra(SingleInstanceActivity.minappid,minappid);
                startActivity(intent);
            }

           for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                Log.i("tchl","size =3  "+entry.getKey() + " " + entry.getValue());
                className = entry.getValue();
                break;
            }

            Intent intent= new Intent();//packageManager.getLaunchIntentForPackage(className);
            intent.setClassName(getApplicationContext(),className);
            intent.putExtra("value","size=3"+ " 使用的activity；"+ className+"  Btn:"+minappid);
            intent.putExtra(SingleInstanceActivity.minappid,minappid);
            startActivity(intent);
        }else if(resultMap != null && resultMap.size()==2){
            List<String> listActivity = new ArrayList<String>();
            listActivity.add(SingleInstanceActivity.a1);
            listActivity.add(SingleInstanceActivity.a2);
            listActivity.add(SingleInstanceActivity.a3);

            //当有两个activity在使用时，将这两个activity对应的list内容情况。
            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                Log.i("tchl","size =2 "+entry.getKey() + " " + entry.getValue());
                for(int i=0;i<listActivity.size();i++){
                    if(entry.getValue().equals(listActivity.get(i))){
                        listActivity.set(i,"");
                    }
                }
            }
            String  className = "";
            //将list中不是""的class取出。也就是说这个activity还没有被使用。
            for(int i=0;i<listActivity.size();i++){
                if(!listActivity.get(i).equals("")){
                    className = listActivity.get(i);
                    break;
                }
            }
            Intent intent= new Intent();//packageManager.getLaunchIntentForPackage(className);
            intent.setClassName(getApplicationContext(),className);
            intent.putExtra("value","size =2"+ " 使用的activity；"+ className+"  Btn:"+minappid);
            intent.putExtra(SingleInstanceActivity.minappid,minappid);
            startActivity(intent);

        }else if(resultMap != null && resultMap.size()==1){

            List<String> listActivity = new ArrayList<String>();
            listActivity.add(SingleInstanceActivity.a1);
            listActivity.add(SingleInstanceActivity.a2);
            listActivity.add(SingleInstanceActivity.a3);


            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                Log.i("tchl","size =1  "+entry.getKey() + " " + entry.getValue());
                for(int i=0;i<listActivity.size();i++){
                    if(entry.getValue().equals(listActivity.get(i))){
                        listActivity.set(i,"");
                    }
                }
            }

            String  className = "";
            //将list中不是""的第一个class取出。也就是说这个activity还没有被使用。
            for(int i=0;i<listActivity.size();i++){
                if(!listActivity.get(i).equals("")){
                    className = listActivity.get(i);
                    break;
                }
            }

            Intent intent= new Intent();//packageManager.getLaunchIntentForPackage(className);
            intent.setClassName(getApplicationContext(),className);
            intent.putExtra("value"," size=1"+ " 使用的activity；"+ className+"  Btn:"+minappid);
            intent.putExtra(SingleInstanceActivity.minappid,minappid);
            startActivity(intent);

        }else{
            Intent intent = new Intent(getApplicationContext(),TaskActivity1.class);
            intent.putExtra("value"," size=0" + "使用的activity；taskActivity.class"+"  Btn:"+minappid);
            intent.putExtra(SingleInstanceActivity.minappid,minappid);
            startActivity(intent);
        }

    }




    //获取栈信息，根据栈打开的实际进行升序排序
    private void getStackInfo2() {

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int maxNum = 10;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(maxNum);
        //manager.moveTaskToFront();
        if(mMap!=null){
            mMap.clear();
        }
        if(resultMap != null){
            resultMap.clear();
        }

        if (runningTaskInfos != null) {
            int param =0;
            for(int i=0; i<runningTaskInfos.size();i++){
                ComponentName cn = runningTaskInfos.get(i).topActivity;

                if(cn.getClassName().equals(SingleInstanceActivity.a1)){
                    //mMap.put( runningTaskInfos.get(i).id+"",cn.getClassName());

                   /* mMap.put( System.currentTimeMillis()+param+"",cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+(System.currentTimeMillis()+param)+" className:"+cn.getClassName());
                    param++;*/

                   mMap.put(JUtils.getSharedPreference().getString(SingleInstanceActivity.a1,""),cn.getClassName());
                   Log.i("tchl","getStackInfo2: time="+JUtils.getSharedPreference().getString(SingleInstanceActivity.a1,"") +" className:"+cn.getClassName());
                }
                if(cn.getClassName().equals(SingleInstanceActivity.a2)){
                    //mMap.put( runningTaskInfos.get(i).id+"",cn.getClassName());
                    /*mMap.put( System.currentTimeMillis()+param+"",cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+(System.currentTimeMillis()+param)+" className:"+cn.getClassName());
                    param++;*/

                    mMap.put(JUtils.getSharedPreference().getString(SingleInstanceActivity.a2,""),cn.getClassName());
                    Log.i("tchl","getStackInfo2: time="+JUtils.getSharedPreference().getString(SingleInstanceActivity.a2,"") +" className:"+cn.getClassName());

                }
                if(cn.getClassName().equals(SingleInstanceActivity.a3)){
                    //mMap.put( runningTaskInfos.get(i).id+"",cn.getClassName());
                    /*mMap.put( System.currentTimeMillis()+param+"",cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+(System.currentTimeMillis()+param)+" className:"+cn.getClassName());
                    param++;*/

                    mMap.put(JUtils.getSharedPreference().getString(SingleInstanceActivity.a3,""),cn.getClassName());
                    Log.i("tchl","getStackInfo2: time="+JUtils.getSharedPreference().getString(SingleInstanceActivity.a3,"") +" className:"+cn.getClassName());

                }

            }

            resultMap = sortMapByKey(mMap);    //按Key进行排序

            if(resultMap != null){
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    Log.i("tchl","getStackInfo2:"+entry.getKey() + " " + entry.getValue());
                }
            }

        }
    }


    //获取栈信息，根据栈打开的实际进行升序排序
    private void getStackInfo() {

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int maxNum = 10;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(maxNum);
        if(mMapInfo!=null){
            mMapInfo.clear();
        }
        if(resultMapInfo != null){
            resultMapInfo.clear();
        }

        if (runningTaskInfos != null) {
            int param =0;
            for(int i=0; i<runningTaskInfos.size();i++){
                ComponentName cn = runningTaskInfos.get(i).topActivity;
                if(cn.getClassName().equals(SingleInstanceActivity.a1)){
                    mMapInfo.put( runningTaskInfos.get(i).id+"",cn.getClassName());

                   /* mMap.put( System.currentTimeMillis()+param+"",cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+(System.currentTimeMillis()+param)+" className:"+cn.getClassName());
                    param++;*/

                 /*  mMap.put(JUtils.getSharedPreference().getString(SingleInstanceActivity.a1,""),cn.getClassName());
                   Log.i("tchl","getStackInfo: time="+JUtils.getSharedPreference().getString(SingleInstanceActivity.a1,"") +" className:"+cn.getClassName());*/
                }
                if(cn.getClassName().equals(SingleInstanceActivity.a2)){
                    mMapInfo.put( runningTaskInfos.get(i).id+"",cn.getClassName());
                    /*mMap.put( System.currentTimeMillis()+param+"",cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+(System.currentTimeMillis()+param)+" className:"+cn.getClassName());
                    param++;*/

                   /* mMap.put(JUtils.getSharedPreference().getString(SingleInstanceActivity.a2,""),cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+JUtils.getSharedPreference().getString(SingleInstanceActivity.a2,"") +" className:"+cn.getClassName());
*/
                }
                if(cn.getClassName().equals(SingleInstanceActivity.a3)){
                    mMapInfo.put( runningTaskInfos.get(i).id+"",cn.getClassName());
                    /*mMap.put( System.currentTimeMillis()+param+"",cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+(System.currentTimeMillis()+param)+" className:"+cn.getClassName());
                    param++;*/

                    /*mMap.put(JUtils.getSharedPreference().getString(SingleInstanceActivity.a3,""),cn.getClassName());
                    Log.i("tchl","getStackInfo: time="+JUtils.getSharedPreference().getString(SingleInstanceActivity.a3,"") +" className:"+cn.getClassName());
*/
                }

            }

           resultMapInfo = sortMapByKey(mMapInfo);    //按Key进行排序

            if(resultMapInfo != null){
                for (Map.Entry<String, String> entry : resultMapInfo.entrySet()) {
                    Log.i("tchl","getStackInfo:"+entry.getKey() + " " + entry.getValue());
                }
            }

          /*  ComponentName cn = runningTaskInfos.get(0).topActivity;
            String temp = "你当前打开程序的信息为：\n包名：" + cn.getPackageName() + "\n类名："
                    + cn.getClassName()+runningTaskInfos.get(0).description ;
            Log.i("tchl", temp);
           // String = runningTaskInfos.get(0).;
            ActivityManager.RunningTaskInfo  a = runningTaskInfos.get(1);
            Log.i("tchl",a.id+"  ");*/
        }
    }
    /**
     * 使用 Map按key进行排序,升序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }


}
