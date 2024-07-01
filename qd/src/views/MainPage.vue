<template>
  <div class="main-page">
    <nav class="sidebar">
      <p>用户名: {{ userName }}</p>
      <p>电话号码: {{ phoneNumber }}</p>
      <p>邮箱: {{ email }}</p>
      <p>ID: {{ id }}</p>
      <button @click="goToRecycleBin">回收站</button>
      <button @click="goToShareCenter">分享中心</button>
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
      files: [
        { id: 1, name: '文件1.txt', shared: false },
        { id: 2, name: '文件2.txt', shared: true },
        // 其他文件示例
      ],
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
          this.files = response.data.map(file => ({
            id: file.id,
            name: file.fileName,
            uploadTime: file.uploadTime,
            shared: false // Assuming the `shared` attribute is not provided by the backend
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
    goToRecycleBin() {
      this.$router.push({ name: 'RecycleBin' }); // 跳转到回收站页面
    },
    goToShareCenter() {
      this.$router.push({ name: 'ShareCenter' }); // 跳转到分享中心页面
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
}

.sidebar {
  width: 200px;
  background-color: #f0f0f0;
  padding: 20px;
}

.sidebar p {
  margin: 10px 0;
}

.sidebar button {
  display: block;
  width: 100%;
  margin: 10px 0;
}

.content {
  flex: 1;
  padding: 20px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
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
</style>
