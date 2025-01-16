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
            <el-sub-menu index="2" v-if="currentUser.role == 'admin'">
              <template #title>管理页面</template>
              <el-menu-item index="2-1" @click="goToManageLabel">类别管理</el-menu-item>
              <el-menu-item index="2-2" @click="goToManageAlbum">相册管理</el-menu-item>
              <el-menu-item index="2-3" @click="goToManageUser">用户管理</el-menu-item>
            </el-sub-menu>
            <!-- <el-menu-item index="2" v-if="currentUser.role == 'admin'" @click="goToManageAlbum">相册管理</el-menu-item> -->
            <el-menu-item index="3" @click="goToPerson">个人博客</el-menu-item>
            <el-menu-item index="4" @click="logout">登出</el-menu-item>
            <el-menu-item index="5" @click="goToNOtice">
              <el-icon><Bell />通知</el-icon>
            </el-menu-item>
            <el-menu-item index="6" @click="goToFav">收藏</el-menu-item>
            <el-menu-item index="7" @click="goToInfo">
              <div v-if="currentUser">{{ currentUser.name }}</div>
              <!-- <div v-if="currentUser">{{ currentUser.role }}</div> -->
            </el-menu-item>
          </el-menu>
        </el-header>
  
        <div>
            博客首页
        </div>

      </el-container>
      
  
     <!-- 弹窗 -->
     <el-dialog
        v-model="dialogVisible"
        :title="currentAlbum.name"
        width="600"
        center
      >
        <div class="album-images">
          <img
            v-for="(image, index) in currentAlbum.images"
            :key="index"
            :src="image"
            alt="相册图片"
            class="album-image"
          />
        </div>
        
        <!-- 添加评论区域 -->
        <div class="comment-section">
          <el-input
            type="textarea"
            v-model="comment"
            placeholder="在此输入评论"
            rows="4"
            style="margin-top: 10px;"
          />
          <el-button type="primary" @click="submitComment">提交评论</el-button>
          
          <!-- 显示之前的评论 -->
          <div class="previous-comments" v-if="comments.length > 0" style="margin-top: 20px;">
            <h4>之前的评论:</h4>
            <el-card v-for="(prevComment, index) in comments" :key="index" class="comment-card">
              <div><strong>{{ prevComment.commenter }}:</strong> {{ prevComment.comment }}</div>
            </el-card>
          </div>
        </div>
        
        <input
          type="file"
          @change="handleImageUpload"
          accept="image/*"
          style="display: none;"
          ref="fileInput"
        />
        
        <template #footer>
          <div class="dialog-footer">
            <!-- <el-button @click="addImage" type="primary">添加图片</el-button> -->
            <el-button @click="dialogVisible = false">关闭</el-button>
          </div>
        </template>
      </el-dialog>
  
  
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
  
  export default {
    name: 'NavBar',
    computed: {
        ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
      },
    setup() {
      const dialogVisible = ref(false); // 对话框是否可见
      const currentAlbum = ref({ name: '', images: [] ,owner: '', thumbs: 0,favorites: 0,recommends: 0,like : false,favorite : false,recommend : false}); // 当前相册，初始包含 name 属性
     
  
      const album1 = ref([]);
      const createAlbumVisible = ref(false); // 控制弹窗显示
      const newAlbum = ref({ name: '' }); // 新相册数据
  
  
      const showCreateAlbumDialog = () => {
        createAlbumVisible.value = true; // 显示弹窗
      };
  
      const coverImage = ref(null); // 定义封面图片变量
  
      return {
        album1,
        newAlbum,
        dialogVisible,
        currentAlbum,
        createAlbumVisible,
        showCreateAlbumDialog,
        coverImage
      };
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
      this.fetchAlbumsWithLike();
      console.log(this.albums)
    },
    methods: {
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
        this.$router.push("/")
      },
      toHome() {
        this.$router.push("/home");
      },
      
    }
  }
  </script>
  
  <style scoped>
  </style>
  