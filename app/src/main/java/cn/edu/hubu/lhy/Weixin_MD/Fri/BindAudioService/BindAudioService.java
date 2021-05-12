package cn.edu.hubu.lhy.Weixin_MD.Fri.BindAudioService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import cn.edu.hubu.lhy.Weixin_MD.R;

//public class BindAudioService extends Service {
//    public BindAudioService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//}
/*
    Service是编写服务的抽象基类
    onCreate()和onDestroy()是Service的2个重要生命周期方法，需要我们在其内写代码
    代理人例子：“取款人—自动取款机—银行服务器”中的自动取款机
    非绑定方式启动服务时，不需要我们在生命周期方法onBind()内写代码
 */
public class BindAudioService extends Service {
    private MediaPlayer mp;  //成员

    //定义代理人内部类
    public class PlayBinder extends Binder {  //用作代理的内部类
        public void MyMethod() {  //服务方法
            //应用自带的音乐文件white.mp3
            mp = MediaPlayer.create(getApplicationContext(), R.raw.symphony);
            mp.start();
            Log.v("Bind Service Music", "Bind Service Music is onCreate");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.v("Bind Service Music", "Bind Service Music is onBind");
        return new PlayBinder();  //返回服务代理类对象
    }

    @Override
    public void onDestroy() {  //停止音乐播放
        if (mp != null) {
            mp.stop();
            mp.release();
        }
        super.onDestroy();
        Log.v("Bind Service Music", "Bind Service Music is onDestroy");
    }
}