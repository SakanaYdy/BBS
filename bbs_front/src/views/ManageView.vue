<template>
  <div>
    <!-- 导航栏 -->
    <NavBar />

    <!-- 搜索框 -->
    <div class="search-container">
      <input type="text" class="search-input" placeholder="请输入搜索关键词" />
      <button class="search-btn">搜索</button>
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
            <span class="spacer"></span> <!-- 添加一个空白间隔 -->
            <span class="favorites">收藏：{{ blog.postVO.favourNum }}</span>
          </div>
          <!-- 删除按钮 -->
          <button class="delete-btn" @click.stop="openDeleteModal(blog.postVO.id)">删除</button>
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
            <button @click="submitComment(selectedBlog.postVO.id)" class="comment-submit-btn">提交</button>
          </div>

          <h4>之前的评论:</h4>
          <el-card v-for="(prevComment, index) in selectedBlog.comments" :key="index" class="comment-card">
            <div><strong>{{ prevComment.username }}:</strong> {{ prevComment.comment }}</div>
          </el-card>
        </div>

        <button class="close-btn" @click="closeModal">关闭</button>
      </div>
    </div>

    <!-- 删除原因弹窗 -->
    <div v-if="showDeleteModal" class="modal-overlay" @click.self="closeDeleteModal">
      <div class="modal-content delete-modal">
        <h2>删除博客</h2>
        <p>请输入删除原因：</p>
        <textarea v-model="deleteReason" placeholder="请输入删除原因..." class="delete-reason-input"></textarea>
        <div class="delete-modal-footer">
          <button @click="confirmDelete" class="confirm-delete-btn">确认删除</button>
          <button @click="closeDeleteModal" class="cancel-delete-btn">取消</button>
        </div>
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
      tagList: [], // 存储板块信息
      blogList: [], // 存储从后端获取的博客数据
      showModal: false, // 控制弹窗的显示与隐藏
      selectedBlog: null, // 存储点击的博客数据
      comments: [],  // 村村选中blog的评论
      showDeleteModal: false, // 控制删除弹窗的显示与隐藏
    };
  },
  mounted() {
    this.fetchLabels();
    this.fetchBlogData();
  },
  computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
  methods: {
    // 打开删除弹窗
  openDeleteModal(postId) {
    this.selectedPostId = postId; // 存储当前选中的博客 ID
    this.showDeleteModal = true; // 显示删除弹窗
  },

  // 关闭删除弹窗
  closeDeleteModal() {
    this.showDeleteModal = false;
    this.deleteReason = ''; // 清空删除原因
    this.selectedPostId = null; // 清空选中的博客 ID
  },
 // 确认删除
 confirmDelete() {
    if (!this.deleteReason.trim()) {
      alert('请输入删除原因');
      return;
    }

    const requestBody = {
      id: this.selectedPostId
    }

    axios.post(`http://localhost:8101/api/post/delete`, requestBody)
      .then(response => {
        if (response.data.code === 0) {
          // 删除成功后，从 blogList 中移除对应的博客
          this.blogList = this.blogList.filter(blog => blog.postVO.id !== this.selectedPostId);
          alert('博客删除成功');
        } else {
          alert('删除失败：' + response.data.message);
        }
      })
      .catch(error => {
        console.error('删除博客失败', error);
        alert('删除博客失败，请稍后重试');
      })
      .finally(() => {
        this.closeDeleteModal(); // 关闭删除弹窗
        this.fetchBlogData()
      });
      
  },
    fetchLabels() {
      try {
        axios.get('http://localhost:8101/api/user/get/labels') 
            .then(response => {
                console.log(response)
            if (response.data.code == 0) {
                this.tagList = response.data.data;  // 将数据赋值给 blogList
                console.log(this.tagList)
            }
            })
            .catch(error => {
            console.error('获取板块信息失败', error);
            });
      } catch (error) {
        console.error('获取标签失败', error);
      }
    },
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
            // console.log(response.data)
            // console.log(this.blogList)
            if (this.selectedBlog) {
              const updatedBlog = this.blogList.find(blog => blog.id === this.selectedBlog.id);
              if (updatedBlog) {
                this.selectedBlog = updatedBlog;  // 更新弹窗中的博客数据
              }
            }
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

  }
};
</script>
  
  <style scoped>

/* 删除弹窗样式 */
.delete-modal {
  max-width: 400px;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  text-align: center;
}

.delete-reason-input {
  width: 80%;
  height: 100px;
  padding: 10px;
  margin-top: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: none;
}

.delete-modal-footer {
  margin-top: 20px;
}

.confirm-delete-btn {
  background-color: #ff4d4f;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px;
}

.confirm-delete-btn:hover {
  background-color: #ff7875;
}

.cancel-delete-btn {
  background-color: #f0f0f0;
  color: #333;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-delete-btn:hover {
  background-color: #ddd;
}


  /* 搜索框容器 */
  /* 搜索框容器 */

.blog-item {
  position: relative;
  padding: 16px;
  border: 1px solid #ddd;
  margin-bottom: 16px;
  cursor: pointer;
}

.delete-btn {
  position: absolute;
  bottom: 10px;
  right: 10px;
  background-color: #ff4d4f;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #ff7875;
}
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
  