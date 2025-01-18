package com.yupi.springbootinit.controller;


import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.mapper.CommentMapper;
import com.yupi.springbootinit.model.entity.Comment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentMapper commentMapper;

    @RequestMapping("/add")
    public BaseResponse<Comment> addComment(@RequestBody Comment comment){
        System.out.println("新增评论");
        commentMapper.insert(comment);
        return ResultUtils.success(comment);
    }
}
