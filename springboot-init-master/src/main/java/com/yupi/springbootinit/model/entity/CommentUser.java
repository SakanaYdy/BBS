package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@AllArgsConstructor
@Setter
@Data
@NoArgsConstructor
public class CommentUser implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer blogId;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String comment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public Integer getBlogId() {
        return blogId;
    }

    /**
     * 
     */
    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }



    /**
     * 
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


}