package com.qflbai.jetpack.testdemo;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.qflbai.jetpack.testdemo.aidl.IBookManager;
import com.qflbai.jetpack.testdemo.widget.CasualBgView;
import com.qflbai.jetpack.testdemo.widget.marquee.MarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    IBookManager iBookManager;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager = IBookManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iBookManager = null;
        }
    };
    private int i;
    private MarqueeView mColorProgressBar;
    private CasualBgView casualBgView;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // aidlTest();
        initListView();
    }


    private void initListView() {
        mColorProgressBar = findViewById(R.id.home_marrquee_view);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        List<String> list1 = new ArrayList<>();
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        list1.add("10");
        list1.add("11");


        mColorProgressBar.startWithList(list, list1);
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorProgressBar.showNext();
            }
        });

        a = 0;
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 1) {
                    a = 0;
                } else {
                    a = 1;
                }
                mColorProgressBar.setShowPlace(a);


            }
        });
        // casualBgView = findViewById(R.id.cv);
        // new Thread(new ProgressRunable()).start();
    }

    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;
    // 随机颜色  
    Random random = new Random();

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (true) {
                casualBgView.post(new Runnable() {
                    @Override
                    public void run() {
                        int color = 0xff000000 | random.nextInt(0x00ffffff);
                        casualBgView.setCasualColor(color);
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
