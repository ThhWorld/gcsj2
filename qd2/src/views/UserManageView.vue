<template>
  <div>
    <h1>用户管理</h1>
    <table>
      <thead>
      <tr>
        <th class="table-header">用户ID</th>
        <th class="table-header">用户名</th>
        <th class="table-header">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.name }}</td>
        <td>
          <button @click="deleteUser(user.id)" class="delete-button">删除</button>
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
      users: []
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      axios.get('/admin/user')
          .then(response => {
            this.users = response.data;
          })
          .catch(error => {
            console.error('获取用户列表失败:', error);
          });
    },
    deleteUser(id) {
      if (confirm('确定要删除此用户吗？')) {
        axios.delete(`/admin/deleteuser?id=${id}`)
            .then(response => {
              if (response.data) {
                this.fetchUsers(); // 删除成功后刷新用户列表
              }
            })
            .catch(error => {
              console.error('删除用户失败:', error);
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
