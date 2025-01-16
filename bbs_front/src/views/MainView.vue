<template>
    <NavBar/>
    <div>
        <h1>博客首页</h1>

        <!-- 搜索框 -->
        <div class="search-container">
            <input type="text" class="search-input" placeholder="请输入搜索关键词">
            <button class="search-btn">搜索</button>
        </div>

        <!-- 标签 -->
        <div class="tags-container">
            <span class="tag">Java</span>
            <span class="tag">机器学习</span>
            <span class="tag">深度学习</span>
            <span class="tag">Python</span>
            <span class="tag">前端</span>
        </div>

        <!-- 博客显示区域 -->
        <div class="blog-container">
            <div class="blog-item">
            <div class="blog-cover">
                <img src="https://via.placeholder.com/150" alt="封面" />
            </div>
            <div class="blog-content">
                <h2 class="blog-title">博客标题 1</h2>
                <p class="blog-description">这是博客的简短内容，展示部分内容，可以点击阅读全文。</p>
            </div>
            </div>

            <div class="blog-item">
            <div class="blog-cover">
                <img src="https://via.placeholder.com/150" alt="封面" />
            </div>
            <div class="blog-content">
                <h2 class="blog-title">博客标题 2</h2>
                <p class="blog-description">这是博客的简短内容，展示部分内容，可以点击阅读全文。</p>
            </div>
            </div>

            <div class="blog-item">
            <div class="blog-cover">
                <img src="https://via.placeholder.com/150" alt="封面" />
            </div>
            <div class="blog-content">
                <h2 class="blog-title">博客标题 3</h2>
                <p class="blog-description">这是博客的简短内容，展示部分内容，可以点击阅读全文。</p>
            </div>
            </div>
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


  </style>
  