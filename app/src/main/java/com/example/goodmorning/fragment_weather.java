package com.example.goodmorning;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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

        final String data[] = new String[30];
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 3) {
                    Bundle bdl = msg.getData();

                    String errocode = bdl.getString("1");
                    String[] erro = errocode.split("\":\"|\",\"|\":|,");

                    if(erro[3].equals("0")){    //如果查询有结果
                        //解析数据
                        data[5] = bdl.getString("5");
                        String[] d5 = data[5].split(",\"|\":\"|\",\"|\":");
                        wet.setText(d5[2]);
                        qiya.setText(d5[8]);

                        data[6] = bdl.getString("6");
                        String[] d6 = data[6].split("\":\"|\",\"");
                        winddire.setText(d6[1]);
                        if(d6[7].equals("")){
                            windspeed.setText("0");
                        }else{
                            windspeed.setText(d6[7]);
                        }


                        //未来24小时
                        data[15] = bdl.getString("15");
                        String[] d15 = data[15].split("\":\"|（|）|\",\"");
                        h1.setText(d15[1]);
                        h11.setText(d15[5]);
                        h111.setText(d15[7]);

                        data[17] = bdl.getString("17");
                        String[] d17 = data[17].split("\":\"|（|）|\",\"");
                        h2.setText(d17[1]);
                        h22.setText(d17[5]);
                        h222.setText(d17[7]);

                        data[21] = bdl.getString("21");
                        String[] d21 = data[21].split("\":\"|（|）|\",\"");
                        h3.setText(d21[1]);
                        h33.setText(d21[5]);
                        h333.setText(d21[7]);

                        data[23] = bdl.getString("23");
                        String[] d23 = data[23].split("\":\"|（|）|\",\"");
                        h4.setText(d23[1]);
                        h44.setText(d23[5]);
                        h444.setText(d23[7]);

                        data[25] = bdl.getString("25");
                        String[] d25 = data[25].split("\":\"|（|）|\",\"");
                        h5.setText(d25[1]);
                        h55.setText(d25[5]);
                        h555.setText(d25[7]);

                        data[27] = bdl.getString("27");
                        String[] d27 = data[27].split("\":\"|（|）|\",\"");
                        h6.setText(d27[1]);
                        h66.setText(d27[5]);
                        h666.setText(d27[7]);

                        data[29] = bdl.getString("29");
                        String[] d29 = data[29].split("\":\"|（|）|\",\"");
                        h7.setText(d29[1]);
                        h77.setText(d29[5]);
                        h777.setText(d29[7]);
                    }
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
                String str = buffer.toString();
                Message msg = handler.obtainMessage(3);
                Bundle bdl = new Bundle();

                String elements[] = str.split("\\{|\\}");
                for(int i=0;i<elements.length;i++) {
                    bdl.putString("" + i, elements[i]);
                }
                msg.setData(bdl);
                msg.sendToTarget();
            } catch (Exception e) {

            }
        }
    }

}

