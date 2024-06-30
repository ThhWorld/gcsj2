<template>
  <div>
    <h1>分享文件管理</h1>
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
          <button @click="toBin(file.id)" class="delete-button">放入回收站</button>
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
      axios.get('/admin/file')
          .then(response => {
            this.files = response.data;
          })
          .catch(error => {
            console.error('获取文件列表失败:', error);
          });
    },
    toBin(id) {
      if (confirm('确定要将此文件放入回收站吗？')) {
        axios.patch(`/admin/delete?id=${id}`)
            .then(response => {
              if (response.data) {
                this.fetchFiles(); // 操作成功后刷新文件列表
              }
            })
            .catch(error => {
              console.error('放入回收站失败:', error);
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
