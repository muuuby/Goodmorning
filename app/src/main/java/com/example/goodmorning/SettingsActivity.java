package com.example.goodmorning;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null; 	//地级市

    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;	//地级适配器

    static int provincePosition = 3;

    String[] province = new String[]{"北京","上海","天津","广东","重庆","黑龙江","吉林","辽宁","内蒙古","河北","山西","陕西","山东","新疆","西藏","青海","甘肃","宁夏","河南","江苏",
            "湖北","浙江","安徽","福建","江西","湖南","贵州","四川","云南","广西","海南","香港","澳门","台湾"};
    String[][] city = new String[][]
            {
                    {"北京"},
                    {"上海"},
                    {"天津"},
                    {"广州","韶关","惠州","梅州","汕头","深圳","珠海","佛山","肇庆","湛江","江门","清远","云浮","潮州","阳江"},
                    {"重庆"},

                    {"哈尔滨","齐齐哈尔","牡丹江","佳木斯","绥化","黑河","大兴安岭","伊春","大庆","新兴","鸡西","鹤岗","双鸭山"},
                    {"长春","吉林","延吉","四平","通化","白城","辽源","松原","白山"},
                    {"沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","铁岭","朝阳","盘锦","葫芦岛"},
                    {"呼和浩特","包头","乌海","集宁","通辽","赤峰","鄂尔多斯","临河","锡林浩特","海拉尔","乌兰浩特","阿左旗"},
                    {"石家庄","保定","张家口","双桥","唐山","廊坊","沧州","衡水","邢台","邯郸","秦皇岛","雄安新区"},
                    {"太原","大同","阳泉","晋中","长治","晋城","临汾","运城","朔州","忻州","吕梁"},
                    {"西安","咸阳","延安","榆林","渭南","商洛","安康","汉中","宝鸡","铜川","杨凌"},
                    {"济南","青岛","淄博","德州","烟台","潍坊","济宁","泰安","临沂","菏泽","滨州","东营","威海","枣庄","日照","莱芜","聊城"},
                    {"乌鲁木齐","克拉玛依","石河子","昌吉","吐鲁番","库尔勒","阿拉尔","阿克苏","喀什","伊宁","塔城","哈密","和田","阿勒泰","阿图什","博乐","图木舒克","五家渠","铁门关","北屯"},
                    {"拉萨","日喀则","山南","林芝","昌都","那曲","阿里"},
                    {"西宁","平安","同仁","共和","玛沁","玉树","德令哈","海晏"},
                    {"兰州","定西","平凉","庆阳","武威","金昌","张掖","酒泉","天水","武都","临夏","合作","白银"},
                    {"银川","石嘴山","吴忠","固原","中卫"},
                    {"郑州","安阳","新乡","许昌","平顶山","信阳","南阳","开封","洛阳","商丘","焦作","鹤壁","濮阳","周口","漯河","驻马店","三门峡"},
                    {"南京","镇江","苏州","南通","扬州","盐城","徐州","淮安","连云港","常州","泰州","宿迁"},
                    {"武汉","襄阳","鄂州","孝感","黄冈","黄石","咸宁","荆州","宜昌","恩施","十堰","神农架","随州","荆门","天门","仙桃","潜江"},
                    {"杭州","湖州","嘉兴","宁波","越城","台州","温州","丽水","金华","衢州","舟山"},
                    {"合肥","蚌埠","芜湖","淮南","马鞍山","安庆","宿州","阜阳","亳州","滁州","淮北","铜陵","宣城","六安","池州"},
                    {"福州","厦门","宁德","莆田","泉州","漳州","龙岩","三明","南平","钓鱼岛"},
                    {"南昌","九江","上饶","抚州","宜春","吉安","赣州","景德镇","萍乡","新余","鹰潭"},
                    {"长沙","湘潭","株洲","衡阳","郴州","常德","益阳","娄底","邵阳","岳阳","张家界","怀化","黔阳","永州","吉首"},
                    {"贵阳","遵义","安顺","都匀","凯里","铜仁","毕节","水城","兴义"},
                    {"成都","攀枝花","自贡","绵阳","南充","达州","遂宁","广安","巴中","泸州","宜宾","内江","资阳","乐山","眉山","凉山","雅安","广元","德阳","阿坝","甘孜"},
                    {"昆明","大理","红河","曲靖","保山","文山","玉溪","楚雄","普洱","昭通","临沧","怒江","香格里拉","丽江","德宏","景洪"},
                    {"南宁","崇左","柳州","来宾","桂林","梧州","贺州","贵港","玉林","百色","钦州","河池","北海","防城港"},
                    {"海口","三亚","三沙"},
                    {"香港"},
                    {"澳门"},
                    {"台北","高雄","台中"},
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        //把数据存到文件
        final SharedPreferences sharedPreferences = getSharedPreferences("identity", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //选择城市
        provinceSpinner = (Spinner)findViewById(R.id.city1);
        citySpinner = (Spinner)findViewById(R.id.city2);
        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(SettingsActivity.this,
                R.layout.spinner_item, province);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        int pp = sharedPreferences.getInt("pp",0);
        provinceSpinner.setSelection(pp,true);  //设置默认选中项，此处为默认选中第4个值

        cityAdapter = new ArrayAdapter<String>(SettingsActivity.this,
                R.layout.spinner_item2, city[pp]);
        cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item2);
        citySpinner.setAdapter(cityAdapter);
        // 设置二级下拉列表的选项内容适配器
        int cp = sharedPreferences.getInt("cp",0);
        citySpinner.setSelection(cp,true);  //默认选中第0个

        //省级下拉框监听
        final Spinner finalCitySpinner = citySpinner;
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                //position为当前省级选中的值的序号

                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        SettingsActivity.this,R.layout.spinner_item2, city[position]);
                cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item2);
                // 设置二级下拉列表的选项内容适配器
                finalCitySpinner.setAdapter(cityAdapter);
                provincePosition = position;	//记录当前省级序号，留给下面修改县级适配器时用
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
            }
        });
        //市级下拉框监听
        citySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String ct = city[provincePosition][position];
                editor.putString("city",ct);
                editor.putInt("pp",provincePosition);
                editor.putInt("cp",position);
                editor.commit();
                System.out.println(ct);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //选择星座
        Spinner s2 = (Spinner)findViewById(R.id.Spinner_id2);
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Aries");list2.add("Taurus");list2.add("Gemini");list2.add("Cancer");list2.add("Leo");list2.add("Virgo");
        list2.add("Libra");list2.add("Scorpio");list2.add("Sagittarius");list2.add("Capricorn");list2.add("Aquarius");list2.add("Pisces");

        //为下拉列表定义一个适配器
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(this,R.layout.spinner_item2, list2);
        //设置下拉菜单样式。
        ad2.setDropDownViewResource(R.layout.spinner_dropdown_item2);
        //添加数据
        s2.setAdapter(ad2);
        //默认选项
        int depositon = sharedPreferences.getInt("xingzuonum",0);
        s2.setSelection(depositon,true);
        //点击响应事件
        s2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String xz = list2.get(arg2);
                editor.putString("xingzuo",xz);
                editor.putInt("xingzuonum",arg2);
                editor.commit();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



    }
}