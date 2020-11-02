package com.example.goodmorning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class fragment_weather extends Fragment {
    TextView winddire,windspeed,wet,qiya;

    Handler handler;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        View fragment1 = inflater.inflate(R.layout.fragment_weather, null);

        winddire = fragment1.findViewById(R.id.winddire);
        wet = fragment1.findViewById(R.id.wet);
        windspeed = fragment1.findViewById(R.id.windspeed);
        qiya = fragment1.findViewById(R.id.qiya);

        final String data[] = new String[10];
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 3) {
                    Bundle bdl = msg.getData();
                    //解析数据
                    data[3] = bdl.getString("25");
                    String[] d3 = data[3].split("\"");
                    data[5] = bdl.getString("31");
                    String[] d5 = data[5].split("\"");
                    data[7] = bdl.getString("35");
                    String[] d7 = data[7].split("\"");

                    String ws;
                    data[9] = bdl.getString("41");
                    String[] d9 = data[9].split("\"");
                    if(d9.length<2){
                        ws = "0";
                    }else{
                        ws = d9[1];
                    }

                    winddire.setText(d7[1]);
                    wet.setText(d3[1]);
                    qiya.setText(d5[1]);
                    windspeed.setText(ws);
                }
                super.handleMessage(msg);
            }
        };

        //开启子线程
        fragment_weather.myThread t = new fragment_weather.myThread();
        new Thread(t).start();
        return fragment1;
    }

    //后台线程访问网络
    public class myThread implements Runnable {
        @Override
        public void run() {

            //weather data
            String cityname = "成都";
            String url = "https://api.djapi.cn/rtweather/get?cityname_ch="+cityname+"&cn_to_unicode=0&token=fef56038bf629f09129d7521716fa5f0&datatype=json";
            try {
                //从接口获取数据
                URL ur = new URL(url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                Log.e("Json","success");
                String str = buffer.toString();
                String elements[] = str.split("\\{|\\}|,|:");

                Message msg = handler.obtainMessage(3);
                Bundle bdl = new Bundle();

                for(int i=0;i<elements.length;i++) {
                    bdl.putString("" + i, elements[i]);
                }

                msg.setData(bdl);
                msg.sendToTarget();
                Log.e("Json","success");
            } catch (Exception e) {

            }
        }
    }

}

