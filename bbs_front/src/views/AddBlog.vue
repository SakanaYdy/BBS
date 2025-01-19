<template>
  <NavBar/>
  <div class="blog-upload">
    <el-container>
      <el-header>
        <h2>博客上传</h2>
      </el-header>

      <el-main>
        <el-form :model="form" ref="formRef" label-width="100px" class="upload-form">
          <!-- 标题 -->
          <el-form-item label="标题" :rules="[{ required: true, message: '请输入标题', trigger: 'blur' }]">
            <el-input v-model="form.title" placeholder="请输入博客标题"></el-input>
          </el-form-item>

          <!-- 内容 -->
          <el-form-item label="内容" :rules="[{ required: true, message: '请输入博客内容', trigger: 'blur' }]">
            <el-input type="textarea" v-model="form.content" placeholder="请输入博客内容" rows="6"></el-input>
          </el-form-item>

          <!-- 标签 -->
          <el-form-item label="标签" :rules="[{ required: true, message: '请选择标签', trigger: 'change' }]">
            <el-select v-model="form.tags" placeholder="请选择标签" multiple>
              <el-option
                v-for="(label, index) in labels"
                :key="index"
                :label="label.labelName" 
                :value="label.labelName" 
              />
            </el-select>
          </el-form-item>

          <!-- 提交按钮 -->
          <el-form-item>
            <el-button type="primary" @click="handleSubmit">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElContainer, ElHeader, ElMain } from 'element-plus';
import NavBar from '@/components/NavBar.vue'; 
import axios from 'axios'; 

export default {
  name: 'BlogUpload',
  components: {
    ElForm,
    ElFormItem,
    ElInput,
    ElSelect,
    ElOption,
    ElButton,
    ElContainer,
    ElHeader,
    ElMain,
    NavBar
  },
  setup() {
    const form = ref({
      title: '',
      content: '',
      tags: [],
    });

    const formRef = ref(null);

    // 提交函数
    const handleSubmit = () => {
      formRef.value.validate((valid) => {
        if (valid) {
          const requestBody = {
            title: form.value.title,
            content: form.value.content,
            tags: form.value.tags
          };

          axios.post('http://localhost:8101/api/post/add', requestBody)
            .then(response => {
              if (response.data.code === 0) {
                console.log('博客提交成功', response.data);
                alert('帖子创建成功');
                resetForm(); // 提交成功后清空表单
              } else {
                console.error('提交失败：', response.data.message);
              }
            })
            .catch(error => {
              console.error('发送请求失败', error);
            });
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    };

    // 重置表单
    const resetForm = () => {
      console.log("重置表单")
      form.value.title = '';
      form.value.content = '';
      form.value.tags = [];
      formRef.value.resetFields();
    };

    return {
      form,
      formRef,
      handleSubmit,
      resetForm,
    };
  },
  data() {
    return {
      labels: [], // 存储从后端获取的标签数据
    };
  },
  methods: {
    // 获取标签列表
    fetchLabels() {
      try {
        axios.get('http://localhost:8101/api/user/get/labels') 
          .then(response => {
            console.log(response);
            if (response.data.code == 0) {
              this.labels = response.data.data;  // 将数据赋值给 labels
              console.log(this.labels);
            }
          })
          .catch(error => {
            console.error('获取博客数据失败', error);
          });
      } catch (error) {
        console.error('获取标签失败', error);
      }
    },
  },
  mounted() {
    // 组件挂载后获取标签
    this.fetchLabels();
  },
};
</script>
  
  <style scoped>
  .blog-upload {
    padding: 20px;
  }
  
  .upload-form {
    max-width: 600px;
    margin: 0 auto;
  }
  
  .el-header {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
  }
  </style>
  