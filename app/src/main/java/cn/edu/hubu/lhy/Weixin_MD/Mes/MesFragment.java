package cn.edu.hubu.lhy.Weixin_MD.Mes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.edu.hubu.lhy.Weixin_MD.Mes.Music_Player.MusicService;
import cn.edu.hubu.lhy.Weixin_MD.Mes.Music_Player.MusicPlayerStatus;
import cn.edu.hubu.lhy.Weixin_MD.R;
import cn.edu.hubu.lhy.Weixin_MD.Mes.Recycler_View.MyAdapter;
import cn.edu.hubu.lhy.Weixin_MD.Mes.Recycler_View.NatureModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MesFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class MesFragment extends Fragment implements View.OnClickListener {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    public MesFragment() {
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

    // 获取界面中显示歌曲标题、作者文本框
    TextView title, author;
    // 播放/暂停、停止按钮、上一曲按钮，下一曲按钮
    ImageButton play, stop, next, last;

    ActivityReceiver activityReceiver;

    MusicPlayerStatus musicPlayerStatus;

    public static final String CTL_ACTION =
            "org.xr.action.CTL_ACTION";
    public static final String UPDATE_ACTION =
            "org.xr.action.UPDATE_ACTION";
    // 定义音乐的播放状态，0x11代表没有播放；0x12代表正在播放；0x13代表暂停

    int status = 0x11;
    String[] titleStrs = new String[]{"Inside the Lines", "Landslide", "Life", "Symphony", "The Spectre"};
    String[] authorStrs = new String[]{"Mike Perry", "Headhunterz", "Tobu", "Clean Bandit", "Alan Walker"};

    public MesFragment(MusicPlayerStatus musicPlayerStatus) {
        this.musicPlayerStatus = musicPlayerStatus;
    }
//    String[] titleStrs = new String[] { "liangliang", "wuhangya","three" ,"four","fif" };
//    String[] authorStrs = new String[] { "WuBiceng", "A Group people","No Idea","Who sing","This"};


    /**
     * 创建视图代码
     *
     * @param inflater           布局文件访问工具
     * @param container          容器
     * @param savedInstanceState 数据
     * @return View 返回视图
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);

//        MyAdapter adapter = new MyAdapter(this, NatureModel.getObjectList());
        MyAdapter adapter = new MyAdapter(getContext(), NatureModel.getObjectList());
        recyclerView.setAdapter(adapter);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        return inflater.inflate(R.layout.fragment_mes, container, false);
        /////////////////////////////////////////////////////////////////////////////

        // 获取程序界面界面中的两个按钮
        play = (ImageButton) view.findViewById(R.id.iB_playPause);
        stop = (ImageButton) view.findViewById(R.id.iB_stop);
        title = (TextView) view.findViewById(R.id.tV_musicName);
        author = (TextView) view.findViewById(R.id.tV_singerName);

        //获取上一首、下一首按钮
        last = (ImageButton) view.findViewById(R.id.iB_lastPiece);
        next = (ImageButton) view.findViewById(R.id.iB_nextPlay);

        // 为两个按钮的单击事件添加监听器
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        //为上一首、下一首按钮添加监听器
        last.setOnClickListener(this);
        next.setOnClickListener(this);


        activityReceiver = new ActivityReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        // 指定BroadcastReceiver监听的Action
        filter.addAction(UPDATE_ACTION);
        // 注册BroadcastReceiver

        getActivity().registerReceiver(activityReceiver, filter);

        Intent intent = new Intent(getActivity(), MusicService.class);
        // 启动后台Service

        getActivity().startService(intent);
        flush(musicPlayerStatus.getUpdate(),musicPlayerStatus.getCurrent());


        return view;


    }

    // 自定义的BroadcastReceiver，负责监听从Service传回来的广播
    public class ActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取Intent中的update消息，update代表播放状态
            int update = intent.getIntExtra("update", -1);
            musicPlayerStatus.setUpdate(update);
            // 获取Intent中的current消息，current代表当前正在播放的歌曲
            int current = intent.getIntExtra("current", -1);
            musicPlayerStatus.setCurrent(current);
            flush(update,current);
        }
    }

    public void flush(int update,int current) {

        if (current >= 0) {
            title.setText(titleStrs[current]);
            author.setText(authorStrs[current]);
        }


        switch (update) {
            case 0x11:
                play.setImageResource(R.drawable.arrow_right_drop_circle_outline);
                status = 0x11;
                break;
            //控制系统进入播放状态
            case 0x12:
                // 播放状态下设置使用暂停图标
                play.setImageResource(R.drawable.pause_circle_outline);
                // 设置当前状态
                status = 0x12;
                break;
            // 控制系统进入暂停状态
            case 0x13:
                // 暂停状态下设置使用播放图标
                play.setImageResource(R.drawable.arrow_right_drop_circle_outline);
                // 设置当前状态
                status = 0x13;
                break;
        }
    }

    @Override
    public void onClick(View source) {
        // 创建Intent
        Intent intent = new Intent("org.xr.action.CTL_ACTION");
        switch (source.getId()) {
            // 按下播放/暂停按钮
            case R.id.iB_playPause:
                intent.putExtra("control", 1);
                break;
//            // 按下停止按钮
            case R.id.iB_stop:
                intent.putExtra("control", 2);
                break;
            // 按下上一曲按钮
            case R.id.iB_lastPiece:
                intent.putExtra("control", 3);
                break;
            // 按下下一曲按钮
            case R.id.iB_nextPlay:
                intent.putExtra("control", 4);
                break;
        }
        // 发送广播，将被Service组件中的BroadcastReceiver接收到
        getActivity().sendBroadcast(intent);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
////        停止播放音乐
//        Intent intent = new Intent("org.xr.action.CTL_ACTION");
//        intent.putExtra("control", 2);
//    }

    @Override
    public void onDestroy() {  //考虑播放时按返回键
        super.onDestroy();
        getActivity().unregisterReceiver(activityReceiver);//将service与activity解绑
    }
}