package com.mengyou.localloop.event;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public interface CreateAvtivityCallBack {

    /**
     * 回调函数
     * @param images 图片
     * @param contentStr 内容
     */
    void callBack(List<String> images,String contentStr,boolean isHidename);
}
