package cn.edu.hubu.lhy.Weixin_MD.Fri.AudioService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import cn.edu.hubu.lhy.Weixin_MD.R;

//public class AudioService extends Service {
//    public AudioService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//}
/*
    Service是编写服务的抽象基类。
    onCreate()和onDestroy()是Service的2个重要生命周期方法，通常需要我们在其内写代码。
    以非绑定方式启动服务时，不需要我们在生命周期方法onBind()内写代码，反之不然。
 */
public class AudioService extends Service {
    MediaPlayer mp;
    @Override
    public void onCreate() {  //在开始服务时调用
        super.onCreate();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.life);
        mp.start();
        Log.v("Service Music", "Service Music is onCreate");
    }
    @Override
    public void onDestroy() {  //在停止服务时调用
        super.onDestroy();
        mp.stop();
        if(mp!=null) mp=null;
        Log.v("Service Music", "Service Music is onDestroy");
    }
    @Override
    public IBinder onBind(Intent intent) {  //不可省略的生命周期方法
        // TODO: Return the communication channel to the service.
        Log.v("Service Music", "Service Music is onBind");
        throw new UnsupportedOperationException("Not yet implemented");

    }
}