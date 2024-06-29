<template>
  <div class="manage-view">
    <h1>Manage Users</h1>
    <div class="search-bar">
      <input v-model="searchQuery" placeholder="Search users..." />
      <button @click="searchUsers">Search</button>
    </div>
    <ul class="user-list">
      <li v-for="user in filteredUsers" :key="user.id" class="user-item">
        {{ user.name }}
        <button @click="deleteUser(user.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'ManageView',
  data() {
    return {
      users: [
        { id: 1, name: 'Alice' },
        { id: 2, name: 'Bob' },
        { id: 3, name: 'Charlie' },
        // 其他用户...
      ],
      searchQuery: ''
    }
  },
  computed: {
    filteredUsers() {
      return this.users.filter(user =>
          user.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      )
    }
  },
  methods: {
    searchUsers() {
      // 这里可以添加搜索逻辑，例如调用API
      console.log('Searching users with query:', this.searchQuery)
    },
    deleteUser(userId) {
      this.users = this.users.filter(user => user.id !== userId)
      console.log('User deleted:', userId)
    }
  }
}
</script>

<style scoped>
.manage-view {
  padding: 20px;
}

.manage-view h1 {
  color: green; /* 标题颜色改为绿色 */
}

.search-bar {
  margin-bottom: 20px;
}

.search-bar input {
  padding: 10px;
  width: 300px;
  margin-right: 10px;
}

.search-bar button {
  padding: 10px 20px;
}

.user-list {
  list-style: none;
  padding: 0;
}

.user-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ccc;
  color: white; /* 用户列表项颜色改为白色 */
}

.user-item button {
  padding: 5px 10px;
}
</style>
