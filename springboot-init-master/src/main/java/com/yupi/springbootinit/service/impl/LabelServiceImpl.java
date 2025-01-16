package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.mapper.LabelMapper;
import com.yupi.springbootinit.model.entity.Label;
import com.yupi.springbootinit.service.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 杨大宇
* @description 针对表【label】的数据库操作Service实现
* @createDate 2024-10-30 19:59:19
*/
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label>
    implements LabelService {

    @Resource
    private LabelMapper labelMapper;


    @Override
    public BaseResponse<List<Label>> getAllLabel() {
        List<Label> labels = labelMapper.getAllLabel();

        return ResultUtils.success(labels);
    }

    @Override
    public BaseResponse<String> changeLabel(String labelName) {
        labelMapper.changeLabel(labelName);
        return ResultUtils.success(labelName);
    }

    @Override
    public BaseResponse<Label> addLabel(String labelName) {
        labelMapper.add(labelName);
        Label label = new Label(labelName,0);
        return ResultUtils.success(label);
    }
}




