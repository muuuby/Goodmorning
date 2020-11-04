package com.example.goodmorning;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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
    TextView h1,h2,h3,h4,h5,h6,h7,h11,h22,h33,h44,h55,h66,h77,h111,h222,h333,h444,h555,h666,h777;

    Handler handler;
    String cityname;

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

        h1 = fragment1.findViewById(R.id.h1);
        h11 = fragment1.findViewById(R.id.h11);
        h111 = fragment1.findViewById(R.id.h111);

        h2 = fragment1.findViewById(R.id.h2);
        h22 = fragment1.findViewById(R.id.h22);
        h222 = fragment1.findViewById(R.id.h222);

        h3 = fragment1.findViewById(R.id.h3);
        h33 = fragment1.findViewById(R.id.h33);
        h333 = fragment1.findViewById(R.id.h333);

        h4 = fragment1.findViewById(R.id.h4);
        h44 = fragment1.findViewById(R.id.h44);
        h444 = fragment1.findViewById(R.id.h444);

        h5 = fragment1.findViewById(R.id.h5);
        h55 = fragment1.findViewById(R.id.h55);
        h555 = fragment1.findViewById(R.id.h555);

        h6 = fragment1.findViewById(R.id.h6);
        h66 = fragment1.findViewById(R.id.h66);
        h666 = fragment1.findViewById(R.id.h666);

        h7 = fragment1.findViewById(R.id.h7);
        h77 = fragment1.findViewById(R.id.h77);
        h777 = fragment1.findViewById(R.id.h777);

        final String data[] = new String[35];
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

                    //未来24小时
                    data[10] = bdl.getString("76");
                    String[] d10 = data[10].split("\"|\\（");
                    data[11] = bdl.getString("78");
                    String[] d11 = data[11].split("\"");
                    data[12] = bdl.getString("80");
                    String[] d12 = data[12].split("\"");
                    h1.setText(d10[1]);
                    h11.setText(d11[1]);
                    h111.setText(d12[1]);

                    data[13] = bdl.getString("89");
                    String[] d13 = data[13].split("\"|\\（");
                    data[14] = bdl.getString("91");
                    String[] d14 = data[14].split("\"");
                    data[15] = bdl.getString("93");
                    String[] d15 = data[15].split("\"");
                    h2.setText(d13[1]);
                    h22.setText(d14[1]);
                    h222.setText(d15[1]);

                    data[16] = bdl.getString("102");
                    String[] d16 = data[16].split("\"|\\（");
                    data[17] = bdl.getString("104");
                    String[] d17 = data[17].split("\"");
                    data[18] = bdl.getString("106");
                    String[] d18 = data[18].split("\"");
                    h3.setText(d16[1]);
                    h33.setText(d17[1]);
                    h333.setText(d18[1]);

                    data[19] = bdl.getString("115");
                    String[] d19 = data[19].split("\"|\\（");
                    data[20] = bdl.getString("117");
                    String[] d20 = data[20].split("\"");
                    data[21] = bdl.getString("119");
                    String[] d21 = data[21].split("\"");
                    h4.setText(d19[1]);
                    h44.setText(d20[1]);
                    h444.setText(d21[1]);

                    data[22] = bdl.getString("128");
                    String[] d22 = data[22].split("\"|\\（");
                    data[23] = bdl.getString("130");
                    String[] d23 = data[23].split("\"");
                    data[24] = bdl.getString("132");
                    String[] d24 = data[24].split("\"");
                    h5.setText(d22[1]);
                    h55.setText(d23[1]);
                    h555.setText(d24[1]);

                    data[25] = bdl.getString("141");
                    String[] d25 = data[25].split("\"|\\（");
                    data[26] = bdl.getString("143");
                    String[] d26 = data[26].split("\"");
                    data[27] = bdl.getString("145");
                    String[] d27 = data[27].split("\"");
                    h6.setText(d25[1]);
                    h66.setText(d26[1]);
                    h666.setText(d27[1]);

                    data[28] = bdl.getString("157");
                    String[] d28 = data[28].split("\"|\\（");
                    data[29] = bdl.getString("159");
                    String[] d29 = data[29].split("\"");
                    data[30] = bdl.getString("161");
                    String[] d30 = data[30].split("\"");
                    h7.setText(d28[1]);
                    h77.setText(d29[1]);
                    h777.setText(d30[1]);

                }
                super.handleMessage(msg);
            }
        };

        //读取城市和星座
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("identity", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(getContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        cityname = sharedPreferences.getString("city", "");

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
//           cityname = "成都";
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
                    System.out.println(elements[i]+i);
                }

                msg.setData(bdl);
                msg.sendToTarget();
                Log.e("Json","success");
            } catch (Exception e) {

            }
        }
    }

}

