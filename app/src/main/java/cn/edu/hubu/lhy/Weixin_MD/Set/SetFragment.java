package cn.edu.hubu.lhy.Weixin_MD.Set;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.edu.hubu.lhy.Weixin_MD.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetFragment extends Fragment {

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
    View view;

    LocationClient mLocationClient;  //???????????????
    MapView mapView;  //Android Widget????????????
    BaiduMap baiduMap;
    boolean isFirstLocate = true;

    private MyLocationListener myListener = new MyLocationListener();

    TextView tv_Lat;  //??????
    TextView tv_Lon;  //??????
    TextView tv_Add;  //??????
    TextView tv_Rds;  //??????
    TextView tv_Drt;  //??????
    TextView tv_Dsc;  //??????

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_set, container, false);
//??????????????????????????????????????????????????????????????????
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            requestLocation();
        }
        return view;
//        return inflater.inflate(R.layout.fragment_set, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } else {
                    requestLocation();
                }
        }
    }

    private void requestLocation() {
//        initLocation();
        checkVersion();
        mLocationClient.start();
        //??????LocationListener?????????
//        MyLocationListener myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLocationListener);
    }

    private void initLocation() {  //?????????
//        getApplicationContext()
        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(myListener);
//        getApplicationContext()
//        SDKInitializer.initialize(getActivity());
//        getActivity().setContentView(R.layout.activity_main);

        mapView = view.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        // ??????????????????
        baiduMap.setMyLocationEnabled(true);

        tv_Lat = view.findViewById(R.id.tv_Lat);
        tv_Lon = view.findViewById(R.id.tv_Lon);
        tv_Add = view.findViewById(R.id.tv_Add);
        tv_Rds = view.findViewById(R.id.tv_Rds);
        tv_Drt = view.findViewById(R.id.tv_Drt);
        tv_Dsc = view.findViewById(R.id.tv_Dsc);


        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ??????gps
        option.setCoorType("bd09ll"); // ??????????????????
        //????????????????????????
        option.setScanSpan(1000);
        //??????????????????????????????
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        /*option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);*/
        //????????????????????????
        option.setIsNeedAddress(true);
        //??????????????????
        mLocationClient.setLocOption(option);
    }

    //?????????????????????????????????
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            if (bdLocation == null || mapView == null){
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(bdLocation.getRadius())
//                    // ?????????????????????????????????????????????????????????0-360
//                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
//                    .longitude(bdLocation.getLongitude()).build();
            tv_Lat.setText(bdLocation.getLatitude() + "");
            tv_Lon.setText(bdLocation.getLongitude() + "");
            tv_Add.setText(bdLocation.getAddrStr());


            tv_Rds.setText(bdLocation.getRadius() + "");
            tv_Drt.setText(bdLocation.getDirection() + "");
            String locationDescribe = "";
            if (bdLocation.getLocationDescribe() == null) locationDescribe = "????????????";
            else locationDescribe = bdLocation.getLocationDescribe();
            tv_Dsc.setText(locationDescribe);
//            tv_Dsc.setText(bdLocation.getLocationDescribe().equals("")||
//                    bdLocation.getLocationDescribe()==null||
//                    bdLocation.getLocationDescribe().length()==0
//                    ?"????????????":bdLocation.getLocationDescribe()+"");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
        }
    }

    private void navigateTo(BDLocation bdLocation) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
    }

    /**
     * ????????????
     */
    private void checkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            this
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//????????????
                            //????????????????????????
                            initLocation();// ???????????????
                        } else {//????????????
//                            MainActivity.this
                            Toast.makeText(getContext(), "???????????????", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            initLocation();// ???????????????
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();

        baiduMap.setMyLocationEnabled(false);

        mapView.onDestroy();
        isFirstLocate = true;
        mapView = null;
    }
}