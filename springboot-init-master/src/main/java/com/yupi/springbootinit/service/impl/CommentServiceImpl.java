package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.Comment;
import com.yupi.springbootinit.service.CommentService;
import com.yupi.springbootinit.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 杨大宇
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2025-01-17 11:11:43
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




