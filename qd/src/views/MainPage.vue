<template>
  <div class="main-page">
    <nav class="sidebar">
      <p>用户名: {{ userName }}</p>
      <p>电话号码: {{ phoneNumber }}</p>
      <p>邮箱: {{ email }}</p>
      <p>ID: {{ id }}</p>
      <button @click="goToRecycleBin">回收站</button>
      <button @click="goToShareCenter">分享中心</button>
      <button @click="goToUserProfile">个人中心</button>
    </nav>
    <div class="content">
      <div class="toolbar">
        <button @click="createFolder">新建文件夹</button>
        <button @click="searchFiles">搜索</button>
        <button @click="uploadFiles">上传</button>
        <div v-if="selectedFiles.length > 0" class="selected-toolbar">
          <button @click="downloadFiles">下载</button>
          <button @click="deleteFiles">删除</button>
          <button v-if="selectedFiles.length === 1" @click="renameFile">重命名</button>
          <button v-if="selectedFiles.every(file => !file.shared)" @click="shareFiles">分享</button>
          <button v-if="selectedFiles.every(file => file.shared)" @click="unshareFiles">取消分享</button>
        </div>
      </div>
      <ul class="file-list">
        <li v-for="file in files" :key="file.id">
          <input type="checkbox" :value="file" v-model="selectedFiles">
          <span>{{ file.name }}</span>
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
      userName: '示例用户',
      phoneNumber: '123-456-7890',
      email: 'user@example.com',
      id: '123456',
      files: [
        { id: 1, name: '文件1.txt', shared: false },
        { id: 2, name: '文件2.txt', shared: true },
        // 其他文件示例
      ],
      selectedFiles: []
    };
  },
  methods: {
    goToRecycleBin() {
      this.$router.push({ name: 'RecycleBin' }); // 跳转到回收站页面
    },
    goToShareCenter() {
      this.$router.push({ name: 'ShareCenter' }); // 跳转到分享中心页面
    },
    goToUserProfile() {
      this.$router.push({ name: 'UserProfile' }); // 跳转到个人中心页面
    },
    async searchFiles() {
      try {
        const response = await axios.get(`http://localhost:8081/user/flie`, {
          params: {
            useid: this.id,
            keyword: this.searchKeyword // 传递搜索关键词参数
          },
          headers: {
            'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
          }
        });
        if (response.status === 200) {
          this.files = response.data.files; // 更新文件列表为搜索结果
        } else {
          alert('搜索失败');
        }
      } catch (error) {
        console.error('搜索文件时发生错误:', error);
        alert('搜索失败');
      }
    },
    async uploadFiles(event) {
      const files = event.target.files;
      if (files.length > 0) {
        const formData = new FormData();
        formData.append('file', files[0]);
        formData.append('userId', this.id);
        formData.append('filePath', '/uploads'); // 指定上传路径

        try {
          const response = await axios.post('http://localhost:8081/user/fileupload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });
          if (response.status === 200) {
            alert('文件上传成功');
            // 在这里你可以更新文件列表
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
        try {
          const response = await axios.get(`http://localhost:8081/user/download?id=${fileId}`, {
            responseType: 'blob' // 确保接收到的是一个blob对象
          });
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', this.selectedFiles[0].name);
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
    async deleteFiles() {
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
    renameFile() {
      if (this.selectedFiles.length === 1) {
        const newName = prompt('请输入新的文件名:', this.selectedFiles[0].name);
        if (newName && newName.trim() !== '') {
          const fileId = this.selectedFiles[0].id;
          axios.patch(`http://localhost:8081/user/rename?id=${fileId}&fileName=${newName}`, {}, {
            headers: {
              'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
            }
          })
              .then(response => {
                if (response.status === 200) {
                  alert('文件重命名成功');
                  this.selectedFiles[0].name = newName;
                  // 更新文件列表
                  const fileIndex = this.files.findIndex(file => file.id === fileId);
                  if (fileIndex !== -1) {
                    this.$set(this.files, fileIndex, { ...this.files[fileIndex], name: newName });
                  }
                } else {
                  alert('文件重命名失败');
                }
              })
              .catch(error => {
                console.error('重命名文件时发生错误:', error);
                alert('文件重命名失败');
              });
        }
      } else {
        alert('请选择一个文件进行重命名');
      }
    },
    async shareFiles() {
      if (this.selectedFiles.length > 0) {
        const fileIds = this.selectedFiles.map(file => file.id).join(',');
        try {
          const response = await axios.put(`http://localhost:8081/user/share?id=${fileIds}`, {}, {
            headers: {
              'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
            }
          });
          if (response.status === 200) {
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
              'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
            }
          });
          if (response.status === 200) {
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
    }
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
