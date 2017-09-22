package com.zhang.myselfview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {

    public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override//使用 getView方法来返回你需要的布局
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //这里是要显示的itemView converView 展示在界面上的一个item。因为手机屏幕就那么大，
            // 所以一次展示给用户看见的内容是固定的，如果你List中有1000条数据，不应该new1000个converView，
            // 那样内存肯定不足，应该学会控件重用，滑出屏幕的converView就在下面新进来的item中重新使用
            view = LayoutInflater.from(getContext()).inflate(R.layout.my_list_view_item, null);
        } else {
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(getItem(position));
        return view;
    }

}