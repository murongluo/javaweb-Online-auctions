<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统</div>
            <el-form :model="param" :rules="rules" ref="loginForm" label-width="0px" class="ms-content">
                <el-form-item prop="userid">
                    <el-input v-model="param.userId" placeholder="userId">
                        <el-button slot="prepend" icon="el-icon-user"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="Password" v-model="param.password" @keyup.enter.native="submitForm">
                        <el-button slot="prepend" icon="el-icon-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            
            param: {
                userId: '',
                password: '',
            },
            rules: {
                userId: [{ required: true, message: '请输入用户id', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            }
        };
    },
    methods: {
        submitForm() {
            console.info(this.param)
            this.$refs.loginForm.validate(async valid => {
                if (valid) {
                    try {
                        const response = await axios.post('http://localhost:8080/adminlogin', this.param);
                        if (response.data.code === 1) {
                            this.$message.success('登录成功');
                            const userData = {
                                userId: response.data.data.userId,
                                userType: response.data.data.userType,
                                username: response.data.data.username,
                                token: response.data.data.token,
                                image: response.data.data.image,
                                lastLoginDate:response.data.data.lastLoginDate
                            };
                            localStorage.setItem('user', JSON.stringify(userData));
                            this.$router.push('/dashboard');  // 跳转到仪表板
                        } else {
                            this.$message.error(response.data.msg || '登录失败');  // 显示错误信息
                        }
                    } catch (error) {
                        this.$message.error('网络错误或服务器无响应');
                        console.error('Error on login:', error);
                    }
                } else {
                    this.$message.error('请输入有效的用户名和密码');
                }
            });
        }
    }
};
</script>


<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
