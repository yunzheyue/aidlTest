package com.example.aldltest1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aldltest1.bean.User;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection serviceConnection;
    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e("TAG", "连接成功了");

                //将IBinder的数据转化成aidl格式的数据
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("TAG", "连接失败了");
            }
        };

    }

    public void bindService(View view) {
        Intent intent = new Intent(this, MyService.class);

        //第三个参数 就是自动创建各自的对象（跨线程的情况下）
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void getNameOrUserBean(View view) {
        try {
            Toast.makeText(MainActivity.this, iMyAidlInterface.getNameOut() + "   user=" + iMyAidlInterface.getUserBeanOut().toString(), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void activityToService(View view) {
        User user = new User("大美妞", 18);
        try {
            iMyAidlInterface.setUserIn(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void serviceToActivity(View view) {
        User user = new User("中美妞", 16);
        try {
            iMyAidlInterface.getUserOut(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "单向通讯--从service->activity==="+user.toString());
    }

    public void activityAndServiceToCommunicate(View view) {
        User user = new User("小美妞", 14);
        try {
            iMyAidlInterface.getUserInOut(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "双向通讯--从service->activity==="+user.toString());
    }
}
