<template>
    <div class="common-layout">
      <el-container>
        <el-header>
          <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect"
          >
            <el-menu-item index="1" @click="toHome">BBS首页</el-menu-item>
            <!-- <el-sub-menu index="2" v-if="currentUser.role == 'admin'"> -->
            <el-sub-menu index="2">
              <template #title>管理页面</template>
              <el-menu-item index="2-1" @click="goToManageLabel">板块管理</el-menu-item>
              <el-menu-item index="2-2" @click="goToManageAlbum">帖子管理</el-menu-item>
              <!-- <el-menu-item index="2-3" @click="goToManageUser">用户管理</el-menu-item> -->
            </el-sub-menu>
            <!-- <el-menu-item index="2" v-if="currentUser.role == 'admin'" @click="goToManageAlbum">相册管理</el-menu-item> -->
            <el-menu-item index="3" @click="goToPerson">个人博客</el-menu-item>
            <el-menu-item index="4" @click="logout">登出</el-menu-item>
            <el-menu-item index="5" v-if=false @click="goToNOtice">
              <el-icon><Bell />通知</el-icon>
            </el-menu-item>
            <el-menu-item index="6" @click="goToFav">收藏</el-menu-item>
            <el-menu-item index="7" @click="goToInfo">
              <div v-if="currentUser">{{ currentUser.userName }}</div>
              <!-- <div v-if="currentUser">{{ currentUser.role }}</div> -->
            </el-menu-item>

          <!-- 添加占位元素 -->
          <div class="spacer"></div>

          <!-- 右侧的“创建博客”按钮 -->
          <el-menu-item class="create-blog-item" index="8" @click="goToCreateBlog">
            创建博客
          </el-menu-item>
          </el-menu>
        </el-header>
  
      </el-container>

    </div>
  </template>
  
  <script>
  import { mapState } from 'vuex'; // 引入 Vuex 的 mapState

  export default {
    
    name: 'NavBar',
    computed: {
        ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
      },
    data() {
      return {
        albums: [], // 初始化为空数组
        activeIndex2: '1', // 当前活动的菜单项
        comment: '', // 存储当前输入的评论
        comments: [],  // 存储之前的评论
      };
    },
    
    mounted() {
      // this.fetchAlbums(); // 组件挂载后获取相册数据
      //this.fetchAlbumsWithLike();
      // console.log(this.albums)
    },
    methods: {
    handleSelect(index) {
        console.log("索引改变" + index)
        this.activeIndex2 = index
      this.$emit('update:activeIndex2', index);  // 触发事件将新的 activeIndex2 传递给父组件
      console.log("当前索引" + this.activeIndex2)
      
    },
    goToCreateBlog(){
        this.$router.push("/add")
    },
      goToManageLabel(){
        this.$router.push("/manageLabel")
      },
      goToManageUser(){
          this.$router.push("/manageUser")
      },
      goToInfo(){
          this.$router.push("/personalInfo")
      },  
      goToFav(){
        this.$router.push("/favorite")
      },
      goToManageAlbum(){
        this.$router.push("/manage")
      },
      goToNOtice(){
        this.$router.push("/notice")
      },
      goToPerson(){
        this.$router.push("/person")
      },
      logout(){
        this.$store.dispatch('logout'); // 登出之后清除用户信息
        this.$router.push("/login")
      },
      toHome() {
        this.$router.push("/main");
      },
      
    }
  }
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

.spacer {
  flex-grow: 0.9; /* 占位元素，推送右侧的“创建博客”按钮 */
}

.create-blog-item {
  background-color: #0c1b1b; /* 设置按钮背景颜色 */
  color: #fff;
  padding: 10px 20px;
  border-radius: 5px;
}

.create-blog-item:hover {
  background-color: #0c1b1b; /* 鼠标悬浮时的颜色 */
}


  </style>
  