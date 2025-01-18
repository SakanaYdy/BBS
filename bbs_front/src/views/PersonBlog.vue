<template>
  <NavBar/>
  <div>
      <h1>个人帖子</h1>

      <div class="blog-container">
          <!-- 遍历 blogList 渲染每个博客 -->
          <div v-for="(blog, index) in blogList" :key="index" class="blog-item">
          <div class="blog-content">
              <h2 class="blog-title">{{ blog.title }}</h2>
              <p class="blog-description">{{ blog.content }}</p>
               <!-- 显示点赞数和收藏数 -->
              <div class="blog-stats">
              <span class="likes">点赞：{{ blog.thumbNum }} </span>
              <span class="spacer"></span>  <!-- 添加一个空白间隔 -->
              <span class="favorites">收藏：{{ blog.favourNum }} </span>
              </div>
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
    NavBar // 注册NavBar组件
  },
  computed: {
      ...mapState(['currentUser']), 
    },
  data() {
  return {
    blogList: [], // 存储从后端获取的博客数据
  };
  },
  mounted() {
      // 页面加载完成后获取博客数据
      this.fetchBlogData();
  },
  methods: {
      // 根据用户获取博客数据的方法
      fetchBlogData() {
      axios.get('http://localhost:8101/api/post/get/vo',{
        params:{
          id: this.currentUser.id
        }
      }) 
          .then(response => {
              console.log(response)
          if (response.data.code == 0) {
              this.blogList = response.data.data;  // 将数据赋值给 blogList
              console.log(this.blogList)
          }
          })
          .catch(error => {
          console.error('获取博客数据失败', error);
          });
      },
  },
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


</style>
