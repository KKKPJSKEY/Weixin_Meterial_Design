package cn.edu.hubu.lhy.Weixin_MD.Fri.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.hubu.lhy.Weixin_MD.Fri.FriFragment;
import cn.edu.hubu.lhy.Weixin_MD.MainActivity;

public class SmsReceiver extends BroadcastReceiver {

    //    @Override
//    public void onReceive(Context context, Intent intent) {
//        // TODO: This method is called when the BroadcastReceiver is receiving
//        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//    public SmsReceiver(){
//
//    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, MesAudioService.class);
        //在广播组件里，通过上下文对象启动音乐播放服务组件
        context.startService(serviceIntent);

        //新建调用Activity组件的意图
        Intent activityIntent = new Intent(context, MainActivity.class);
        activityIntent.putExtra("iscast", true);  //携带数据
        //新建栈用来存放被启动的Activity（当已经存在时，只做移动处理）
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //在广播组件里，通过上下文对象启动Activity组件
        context.startActivity(activityIntent);

    }
}