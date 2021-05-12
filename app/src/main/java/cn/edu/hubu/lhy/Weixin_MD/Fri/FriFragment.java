package cn.edu.hubu.lhy.Weixin_MD.Fri;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import cn.edu.hubu.lhy.Weixin_MD.Fri.AudioService.AudioService;
import cn.edu.hubu.lhy.Weixin_MD.Fri.BindAudioService.BindAudioService;
import cn.edu.hubu.lhy.Weixin_MD.Fri.BroadcastReceiver.MesAudioService;
import cn.edu.hubu.lhy.Weixin_MD.MainActivity;
import cn.edu.hubu.lhy.Weixin_MD.R;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriFragment extends Fragment implements View.OnClickListener {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    public FriFragment() {
    // Required empty public constructor
//    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MesFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MesFragment newInstance(String param1, String param2) {
//        MesFragment fragment = new MesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }


    Intent intent;
    Button bTSPlay, bTSStop;

    Button bTBSPlay;

    private Button bTBCRStop;
    private boolean isCast; //是否为广播激活

    ServiceConnection conn = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fri, container, false);
//        return inflater.inflate(R.layout.fragment_fri, container, false);

        bTSPlay = view.findViewById(R.id.bT_SstartMusic);
        bTSPlay.setOnClickListener(this);  //播放按钮
        bTSStop = view.findViewById(R.id.bT_SstopMusic);
        bTSStop.setOnClickListener(this);  //停止按钮
//------------------------------------------------------------------------------------------

        bTBSPlay = view.findViewById(R.id.bT_BSstartMusic);
        bTBSPlay.setOnClickListener(this);  //绑定播放按钮

//------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////
        //this
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ///////////////////////////////////////////////////////////////////////////////////////////
            //this
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.RECEIVE_SMS"}, 1);
        }
        bTBCRStop = view.findViewById(R.id.bT_stopMusic);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //+++++++++++
        Intent intent = getActivity().getIntent(); //获取广播意图对象
        isCast = intent.getBooleanExtra("iscast", false);  //默认值为false
        bTBCRStop.setEnabled(isCast);   //设置停止按钮可用和单击监听
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), R.raw.air);
        if (isCast) {
            ///////////////////////////////////////////////////////////////////////////////////////////
            //this
            Toast.makeText(getContext(), "正在播放音乐...", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();//?????????????????????????
        }
        bTBCRStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显式服务调用意图（非绑定式）
                ///////////////////////////////////////////////////////////////////////////////////////////
                //MainActivity.this
                Intent intent = new Intent(getActivity(), MesAudioService.class);
                //在Activity组件里，停止音乐播放服务
                ///////////////////////////////////////////////////////////////////////////////////////////
                mediaPlayer.stop();//?????????????????????????
                Toast.makeText(getContext(), "已停止播放音乐！", Toast.LENGTH_SHORT).show();
                //+++++++++++
                getActivity().stopService(intent);
                ///////////////////////////////////////////////////////////////////////////////////////////
                //+++++++++++
                getActivity().finish();  //销毁本活动
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ///////////////////////////////////////////////////////////////////////////////////////////
                    //this
                    Toast.makeText(getContext(), "未授权，无法实现预定的功能！", Toast.LENGTH_SHORT).show();
                    ///////////////////////////////////////////////////////////////////////////////////////////
                    //+++++++++++
                    getActivity().finish();
                } else {
                    ///////////////////////////////////////////////////////////////////////////////////////////
                    //this
                    Toast.makeText(getContext(), "请发一条短信验证...", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(getContext(), AudioService.class);  //显式调用服务意图
        /*intent=new Intent("cn.edu.hubu.lhy.Weixin_MD.audio_service_test.MAS");//隐式调用时使用，需要意图过滤器
        intent.setPackage("cn.edu.hubu.lhy.Weixin_MD"); //隐式调用时还需要指定应用的包名*/
        int id = v.getId();  //获取视图id
        switch (id) {
            case R.id.bT_SstartMusic:
                getActivity().startService(intent);
                Toast.makeText(getContext(), "音乐播放服务进行中...", Toast.LENGTH_SHORT).show();
                bTSStop.setEnabled(true);
                bTSPlay.setEnabled(false);
                break;
            case R.id.bT_SstopMusic:
                getActivity().stopService(intent);
                bTSStop.setEnabled(false);
                bTSPlay.setEnabled(true);
                break;
            case R.id.bT_BSstartMusic:
                conn = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) { //建立服务连接时
                        //获取代理人对象
                        BindAudioService.PlayBinder playBinder = (BindAudioService.PlayBinder) service;
                        //调用代理方法
                        playBinder.MyMethod();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        //断开服务连接
                    }
                };

                Intent intent = new Intent(getContext(), BindAudioService.class);  //绑定、显式调用
                /*Intent intent=new Intent("cn.edu.hubu.lhy.Weixin_MD.MUSIC_PLAY_SERVICE");  //需要建立意图过滤器
                intent.setPackage("cn.edu.hubu.lhy.Weixin_MD"); //绑定、隐式时需要指定应用的包名*/
                //绑定服务
                getActivity().bindService(intent, conn, BIND_AUTO_CREATE);
                break;
        }
    }

    @Override
    public void onDestroy() {  //考虑播放时按返回键
//        if(conn!=null){
//            getActivity().unbindService(conn);//将service与activity解绑
//        }
        super.onDestroy();
        if (intent != null) getActivity().stopService(intent); //停止服务
//        getActivity().finish();  //关闭活动
    }
}