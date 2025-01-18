package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWithComment {

    private PostVO postVO;

    private List<Comment> comments;


}
