package cn.edu.hubu.lhy.Weixin_MD.Net;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

import cn.edu.hubu.lhy.Weixin_MD.Net.DTO.GoodImg;
import cn.edu.hubu.lhy.Weixin_MD.Net.DTO.Goods;
import cn.edu.hubu.lhy.Weixin_MD.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetSuccFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetSuccFragment extends Fragment implements View.OnClickListener {

    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public NetSuccFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment NetSuccFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static NetSuccFragment newInstance(String param1, String param2) {
//        NetSuccFragment fragment = new NetSuccFragment();
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
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_net_succ, container, false);
//    }
Context context=getContext();

    TextView goodsRange;
    String mids;

    TextView inputGoodSearchId;
    Button searchCommit;

    ImageView goodImgIv;
    TextView goodName;
    TextView goodType;
    TextView goodPrice;
    TextView goodNum;
    TextView goodDesc;
    TextView goodDetail;

    Goods good;

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mids = msg.getData().getString("goodsRange");
                    goodsRange.setText("商品ID范围：1-" + mids + "(注意部分ID可能被删除哦)");// 传递过来的参数
                    break;
                case 21:
                    good = (Goods) msg.getData().getSerializable("good");
                    goodName.setText(good.getName());
                    goodType.setText(good.getType());
                    goodPrice.setText(good.getPrice().toString());
                    goodNum.setText(good.getNum() + "");
                    goodDesc.setText(good.getDesc());
                    goodDetail.setText(good.getDetail());
//                    System.out.println(good.getImg());
                    break;
                case 22:
                    GoodImg goodImg = (GoodImg) msg.getData().getSerializable("goodImg");//接受msg
                    goodImgIv.setImageDrawable(goodImg.getDrawable());// 传递过来的参数
                    break;
//                case 3:
//                    Toast.makeText(getContext(), msg.getData().getString("error"), Toast.LENGTH_SHORT).show();// 传递过来的参数
//                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_succ, container, false);
        goodsRange = view.findViewById(R.id.goodRangeMessage);

        inputGoodSearchId = view.findViewById(R.id.inputGoodID_txt);
        searchCommit = (Button) view.findViewById(R.id.searchcommit);
        searchCommit.setOnClickListener(this);

        goodImgIv = view.findViewById(R.id.goodImg);
        goodName = view.findViewById(R.id.goodName);
        goodType = view.findViewById(R.id.goodType);
        goodPrice = view.findViewById(R.id.goodPrice);
        goodNum = view.findViewById(R.id.goodNum);
        goodDesc = view.findViewById(R.id.goodDesc);
        goodDetail = view.findViewById(R.id.goodDetail);

        //请求总数
        getGoodsIdRange();

        return view;
//        return inflater.inflate(R.layout.fragment_net_succ, container, false);
    }

    private void getGoodsIdRange() {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://10.185.150.5:8080/Java_xmsc/GoodsGetLastId")
                .get()
                .build();
        //3、执行Request请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                //请求失败
                e.printStackTrace();
                System.out.println("请求失败请求失败请求失败请求失败请求失败");
            }

            public void onResponse(Call call, final Response response) throws IOException {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            Log.v("onResponse", response.body().string());
                            Message msg = new Message();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
//                            Log.v("resBody",resBody);
                            bundle.putString("goodsRange", response.body().string());  //往Bundle中存放数据
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mHandler.sendMessage(msg);//用activity中的handler发送消息
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        //1、创建OkHttpClient对象实例
        OkHttpClient okHttpClient = new OkHttpClient();
        //2、创建Request对象
        Request request21 = new Request.Builder()
                .url("http://10.185.150.5:8080/Java_xmsc/GoodsDetailServlet?gid=" +
                        inputGoodSearchId.getText())
                .get()
                .build();
        //3、执行Request请求
        okHttpClient.newCall(request21).enqueue(new Callback() {

            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            public void onResponse(Call call, final Response response) throws IOException {
                new Thread(new Runnable() {
                    //                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String respBody = response.body().string();

                            Message msg = new Message();
                            msg.what = 21;
                            Bundle bundle = new Bundle();
                            Goods goods = new Goods();
//                            System.out.println(respBody);
//                            System.out.println(respBody.indexOf("<td><img"));
//                            System.out.println(respBody.substring(respBody.indexOf("<td><img")));
//                            System.out.println(respBody.substring(respBody.indexOf("<td><img")).split("\"")[1]);
                            String img = respBody.substring(respBody.indexOf("<td><img")).split("\"")[1];
                            goods.setImg(img);
//                            String id =respBody.substring(respBody.indexOf("name=\"gid\"")).split("\"")[3];
                            int id=0;
                            try {
                                id = Integer.parseInt(respBody.substring(respBody.indexOf("name=\"gid\"")).split("\"")[3]);
                            } catch (Exception e) {

                            }
//                            if (id!=null||id.length()!=0) {
                            if (id <= Integer.parseInt(mids) && id > 0) {
                                goods.setId(id);
                                goods.setName(respBody.substring(respBody.indexOf("id=\"goodsName\"")).split("\"")[3]);
                                goods.setType(respBody.substring(respBody.indexOf("selected")).split("<")[0].split(">")[1]);
                                goods.setPrice(Float.parseFloat(respBody.substring(respBody.indexOf("id=\"goodsPrice\"")).split("\"")[5]));
                                goods.setNum(Integer.parseInt(respBody.substring(respBody.indexOf("id=\"goodsNum\"")).split("\"")[5]));
                                goods.setDesc(respBody.substring(respBody.indexOf("name=\"goodsDesc\"")).split("<")[0].split(">")[1]);
                                goods.setDetail(respBody.substring(respBody.indexOf("name=\"goodsDetail\"")).split("<")[0].split(">")[1]);

                                //2、创建Request对象
//                            System.out.println("======================================="+midGoodImg[0]);
                                Request request22 = new Request.Builder()
                                        .url("http://10.185.150.5:8080/Java_xmsc/" + img)
                                        .get()
                                        .build();
                                //3、执行Request请求
                                okHttpClient.newCall(request22).enqueue(new Callback() {

                                    public void onFailure(Call call, IOException e) {
                                        //请求失败
                                    }

                                    public void onResponse(Call call, final Response response) throws IOException {
                                        new Thread(new Runnable() {
                                            //                runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Message msg = new Message();
                                                msg.what = 22;
                                                Bundle bundle = new Bundle();
                                                Drawable drawable = Drawable.createFromStream(response.body().byteStream(), "goodImg");
                                                drawable.setBounds(0, 0, 1000, 1000);
                                                GoodImg goodImg = new GoodImg();
                                                goodImg.setDrawable(drawable);
//                            Log.v("resBody",resBody);
                                                bundle.putSerializable("goodImg", (Serializable) goodImg);  //往Bundle中存放数据
                                                msg.setData(bundle);//mes利用Bundle传递数据
                                                mHandler.sendMessage(msg);//用activity中的handler发送消息
                                            }
                                        }).start();

                                        //请求成功

                                    }
                                });
//                            Log.v("resBody",resBody);
                                bundle.putSerializable("good", (Serializable) goods);  //往Bundle中存放数据
                                msg.setData(bundle);//mes利用Bundle传递数据
                                mHandler.sendMessage(msg);//用activity中的handler发送消息
                            } else {
                                System.out.println("输入错误或id不存在");

                                Looper.prepare();
                                Toast.makeText(getContext(), "输入错误或id不存在", Toast.LENGTH_SHORT).show();
                                Looper.loop();

//                                msg.what = 3;
//                                Bundle bundleError = new Bundle();
////                            Log.v("resBody",resBody);
//                                bundleError.putString("error", "输入错误或id不存在");  //往Bundle中存放数据
//                                msg.setData(bundle);//mes利用Bundle传递数据
//                                mHandler.sendMessage(msg);//用activity中的handler发送消息
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        FragmentManager fagmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fagmentManager.beginTransaction();
//        fragmentTransaction.hide(this);
//        fragmentTransaction.remove(this);
//        fragmentTransaction.commit();
//        MainActivity mainActivity =new MainActivity();
//        System.out.println("sakdjfkjaldkfs;kadsf");

    }
}