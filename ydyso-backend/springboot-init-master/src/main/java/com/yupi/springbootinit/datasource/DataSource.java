package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口（所有新的数据源都要实现这个接口）
 * @param <T>
 */
public interface DataSource <T>{

    /**
     * 搜索接口
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText,long pageNum,long pageSize);

}
