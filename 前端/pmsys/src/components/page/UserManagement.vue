<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-user"></i> 用户管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="用户Id">
                    <el-input v-model="formInline.userId" placeholder="请输入用户Id"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="formInline.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户类型">
                    <el-select v-model="formInline.userType" placeholder="选择用户类型">
                        <el-option label="不区分" value=""></el-option>
                        <el-option label="管理员" value="管理员"></el-option>
                        <el-option label="用户" value="用户"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="fetchUsers">查询</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="tableData" border :key="keys" show-header style="width: 100%">
                <el-table-column fixed type="index" label="序号" width="60"></el-table-column>
                <el-table-column prop="userId" label="用户ID" width="80"></el-table-column>
                <el-table-column prop="username" label="姓名" width="100"></el-table-column>
                <el-table-column prop="password" label="密码" width="150"></el-table-column>
                <el-table-column prop="image" label="图片" width="150">
                    <template v-slot="scope">
                        <img :src="scope.row.image" style="width: 100%; height: auto" />
                    </template>
                </el-table-column>
                <el-table-column prop="email" label="邮箱" width="160"></el-table-column>
                <el-table-column prop="address" label="地址" width="300"></el-table-column>
                <el-table-column prop="userType" label="类型" width="120"></el-table-column>
                <el-table-column prop="createDate" label="注册时间" width="120"></el-table-column>
                <el-table-column prop="lastLoginDate" label="最后登录" width="120"></el-table-column>
                <el-table-column fixed label="操作" width="200">
                    <template v-slot="scope">
                        <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="text" size="small" @click="handleDelete(scope.row.userId)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog title="编辑用户信息" :visible.sync="dialogFormVisible">
                <el-form :model="detail">
                    <el-form-item label="用户ID" :label-width="formLabelWidth">
                        <el-input v-model="detail.userId" autocomplete="off" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="用户名" :label-width="formLabelWidth">
                        <el-input v-model="detail.username" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="用户类型" :label-width="formLabelWidth">
                        <el-select v-model="detail.userType" placeholder="请选择用户类型">
                            <el-option label="管理员" value="管理员"></el-option>
                            <el-option label="用户" value="用户"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateUser">确认</el-button>
                </div>
            </el-dialog>
        </div>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalItems">
        </el-pagination>
    </div>
</template>

<script>
import Axios from 'axios';

export default {
    data() {
        return {
            formInline: {
                userId: '',
                username: '',
                userType: ''
            },
            tableData: [],
            dialogFormVisible: false,
            detail: {},
            keys: 0,
            currentPage: 1,
            pageSize: 100,
            totalItems: 0,
            formLabelWidth: '120px'
        };
    },
    created() {
        this.fetchUsers();
    },
    methods: {
        async fetchUsers() {
            try {
                const response = await Axios.get('http://localhost:8080/getusers', {
                    params: {
                        page: this.currentPage,
                        pageSize: this.pageSize,
                        userId: this.formInline.userId,
                        username: this.formInline.username,
                        userType: this.formInline.userType
                    }
                });
                
                this.tableData = response.data.data.rows;
                this.totalItems = response.data.data.total;
                console.info(this.formInline)
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.fetchUsers();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.fetchUsers();
        },
        handleEdit(row) {
            this.dialogFormVisible = true;
            this.detail = { ...row };
        },
        async handleDelete(userId) {
            try {
                await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                });
                await Axios.delete(`http://localhost:8080/deluser/${userId}`);
                this.$message.success('删除成功!');
                this.fetchUsers();
            } catch (error) {
                if (error.response) {
                    this.$message.error('删除失败');
                } else {
                    this.$message.info('已取消删除');
                }
            }
        },
        async updateUser() {
            try {
                const response = await Axios.post('http://localhost:8080/upuser', {
                    userId: this.detail.userId,
                    username: this.detail.username,
                    userType: this.detail.userType
                });
                this.dialogFormVisible = false;
                this.fetchUsers();
                if (response.data.code === 1) {
                    this.$message.success('修改成功');
                } else {
                    this.$message.error('修改失败');
                }
            } catch (error) {
                console.error('Update failed:', error);
                this.$message.error('修改失败');
            }
        }
    }
};
</script>

