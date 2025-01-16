<template>
  <!-- <NavBar v-model:activeIndex2="activeIndex2" :index = "activeIndex2" /> -->

  <NavBar/>

  <div>
    管理帖子页面
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'; // 引入NavBar组件
  
  export default {
    name: 'ManageView', // 组件名称
    components: {
      NavBar // 注册NavBar组件
    },
    data() {
    return {
      activeIndex2: '6',  // 默认活动菜单项
    };
  },
  };
</script>

<style scoped>

</style>