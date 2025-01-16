<template>
    <NavBar/>
    <div>
        个人博客
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