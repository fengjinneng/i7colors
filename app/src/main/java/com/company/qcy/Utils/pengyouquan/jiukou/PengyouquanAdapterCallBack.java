package com.company.qcy.Utils.pengyouquan.jiukou;

import com.company.qcy.bean.pengyouquan.PengyouquanBean;

public interface PengyouquanAdapterCallBack {

    void save(String content,Long id, Long parentId, String commentUser, PengyouquanBean bean, int position);

}
