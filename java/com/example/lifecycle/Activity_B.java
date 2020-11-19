package com.example.lifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Activity_B extends AppCompatActivity{
    private Button mStartBA_Button;
    private Button mStartBC_Button;
    private Button mFinishB_Button;
    private Button mDialogB_Button;
    private TextView mActivityB_TextView1;
    private TextView mActivityB_TextView2;
    private String mStatusA="";
    private String mStatusB="";
    private String mStatusC="";
    private String mStatusABC="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        mStartBA_Button=(Button)findViewById(R.id.startBA_button);
        mStartBA_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                如果A是第一次启动，默认值为true，收到false，则不是第一次
                Intent intent=MainActivity.newIntent(Activity_B.this,false);
                startActivity(intent);
            }
        });
        mStartBC_Button=(Button)findViewById(R.id.startBC_button);
        mStartBC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_B.this,Activity_C.class);
                startActivity(intent);
            }
        });
        mFinishB_Button=(Button)findViewById((R.id.finishB_button));
        mFinishB_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDialogB_Button=(Button)findViewById(R.id.dialogB_button);
        mDialogB_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_B.this,Activity_Dia.class);
                startActivity(intent);
            }
        });
        mActivityB_TextView1=(TextView)findViewById(R.id.activityB_textView1);
        mActivityB_TextView2=(TextView)findViewById(R.id.activityB_textView2);
        mStatusB="Activity B:onCreate()";
        showText(mStatusB);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStatusB="Activity B:onStart( )";
        showText(mStatusB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatusB="Activity B:onResume( )";
        showText(mStatusB);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatusB="Activity B:onPause( )";
        showText(mStatusB);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusB="Activity B:onStop( )";
        showText(mStatusB);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusB="Activity B:onDestroy( )";
        showText(mStatusB);
    }


    public void showText(String inputText){
        mStatusB=inputText;
        mSave("dataB",mStatusB);
        mStatusABC=loadABC();
        mStatusABC=inputText+'\n'+mStatusABC;
        mSave("dataABC",mStatusABC);
        mStatusA=mLoad("dataA");
        mStatusC=mLoad("dataC");

//        若在创建B时A的状态为暂停，则说明A启动B，而此时B正在运行，
//        不会调用stop A，在B销毁时才有，故手工在此加上
        String ExceptA="Activity A:onPause( )";
        String ExceptC="Activity C:onPause( )";
        if(mStatusA.equals(ExceptA)){
            mStatusA="Activity A:onStop( )";
            mStatusABC=mStatusA+'\n'+mStatusABC;
        }
        if(mStatusC.equals(ExceptC)){
            mStatusC="Activity C:onStop( )";
            mStatusABC=mStatusC+'\n'+mStatusABC;
        }
        mActivityB_TextView1.setText(mStatusABC);
        if(!mStatusC.equals("")){
            inputText=mStatusA+'\n'+mStatusB+'\n'+mStatusC;
        }else{
            inputText=mStatusA+'\n'+mStatusB;
        }
        mActivityB_TextView2.setText(inputText);
    }

    public void mSave(String fileName,String inputText){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
//            输出流，输入文件流
            out=openFileOutput(fileName, Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(writer!=null) writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String mLoad(String fileName){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder result=new StringBuilder();
        try{
            in=openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                result.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return  result.toString();
    }

    public String loadABC(){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder result=new StringBuilder();
        try{
            in=openFileInput("dataABC");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            if((line=reader.readLine())!=null) result.append(line);
            while((line=reader.readLine())!=null){
                result.append('\n'+line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(reader!=null) reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result.toString();
    }

}
