package cn.edu.hubu.lhy.Weixin_MD.Ctt;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.hubu.lhy.Weixin_MD.Ctt.DAO.MyDAO;
import cn.edu.hubu.lhy.Weixin_MD.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CttFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class CttFragment extends Fragment implements View.OnClickListener {

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

    private MyDAO myDAO;  //数据库访问对象
    private ListView listView;
    private List<Map<String, Object>> listData;
    private Map<String, Object> listItem;
    private SimpleAdapter listAdapter;

    private EditText et_name;  //数据表包含3个字段，第1字段为自增长类型
    private EditText et_tel;

    private String selId = null;  //选择项id
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_ctt, container, false);
        Button bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        Button bt_modify = (Button) view.findViewById(R.id.bt_modify);
        bt_modify.setOnClickListener(this);
        Button bt_del = (Button) view.findViewById(R.id.bt_del);
        bt_del.setOnClickListener(this);

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_tel = (EditText) view.findViewById(R.id.et_tel);

        myDAO = new MyDAO(getContext());  //创建数据库访问对象
        if (myDAO.getRecordsNumber() == 0) {  //防止重复运行时重复插入记录
            myDAO.insertInfo("liu", "13333333333");   //插入记录
            myDAO.insertInfo("li", "18888888888"); //插入记录
        }


        displayRecords();   //显示记录


//        return inflater.inflate(R.layout.fragment_ctt, container, false);
        return view;
    }

    public void displayRecords() {  //显示记录方法定义
        listView = (ListView) view.findViewById(R.id.listView);

        listData = new ArrayList<Map<String, Object>>();

        Cursor cursor = myDAO.allQuery();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);  //获取字段值
            String name = cursor.getString(1);
            //int age=cursor.getInt(2);
            String tel = cursor.getString(cursor.getColumnIndex("tel"));//推荐此种方式
            listItem = new HashMap<String, Object>(); //必须在循环体里新建
            listItem.put("_id", id);  //第1参数为键名，第2参数为键值
            listItem.put("name", name);
            listItem.put("tel", tel);
            listData.add(listItem);   //添加一条记录
        }
        listAdapter = new SimpleAdapter(getContext(),
                listData,
                R.layout.ctt_list_item, //自行创建的列表项布局
                new String[]{"_id", "name", "tel"},
                new int[]{R.id.tv_id, R.id.tvname, R.id.tvtel});
        listView.setAdapter(listAdapter);  //应用适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //列表项监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> rec = (Map<String, Object>) listAdapter.getItem(position);  //从适配器取记录
                et_name.setText(rec.get("name").toString());  //刷新文本框
                et_tel.setText(rec.get("tel").toString());
                Log.i("ly", rec.get("_id").toString());
                selId = rec.get("_id").toString();  //供修改和删除时使用
            }
        });
    }

    @Override
    public void onClick(View v) {  //实现的接口方法
        if (selId != null) {  //选择了列表项后，可以增加/删除/修改
            String p1 = et_name.getText().toString().trim();
            String p2 = et_tel.getText().toString();
            switch (v.getId()) {
                case R.id.bt_add:
                    myDAO.insertInfo(p1, p2);
                    break;
                case R.id.bt_modify:
                    myDAO.updateInfo(p1, p2, selId);
                    Toast.makeText(getContext(), "更新成功！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_del:
                    myDAO.deleteInfo(selId);
                    Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    et_name.setText(null);
                    et_tel.setText(null);
                    selId = null; //提示
            }
        } else {  //未选择列表项
            if (v.getId() == R.id.bt_add) {  //单击添加按钮
                String p1 = et_name.getText().toString();
                String p2 = et_tel.getText().toString();
                if (p1.equals("") || p2.equals("")) {  //要求输入了信息
                    Toast.makeText(getContext(), "姓名和年龄都不能空！", Toast.LENGTH_SHORT).show();
                } else {
                    myDAO.insertInfo(p1, p2);  //第2参数转型
                }
            } else {   //单击了修改或删除按钮
                Toast.makeText(getContext(), "请先选择记录！", Toast.LENGTH_SHORT).show();
            }
        }
        displayRecords();//刷新ListView对象
    }
}