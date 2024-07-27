<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-goods"></i> 拍品管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="商品ID">
                <el-input v-model="formInline.itemId" placeholder="请输入商品ID"></el-input>
            </el-form-item>
            <el-form-item label="商品名称">
                <el-input v-model="formInline.name" placeholder="请输入商品名称"></el-input>
            </el-form-item>
            <el-form-item label="卖家名称">
                <el-input v-model="formInline.sellername" placeholder="请输入商品名称"></el-input>
            </el-form-item>
            <el-form-item label="商品状态">
                <el-select v-model="formInline.status" placeholder="请选择商品状态">
                    <el-option label="不区分" value=""></el-option>
                    <el-option label="待审核" value="待审核"></el-option>
                    <el-option label="通过" value="通过"></el-option>
                    <el-option label="失败" value="失败"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" border show-header style="width: 100%">
            <el-table-column fixed type="index" label="序号" width="60"></el-table-column>
            <el-table-column prop="itemId" label="拍品ID" width="80"></el-table-column>
            <el-table-column prop="seller.username" label="卖家" width="100"></el-table-column>
            <el-table-column prop="name" label="拍品名"></el-table-column>
            <el-table-column prop="description" label="描述"></el-table-column>
            <el-table-column prop="category" label="分类"></el-table-column>
            <el-table-column prop="startingPrice" label="起拍价"></el-table-column>
            <el-table-column prop="listingDate" label="上传时间"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column prop="image" label="图片">
                <template v-slot="scope">
                    <img :src="scope.row.image" style="width: 100%; height: auto" />
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template v-slot="scope">
                    <el-button v-if="scope.row.status === '通过'" @click="openBindAuctionDialog(scope.row)" type="text" size="small">绑定拍卖会</el-button>
                    <el-button v-if="scope.row.status === '待审核'" @click="openAuditDialog(scope.row)" type="text" size="small"
                        >审核</el-button
                    >
                    <el-button @click="openDetailDialog(scope.row)" type="text" size="small">详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        ></el-pagination>
        <el-dialog title="绑定拍卖会" :visible.sync="bindAuctionDialogVisible">
    <el-form>
        <el-form-item label="拍卖会ID">
            <el-input v-model="auctionIdToBind" placeholder="请输入拍卖会ID"></el-input>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="bindAuctionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="bindItemToAuction(currentRow.itemId, auctionIdToBind)">绑定</el-button>
    </span>
</el-dialog>

        <!-- Audit Dialog -->
        <el-dialog title="审核操作" :visible.sync="auditDialogVisible" append-to-body>
            <div>确认审核操作：</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="auditDialogVisible = false">取消</el-button>
                <el-button @click="approve" type="success">通过</el-button>
                <el-button @click="reject" type="danger">不通过</el-button>
            </span>
        </el-dialog>
        <div>
            <!-- Existing code -->
            <el-dialog title="拍品详情" :visible.sync="detailDialogVisible" width="50%" append-to-body>
                <div>
                    <p><strong>商品ID:</strong> {{ detailData.itemId }}</p>
                    <p><strong>卖家ID:</strong> {{ detailData.seller.userId }}</p>
                    <p><strong>卖家用户名:</strong> {{ detailData.seller.username }}</p>
                    <p><strong>商品名称:</strong> {{ detailData.name }}</p>
                    <p><strong>描述:</strong> {{ detailData.description }}</p>
                    <p><strong>分类:</strong> {{ detailData.category }}</p>
                    <p><strong>起拍价:</strong> {{ detailData.startingPrice }}</p>
                    <p><strong>上传时间:</strong> {{ detailData.listingDate }}</p>
                    <p><strong>状态:</strong> {{ detailData.status }}</p>
                    <p><strong>当前价格:</strong> {{ detailData.nowPrice || '无' }}</p>
                    <img :src="detailData.image" alt="商品图片" style="max-width: 100%; height: auto" />
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import Axios from 'axios';

export default {
    data() {
        return {
            bindAuctionDialogVisible: false,
        auctionIdToBind: '',
        currentRow: null,
            detailDialogVisible: false,
            auditDialogVisible: false,
            currentRow: null,
            detailData: {
                itemId: '',
                seller: {
                    userId: '',
                    username: ''
                },
                name: '',
                description: '',
                category: '',
                startingPrice: 0,
                listingDate: '',
                status: '',
                imageUrl: '',
                nowPrice: null
            },
            currentPage: 1,
            pageSize: 10,
            total: 0,
            formInline: {
                itemId: '',
                name: '',
                status: '',
                sellername:''
            },
            tableData: []
        };
    },
    methods: {
        openBindAuctionDialog(row) {
        this.currentRow = row;
        this.bindAuctionDialogVisible = true;
    },
    bindItemToAuction(itemId, auctionId) {
        if (!auctionId) {
            this.$message.error('请输入拍卖会ID');
            return;
        }
        Axios.post('http://localhost:8080/addauit', {
            itemId: itemId,
            auctionId: auctionId
        })
        .then(response => {
            if (response.data.code === 1) {
                this.$message.success('商品成功绑定到拍卖会');
                this.bindAuctionDialogVisible = false;
                this.getdate(); // Optionally refresh the data in the table
            } else {
                this.$message.error('绑定失败: ' + response.data.msg);
            }
        })
       
    },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getdate();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.getdate();
        },
        onSubmit() {
            this.currentPage = 1;
            this.getdate();
        },
        async getdate() {
            try {
                const response = await Axios.get('http://localhost:8080/getitem', {
                    params: {
                        sellername:this.formInline.sellername,
                        itemId: this.formInline.itemId,
                        name: this.formInline.name,
                        status: this.formInline.status,
                        page: this.currentPage,
                        pageSize: this.pageSize
                    }
                });
                this.tableData = response.data.data.rows;
                this.total = response.data.data.total;
            } catch (error) {
                console.error('Error fetching data:', error);
                this.$message.error('获取数据失败，请重试');
            }
        },
        openAuditDialog(row) {
            this.currentRow = row;
            this.auditDialogVisible = true;
        },
        async approve() {
            console.log('Approving:', this.currentRow);
            try {
                const response = await Axios.post('http://localhost:8080/upitem', {
                    itemId: this.currentRow.itemId,
                    status: '通过' // Assuming 'listed' is the status for approved items
                });
                console.info(response);
                // Check if the API call was successful
                if (response.data.code === 1) {
                    this.$message.success('审核操作完成');
                } else {
                    this.$message.error('审核失败');
                }
            } catch (error) {
                console.error('Approval failed:', error);
                this.$message.error('审核过程中发生错误');
            }
            this.getdate(); // Refresh the data
            this.auditDialogVisible = false; // Close the dialog
        },

        async reject() {
            console.log('Rejecting:', this.currentRow);
    try {
        const response = await Axios.post('http://localhost:8080/upitem', {
            itemId: this.currentRow.itemId,
            status: '失败' // Assuming 'error' is the status for rejected items
        });
        console.info(response);
        // Check if the API call was successful
        if ( response.data.code === 1) {
            this.$message.success('审核已拒绝');
        } else {
            this.$message.error('拒绝失败');
        }
    } catch (error) {
        console.error('Rejection failed:', error);
        this.$message.error('拒绝过程中发生错误');
    }
    this.getdate(); // Refresh the data
    this.auditDialogVisible = false; // Close the dialog
        },

        openDetailDialog(row) {
            this.detailData = row; // Load the data into the detail view
            this.detailDialogVisible = true;
        }
    },
    created() {
        this.getdate();
    }
};
</script>

