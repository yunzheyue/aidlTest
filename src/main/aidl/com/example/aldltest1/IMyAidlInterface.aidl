
package com.example.aldltest1;

import com.example.aldltest1.bean.User;


interface IMyAidlInterface {
//跨进程 从Service获取最初的数据
    String getNameOut();

    User getUserBeanOut();
//跨进程 从当前进程向Service中传递数据
//注意in out inout的使用  双向通讯
    void setUserIn(in User user);

    void getUserOut(out User user);

    void getUserInOut(inout User user);
}
