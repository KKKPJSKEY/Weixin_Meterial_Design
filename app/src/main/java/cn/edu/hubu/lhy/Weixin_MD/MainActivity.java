package cn.edu.hubu.lhy.Weixin_MD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.edu.hubu.lhy.Weixin_MD.Ctt.CttFragment;
import cn.edu.hubu.lhy.Weixin_MD.Ctt.CttFragment_a;
import cn.edu.hubu.lhy.Weixin_MD.Fri.FriFragment;
import cn.edu.hubu.lhy.Weixin_MD.Mes.MesFragment;
import cn.edu.hubu.lhy.Weixin_MD.Mes.Music_Player.MusicPlayerStatus;
import cn.edu.hubu.lhy.Weixin_MD.Net.NetFragment;
import cn.edu.hubu.lhy.Weixin_MD.Set.SetFragment;

public class MainActivity extends AppCompatActivity {

//    private MesFragment mesFragment = null;
//    private FriFragment friFragment = null;
//    private CttFragment cttFragment = null;
//    private CttFragment_a cttFragment_a = null;
//    private SetFragment setFragment = null;
//    private NetFragment netFragment = null;
//    private NetSuccFragment netSuccFragment = null;

    public Fragment current = null;

    public Fragment getCurrent() {
        return current;
    }

    public void setCurrent(Fragment current) {
        this.current = current;
    }

    public MusicPlayerStatus musicPlayerStatus=new MusicPlayerStatus();

/*    public MainActivity(){

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottom_navigation_menu = (BottomNavigationView) findViewById(R.id.bottom_navigation_menu);
        bottom_navigation_menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        mesFragment = new MesFragment();
//        changeFrameLayout("您好", mesFragment);
//        friFragment = new FriFragment();
//        changeFrameLayout("您好", friFragment);
        current=changeFrameLayout("您好", new FriFragment(),current);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.page_Mes:
//                    if (mesFragment == null) {
//                        mesFragment = new MesFragment();
//                    }
                    current=changeFrameLayout("音乐", new MesFragment(musicPlayerStatus),current);
                    return true;
                case R.id.page_Fri:
//                    if (friFragment == null) {
//                        friFragment = new FriFragment();
//                    }
                    current=changeFrameLayout("信息", new FriFragment(),current);
                    return true;
                case R.id.page_Ctt:
//                    if (cttFragment == null) {
//                        cttFragment = new CttFragment();
//                    }
//                    changeFrameLayout("联系人", cttFragment);
//                    if (cttFragment_a == null) {
//                        cttFragment_a = new CttFragment_a();
//                    }
//                    changeFrameLayout("联系人", new CttFragment(),current);
                    current=changeFrameLayout("联系人", new CttFragment_a(),current);
                    return true;
                case R.id.page_Set:
//                    if (setFragment == null) {
//                        setFragment = new SetFragment();
//                    }
                    current=changeFrameLayout("地图", new SetFragment(),current);
                    return true;

                case R.id.page_Net:
//                    if (setFragment == null) {
//                        setFragment = new SetFragment();
//                    }
                    current=changeFrameLayout("网络", new NetFragment(),current);
                    return true;
            }
            return false;
        }
    };

    /**
     * 使用Fragment组件替换原界面上的FragLayout
     *
     * @param string   提示信息
     * @param fraGment 要替换成的fragment
     */
    public Fragment changeFrameLayout(String string, Fragment fraGment, Fragment current) {
        //弹窗
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
//      mTextMessage.setText(R.string.title_dashboard);
        //每一次都要重新创建事务和管理工具
        FragmentManager fagmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fagmentManager.beginTransaction();
//      fragmentTransaction.hide(mesFragment);
        //替换操作

        if(current!=null)fragmentTransaction.remove(current);
//        current.getActivity().onBackPressed();
        fragmentTransaction.add(R.id.frameLayout,fraGment);
        current =fraGment;
//        fragmentTransaction.replace(R.id.frameLayout, fraGment);
        //事务具有原子性，类似数据库，每一次操作完成需要提交操作
        fragmentTransaction.commit();
//      fragmentTransaction.show(friFragment);
//      fragmentTransaction.hide(mesFragment);
        return current;
    }


//    public class MainActivity extends AppCompatActivity {
//        protected Fragment currentFragment = new Fragment();
//        protected MesFragment mesFragment = new MesFragment();
//        protected FriFragment friFragment = new FriFragment();
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//            BottomNavigationView bottom_navigation_menu = (BottomNavigationView) findViewById(R.id.bottom_navigation_menu);
//            bottom_navigation_menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
////        Fragment fragment = findFragmentById(R.id.Fragment);
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            mesFragment = (MesFragment) fragmentManager.findFragmentById(R.id.mesFragment);
//            currentFragment = mesFragment;
//        }
//
//        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//                = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//                    case R.id.page_Mes:
//                        FragmentManager mSupportFragmentManager1=getSupportFragmentManager();
//                        FragmentTransaction mTransaction1 = mSupportFragmentManager1.beginTransaction();
//                        //弹窗
//                        Toast.makeText(MainActivity.this, "信息", Toast.LENGTH_SHORT).show();
//                        //隐藏当前，展示目标
//                        mTransaction1.hide(currentFragment);
//                        mTransaction1.show(mesFragment);
//                        mTransaction1.commit();
//                        //更新当前Fragment
//                        currentFragment = mesFragment;
////                    mTextMessage.setText(R.string.title_home);
//                        return true;
//                    case R.id.page_Fri:
//                        FragmentTransaction mTransaction2;
//                        FragmentManager mSupportFragmentManager2;
//                        mSupportFragmentManager1 = getSupportFragmentManager();
//                        mTransaction1 = mSupportFragmentManager1.beginTransaction();
//                        //弹窗
//                        Toast.makeText(MainActivity.this, "朋友", Toast.LENGTH_SHORT).show();
//                        //隐藏当前，展示目标
//                        mTransaction1.hide(currentFragment);
//                        mTransaction1.show(friFragment);
//                        mTransaction1.commit();
//                        //更新当前Fragment
//                        currentFragment = friFragment;
//                        //                    mTextMessage.setText(R.string.title_dashboard);
//                        return true;
//                    case R.id.page_Ctt:
//                        Toast.makeText(MainActivity.this, "Ctt", Toast.LENGTH_SHORT).show();
////                    mTextMessage.setText(R.string.title_notifications);
//                        return true;
//                    case R.id.page_Set:
//                        Toast.makeText(MainActivity.this, "Set", Toast.LENGTH_SHORT).show();
////                    mTextMessage.setText(R.string.title_notifications);
//                        return true;
//                }
//                return false;
//            }
//        };

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater=getMenuInflater();
////        inflater.inflate(R.menu.bottom_navigation_menu,bnMenu);
//        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.page_Mes:
////                System.out.println("Mes");
////                return true;
//                Toast.makeText(this, "Mes", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.page_Fri:
////                System.out.println("Fri");
////                return true;
//                Toast.makeText(this, "Fri", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.page_Ctt:
////                System.out.println("Ctt");
////                return true;
//                Toast.makeText(this, "Ctt", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.page_Set:
////                System.out.println("Set");
////                return true;
//                Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return true;
//    }
    @Override
    public void onDestroy() {  //考虑播放时按返回键
        super.onDestroy();
        finish();  //关闭活动
    }
}