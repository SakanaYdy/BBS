package com.yupi.springbootinit.model.dto.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostQuery {

    private Long userId;

    private String content;

    private String tag;

}
