package com.example.goodmorning;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class fragment_luck extends ListFragment implements AdapterView.OnItemClickListener {
    EditText edit;
    String txt;
    ImageButton btn1,btn2,btn3;


    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        View fragment1 = inflater.inflate(R.layout.fragment_luck, null);

        edit = fragment1.findViewById(R.id.edittext);

        //读取数据库
        listManager lm = new listManager(getContext());
        ArrayList<HashMap<String, String>> listItems;
        listItems = new ArrayList<HashMap<String, String>>();
        listItem item = new listItem();

        int i = 0;
        while (i<100){
            if(lm.findById(i)!=null){
                HashMap<String, String> map = new HashMap<String,
                        String>();
                item = lm.findById(i);
                String date = item.getdate();
                String detail = item.getdetail();
                Log.i(TAG,detail+i);
                map.put("ItemTitle", date);
                map.put("ItemDetail", detail);
                listItems.add(map);
            }
            i++;
        }
        Log.i(TAG,"datas from database");

        MyAdapter adapter = new MyAdapter(getContext(),R.layout.list_main,listItems);
        this.setListAdapter(adapter);

        return fragment1;
    }


    //点击item 删除
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Object itemAtPosition = getListView().getItemAtPosition(position);
        HashMap<String, String> map = (HashMap<String, String>) itemAtPosition;
        String date = map.get("ItemTitle");

        Log.i(TAG, "delete");
        listManager lm = new listManager(getContext());
        listItem item = new listItem();

        item.setdate(date);
        Log.i(TAG, date);

        lm.delete(date);
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        super.onListItemClick(l, v, position, id);
        //刷新当前页面
        onCreateView(inflater, container,savedInstanceState);
    }

    //按钮点击事件
    @SuppressLint("WrongViewCast")
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn1 = getActivity().findViewById(R.id.imageButton4);
        btn2 = getActivity().findViewById(R.id.imageButton5);
        btn3 = getActivity().findViewById(R.id.imageButton6);

        txt = edit.getText().toString();

        //搜索按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "search");
                txt = edit.getText().toString();
                if (txt.equals("")) {
                    Toast.makeText(getContext(), "请输入内容！", Toast.LENGTH_SHORT).show();
                } else {
                    //查询并显示
                    listManager lm = new listManager(getContext());
                    ArrayList<HashMap<String, String>> listItems;
                    listItems = new ArrayList<HashMap<String, String>>();
                    listItem item = new listItem();
                    if(lm.findByContent(txt)!=null){
                        HashMap<String, String> map = new HashMap<String,
                                    String>();
                        listItems = lm.findByContent(txt);
                    }

                    MyAdapter adapter = new MyAdapter(getContext(),R.layout.list_main,listItems);
                    fragment_luck.this.setListAdapter(adapter);
                    Log.i(TAG,"datas from database");

                    Toast.makeText(getContext(), "搜索结果", Toast.LENGTH_SHORT).show();
                }
                closeSoftInput(getContext());
                edit.setText(null);
            }
        });

        //添加按钮
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "add");
                txt = edit.getText().toString();
                if (txt.equals("")) {
                    Toast.makeText(getContext(), "请输入内容！", Toast.LENGTH_SHORT).show();
                } else {
                    //写入数据库
                    listManager lm = new listManager(getContext());
                    listItem item = new listItem();

                    Calendar c = Calendar.getInstance();
                    String time = c.get(Calendar.YEAR) + "-" + //得到年
                    formatTime(c.get(Calendar.MONTH) + 1) + "-" +//month加一 //月
                    formatTime(c.get(Calendar.DAY_OF_MONTH)) + " " + //日
                    formatTime(c.get(Calendar.HOUR_OF_DAY)) + ":" + //时
                    formatTime(c.get(Calendar.MINUTE)) + ":" + //分
                    formatTime(c.get(Calendar.SECOND)); //秒
                    System.out.println(time); //输出bai
                    item.setdate(time);
                    item.setdetail(txt);
                    item.setstate("0");
                    lm.add(item);

                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                }

                closeSoftInput(getContext());
                edit.setText(null);
                //刷新当前页面
                onCreateView(inflater, container,savedInstanceState);
            }
         });

        //刷新按钮
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "renew");
                txt = edit.getText().toString();

                closeSoftInput(getContext());
                edit.setText(null);
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                //刷新当前页面
                Intent main = new Intent(getContext(),MainActivity.class);
                main.putExtra("key",2);
                startActivityForResult(main, 1);
            }
        });

     }

     private String formatTime(int t){
        return t>=10? ""+t:"0"+t;//三元运算符 t>10时取 ""+t
    }

    //关闭键盘
    private void closeSoftInput(Context context){
        if (context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null
                    && ((Activity) context).getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}


class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context,
                     int resource,
                     ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_main,parent,false);
        }
        Map<String,String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));
        return itemView;
    }

}
