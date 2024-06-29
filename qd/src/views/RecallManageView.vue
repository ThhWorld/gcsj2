<template>
  <div>
    <h1>回收站管理</h1>
    <table>
      <thead>
      <tr>
        <th class="table-header">文件ID</th>
        <th class="table-header">文件名</th>
        <th class="table-header">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="file in files" :key="file.id">
        <td>{{ file.id }}</td>
        <td>{{ file.name }}</td>
        <td>
          <button @click="deleteFile(file.id)" class="delete-button">彻底删除</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      files: []
    };
  },
  created() {
    this.fetchFiles();
  },
  methods: {
    fetchFiles() {
      axios.get('/admin/bin')
          .then(response => {
            this.files = response.data;
          })
          .catch(error => {
            console.error('获取文件列表失败:', error);
          });
    },
    deleteFile(id) {
      if (confirm('确定要彻底删除此文件吗？')) {
        axios.delete(`/admin/deletefile?id=${id}`)
            .then(response => {
              if (response.data) {
                this.fetchFiles(); // 删除成功后刷新文件列表
              }
            })
            .catch(error => {
              console.error('彻底删除文件失败:', error);
            });
      }
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
}

.table-header {
  color: #333;
  font-weight: bold;
}

.delete-button {
  padding: 6px 10px;
  background-color: #f44336;
  color: white;
  border: none;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #d32f2f;
}
</style>
