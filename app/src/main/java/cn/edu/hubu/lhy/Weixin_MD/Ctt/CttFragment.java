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

    private MyDAO myDAO;  //?????????????????????
    private ListView listView;
    private List<Map<String, Object>> listData;
    private Map<String, Object> listItem;
    private SimpleAdapter listAdapter;

    private EditText et_name;  //???????????????3???????????????1????????????????????????
    private EditText et_tel;

    private String selId = null;  //?????????id
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

        myDAO = new MyDAO(getContext());  //???????????????????????????
        if (myDAO.getRecordsNumber() == 0) {  //???????????????????????????????????????
            myDAO.insertInfo("liu", "13333333333");   //????????????
            myDAO.insertInfo("li", "18888888888"); //????????????
        }


        displayRecords();   //????????????


//        return inflater.inflate(R.layout.fragment_ctt, container, false);
        return view;
    }

    public void displayRecords() {  //????????????????????????
        listView = (ListView) view.findViewById(R.id.listView);

        listData = new ArrayList<Map<String, Object>>();

        Cursor cursor = myDAO.allQuery();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);  //???????????????
            String name = cursor.getString(1);
            //int age=cursor.getInt(2);
            String tel = cursor.getString(cursor.getColumnIndex("tel"));//??????????????????
            listItem = new HashMap<String, Object>(); //???????????????????????????
            listItem.put("_id", id);  //???1?????????????????????2???????????????
            listItem.put("name", name);
            listItem.put("tel", tel);
            listData.add(listItem);   //??????????????????
        }
        listAdapter = new SimpleAdapter(getContext(),
                listData,
                R.layout.ctt_list_item, //??????????????????????????????
                new String[]{"_id", "name", "tel"},
                new int[]{R.id.tv_id, R.id.tvname, R.id.tvtel});
        listView.setAdapter(listAdapter);  //???????????????
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //???????????????
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> rec = (Map<String, Object>) listAdapter.getItem(position);  //?????????????????????
                et_name.setText(rec.get("name").toString());  //???????????????
                et_tel.setText(rec.get("tel").toString());
                Log.i("ly", rec.get("_id").toString());
                selId = rec.get("_id").toString();  //???????????????????????????
            }
        });
    }

    @Override
    public void onClick(View v) {  //?????????????????????
        if (selId != null) {  //????????????????????????????????????/??????/??????
            String p1 = et_name.getText().toString().trim();
            String p2 = et_tel.getText().toString();
            switch (v.getId()) {
                case R.id.bt_add:
                    myDAO.insertInfo(p1, p2);
                    break;
                case R.id.bt_modify:
                    myDAO.updateInfo(p1, p2, selId);
                    Toast.makeText(getContext(), "???????????????", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_del:
                    myDAO.deleteInfo(selId);
                    Toast.makeText(getContext(), "???????????????", Toast.LENGTH_SHORT).show();
                    et_name.setText(null);
                    et_tel.setText(null);
                    selId = null; //??????
            }
        } else {  //??????????????????
            if (v.getId() == R.id.bt_add) {  //??????????????????
                String p1 = et_name.getText().toString();
                String p2 = et_tel.getText().toString();
                if (p1.equals("") || p2.equals("")) {  //?????????????????????
                    Toast.makeText(getContext(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    myDAO.insertInfo(p1, p2);  //???2????????????
                }
            } else {   //??????????????????????????????
                Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
        displayRecords();//??????ListView??????
    }
}