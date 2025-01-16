<template>
  <NavBar/>
  <h1>消息通知</h1>
      <el-card v-for="(notification, index) in notifications" :key="index" class="notification-card" @click="viewNotification(notification)">
        <div class="notification-content">
          <div class="notification-title">{{ notification.from }} {{ notifications.to }}</div>
          <span class="notification-time">{{ formatTime(notification.time) }}</span>
        </div>
        <p class="notification-message">{{ notification.notice }}</p>
      </el-card>
</template>

<script>
import NavBar from '@/components/NavBar.vue'; // 引入NavBar组件

import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
export default {
  name: 'NoticeView',
  components: {
      NavBar // 注册NavBar组件
    },
  data() {
    return {
      notifications: [],
      activeIndex2: '5', // 当前活动的菜单项
    };
  },
  computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
  mounted() {
  },
  methods: {
    viewNotification(notification) {
      // 跳转到通知详情页或执行相应操作
      console.log('查看通知:', notification);
      // 例如：this.$router.push(`/notification/${notification.id}`);
    },
    formatTime(time) {
      return time.toLocaleString();
    }
  }
}

</script>


<style scoped>

</style>

