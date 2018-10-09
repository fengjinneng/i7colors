package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.company.qcy.R;

import java.util.List;
public class ScrollGridViewAdapter extends BaseAdapter {

    Context context;
    List<String> datas;
    LayoutInflater layoutInflater;

    public ScrollGridViewAdapter(Context context, List<String> datas,
                                 LayoutInflater layoutInflater) {
        this.context = context;
        this.datas = datas;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.vlayout_home_store_grid_item, null);
            viewHolder.name = convertView.findViewById(R.id.vlayout_home_store_grid_item_text);
//            viewHolder.price = convertView.findViewById(R.id.item_homepage_floor_gridview_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(datas.get(position));
//        if (StringUtil.isEmpty(list.get(position).getShop_price())) {
//            viewHolder.name.setText(list.get(position).getShop_store_name());
//            viewHolder.relativeLayout.setVisibility(View.GONE);
//        } else {
//            viewHolder.name.setText(list.get(position).getShop_store_name());
//            viewHolder.price.setText(StringUtil.numberFormat(list.get(position).getShop_price()));
//        }
        return convertView;
    }

    static class ViewHolder {
        TextView name, price;

    }

}
