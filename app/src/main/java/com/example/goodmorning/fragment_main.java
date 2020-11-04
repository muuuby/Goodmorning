package com.example.goodmorning;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class fragment_main extends Fragment {

    Handler handler;
    TextView date,city,temp,weather,warning;
    TextView ganq,caiy,jiangk,gongz,zongh,color,number,txt;
    ImageButton btn4;

    String cityname;
    String cons;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        View fragment1 = inflater.inflate(R.layout.fragment_main, null);

        date = fragment1.findViewById(R.id.date);
        city = fragment1.findViewById(R.id.city);
        temp = fragment1.findViewById(R.id.temp);
        weather = fragment1.findViewById(R.id.weather);
        warning = fragment1.findViewById(R.id.warning);

        ganq = fragment1.findViewById(R.id.ganq);
        caiy = fragment1.findViewById(R.id.caiy);
        gongz = fragment1.findViewById(R.id.gongz);
        jiangk =fragment1.findViewById(R.id.jiank);
        zongh = fragment1.findViewById(R.id.zongh);
        color = fragment1.findViewById(R.id.color1);
        number = fragment1.findViewById(R.id.number1);
        txt = fragment1.findViewById(R.id.txt);

        final String data[] = new String[10];
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 3) {
                    Bundle bdl = msg.getData();
                        //解析数据
                        data[0] = bdl.getString("52");
                        if(data[0]!=null) {
                        String[] d0 = data[0].split("\"");
                        data[1] = bdl.getString("67");
                        String[] d1 = data[1].split("\"");
                        data[2] = bdl.getString("20");
                        String[] d2 = data[2].split("\"");
                        data[3] = bdl.getString("25");
                        String[] d3 = data[3].split("\"");
                        data[4] = bdl.getString("27");
                        String[] d4 = data[4].split("\"");
                        data[5] = bdl.getString("31");
                        String[] d5 = data[5].split("\"");
                        String warning_only;
                        data[6] = bdl.getString("16");
                        String[] d6 = data[6].split("\"");
                        if (d6.length < 2) {
                            warning_only = "无气象预警";
                        } else {
                            warning_only = d6[1];
                        }

                        String fraction = bdl.getString("fraction");
                        String[] frac = fraction.split("\\s");

                        String word = bdl.getString("word");
                        String[] w = word.split("\\s");

                        String t = bdl.getString("txt");
                        String[] t1 = t.split("\\s");

                        date.setText(d0[1]);
                        city.setText(d1[1]);
                        temp.setText(d2[1]);
                        weather.setText(d4[1]);
                        warning.setText(warning_only);

                        ganq.setText(frac[1]);
                        jiangk.setText(frac[3]);
                        caiy.setText(frac[5]);
                        gongz.setText(frac[7]);
                        zongh.setText(frac[9]);
                        color.setText(w[0]);
                        number.setText(w[1]);
                        txt.setText(t1[0]);
                    }
                }
                super.handleMessage(msg);
            }
        };

        //读取城市和星座
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("identity", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(getContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        cons = sharedPreferences.getString("xingzuo", "");
        cityname = sharedPreferences.getString("city", "");

        //开启子线程
        myThread t = new myThread();
        new Thread(t).start();
        return fragment1;
    }

    //后台线程访问网络
    public class myThread implements Runnable {
        @Override
        public void run() {


            //weather data
//            String cityname = "成都";
//            String cons = "Aries";
            String url = "https://api.djapi.cn/rtweather/get?cityname_ch="+cityname+"&cn_to_unicode=0&token=fef56038bf629f09129d7521716fa5f0&datatype=json";
            String url2 = "https://www.d1xz.net/yunshi/today/"+cons+"/";
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

                //luck data
                //解析html文件
                Document doc = Jsoup.connect(url2).get();
                Log.i("fragment_main","run:" + doc.title());

                //读取运势
                Elements fraction = doc.getElementsByClass("fraction");
                Elements txt = doc.getElementsByClass("txt");
                Elements words_t = doc.getElementsByClass("words_t");

                Message msg = handler.obtainMessage(3);
                Bundle bdl = new Bundle();

                for(int i=0;i<elements.length;i++){
                    bdl.putString(""+i,elements[i]);
                }

                bdl.putString("fraction",fraction.text());
                bdl.putString("txt",txt.text());
                bdl.putString("word",words_t.text());

                msg.setData(bdl);
                msg.sendToTarget();
                Log.e("Json","success");
            } catch (Exception e) {

            }
        }
    }

    //按钮点击事件
    @SuppressLint("WrongViewCast")
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        btn4 = getActivity().findViewById(R.id.imageButton1);

        //设置按钮
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "settings");

                Intent main = new Intent(getContext(),SettingsActivity.class);
                startActivityForResult(main, 1);
            }
        });

    }

}

