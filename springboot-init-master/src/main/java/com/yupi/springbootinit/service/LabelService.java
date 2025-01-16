package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.model.entity.Label;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【label】的数据库操作Service
* @createDate 2024-10-30 19:59:19
*/
public interface LabelService extends IService<Label> {

    BaseResponse<List<Label>> getAllLabel();

    BaseResponse<String> changeLabel(String labelName);

    BaseResponse<Label> addLabel(String labelName);
}
