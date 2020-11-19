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


public class Activity_C extends AppCompatActivity{
    private Button mStartCA_Button;
    private Button mStartCB_Button;
    private Button mFinishC_Button;
    private Button mDialogC_Button;
    private TextView mActivityC_TextView1;
    private TextView mActivityC_TextView2;
    private String mStatusA="";
    private String mStatusB="";
    private String mStatusC="";
    private String mStatusABC="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        mStartCA_Button=(Button)findViewById(R.id.startCA_button);
        mStartCA_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                如果A是第一次启动，默认值为true，收到false，则不是第一次
                Intent intent=MainActivity.newIntent(Activity_C.this,false);
                startActivity(intent);
            }
        });
        mStartCB_Button=(Button)findViewById(R.id.startCB_button);
        mStartCB_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_C.this,Activity_B.class);
                startActivity(intent);
            }
        });
        mFinishC_Button=(Button)findViewById((R.id.finishC_button));
        mFinishC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDialogC_Button=(Button)findViewById(R.id.dialogC_button);
        mDialogC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_C.this,Activity_Dia.class);
                startActivity(intent);
            }
        });
        mActivityC_TextView1=(TextView)findViewById(R.id.activityC_textView1);
        mActivityC_TextView2=(TextView)findViewById(R.id.activityC_textView2);
        mStatusC="Activity C:onCreate()";
        showText(mStatusC);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStatusC="Activity C:onStart( )";
        showText(mStatusC);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatusC="Activity C:onResume( )";
        showText(mStatusC);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatusC="Activity C:onPause( )";
        showText(mStatusC);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusC="Activity C:onStop( )";
        showText(mStatusC);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusC="Activity C:onDestroy( )";
        showText(mStatusC);
    }

    public void showText(String inputText){
        mStatusC=inputText;
        mSave("dataC",mStatusC);
        mStatusABC=loadABC();
        mStatusABC=inputText+'\n'+mStatusABC;
        mSave("dataABC",mStatusABC);
        mStatusA=mLoad("dataA");
        mStatusB=mLoad("dataB");

        mActivityC_TextView1.setText(mStatusABC);
        if(!mStatusB.equals("")){
            inputText=mStatusA+'\n'+mStatusB+'\n'+mStatusC;
        }else{
            inputText=mStatusA+'\n'+mStatusC;
        }
        mActivityC_TextView2.setText(inputText);
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