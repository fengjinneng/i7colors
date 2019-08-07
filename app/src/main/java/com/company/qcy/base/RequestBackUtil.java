package com.company.qcy.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class RequestBackUtil {

    public static void parseArray(JSONArray data, List list, int pageNo,
                                  BaseQuickAdapter adapter,boolean isReflash,Class<?> clz) {


            if (ObjectUtils.isEmpty(data)) {
                //第一次进来就是空的
                if (pageNo == 1) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreEnd();

                return;
            }
            List<?> beans = JSONObject.parseArray(data.toJSONString(), clz);
            if (ObjectUtils.isEmpty(beans)) {
                adapter.loadMoreEnd();
                return;
            }
            if (isReflash) {
                list.clear();
                list.addAll(beans);
                adapter.setNewData(list);
                isReflash = false;
                adapter.loadMoreComplete();
                adapter.disableLoadMoreIfNotFullPage();
                return;
            }
            list.addAll(beans);
            adapter.setNewData(list);
            adapter.loadMoreComplete();
        }

}
