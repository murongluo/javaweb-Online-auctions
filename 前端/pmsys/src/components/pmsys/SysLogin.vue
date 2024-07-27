<template>
    <div>
      <div
        class="container"
        :style="{
          minHeight: '100vh',
          alignItems: 'center',
          background:
            'url(http://codegen.caihongy.cn/20220804/122aadd2e96d4d5aa6a265bbd560a18d.gif)',
          display: 'flex',
          width: '100%',
          backgroundSize: 'cover',
          backgroundPosition: 'center center',
          backgroundRepeat: 'no-repeat',
          justifyContent: 'center',
        }"
      >
        <div class="wrapper">
          <div
            style="
              margin: 200px auto;
              width: 400px;
              height: 300px;
              padding: 20px;
              border-radius: 10px;
            "
          >
            <div style="margin: 20px 0; text-align: center; font-size: 24px">
              <b>登 录</b>
            </div>
            <el-form :model="user" :rules="rules" ref="userForm">
              <el-form-item prop="username">
                <el-input
                  placeholder="用户名Id"
                  size="medium"
                  style="margin: 10px 0"
                  prefix-icon="el-icon-user"
                  v-model="user.userId"
                >
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  placeholder="请输入密码"
                  size="medium"
                  style="margin: 10px 0"
                  prefix-icon="el-icon-lock"
                  show-password
                  v-model="user.password"
                ></el-input>
              </el-form-item>
              <div style="margin: 10px 0; text-align: right">
                <el-button
                  type="primary"
                  size="small"
                  autocomplete="off"
                  @click="login()"
                  >登录</el-button
                >
                <el-button
                  type="warning"
                  size="small"
                  autocomplete="off"
                  @click="$router.push('/sysregister')"
                  >注册</el-button
                >
                <el-button
                  type="warning"
                  size="small"
                  autocomplete="off"
                  @click="$router.push('/forgot')"
                  >忘记密码？</el-button
                >
            
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
  name:'syslogin',
    data() {
      return {
        data: [],
        user: {
          userId: "",
          password: "",
        },
        rules: {
          userId: [
            { required: true, message: "请输入用户名", trigger: "blur" },
            {
              min: 1,
              max: 15,
              message: "长度在 1 到 15 个字符",
              trigger: "blur",
            },
          ],
  
          password: [
            { required: true, message: "请输入密码", trigger: "blur" },
            {
              min: 1,
              max: 15,
              message: "长度在 1 到 15 个字符",
              trigger: "blur",
            },
          ],
        },
      };
    },
    methods: {
      async login() {
        console.info("login!");
        console.info(this.user);
        try {
          const response = await axios.post("http://localhost:8080/login", {
            userId: this.user.userId,
            password: this.user.password,
          });
          if(response.data.code==1){
            const userData = {
                                userId: response.data.data.userId,
                                userType: response.data.data.userType,
                                username: response.data.data.username,
                                token: response.data.data.token,
                                image: response.data.data.image,
                                lastLoginDate:response.data.data.lastLoginDate
                              
                            };
                            localStorage.setItem('user', JSON.stringify(userData));
          
            this.$message({
          message: '登录成功',
          type: 'success'
        });
       this.$router.push('/home');
        
          }
          console.log(response.data.data);
        } catch (error) {
          console.error(error);
        }
      },
    },
  };
  </script>
