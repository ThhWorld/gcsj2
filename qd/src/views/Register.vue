<template>
  <div class="register-container">
    <div class="register-box">
      <h2>注册</h2>
      <form @submit.prevent="register">
        <div class="form-item">
          <label for="account">账号:</label>
          <input type="text" id="account" v-model="user.account" required />
        </div>
        <div class="form-item">
          <label for="password">密码:</label>
          <input type="password" id="password" v-model="user.password" required />
        </div>
        <div class="form-item">
          <label for="userName">用户名:</label>
          <input type="text" id="userName" v-model="user.userName" required />
        </div>
        <div class="form-item">
          <label for="phoneNumber">电话号码:</label>
          <input type="tel" id="phoneNumber" v-model="user.phoneNumber" required />
        </div>
        <div class="form-item">
          <label for="email">邮箱:</label>
          <input type="email" id="email" v-model="user.email" required />
        </div>
        <div class="form-item">
          <label for="sex">性别:</label>
          <select id="sex" v-model="user.sex" required>
            <option value="">请选择</option>
            <option value="0">男</option>
            <option value="1">女</option>
            <option value="2">其他</option>
          </select>
        </div>
        <button type="submit">注册</button>
      </form>
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script>
var axios = require('axios');

export default {
  data() {
    return {
      user: {
        account: '',
        password: '',
        userName: '',
        phoneNumber: '',
        email: '',
        sex: ''
      },
      message: ''
    };
  },
  methods: {
    register() {
      var config = {
        method: 'post',
        url: 'http://localhost:8081/user/register',
        headers: {
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({
          account: this.user.account,
          password: this.user.password,
          userName: this.user.userName,
          phoneNumber: this.user.phoneNumber,
          email: this.user.email,
          sex: parseInt(this.user.sex)  // 将性别转换为整数
        })
      };

      axios(config)
          .then(response => {
            if (response.data == true) {
              this.message = '注册成功';
              setTimeout(() => {
                this.$router.push({ path: '/login' });
              }, 2000); // 2秒后跳转到登录界面
            } else {
              this.message = '注册失败';
            }
          })
          .catch(error => {
            console.log("注册时发生错误:", error);
          });
    }
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('@/assets/logo.jpg'); /* 请确保图片路径正确 */
  background-size: cover;
}

.register-box {
  background-color: white;
  padding: 35px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 350px;
}

h2 {
  font-size: 24px;
  color: #303133;
  margin-bottom: 30px;
}

.form-item {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
}

input[type="text"],
input[type="password"],
input[type="tel"],
input[type="email"],
select {
  width: calc(100% - 20px);
  padding: 10px;
  margin: 0 auto;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

select {
  height: 40px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #5cb85c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #4cae4c;
}

p {
  color: #d9534f;
  text-align: center;
  margin-top: 10px;
}
</style>
