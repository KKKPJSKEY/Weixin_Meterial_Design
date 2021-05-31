package cn.edu.hubu.lhy.Weixin_MD.Ctt;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hubu.lhy.Weixin_MD.Ctt.DAO.MyDAO_a;
import cn.edu.hubu.lhy.Weixin_MD.Ctt.DTO.Person;
import cn.edu.hubu.lhy.Weixin_MD.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CttFragment_a #newInstance} factory method to
 * create an instance of this fragment.
 */
public class CttFragment_a extends Fragment implements View.OnClickListener {

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

    MyDAO_a myDAO;  //数据库访问对象
    ListView listView;  //列表控件
    List<Person> listData;  //列表数据
    BaseAdapter adapter;  //列表控件的数据适配合器
    //数据表包含3个字段，第1字段为自增长类型
    int selId;  //选择项id，与自增长类型的第一字段相对应
    EditText et_name;  //对应第二字段的控件对象
    EditText et_tel;  //对应第三字段的控件对象

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //3个按钮
        view = inflater.inflate(R.layout.fragment_ctt, container, false);

        Button bt_add = view.findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        Button bt_modify = view.findViewById(R.id.bt_modify);
        bt_modify.setOnClickListener(this);
        Button bt_del = view.findViewById(R.id.bt_del);
        bt_del.setOnClickListener(this);
        //2个编辑文本框
        et_name = view.findViewById(R.id.et_name);
        et_tel = view.findViewById(R.id.et_tel);
        //初次安装时建立2条记录
        myDAO = new MyDAO_a(getContext());  //创建数据库访问对象
        if (myDAO.getRecordsNumber() == 0) {  //防止重复运行时重复插入记录
            myDAO.insertInfo("liu", "13333333333");   //插入记录
            myDAO.insertInfo("li", "18888888888"); //插入记录
        }
        displayRecords();   //显示记录
//        return inflater.inflate(R.layout.fragment_ctt, container, false);
        return view;
    }

    public void displayRecords() {  //定义显示记录方法
        listView = view.findViewById(R.id.listView);
        listData = new ArrayList<Person>();
        Cursor cursor = myDAO.allQuery();
        while (cursor.moveToNext()) {  //遍历记录集
            int id = cursor.getInt(0);  //获取字段值
            String name = cursor.getString(1);
            String tel = cursor.getString(cursor.getColumnIndex("tel"));//推荐此种方式
            Person person = new Person(id, name, tel);  //创建Person对象
            listData.add(person);     //添加一条记录
        }
        adapter = new BaseAdapter() {  //创建适配器，需要重写4个抽象方法
            @Override
            public int getCount() {
                return listData.size();
            }

            @Override
            public Object getItem(int position) {
                return listData.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // 根据布局文件创建列表项View（不同于SimpleAdapter）
                View item = View.inflate(getContext(), R.layout.ctt_list_item, null);
                // 获取这个动态生成的列表项中的各个字段对应的文本标签
                TextView idTV = item.findViewById(R.id.tv_id);
                TextView nameTV = item.findViewById(R.id.tvname);
                TextView ageTV = item.findViewById(R.id.tvtel);
                // 根据位置获取Person对象（列表项数据）
                Person p = listData.get(position);
                /*设置标签文本（不同于SimpleAdapter）。其中，_id和age两个字段是整型，
                不做字符串处理时，将导致闪退。用到实体类的get/set访问方法*/
                idTV.setText(p.get_id() + "");
                nameTV.setText(p.getName());
                ageTV.setText(p.getTel() + "");
                return item;
            }
        };
        listView.setAdapter(adapter);  //应用适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //列表项监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person rec = (Person) adapter.getItem(position);  //从适配器取记录
                et_name.setText(rec.getName());  //刷新文本框
                et_tel.setText(rec.getTel() + "");
                selId = rec.get_id();  //供修改和删除时使用
            }
        });
    }

    @Override
    public void onClick(View v) {  //实现的接口方法
        if (selId > 0) {  //选择了列表项后，可以增加/删除/修改
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
                    selId = 0; //提示
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