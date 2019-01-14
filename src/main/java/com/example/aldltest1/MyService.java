package com.example.aldltest1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aldltest1.bean.User;


public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getNameOut() throws RemoteException {
            return "testname";
        }

        @Override
        public User getUserBeanOut() throws RemoteException {
            return new User("美妞",12);
        }

        @Override
        public void setUserIn(User user) throws RemoteException {
            Log.e("TAG", "单向通讯--从activity->service==="+user.toString());
        }

        //这里虽然配置了参数，看似能从activity中传递过来，但事其实不能获取数据。因为在User的bean类中设置了方向为out
        @Override
        public void getUserOut(User user) throws RemoteException {
            Log.e("TAG", "单向通讯--从activity->service==="+user.toString());
            user.setAge(88);
        }

        @Override
        public void getUserInOut(User user) throws RemoteException {
            Log.e("TAG", "双向通讯--从activity->service==="+user.toString());
            user.setName("小小美妞");
            user.setAge(10);
        }
    }
}
