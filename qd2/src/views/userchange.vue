<template>
  <div>
    <h2>修改用户信息</h2>
    <div>
      <label for="account">账号：</label>
      <input type="text" id="account" v-model="account" @input="fetchUserInfo" />
    </div>
    <div v-if="user">
      <form @submit.prevent="submitForm">
        <label for="userName">用户名：</label>
        <input type="text" id="userName" v-model="user.userName" />
        <label for="phoneNumber">手机号码：</label>
        <input type="text" id="phoneNumber" v-model="user.phoneNumber" />
        <label for="email">邮箱：</label>
        <input type="text" id="email" v-model="user.email" />
        <button type="submit">保存修改</button>
      </form>
    </div>
    <div v-else>
      <p>用户不存在或正在加载...</p>
    </div>
    <div v-if="user">
      <!-- ... 用户信息表单 ... -->
      <button @click="showChangePassword = true">修改密码</button>
    </div>
    <div v-if="showChangePassword">
      <changepwd ref="changepwdComponent"></changepwd>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Changepwd from './changepwd.vue'; // 假设changepwd.vue组件在当前目录

export default {
  components: {
    Changepwd
  },
  data() {
    return {
      account: "",
      user: null,
      showChangePassword: false
    };
  },
  methods: {
    fetchUserInfo() {
      if (!this.account) return;

      // 查询用户信息
      axios.get(`/user/login?account=${this.account}`)
          .then((response) => {
            // 假设返回的数据格式为 { id, userName, phoneNumber, email }
            this.user = response.data;
          })
          .catch((error) => {
            console.log(error);
            this.user = null; // 如果请求失败，清空用户信息
          });
    },
    submitForm() {
      if (!this.user) return;

      // 发起 PUT 请求修改用户信息
      const config = {
        method: "put",
        url: "/user/userinforchange",
        headers: {
          "User-Agent": "Apifox/1.0.0 (https://apifox.com)",
        },
        data: {
          ...this.user
        },
      };

      axios(config)
          .then((response) => {
            console.log(JSON.stringify(response.data));
          })
          .catch((error) => {
            console.log(error);
          });
    },
    showChangePasswordDialog() {
      this.showChangePassword = true;
    }
  },
};
</script>