<template>
    <div>
      <NavBar />
  
      <div class="user-profile">
        <h2>用户信息</h2>
  
        <!-- 用户头像 -->
        <div class="avatar-section">
          <img :src="userAvatar" alt="用户头像" class="avatar" />
          <input type="file" @change="handleAvatarChange" accept="image/*" />
        </div>
  
        <!-- 用户信息表单 -->
        <el-form :model="userInfo" label-width="100px" class="user-form">
          <!-- 用户名 -->
          <el-form-item label="用户名">
            <el-input v-model="userInfo.username"></el-input>
          </el-form-item>
  
          <!-- 用户身份 -->
          <el-form-item label="用户身份">
            <el-input v-model="userInfo.userRole" disabled></el-input>
          </el-form-item>
  
          <!-- 用户简介 -->
          <el-form-item label="用户简介">
            <el-input type="textarea" v-model="userInfo.userProfile" rows="4"></el-input>
          </el-form-item>
  
          <!-- 保存按钮 -->
          <el-form-item>
            <el-button type="primary" @click="saveUserInfo">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { ElMessage } from 'element-plus';
  import NavBar from '@/components/NavBar.vue';
  import { mapState } from 'vuex';
  
  export default {
    name: 'UserProfile',
    components: {
      NavBar,
    },
    computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
    data() {
      return {
        userInfo: {
          username: '', // 用户名
          userProfile: '', // 用户简介
          userRole: '', // 用户身份
        },
        userAvatar: '', // 用户头像
      };
    },
    methods: {
      // 获取用户信息
      async fetchUserInfo() {
        try {
          const response = await axios.get('http://localhost:8101/api/user/get/vo', {
            params: {
              id: this.currentUser.id,
            },
          });
  
          if (response.data.code === 0) {
            const data = response.data.data;
            this.userInfo = {
              username: data.userName,
              userProfile: data.userProfile,
              userRole: data.userRole,
            };
            this.userAvatar = data.userAvatar || "https://photo-album-ydy.oss-cn-beijing.aliyuncs.com/089c5468-caa2-4cad-a6b2-a09a9e50989d.png"; // 默认头像
          } else {
            ElMessage.error('获取用户信息失败');
          }
        } catch (error) {
          console.error('获取用户信息失败', error);
          ElMessage.error('获取用户信息失败');
        }
      },
  
      // 保存用户信息
      async saveUserInfo() {
        try {
          const requestBody = {
            id: this.currentUser.id,
            userName: this.userInfo.username,
            userAvatar: this.userAvatar,
            userProfile: this.userInfo.userProfile,
          };
          const response = await axios.post('http://localhost:8101/api/user/update', requestBody);
          if (response.data.code === 0) {
            ElMessage.success('用户信息保存成功');
          } else {
            ElMessage.error('保存失败：' + response.data.message);
          }
        } catch (error) {
          console.error('保存用户信息失败', error);
          ElMessage.error('保存用户信息失败');
        }
      },
  
      // 处理头像上传
      handleAvatarChange(event) {
        const file = event.target.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = (e) => {
            this.userAvatar = e.target.result; // 更新头像预览
            this.uploadAvatar(file); // 上传头像
          };
          reader.readAsDataURL(file);
        }
      },
  
      // 上传头像到后端
      async uploadAvatar(file) {
        const formData = new FormData();
        formData.append('avatar', file);
  
        try {
          const response = await axios.post('http://localhost:8101/api/user/uploadAvatar', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          if (response.data.code === 0) {
            ElMessage.success('头像上传成功');
            this.userAvatar = response.data.data.avatarUrl; // 更新头像 URL
          } else {
            ElMessage.error('头像上传失败：' + response.data.message);
          }
        } catch (error) {
          console.error('头像上传失败', error);
          ElMessage.error('头像上传失败');
        }
      },
    },
    mounted() {
      this.fetchUserInfo();
    },
  };
  </script>
  
  
  <style scoped>
  .user-profile {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .avatar-section {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .avatar {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    object-fit: cover;
    margin-bottom: 10px;
  }
  
  .user-form {
    margin-top: 20px;
  }
  </style>