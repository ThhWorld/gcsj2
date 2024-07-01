<template>
  <div class="recycle-bin">
    <h1>回收站</h1>
    <div class="toolbar">
      <button @click="refreshDeletedFiles" class="btn">刷新</button>
      <div v-if="selectedFiles.length > 0" class="selected-toolbar">
        <button @click="restoreFiles" class="btn">还原</button>
        <button @click="permanentlyDeleteFiles" class="btn">彻底删除</button>
      </div>
    </div>
    <ul class="file-list">
      <li v-for="file in deletedFiles" :key="file.id">
        <input type="checkbox" :value="file" v-model="selectedFiles" class="checkbox">
        <span class="file-name">{{ file.fileName }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RecycleBin',
  data() {
    return {
      deletedFiles: [],
      selectedFiles: [],
      userId: this.$route.query.id // 接收从主页传递的用户ID
    };
  },
  created() {
    this.fetchDeletedFiles();
  },
  methods: {
    async fetchDeletedFiles() {
      try {
        const response = await axios.get(`http://localhost:8081/user/flie`, {
          params: {
            useid: this.userId
          }
        });
        if (response.status === 200) {
          this.deletedFiles = response.data.filter(file => file.isDeleted === 1 ).map(file => ({
            id: file.id,
            fileName: file.fileName,
          }));
        } else {
          alert('获取已删除文件列表失败');
        }
      } catch (error) {
        console.error('获取已删除文件列表时发生错误:', error);
        alert('获取已删除文件列表失败');
      }
    },
    async restoreFiles() {
      if (this.selectedFiles.length > 0) {
        const fileIds = this.selectedFiles.map(file => file.id).join(',');
        try {
          const response = await axios.patch(`http://localhost:8081/user/tobin?id=${fileIds}`);
          if (response.status === 200) {
            alert('文件已还原');
            this.deletedFiles = this.deletedFiles.filter(file => !this.selectedFiles.includes(file));
            this.selectedFiles = [];
          } else {
            alert('文件还原失败');
          }
        } catch (error) {
          console.error('还原文件时发生错误:', error);
          alert('文件还原失败');
        }
      } else {
        alert('请选择至少一个文件进行还原');
      }
    },
    async permanentlyDeleteFiles() {
      if (this.selectedFiles.length > 0) {
        const fileIds = this.selectedFiles.map(file => file.id);
        try {
          const response = await axios.delete(`http://localhost:8081/user/realdelete?id=${fileIds}`);
          if (response.status === 200) {
            alert('文件彻底删除成功');
            this.deletedFiles = this.deletedFiles.filter(file => !this.selectedFiles.includes(file));
            this.selectedFiles = [];
          } else {
            alert('文件彻底删除失败');
          }
        } catch (error) {
          console.error('彻底删除文件时发生错误:', error);
          alert('文件彻底删除失败');
        }
      } else {
        alert('请选择至少一个文件进行彻底删除');
      }
    },
    refreshDeletedFiles() {
      this.fetchDeletedFiles();
    }
  }
};
</script>

<style scoped>
.recycle-bin {
  padding: 20px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.btn {
  padding: 8px 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
}

.btn:hover {
  opacity: 0.8;
}

.selected-toolbar {
  display: flex;
  align-items: center;
}

.checkbox {
  margin-right: 10px;
}

.file-list {
  list-style-type: none;
  padding: 0;
}

.file-list li {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.file-name {
  margin-left: 10px;
}
</style>
