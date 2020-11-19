package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    //启动B的按钮
    private Button mStartAB_Button;
    //启动C的按钮
    private Button mStartAC_Button;
    //结束A的按钮
    private Button mFinishA_Button;
    //对话按钮
    private Button mDialogA_Button;
    private TextView mActivityA_textView1;
    private TextView mActivityA_textView2;
    private static String mStatusA="";
    private static String mStatusB="";
    private static String mStatusC="";
    private static String mStatusABC="";
    private boolean FirstCreate=true;//判断activityA是否是第一次启动

    public static final String EXTRA_IS_FIRST_CREATE="com.example.android.lifecycle.extra.is.first.create";
    //Intent方法，从其他activity启动activity_A时，附带一个布尔变量，判断activityA是否是第一次启动，如果是清除文本里以前的内容
    public static Intent newIntent(Context packageContext, boolean isFirstCreate){
        Intent intent = new Intent(packageContext,MainActivity.class);
        intent.putExtra(EXTRA_IS_FIRST_CREATE,isFirstCreate);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirstCreate=getIntent().getBooleanExtra(EXTRA_IS_FIRST_CREATE,true);
        //StartB_Button按钮
        mStartAB_Button = (Button)findViewById(R.id.startAB_button);
        mStartAB_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity_B.class);
                startActivity(intent);
            }
        });
        //StartC_Button按钮
        mStartAC_Button = (Button)findViewById(R.id.startAC_button);
        mStartAC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity_C.class);
                startActivity(intent);
            }
        });
        //FinishA_Button按钮
        mFinishA_Button = (Button)findViewById(R.id.finishA_button);
        mFinishA_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDialogA_Button = (Button)findViewById(R.id.dialogA_button);
        //Dialog_Button按钮
        mDialogA_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity_Dia.class);
                startActivity(intent);
            }
        });
        //文本框
        mActivityA_textView1 = (TextView)findViewById(R.id.activityA_textView1);
        mActivityA_textView2 = (TextView)findViewById(R.id.activityA_textView2);
        mStatusA="Activity A:onCreate( )";
        if(FirstCreate){//初始化文本
            init_data();
            FirstCreate=false;
        }
        showText(mStatusA);//showText显示文本方法调用
    }
    //记录其他5个生命周期的内容，并保存到文件中
    @Override
    protected void onStart() {
        super.onStart();
        mStatusA="Activity A:onStart( )";
        showText(mStatusA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatusA="Activity A:onResume( )";
        showText(mStatusA);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatusA="Activity A:onPause( )";
        showText(mStatusA);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusA="Activity A:onStop( )";
        showText(mStatusA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusA="Activity A:onDestroy( )";
        showText(mStatusA);
    }

    //初始化文本文件
    public void init_data(){
        String input="";
        mSave("dataABC",input);
        mSave("dataA",input);
        mSave("dataB",input);
        mSave("dataC",input);
    }

    //向文本框内显示内容
    public void showText(String inputText){
        mStatusA=inputText;
        mSave("dataA",mStatusA);//此时A的状态并保存
        mStatusABC=loadABC();
        mStatusABC=inputText+'\n'+mStatusABC;
        mSave("dataABC",mStatusABC);//此时保存总记录ABC文件
        mStatusB=mLoad("dataB");
        mStatusC=mLoad("dataC");

        if(!mStatusB.equals("")&&!mStatusC.equals(""))inputText=mStatusA+'\n'+mStatusB+'\n'+mStatusC;
        if(!mStatusB.equals("")&&mStatusC.equals(""))inputText=mStatusA+'\n'+mStatusB;
        if(mStatusB.equals("")&&!mStatusC.equals(""))inputText=mStatusA+'\n'+mStatusC;
        if(mStatusB.equals("")&&mStatusC.equals(""))inputText=mStatusA;
        mActivityA_textView2.setText(inputText);
        mActivityA_textView1.setText(mStatusABC);
    }

    //以下为文本文件的处理函数
    public void mSave(String fileName,String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public String mLoad(String fileName) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            if((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}