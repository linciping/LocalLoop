package com.mengyou.localloop.viewbuilder;

import android.content.Context;
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

import com.mengyou.localloop.R;
import com.mengyou.localloop.model.DateModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SelectDateDialog {
    private Button btn_year,btn_month,btn_day;
    private ListView listView;
    private Context mContext;

    DateModel dateModel;
    private final static int startYear=1900;
    private List<Integer> years;
    private List<Integer> months;
    private List<Integer> days;
    private SelectDateAdapter adapter;
    private int clickCount=0;

    private int yearMemory=0;//记忆变量，用于记录选取的省份的位置
    private int monthMemory=0;//用于记录选取的城市的位置
    private int dayMemory=0;//用于记录选取的县区的位置

    private AlertDialog dialog;

    private void initView()
    {
        View view=LayoutInflater.from(mContext).inflate(R.layout.content_select_date,null);
        String json= null;
        btn_year= (Button) view.findViewById(R.id.btn_year);
        btn_month= (Button) view.findViewById(R.id.btn_month);
        btn_day= (Button) view.findViewById(R.id.btn_day);
        initData();
        listView= (ListView)view.findViewById(R.id.list_select_value);
        listView.setAdapter(adapter = new SelectDateAdapter(years));
        listView.setSelection(dateModel.getYear() - startYear);
        adapter.setSelectItem(dateModel.getYear() - startYear);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clickCount == 0) {
                    yearMemory = position;
                    btn_year.setText(years.get(position) + "年");
                    btn_month.setText(1 + "月");
                    btn_day.setText(1 + "日");
                    listView.setAdapter(adapter = new SelectDateAdapter(months));
                    clickCount++;
                    setTextRes(1);
                    adapter.setSelectItem(0);
                    adapter.notifyDataSetChanged();
                } else if (clickCount == 1) {
                    monthMemory = position;
                    btn_month.setText(months.get(position) + "月");
                    btn_day.setText(1 + "日");
                    getMonthDay(months.get(position));
                    listView.setAdapter(adapter = new SelectDateAdapter(days));
                    clickCount++;
                    setTextRes(2);
                    adapter.setSelectItem(0);
                    adapter.notifyDataSetChanged();
                } else if (clickCount == 2) {
                    dayMemory = position;
                    btn_day.setText(days.get(position) + "日");
                    adapter.setSelectItem(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext)
                .setTitle("请选择日期")
                .setView(view);
        dialog=builder.create();
    }

    public void show(Context mContext)
    {
        this.mContext=mContext;
        initView();
        if (dialog!=null)
        {
            dialog.show();
        }
    }

    private void getMonthDay(int month) {
        days=new ArrayList<>();
        int year=years.get(yearMemory);
        if (month==2)
        {
            if (year%4==0&&year%100!=0||year%400==0)
            {
                for (int i=0;i<29;i++)
                {
                    days.add(i+1);
                }
            }
            else
            {
                for (int i=0;i<28;i++)
                {
                    days.add(i+1);
                }
            }
        }
        else if (month==4||month==6||month==9||month==11)
        {
            for (int i=0;i<30;i++)
            {
                days.add(i+1);
            }
        }
        else
        {
            for (int i=0;i<31;i++)
            {
                days.add(i+1);
            }
        }
    }

    private void initData() {
        years=new ArrayList<>();
        months=new ArrayList<>();
        days=new ArrayList<>();
        for (int i=startYear;i<2100;i++)
        {
            years.add(i);
        }
        for (int i=0;i<12;i++)
        {
            months.add(i+1);
        }
        for (int i=0;i<31;i++)
        {
            days.add(i+1);
        }
        dateModel=new DateModel(new Date());
        btn_year.setText(dateModel.getYear() + "年");
        btn_month.setText(dateModel.getMonth() + "月");
        btn_day.setText(dateModel.getDay() + "日");
        yearMemory=dateModel.getYear()-startYear;
        monthMemory=dateModel.getMonth()-1;
        dayMemory=dateModel.getDay()-1;
        btn_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                setTextRes(0);
                listView.setAdapter(adapter = new SelectDateAdapter(years));
                listView.setSelection(yearMemory);
                adapter.setSelectItem(yearMemory);
                adapter.notifyDataSetChanged();
            }
        });

        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 1;
                setTextRes(1);
                listView.setAdapter(adapter = new SelectDateAdapter(months));
                listView.setSelection(monthMemory);
                adapter.setSelectItem(monthMemory);
                adapter.notifyDataSetChanged();
            }
        });

        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 2;
                setTextRes(2);
                listView.setAdapter(adapter = new SelectDateAdapter(days));
                listView.setSelection(dayMemory);
                adapter.setSelectItem(dayMemory);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setTextRes(int position) {
        switch (position)
        {
            case 0:
                btn_year.setTextSize(16);
                btn_year.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btn_month.setTextSize(12);
                btn_month.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_day.setTextSize(12);
                btn_day.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 1:
                btn_year.setTextSize(12);
                btn_year.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_month.setTextSize(16);
                btn_month.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btn_day.setTextSize(12);
                btn_day.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 2:
                btn_year.setTextSize(12);
                btn_year.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_month.setTextSize(12);
                btn_month.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btn_day.setTextSize(16);
                btn_day.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
        }
    }

    class SelectDateAdapter extends BaseAdapter {

        private List<Integer> datas;

        private int selectItem=0;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        SelectDateAdapter(List<Integer> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
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
                convertView= LayoutInflater.from(mContext).inflate(R.layout.item_select,parent,false);
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
            txt_value.setText(datas.get(position)+"");
            return convertView;
        }
    }
}
