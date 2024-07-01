<template>
  <div class="main-page">
    <nav class="sidebar">
      <div class="user-info">
        <p>用户名: {{ userName }}</p>
        <p>电话号码: {{ phoneNumber }}</p>
        <p>邮箱: {{ email }}</p>
        <p>ID: {{ id }}</p>
      </div>
      <button @click="navigateToRecycleBin">回收站</button>
      <button @click="navigateToShareCenter">前往分享中心</button>
      <button @click="logout">退出</button> <!-- 添加退出按钮 -->
    </nav>
    <div class="content">
      <div class="toolbar">
        <input type="file" ref="fileInput" @change="uploadFiles" style="display: none;">
        <button @click="triggerFileInput">上传</button>
        <button @click="refreshFiles">刷新</button> <!-- 添加刷新按钮 -->
        <div v-if="selectedFiles.length > 0" class="selected-toolbar">
          <button @click="downloadFiles">下载</button>
          <button @click="deleteFilesToBin">删除</button>
          <button v-if="selectedFiles.every(file => !file.shared)" @click="shareFiles">分享</button>
          <button v-if="selectedFiles.every(file => file.shared)" @click="unshareFiles">取消分享</button>
        </div>
      </div>
      <ul class="file-list">
        <li v-for="file in files" :key="file.id">
          <input type="checkbox" :value="file" v-model="selectedFiles">
          <span>{{ file.name }} (上传时间: {{ file.uploadTime }})</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'MainPage',
  data() {
    return {
      userName: '',
      phoneNumber: '',
      email: '',
      id: '',
      files: [],
      selectedFiles: []
    };
  },

  created() {
    const query = this.$route.query;
    this.userName = query.userName;
    this.phoneNumber = query.phoneNumber;
    this.email = query.email;
    this.id = query.id;

    this.fetchUploadedFiles();
  },

  methods: {
    refreshFiles() {
      this.fetchUploadedFiles();
    },
    async fetchUploadedFiles() {
      try {
        const response = await axios.get(`http://localhost:8081/user/flie`, {
          params: {
            useid: this.id
          },
          headers: {

          }
        });
        if (response.status === 200) {
          // 只显示未被删除的文件（isDeleted != 1）
          this.files = response.data.filter(file => file.isDeleted !== 1).map(file => ({
            id: file.id,
            name: file.fileName,
            uploadTime: file.uploadTime,
          }));
        } else {
          alert('获取文件列表失败');
        }
      } catch (error) {
        console.error('获取文件列表时发生错误:', error);
        alert('获取文件列表失败');
      }
    },
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    navigateToRecycleBin() {
      const userId = this.getUserId(); // 获取用户ID的方法
      this.$router.push({ name: 'RecycleBin', query: { id: this.id } });
    },
    navigateToShareCenter() {
      const userId = this.getUserId(); // 获取用户ID的方法
      this.$router.push({ name: 'ShareCenter', query: { id: this.id } });
    },
    getUserId() {
      // 返回当前用户ID的逻辑
      return this.id; // 示例用户ID
    },
    logout() {
      // 清除本地存储中的用户信息
      localStorage.removeItem('userToken');
      // 重定向到登录页面
      this.$router.push({ name: 'Login' });
    },
    async uploadFiles(event) {
      const files = event.target.files;
      if (files.length > 0) {
        const formData = new FormData();
        formData.append('file', files[0]);
        formData.append('useId', this.id);
        formData.append('filePath', 'C:\\gcsj\\personal\\'); // 指定上传路径

        try {
          const response = await axios.post('http://localhost:8081/user/fileupload', formData, {
            headers: {
              'User-Agent': 'Apifox/1.0.0 (https://apifox.com)',
              'Content-Type': 'multipart/form-data'
            }
          });
          if (response.status === 200) {
            alert('文件上传成功');
            // 在这里你可以更新文件列表
            const uploadedFile = {
              id: response.data.id,
              name: response.data.fileName,
              uploadTime: response.data.uploadTime,
              shared: false // Assuming the `shared` attribute is not provided by the backend
            };
            this.files.push(uploadedFile);
          } else {
            alert('文件上传失败');
          }
        } catch (error) {
          console.error('上传文件时发生错误:', error);
          alert('文件上传失败');
        }
      }
    },
    async downloadFiles() {
      if (this.selectedFiles.length === 1) {
        const fileId = this.selectedFiles[0].id;
        const fileName = this.selectedFiles[0].name;

        try {
          const response = await axios.get(`http://localhost:8081/user/download?id=${fileId}`, {
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
    async deleteFilesToBin() {
      if (this.selectedFiles.length > 0) {
        const fileIds = this.selectedFiles.map(file => file.id).join(',');
        try {
          const response = await axios.patch(`http://localhost:8081/user/tobin?id=${fileIds}`, {}, {
            headers: {
              'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
            }
          });
          if (response.status === 200) {
            alert('文件已移至回收站');
            // 更新文件列表，移除已删除的文件
            this.files = this.files.filter(file => !this.selectedFiles.includes(file));
            this.selectedFiles = [];
          } else {
            alert('文件删除失败');
          }
        } catch (error) {
          console.error('删除文件时发生错误:', error);
          alert('文件删除失败');
        }
      } else {
        alert('请选择至少一个文件进行删除');
      }
    },
    async shareFiles() {
      if (this.selectedFiles.length > 0) {
        const fileIds = this.selectedFiles.map(file => file.id).join(',');
        try {
          const response = await axios.put(`http://localhost:8081/user/share?id=${fileIds}`, {}, {
            headers: {

            }
          });
          if (response.data === true) {
            alert('文件分享成功');
            // 更新文件列表，将已分享的文件的 shared 属性设为 true
            this.files = this.files.map(file => {
              if (this.selectedFiles.includes(file)) {
                return { ...file, shared: true };
              }
              return file;
            });
            this.selectedFiles = [];
          } else {
            alert('文件分享失败');
          }
        } catch (error) {
          console.error('分享文件时发生错误:', error);
          alert('文件分享失败');
        }
      } else {
        alert('请选择至少一个文件进行分享');
      }
    },
    async unshareFiles() {
      if (this.selectedFiles.length > 0) {
        const fileIds = this.selectedFiles.map(file => file.id).join(',');
        try {
          const response = await axios.delete(`http://localhost:8081/user/unshare?id=${fileIds}`, {
            headers: {

            }
          });
          if (response.data === true) {
            alert('取消分享成功');
            // 更新文件列表，将已取消分享的文件的 shared 属性设为 false
            this.files = this.files.map(file => {
              if (this.selectedFiles.includes(file)) {
                return { ...file, shared: false };
              }
              return file;
            });
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
  }
};
</script>

<style scoped>
.main-page {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 250px;
  background-color: #3a3f51;
  color: white;
  padding: 20px;
}

.user-info p {
  margin: 10px 0;
}

button {
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 10px;
  border: none;
  background-color: #3a3f51;
  color: white;
  cursor: pointer;
  text-align: left;
}

button:hover {
  background-color: #575d6b;
}

.content {
  flex-grow: 1;
  padding: 20px;
}

.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.selected-toolbar {
  display: flex;
  gap: 10px;
}

.file-list {
  list-style-type: none;
  padding: 0;
}

.file-list li {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
}

.file-list li:hover {
  background-color: #f5f5f5;
}

.file-list input[type="checkbox"] {
  margin: 0;
}

</style>
