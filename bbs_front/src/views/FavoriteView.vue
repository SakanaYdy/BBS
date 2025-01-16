<template>
  <NavBar v-model:activeIndex2="activeIndex2" />
  <div>
    个人收藏页面
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'; // 引入NavBar组件
  
  export default {
    name: 'HomePage', // 组件名称
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