package com.qflbai.jetpack.androidjetpack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        initTitle();
        initTabLayout();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tablayout_title);
        String[] titleTab = {"我的","音乐馆","发现"};
        for (int i = 0; i < titleTab.length; i++) {
            //创建tab tab.setText(titles[i]);//设置文字 tabLayout.addTab(tab);//添加到tabLayout中 }
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.item_tablayout_tabitem);
            TextView tvMy = tab.getCustomView().findViewById(R.id.tv_tab_title);
            tvMy.setText(titleTab[i]);
            tabLayout.addTab(tab);
        }

    }

    private void initTitle() {
        Toolbar toolbar = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
}
