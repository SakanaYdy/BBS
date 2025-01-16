<template>
    <div>
      <!-- 引入 NavBar 组件 -->
      <NavBar />
      <div>
        <!-- 页面内容区域 -->
        <h1>欢迎来到博客首页</h1>
        <p>这里展示博客内容。</p>
      </div>
    </div>
  </template>
  
  <script>
  import NavBar from '@/components/NavBar.vue'; // 引入NavBar组件
  
  export default {
    name: 'HomePage', // 组件名称
    components: {
      NavBar // 注册NavBar组件
    }
  };
  </script>
  
  <style scoped>
  /* 页面样式 */
  h1 {
    text-align: center;
    font-size: 2rem;
  }
  
  p {
    text-align: center;
    font-size: 1.2rem;
    color: #555;
  }
  </style>
  