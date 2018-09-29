package com.qflbai.jetpack.testdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.qflbai.jetpack.testdemo.aidl.Book;
import com.qflbai.jetpack.testdemo.aidl.IBookManager;
import com.qflbai.jetpack.testdemo.service.AidlService;
import com.qflbai.lib.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // aidlTest();
        initListView();
    }

    private void aidlTest() {
//        Button button = findViewById(R.id.btn);
//        Button button2 = findViewById(R.id.btn2);
//        Button button3 = findViewById(R.id.btn3);
//        final Intent intent = new Intent(getApplicationContext(), AidlService.class);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
//            }
//        });
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Book book = new Book();
//                book.setBookName("okkk:" + (i++));
//                book.setBookId(i);
//                try {
//                    iBookManager.addBook(book);
//
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<Book> bookList = null;
//                try {
//                    bookList = iBookManager.getBookList();
//                    for (Book book1 : bookList) {
//                        String bookName = book1.getBookName();
//                        int bookId = book1.getBookId();
//                        LogUtil.w("book", bookName);
//                        LogUtil.w("book", bookId+"");
//                    }
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
    }

    private void initListView() {
        ListView listView = findViewById(R.id.lv_1);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 900000; i++) {
            list.add("有多少  " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listview_item,R.id.tv_1,list);
        listView.setAdapter(adapter);
    }
}
