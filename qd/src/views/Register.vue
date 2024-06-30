<template>
  <div>
    <h2>Register</h2>
    <form @submit.prevent="register">
      <div>
        <label for="account">Account:</label>
        <input type="text" id="account" v-model="user.account" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="user.password" required>
      </div>
      <div>
        <label for="userName">User Name:</label>
        <input type="text" id="userName" v-model="user.userName" required>
      </div>
      <div>
        <label for="phoneNumber">Phone Number:</label>
        <input type="tel" id="phoneNumber" v-model="user.phoneNumber" required>
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="user.email" required>
      </div>
      <div>
        <label for="sex">Sex:</label>
        <select id="sex" v-model="user.sex" required>
          <option value="">Please select one</option>
          <option value="0">Male</option>
          <option value="1">Female</option>
          <option value="2">Other</option>
        </select>
      </div>
      <button type="submit">Register</button>
    </form>
    <p>{{ message }}</p>
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
div {
  max-width: 400px;
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
input[type="password"],
input[type="tel"],
input[type="email"],
select {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

select {
  height: 34px; /* 设置select的高度与input保持一致 */
}

button {
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