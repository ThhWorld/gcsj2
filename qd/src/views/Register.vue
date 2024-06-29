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
          <option value="male">Male</option>
          <option value="female">Female</option>
          <option value="other">Other</option>
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
        url: '/user/register',
        headers: {
          'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
        },
        data: {
          account: this.user.account,
          password: this.user.password,
          userName: this.user.userName,
          phoneNumber: this.user.phoneNumber,
          email: this.user.email,
          sex: this.user.sex
        }
      };

      axios(config)
          .then(response => {
            // 假设服务器返回一个状态字段status，当注册成功时为'success'
            if (response.data.status === 'success') {
              this.message = '注册成功';
            } else {
              this.message = '注册失败: ' + response.data.message;
            }
          })
          .catch(error => {
            console.log(error);
            this.message = '注册失败';
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