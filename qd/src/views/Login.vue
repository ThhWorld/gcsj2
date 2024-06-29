<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="account">Account:</label>
        <input type="text" id="account" v-model="user.account" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="user.password" required>
      </div>
      <button type="submit">Login</button>
    </form>
    <p>{{ message }}</p>
    <!-- 添加注册按钮 -->
    <button @click="goToRegister">Register</button>
  </div>
</template>

<script>
import axios from 'axios';
import router from '@/router';

export default {
  data() {
    return {
      user: {
        account: '',
        password: ''
      },
      message: ''
    };
  },
  methods: {
    login() {
      // 登录方法代码保持不变...
    },
    // 添加方法来处理注册按钮的点击事件
    goToRegister() {
      // 使用Vue Router的push方法导航到注册页面
      router.push({ name: 'register' });
    }
  }
};
</script>
<style scoped>
div {
  max-width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  color: #555;
}

input[type="text"],
input[type="password"] {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box; /* 防止输入框大小超过父容器 */
}

button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

p {
  color: #d9534f;
  text-align: center;
}

button + button {
  margin-top: 10px;
}
</style>