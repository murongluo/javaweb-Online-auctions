<template>
    <div>
      <el-upload
        class="upload-demo"
        name="image"
        action="http://localhost:8080/upload"
        :on-success="handleSuccess"
        :on-error="handleError"
        list-type="picture-card">
        <i class="el-icon-plus"></i>
      </el-upload>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      handleSuccess(response, file, fileList) {
        console.log('上传成功，服务器返回:', response);
        this.$message.success('图片上传成功');
      },
      handleError(err, file, fileList) {
        console.log('上传失败:', err);
        this.$message.error('图片上传失败');
      }
    }
  };
  </script>
  
