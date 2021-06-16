package cn.edu.hubu.lhy.Weixin_MD.Fri.BroadcastReceiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import cn.edu.hubu.lhy.Weixin_MD.MainActivity;
import cn.edu.hubu.lhy.Weixin_MD.R;

public class MesAudioService extends Service {
    MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.air);
        mediaPlayer.start();
//        System.out.println("sadhfkdsajfldlsaf-------playing");
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}