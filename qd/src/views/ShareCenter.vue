<template>
  <div class="share-center">
    <h1>分享中心</h1>
    <div class="toolbar">
      <button @click="refreshFiles">刷新</button>
      <div v-if="selectedFiles.length > 0" class="selected-toolbar">
        <button @click="downloadFiles">下载</button>
        <button v-if="selectedFiles.every(file => file.useId == userId)" @click="unshareFiles">取消分享</button>
      </div>
    </div>
    <ul class="file-list">
      <li v-for="file in sharedFiles" :key="file.id">
        <input type="checkbox" :value="file" v-model="selectedFiles">
        <span>{{ file.fileName }} (上传时间: {{ file.uploadTime }}, 所属用户: {{ file.useId }})</span>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ShareCenter',
  data() {
    return {
      sharedFiles: [],
      selectedFiles: [],
      userId: null // 初始化用户ID
    };
  },
  created() {
    this.userId = this.$route.query.id; // 从路由参数中获取用户ID
    this.fetchSharedFiles();
  },
  watch: {
    '$route.query.id'(newId) {
      this.userId = newId;
      this.fetchSharedFiles(); // 当用户ID变化时重新获取文件列表
    }
  },
  methods: {
    async fetchSharedFiles() {
      try {
        const response = await axios.get('http://localhost:8081/user/sharedfile', {
          headers: {
            // Include headers if needed
          }
        });
        if (response.status === 200) {
          this.sharedFiles = response.data.map(file => ({
            id: file.id,
            fileName: file.fileName,
            uploadTime: file.uploadTime,
            useId: file.useId
          }));
        } else {
          alert('获取共享文件列表失败');
        }
      } catch (error) {
        console.error('获取共享文件列表时发生错误:', error);
        alert('获取共享文件列表失败');
      }
    },
    async downloadFiles() {
      if (this.selectedFiles.length === 1) {
        const fileId = this.selectedFiles[0].id;
        const fileName = this.selectedFiles[0].fileName;


        try {
          const response = await axios.get(`http://localhost:8081/user/Sharedownload?id=${fileId}`, {
            responseType: 'blob' // 确保接收到的是一个blob对象
          });

          // 创建一个URL，指向返回的文件数据
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', fileName);
          document.body.appendChild(link);
          link.click();
          link.remove();
        } catch (error) {
          console.error('下载文件时发生错误:', error);
          alert('文件下载失败');
        }
      } else {
        alert('请选择一个文件进行下载');
      }
    },
    async unshareFiles() {
      if (this.selectedFiles.length > 0) {
        const fileId = this.selectedFiles[0].id;
        try {
          const response = await axios.delete(`http://localhost:8081/user/unshare?id=${fileId}`, {
            headers: {
              // Include headers if needed
            }
          });
          if (response.data === true) {
            alert('取消分享成功');
            // 更新文件列表，移除已取消分享的文件
            this.sharedFiles = this.sharedFiles.filter(file => !this.selectedFiles.includes(file));
            this.selectedFiles = [];
          } else {
            alert('取消分享失败');
          }
        } catch (error) {
          console.error('取消分享文件时发生错误:', error);
          alert('取消分享失败');
        }
      } else {
        alert('请选择至少一个文件取消分享');
      }
    },
    refreshFiles() {
      this.fetchSharedFiles();
    }
  }
};
</script>

<style scoped>
.share-center {
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  color: #333;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.selected-toolbar button {
  margin-left: 10px;
}

.file-list {
  list-style-type: none;
  padding: 0;
}

.file-list li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.file-list li input[type="checkbox"] {
  margin-right: 10px;
}

.file-list li span {
  flex-grow: 1;
}
</style>
