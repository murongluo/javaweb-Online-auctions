<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item> <i class="el-icon-goods"></i> 拍品管理 </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="拍卖ID">
                    <el-input v-model="formInline.auctioniteamId" placeholder="拍卖ID"></el-input>
                </el-form-item>
                <el-form-item label="拍品名">
                    <el-input v-model="formInline.name" placeholder="拍品名"></el-input>
                </el-form-item>
                <el-form-item label="物主">
                    <el-input v-model="formInline.sellername" placeholder="物主"></el-input>
                </el-form-item>
                <el-form-item label="承办方">
                    <el-input v-model="formInline.title" placeholder="承办方"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
            </el-form>
            <div>
                <el-table :data="tableData" border show-header:ture style="width: 100%">
                    <el-table-column fixed type="index" label="序号" width="60"> </el-table-column>
                    <el-table-column prop="auction.auctionId" label="拍卖ID" width="80"> </el-table-column>
                    <el-table-column prop="item.startingPrice" label="起拍价" width="100"> </el-table-column>
                    <el-table-column prop="auction.title" label="承办方"> </el-table-column>
                    <el-table-column prop="item.seller.username" label="物主"> </el-table-column>
                    <el-table-column prop="item.name" label="商品名"> </el-table-column>
            
                
                </el-table>
            </div>
            <div class="block">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalItems"
                >
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import Axios from 'axios';

export default {
    created() {
        this.getData();
    },
    methods: {
        handleSizeChange(val) {
            this.pageSize = val;
            this.getData();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.getData();
        },
        onSubmit() {
            console.log('submit!');
            this.currentPage = 1;  // 重置到第一页
            this.getData();
        },
        async getData() {
            try {
                const params = {
                    page: this.currentPage,
                    pageSize: this.pageSize,
                    auctionId: this.formInline.auctioniteamId,
                    auctionitemId: this.formInline.auctioniteamId,  // 确认这是否正确
                    name: this.formInline.name,
                    sellername: this.formInline.sellername,
                    title: this.formInline.title
                };
                const response = await Axios.get('http://localhost:8080/getauit', { params });
                console.log(response.data);
                this.tableData = response.data.data.rows;
                this.totalItems = response.data.data.total;
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        },
        handleClick(row) {
            console.log(row);
        }
    },
    data() {
        return {
            formInline: {
                auctioniteamId: '',
                name: '',
                sellername: '',
                title: '',
            },
            currentPage: 1,
            pageSize: 10,
            totalItems: 0,
            tableData: []
        };
    }
};
</script>


