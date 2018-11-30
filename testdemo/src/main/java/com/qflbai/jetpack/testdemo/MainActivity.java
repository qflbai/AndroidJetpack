package com.qflbai.jetpack.testdemo;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.qflbai.jetpack.testdemo.aidl.IBookManager;
import com.qflbai.jetpack.testdemo.widget.ColorProgressBar;

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
    private ColorProgressBar mColorProgressBar;

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

        mColorProgressBar = (ColorProgressBar) findViewById(R.id.tasks_view);

        new Thread(new ProgressRunable()).start();
    }

    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;


    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mColorProgressBar.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
