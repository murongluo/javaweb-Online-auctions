<template>
    <div>
        <div
            class="container"
            :style="{
                minHeight: '100vh',
                alignItems: 'center',
                background: 'url(http://codegen.caihongy.cn/20220804/122aadd2e96d4d5aa6a265bbd560a18d.gif)',
                display: 'flex',
                width: '100%',
                backgroundSize: 'cover',
                backgroundPosition: 'center center',
                backgroundRepeat: 'no-repeat',
                justifyContent: 'center'
            }"
        >
            <div class="wrapper">
                <div style="margin: 150px auto; width: 400px; height: 450px; padding: 20px; border-radius: 10px">
                    <div style="margin: 20px 0; text-align: center; font-size: 24px">
                        <b>重置密码</b>
                    </div>
                    <el-form :model="user" :rules="rules" ref="userForm">
                        <el-form-item prop="email">
                            <el-input
                                placeholder="邮箱"
                                size="medium"
                                style="margin: 10px 0"
                                prefix-icon="el-icon-message"
                                v-model="user.email"
                            >
                            </el-input>
                        </el-form-item>

                        <el-form-item prop="userId">
                            <el-input
                                placeholder="用户Id"
                                size="medium"
                                style="margin: 10px 0"
                                prefix-icon="el-icon-user"
                                v-model="user.userId"
                            >
                            </el-input>
                        </el-form-item>
                        <div style="margin: 10px 0; text-align: right">
                            <el-button type="primary" size="small" autocomplete="off" @click="register()">提交</el-button>
                            <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/syslogin')"
                                >返回登录</el-button
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
    // eslint-disable-next-line vue/multi-word-component-names

    // ... rest of your component options
    data() {
        return {
            data: '',
            user: {
                userId: '',
                email: ''
            }
        };
    },
    methods: {
        async register() {
            this.$refs.userForm.validate((valid) => {
                if (valid) {
                    if (this.user.password !== this.user.confirmpassword) {
                        this.$message.error('两次密码不一致！');
                        return false;
                    }
                    console.info(this.user);
                    axios.post('http://localhost:8080/login/forgot', {
                            userId:this.user.userId,
                            email: this.user.email
                        })
                        .then((response) => {
                            // 假设响应数据中有一个字段 `success` 表示注册是否成功
                            if (response.data.code == 1) {
                                console.log('注册成功');
                                this.$message({
                                    message: '恭喜你，重置成功',
                                    type: 'success'
                                });
                                // 这里可以添加注册成功后的处理逻辑，比如跳转到登录页面或显示成功消息
                            } else {
                                console.log('注册失败');
                                this.$message.error('错了哦,email可能重复');
                                // 这里可以添加注册失败后的处理逻辑，比如显示错误消息
                            }
                        })
                        .catch((error) => {
                            console.error('注册过程中发生错误:', error);
                            // 这里可以添加错误处理逻辑，比如显示错误消息
                        });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    }
};
</script>
  
