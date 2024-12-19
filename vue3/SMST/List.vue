<template>
  <div class="list-container">
    <div class="search-section">
      <el-input
        v-model="searchQuery"
        placeholder="请输入搜索内容"
        class="search-input"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-button type="primary" class="search-btn">
        <el-icon><Search /></el-icon>
        搜索
      </el-button>
    </div>

    <div class="action-section">
      <el-button type="primary" class="add-btn">
        <el-icon><Plus /></el-icon>
        新增
      </el-button>
      <el-button type="success">
        <el-icon><Upload /></el-icon>
        导入
      </el-button>
      <el-button type="warning">
        <el-icon><Download /></el-icon>
        导出
      </el-button>
    </div>

    <el-card class="table-card">
      <el-table :data="tableData" style="width: 100%">
        <!-- 保持原有的表格列定义 -->
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Search, Plus, Upload, Download } from '@element-plus/icons-vue'

const searchQuery = ref('')
// 保持原有的数据和方法定义
</script>

<style scoped>
.list-container {
  padding: 20px;
}

.search-section {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.search-input {
  width: 300px;
}

.action-section {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.add-btn {
  background-color: #409EFF;
}

.table-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.el-button {
  transition: all 0.3s;
}

.el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

:deep(.el-input__wrapper) {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 2px 12px rgba(64, 158, 255, 0.2);
}

/* 表格样式优化 */
:deep(.el-table) {
  border-radius: 8px;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
}

:deep(.el-table--enable-row-hover .el-table__body tr:hover > td) {
  background-color: #f0f8ff;
}
</style> 