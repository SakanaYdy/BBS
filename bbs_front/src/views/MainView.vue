<template>
  <div>
    <!-- 导航栏 -->
    <NavBar/>

    <!-- 搜索框 -->
    <div class="search-container">
      <input type="text" class="search-input" placeholder="请输入搜索关键词">
      <button class="search-btn">搜索</button>
    </div>

    <!-- 标签 -->
    <div class="tags-container">
      <span class="tag">Java</span>
      <span class="tag">机器学习</span>
      <span class="tag">深度学习</span>
      <span class="tag">Python</span>
      <span class="tag">前端</span>
    </div>

    <!-- 博客列表 -->
    <div class="blog-container">
      <!-- 遍历 blogList 渲染每个博客 -->
      <div v-for="(blog, index) in blogList" :key="index" class="blog-item" @click="openModal(blog)">
        <div class="blog-content">
          <h2 class="blog-title">{{ blog.postVO.title }}</h2>
          <p class="blog-description">{{ blog.postVO.content.slice(0, 100) }}...</p>
          <!-- 显示点赞数和收藏数 -->
          <div class="blog-stats">
            <span class="likes">点赞：{{ blog.postVO.thumbNum }}</span>
            <span class="spacer"></span>  <!-- 添加一个空白间隔 -->
            <span class="favorites">收藏：{{ blog.postVO.favourNum }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 弹窗显示完整帖子内容 -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2 class="modal-title">{{ selectedBlog.postVO.title }}</h2>
        <p class="modal-description">{{ selectedBlog.postVO.content }}</p>
        
        <!-- 点赞与收藏按钮 -->
        <div class="modal-footer">
          <button @click="toggleLike(selectedBlog.postVO.id)" :class="['like-button', { liked: isLiked }]">
            {{ selectedBlog.postVO.hasThumb ? '已点赞' : '点赞' }}
          </button>
          <button @click="toggleFavorite(selectedBlog.postVO.id)" :class="['favorite-button', { favorited: isFavorited }]">
            {{ selectedBlog.postVO.hasFavour ? '已收藏' : '收藏' }}
          </button>
        </div>

        <!-- 评论区 -->
        <div class="comments-section">
          <!-- 评论输入框 -->
          <div class="comment-box">
            <input type="text" v-model="newComment" placeholder="请输入评论..." class="comment-input" />
            <button @click="submitComment" class="comment-submit-btn">提交</button>
          </div>

          <h4>之前的评论:</h4>
          <el-card v-for="(prevComment, index) in selectedBlog.comments" :key="index" class="comment-card">
            <div><strong>{{ prevComment.userId }}:</strong> {{ prevComment.comment }}</div>
          </el-card>

        </div>

        <button class="close-btn" @click="closeModal">关闭</button>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'; // 引入NavBar组件
import axios from 'axios';
import { mapState } from 'vuex';

export default {
  name: 'HomePage', // 组件名称
  components: {
    NavBar
  },
  data() {
    return {
      blogList: [], // 存储从后端获取的博客数据
      showModal: false, // 控制弹窗的显示与隐藏
      selectedBlog: null, // 存储点击的博客数据
      newComment: '', // 存储新评论的内容
      isLiked: false, // 点赞状态
      isFavorited: false, // 收藏状态
    };
  },
  mounted() {
    this.fetchBlogData();
  },
  computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
  methods: {
    // 获取博客数据的方法
    fetchBlogData() {
      axios.get('http://localhost:8101/api/post/get/all',{
        params:{
          userId : this.currentUser.id
        }
      })
        .then(response => {
          if (response.data.code == 0) {
            this.blogList = response.data.data;  // 将数据赋值给 blogList
            console.log(response.data)
            console.log(this.blogList)
          }
        })
        .catch(error => {
          console.error('获取博客数据失败', error);
        });
    },

    // 打开弹窗并加载点击的博客内容
    openModal(blog) {
      this.selectedBlog = blog; // 设置当前选中的博客数据
      this.isLiked = false; // 重置点赞状态
      this.isFavorited = false; // 重置收藏状态
      this.newComment = ''; // 清空评论输入框
      this.showModal = true; // 显示弹窗
    },

    // 关闭弹窗
    closeModal() {
      this.showModal = false;
      this.selectedBlog = null; // 清空选中的博客
    },

    // 切换点赞状态
    toggleLike(blogId) {
      this.isLiked = !this.isLiked;
      const requestBody = {
        postId: blogId,
        userId: this.currentUser.id
      }
      // 调用后端逻辑
      axios.post('http://localhost:8101/api/post_thumb/',requestBody)
            .then(response => {
                if (response.data.code === 0) {
                console.log('点赞更新成功', response.data);
                // 可以根据需要做后续操作，如跳转到博客列表页等
                } else {
                console.error('点赞更新失败：', response.data.message);
                }
            })
            .catch(error => {
                console.error('发送请求失败', error);
            });
    },

    // 切换收藏状态
    toggleFavorite(blogId) {
      this.isFavorited = !this.isFavorited;
      const requestBody = {
        postId: blogId,
        userId: this.currentUser.id
      }
      // 调用后端逻辑
      axios.post('http://localhost:8101/api/post_favour/',requestBody)
            .then(response => {
                if (response.data.code === 0) {
                console.log('收藏更新成功', response.data);
                // 可以根据需要做后续操作，如跳转到博客列表页等
                } else {
                console.error('收藏更新失败：', response.data.message);
                }
            })
            .catch(error => {
                console.error('收藏请求失败', error);
            });
    },

    // 提交评论
    submitComment() {
      if (!this.newComment.trim()) {
        alert('评论内容不能为空');
        return;
      }

      const newCommentData = {
        username: '用户', // 假设用户名为 '用户'，可以替换为实际的用户名
        content: this.newComment.trim(),
      };

      // 将新的评论添加到评论列表
      this.selectedBlog.comments.push(newCommentData);

      // 清空输入框
      this.newComment = '';

      // 可根据需求，发送评论数据到后端保存
      // axios.post('http://localhost:8101/api/post/comment', {
      //   postId: this.selectedBlog.id,
      //   comment: newCommentData
      // })
      // .then(response => {
      //   console.log('评论提交成功');
      // })
      // .catch(error => {
      //   console.error('评论提交失败', error);
      // });
    }
  }
};
</script>
  
  <style scoped>
  /* 搜索框容器 */
  /* 搜索框容器 */
.search-container {
  text-align: center;
  margin-top: 20px;
}

.search-input {
  padding: 10px;
  width: 300px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.search-btn {
  padding: 10px 20px;
  margin-left: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
}

.search-btn:hover {
  background-color: #45a049;
}

/* 标签容器 */
.tags-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.tag {
  margin: 0 10px;
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 20px;
  background-color: #f0f0f0;
  cursor: pointer;
}

.tag:hover {
  background-color: #e0e0e0;
}

/* 博客容器 */
.blog-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 30px;
}

.blog-item {
  display: flex;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  background-color: #f9f9f9;
  gap: 20px;
}

.blog-cover img {
  width: 150px;
  height: 100px;
  object-fit: cover;
  border-radius: 5px;
}

.blog-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex: 1;
}

.blog-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.blog-description {
  font-size: 1rem;
  color: #555;
}
.blog-stats {
  margin-top: 10px;
  font-size: 0.9em;
  color: #888;
}

.blog-stats .likes {
  margin-right: 10px;  /* 在点赞和收藏之间添加间距 */
}

.blog-stats .favorites {
  margin-left: 10px;  /* 在收藏和右边内容之间添加间距 */
}

.blog-container {
  margin-top: 20px;
}

.blog-item {
  padding: 15px;
  background-color: #fff;
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}

.blog-item:hover {
  background-color: #f9f9f9;
}

.blog-title {
  font-size: 1.5em;
  font-weight: bold;
}

.blog-description {
  font-size: 1.1em;
  color: #666;
}

.blog-stats {
  margin-top: 10px;
  font-size: 0.9em;
  color: #333;
}

.spacer {
  margin: 0 10px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 60%;
  max-width: 800px;
}

.modal-title {
  font-size: 2em;
  font-weight: bold;
}

.modal-description {
  font-size: 1.2em;
  margin-top: 15px;
  color: #333;
}

.modal-footer {
  margin-top: 20px;
  display: flex;
  gap: 20px;
}

.like-button, .favorite-button {
  padding: 10px 20px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1em;
  transition: background-color 0.3s ease;
}

.like-button {
  background-color: #4CAF50;
  color: white;
}

.favorite-button {
  background-color: #FF5722;
  color: white;
}

.like-button:hover {
  background-color: #45a049;
}

.favorite-button:hover {
  background-color: #e64a19;
}

.liked {
  background-color: #388E3C;
}

.favorited {
  background-color: #E64A19;
}

.close-btn {
  background-color: #FF4081;
  color: white;
  border: none;
  padding: 10px;
  margin-top: 20px;
  border-radius: 5px;
  cursor: pointer;
}

.close-btn:hover {
  background-color: #f50057;
}
.comment-box {
  margin-top: 20px;
}

.comment-input {
  width: 80%;
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

.comment-submit-btn {
  padding: 10px 15px;
  font-size: 1em;
  border: none;
  background-color: #4CAF50;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.comment-submit-btn:hover {
  background-color: #45a049;
}


  </style>
  