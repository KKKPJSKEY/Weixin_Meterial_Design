package cn.edu.hubu.lhy.Weixin_MD.Net;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cn.edu.hubu.lhy.Weixin_MD.MainActivity;
import cn.edu.hubu.lhy.Weixin_MD.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetFragment extends Fragment implements View.OnClickListener {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public NetFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment NetFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static NetFragment newInstance(String param1, String param2) {
//        NetFragment fragment = new NetFragment();
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

    TextView user_Name;
    TextView user_Pwd;
    Button log_Commit;

    FragmentManager fagmentManager;
    FragmentTransaction fragmentTransaction;

    Fragment fragment=null;
    Fragment thisFragment=this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_net, container, false);
        user_Name = (TextView) view.findViewById(R.id.username_txt);
        user_Pwd = (TextView) view.findViewById(R.id.password_txt);
        log_Commit = (Button) view.findViewById(R.id.logcommit);
        log_Commit.setOnClickListener(this);
        fagmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fagmentManager.beginTransaction();

        return view;
//        return inflater.inflate(R.layout.fragment_net, container, false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();  //获取视图id
        switch (id) {
            case R.id.logcommit:
//                Toast.makeText(getContext(), user_Name.getText() + "\n" + user_Pwd.getText(), Toast.LENGTH_SHORT).show();

                //发起登录请求
                loginPost();
                break;
            default:
                break;

        }
    }


    private void loginPost() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        创建表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("acc", user_Name.getText().toString());
        builder.add("pwd", user_Pwd.getText().toString());
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url("http://10.185.150.5:8080/Java_xmsc/LoginServlet")
                .post(body)
                .build();
//        Request request = new Request.Builder()
//                .url("http://10.185.150.5:8080/Java_xmsc?"+"acc="+user_Name.getText().toString()+"&pwd="+user_Pwd.getText().toString())
//                .get()
//                .build();
        //3、执行Request请求
        okHttpClient.newCall(request).enqueue(new Callback() {

            public void onFailure(Call call, IOException e) {
                //请求失败
                e.printStackTrace();
                System.out.println("请求失败请求失败请求失败请求失败请求失败");
//
            }

            public void onResponse(Call call, final Response response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Response response1 = response;
//                            Log.v("onResponse", response1.body().string());
//                        String s = response1.headers().values("Set-Cookie").get(0);
//                        String session = s.substring(0, s.indexOf(";"));

                        String[] split = response1.toString().split("/");
//                        System.out.println("--------------------session="+split[split.length-1]);
                        if (split[split.length-1].equals("index.jsp}")) {
                            //登陆成功后切换Fragment
                            fragment = new NetSuccFragment();
//                MainActivity mainActivity =new MainActivity();
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.setCurrent(fragment);
//                fragmentTransaction.replace(R.id.frameLayout, fragment);
                            fragmentTransaction.remove(thisFragment);
//        current.getActivity().onBackPressed();
                            fragmentTransaction.add(R.id.frameLayout,fragment);
//                fragmentTransaction.hide(this);
//                fragmentTransaction.add(R.id.frameLayout, fragment);
                            fragmentTransaction.commit();
                            Toast.makeText(getContext(), "欢迎" + user_Name.getText(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "账号密码错误！", Toast.LENGTH_SHORT).show();
                        }
//                            response.message()=
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        System.out.println("dsabfdksafkdafkdakkfkldaskfl");
//        if (fragment!=null) {
//            fragmentTransaction.remove(fragment);
//            fragmentTransaction.commit();
//        }
    }
}