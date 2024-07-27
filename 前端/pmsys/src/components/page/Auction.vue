<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-goods"></i> 拍品管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="拍卖会id">
                    <el-input v-model="formInline.auctionId" placeholder="拍卖会id"></el-input>
                </el-form-item>
                <el-form-item label="举办方">
                    <el-input v-model="formInline.description" placeholder="举办方"></el-input>
                </el-form-item>
                <el-form-item label="拍卖状态">
                    <el-select v-model="formInline.status" placeholder="状态">
                        <el-option label="不区分" value=""></el-option>
                        <el-option label="结束" value="结束"></el-option>
                        <el-option label="准备中" value="准备中"></el-option>
                        <el-option label="进行中" value="进行中"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="日期范围">
                    <el-date-picker
                        v-model="formInline.dateRange"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        align="right"
                        unlink-panels>
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                    <el-button type="primary" @click="openAddDialog">新增拍卖会</el-button>
                </el-form-item>
            </el-form>
            <div>
                <el-table :data="tableData" border show-header style="width: 100%">
                    <el-table-column fixed type="index" label="序号" width="60"></el-table-column>
                    <el-table-column prop="auctionId" label="拍卖会ID" width="80"></el-table-column>
                    <el-table-column prop="title" label="举办方" width="120"></el-table-column>
                    <el-table-column prop="startDate" label="开始时间"></el-table-column>
                    <el-table-column prop="endDate" label="结束时间"></el-table-column>
                    <el-table-column prop="status" label="状态"></el-table-column>
                    <el-table-column prop="description" label="描述">
                        <template slot-scope="scope">
                            <span>{{ scope.row.description }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="image" label="图片">
                        <template slot-scope="scope">
                            <img :src="scope.row.image" style="width: 100px; height: auto" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100">
                        <template slot-scope="scope">
                            <el-button @click="handleClick(scope.row.auctionId)" type="text" size="small">详情</el-button>
                            <el-button v-if="scope.row.status !== '结束'" @click="openEditDialog(scope.row)" type="text" size="small">编辑</el-button>
                            <el-button v-if="scope.row.status === '准备中'" @click="confirmDelete(scope.row.auctionId)" type="text" size="small" style="color: red;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <el-dialog title="新增拍卖会" :visible.sync="addDialogVisible">
                <el-form :model="addForm">
                    <el-form-item label="标题">
                        <el-input v-model="addForm.title"></el-input>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input type="textarea" v-model="addForm.description"></el-input>
                    </el-form-item>
                    <el-form-item label="开始日期">
                        <el-date-picker v-model="addForm.startDate" type="datetime"  value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束日期">
                        <el-date-picker v-model="addForm.endDate" type="datetime"  value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="图片URL">
                        <el-upload
                            class="upload-demo"
                            action="http://localhost:8080/upload"
                            name="image"
                            :on-success="handleUploadSuccess"
                            :on-error="handleUploadError"
                            list-type="picture-card">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitAddForm">提交</el-button>
                </span>
                
            </el-dialog>
            <!-- 拍卖商品详情对话框 -->
            <el-dialog title="拍卖商品详情" :visible.sync="itemDialogVisible" width="60%">
                <el-table :data="auctionItems" border >
                    <el-table-column prop="item.name" label="商品名称"></el-table-column>
                    <el-table-column prop="item.category" label="类别"></el-table-column>
                    <el-table-column prop="item.description" label="描述"></el-table-column>
                    <el-table-column prop="item.startingPrice" label="起拍价"></el-table-column>
                    <el-table-column prop="reservePrice" label="当前竞价"></el-table-column>
                    <el-table-column prop="soldPrice" label="售出价格"></el-table-column>
                    <el-table-column prop="saleStatus" label="销售状态"></el-table-column>
                    <el-table-column label="图片">
                        <template slot-scope="scope">
                            <img :src="scope.row.item.image" style="width: 100px; height: auto">
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button v-if="scope.row.saleStatus === '未出售'" @click="confirmCancelAuction(scope.row)" type="text" size="small" style="color: red;">取消拍卖</el-button>
            </template>
        </el-table-column>
                </el-table>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="itemDialogVisible = false">关闭</el-button>
                </span>
            </el-dialog>
            <el-dialog title="编辑拍卖会" :visible.sync="editDialogVisible">
    <el-form :model="editForm">
        <el-form-item label="标题">
            <el-input v-model="editForm.title"></el-input>
        </el-form-item>
        <el-form-item label="描述">
            <el-input type="textarea" v-model="editForm.description"></el-input>
        </el-form-item>
        <el-form-item label="开始日期">
            <el-date-picker v-model="editForm.startDate" type="datetime"  value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期">
            <el-date-picker v-model="editForm.endDate" type="datetime"  value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-form-item>
        <el-form-item label="图片URL">
            <el-upload
                class="upload-demo"
                action="http://localhost:8080/upload"
                name="image"
                :on-success="handleEditUploadSuccess"
                :on-error="handleUploadError"
                list-type="picture-card">
                <i class="el-icon-plus"></i>
                <img v-if="editForm.image" :src="editForm.image" style="width: 100px; height: auto">
            </el-upload>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEditForm">更新</el-button>
    </span>
</el-dialog>


        </div>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            editDialogVisible: false,
        editForm: {
            auctionId: '',
            title: '',
            description: '',
            startDate: '',
            endDate: '',
            image: '',
    
        },
            formInline: {
                auctionId: '',
                description: '',
                status: '',
                dateRange: null,
            },
            addDialogVisible: false,
            addForm: {
                title: '',
                description: '',
                startDate: '',
                endDate: '',
                image: ''
            },
            tableData: [],
            itemDialogVisible: false,
            auctionItems: '',
            selectedAuctionId: null,
            currentPage: 1,
            pageSize: 10,
            total: 0
        };
    },
    created() {
        this.getdate(); // Automatically fetch auction data when the page loads
    },
    methods: {
        confirmCancelAuction(row) {
        this.$confirm('确定要取消此拍卖吗?', '警告', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
           
            this.cancelAuction(row.auctionItemId);
        }).catch(() => {
            this.$message.info('取消操作');
        });
    },
    cancelAuction(itemId) {
        axios.delete(`http://localhost:8080/delauit/${itemId}`)
            .then(response => {
                if (response.data && response.data.code === 1) {
                    this.$message.success('拍卖已取消');
                    this.itemDialogVisible = false;
                } else {
                    this.$message.error('取消拍卖失败：' + (response.data.msg || '未知错误'));
                }
            })
           
    },
        onSubmit() {
            this.currentPage = 1;
            this.getdate();
        },
        getdate() {
            const params = {
                page: this.currentPage,
                pageSize: this.pageSize,
                auctionId: this.formInline.auctionId,
                title: this.formInline.description,
                status: this.formInline.status,
                startDate: this.formInline.dateRange ? this.formInline.dateRange[0] : undefined,
                endDate: this.formInline.dateRange ? this.formInline.dateRange[1] : undefined
            };
            axios.get('http://localhost:8080/getauction', { params })
                .then(response => {
                    this.tableData = response.data.data.rows;
                    this.total = response.data.data.total;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        },
        submitEditForm() {
        if (new Date(this.editForm.endDate) <= new Date(this.editForm.startDate)) {
            this.$message.error('结束时间必须晚于开始时间');
            return;
        }
        axios.post(`http://localhost:8080/upauction`, this.editForm)
            .then(() => {
                this.$message.success('拍卖会更新成功');
                this.editDialogVisible = false;
                this.getdate(); // Refresh the auction list
            })
        
    },
        openEditDialog(auction) {
        this.editForm = {...auction, startDate: auction.startDate.slice(0, 16), endDate: auction.endDate.slice(0, 16)};
        this.editDialogVisible = true;
    },
        openAddDialog() {
            this.addDialogVisible = true;
        },
        handleUploadSuccess(response) {
            this.addForm.image = response; // Assume response has url property
            console.info(response)
            this.$message.success('图片上传成功');
        },
        handleUploadError() {
            this.$message.error('图片上传失败');
        },
        handleEditUploadSuccess(response) {
        this.editForm.image = response; // Update the model with the new image URL
        this.$message.success('图片上传成功');
    },
    handleUploadError() {
        this.$message.error('图片上传失败');
    },
        submitAddForm() {
            if (new Date(this.addForm.endDate) <= new Date(this.addForm.startDate)) {
        this.$message.error('结束时间必须晚于开始时间');
        return; // 提前终止函数执行
    }
            axios.post('http://localhost:8080/addauction', this.addForm)
                .then(() => {
                    this.$message.success('拍卖会添加成功');
                    this.addDialogVisible = false;
                    this.getdate(); // Refresh the auction list
                })
                .catch(error => {
                    this.$message.error('添加拍卖会失败: ' + error.message);
                });
        },
        async handleClick(auctionId) {
            this.selectedAuctionId = auctionId;
            axios.get(`http://localhost:8080/getauit`,{
                params:{
                    auctionId:auctionId
                }
            })
                .then(response => {
                    if (response.data.code === 1&& response.data.data.total>0) {
                        console.info(response)
                        this.auctionItems = response.data.data.rows;
                        this.itemDialogVisible = true;
                        console.info(auctionItems);
                    } else {
                        this.$message.info('暂无商品');
                    }
                })
                
        },
      async  confirmDelete(auctionId) {
      this.$confirm('此操作将永久删除该拍卖会, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete(`http://localhost:8080/delaution/${auctionId}`)
        .then(response => {
        // 检查后端响应的code是否为1，表明操作成功
        if (response.data && response.data.code === 1) {
          this.$message.success('拍卖会已删除');
       
          this.getdate();
        } else {
          // 如果code不为1，或者没有正确的数据回调
          this.$message.error('删除拍卖会失败：' + (response.data.msg || '未知错误'));
        }
      });
  
  }).catch(() => {
    // 用户取消操作
    this.$message.info('取消删除');
      });
    },
  }
    
};
</script>

