package com.mengyou.localloop.viewbuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mengyou.localloop.R;
import com.mengyou.localloop.model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SelectCityDialog {

    private Button btn_province,btn_city,btn_area;
    private ListView listView;
    private Context mContext;

    private List<City> citys;
    private List<City.CityEntity> cityEntityList;
    private List<String> areaList;
    private int clickCount=0;
    private SelectProvinceAdapter adapter;
    private SelectCityAdapter cityAdapter;
    private SelectAreaAdapter areaAdapter;

    private int provinceMemory=0;//记忆变量，用于记录选取的省份的位置
    private int cityMemory=0;//用于记录选取的城市的位置
    private int areaMemory=0;//用于记录选取的县区的位置
    private AlertDialog dialog;

    private SelectCityCallBack callBack;

    public SelectCityDialog(Context mContext,SelectCityCallBack callBack) {
        this.callBack = callBack;
        this.mContext = mContext;
    }

    public void initView() {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_city_select,null);
        String json= null;
        btn_area= (Button) view.findViewById(R.id.btn_a);
        btn_city= (Button) view.findViewById(R.id.btn_c);
        btn_province= (Button) view.findViewById(R.id.btn_p);
        listView= (ListView) view.findViewById(R.id.list_select_value);
        try {
            json = convertStreamToString(mContext.getResources().getAssets().open("citylist.json"));
            citys= JSON.parseArray(json, City.class);
            btn_province.setText(citys.get(0).getName());
            btn_city.setText(citys.get(0).getCity().get(0).getName());
            btn_area.setText(citys.get(0).getCity().get(0).getArea().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount=0;
                setTextRes(0);
                listView.setAdapter(adapter = new SelectProvinceAdapter());
                listView.setSelection(provinceMemory);
                adapter.setSelectItem(provinceMemory);
                adapter.notifyDataSetChanged();
            }
        });

        btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount=1;
                setTextRes(1);
                cityEntityList=citys.get(provinceMemory).getCity();
                listView.setAdapter(cityAdapter = new SelectCityAdapter());
                listView.setSelection(cityMemory);
                cityAdapter.setSelectItem(cityMemory);
                cityAdapter.notifyDataSetChanged();
            }
        });

        btn_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 2;
                setTextRes(2);
                areaList = cityEntityList.get(cityMemory).getArea();
                listView.setAdapter(areaAdapter = new SelectAreaAdapter());
                listView.setSelection(areaMemory);
                areaAdapter.setSelectItem(areaMemory);
                areaAdapter.notifyDataSetChanged();
            }
        });


        listView.setAdapter(adapter = new SelectProvinceAdapter());
        adapter.setSelectItem(0);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clickCount == 0) {
                    provinceMemory = position;
                    btn_province.setText(citys.get(position).getName());
                    btn_city.setText(citys.get(position).getCity().get(0).getName());
                    btn_area.setText(citys.get(position).getCity().get(0).getArea().get(0));
                    cityEntityList = citys.get(position).getCity();
                    listView.setAdapter(cityAdapter = new SelectCityAdapter());
                    clickCount++;
                    setTextRes(1);
                    adapter.setSelectItem(position);
                    adapter.notifyDataSetChanged();
                } else if (clickCount == 1) {
                    cityMemory = position;
                    btn_city.setText(cityEntityList.get(position).getName());
                    btn_area.setText(cityEntityList.get(position).getArea().get(0));
                    areaList = cityEntityList.get(position).getArea();
                    listView.setAdapter(areaAdapter = new SelectAreaAdapter());
                    clickCount++;
                    setTextRes(2);
                    cityAdapter.setSelectItem(position);
                    cityAdapter.notifyDataSetChanged();
                } else if (clickCount == 2) {
                    areaMemory = position;
                    btn_area.setText(areaList.get(position));
                    areaAdapter.setSelectItem(position);
                    areaAdapter.notifyDataSetChanged();
                }
            }
        });
        listView.setSelection(0);

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext)
                .setTitle("请选择位置信息")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.callBack(citys.get(provinceMemory).getName(),citys.get(provinceMemory).getCity().get(cityMemory).getName(),
                                citys.get(provinceMemory).getCity().get(cityMemory).getArea().get(areaMemory));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog=builder.create();
    }

    public void show()
    {
        initView();
        if (dialog!=null)
        {
            dialog.show();
        }
    }

    public interface SelectCityCallBack{
        void callBack(String p,String c,String a);
    }

    private void setTextRes(int position) {
        switch (position)
        {
            case 0:
                btn_province.setTextSize(16);
                btn_province.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btn_city.setTextSize(12);
                btn_city.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_area.setTextSize(12);
                btn_area.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 1:
                btn_province.setTextSize(12);
                btn_province.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_city.setTextSize(16);
                btn_city.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btn_area.setTextSize(12);
                btn_area.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 2:
                btn_province.setTextSize(12);
                btn_province.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_city.setTextSize(12);
                btn_city.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_area.setTextSize(16);
                btn_area.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
        }
    }

    class SelectProvinceAdapter extends BaseAdapter {

        private int selectItem=0;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        @Override
        public int getCount() {
            return citys.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt_value;
            if (convertView==null)
            {
                convertView=LayoutInflater.from(mContext).inflate(R.layout.item_select,parent,false);
                txt_value= (TextView) convertView.findViewById(R.id.select_value);
                convertView.setTag(txt_value);
            }
            else {
                txt_value= (TextView) convertView.getTag();
            }
            if (selectItem==position)
            {
                txt_value.setTextSize(16);
                txt_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            else
            {
                txt_value.setTextSize(12);
                txt_value.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            txt_value.setText(citys.get(position).getName());
            return convertView;
        }
    }

    class SelectCityAdapter extends BaseAdapter{

        private int selectItem=0;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        @Override
        public int getCount() {
            return cityEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt_value;
            if (convertView==null)
            {
                convertView=LayoutInflater.from(mContext).inflate(R.layout.item_select,parent,false);
                txt_value= (TextView) convertView.findViewById(R.id.select_value);
                convertView.setTag(txt_value);
            }
            else {
                txt_value= (TextView) convertView.getTag();
            }
            if (selectItem==position)
            {
                txt_value.setTextSize(16);
                txt_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            else
            {
                txt_value.setTextSize(12);
                txt_value.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            txt_value.setText(cityEntityList.get(position).getName());
            return convertView;
        }
    }

    class SelectAreaAdapter extends BaseAdapter{

        private int selectItem=0;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        @Override
        public int getCount() {
            return areaList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt_value;
            if (convertView==null)
            {
                convertView=LayoutInflater.from(mContext).inflate(R.layout.item_select,parent,false);
                txt_value= (TextView) convertView.findViewById(R.id.select_value);
                convertView.setTag(txt_value);
            }
            else {
                txt_value= (TextView) convertView.getTag();
            }
            if (selectItem==position)
            {
                txt_value.setTextSize(16);
                txt_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            else
            {
                txt_value.setTextSize(12);
                txt_value.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            txt_value.setText(areaList.get(position));
            return convertView;
        }
    }

    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
