package com.yupi.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class SearchFacade {

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest,
                                            HttpServletRequest request) {

        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(searchTypeEnum == null,new RuntimeException("查询错误"));
//      ThrowUtils.throwIf(StringUtils.isNullOrEmpty(type),new RuntimeException("查询错误"));

        SearchVO searchVO = new SearchVO();
        String searchText = searchRequest.getSearchText();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        if(type == null){
            // 图片查询
            Page<Picture> picturePage = pictureService.searchPicture(searchText, current, pageSize);

            // 用户查询
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userService.searchUser(userQueryRequest);

            // 文章查询
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
//        postQueryRequest.setCurrent(current);
//        postQueryRequest.setPageSize(pageSize);

            Page<PostVO> postPage = postService.searchPost(postQueryRequest,request);
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());

        }else{
            switch (searchTypeEnum){
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postPage = postService.searchPost(postQueryRequest,request);
                    searchVO.setPostList(postPage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.searchUser(userQueryRequest);
                    searchVO.setUserList(userVOPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureService.searchPicture(searchText, current, pageSize);
                    searchVO.setPictureList(picturePage.getRecords());
                    break;
            }
        }
        return searchVO;
    }

}
