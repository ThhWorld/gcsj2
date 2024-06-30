<template>
  <div>
    <h2>修改密码</h2>
    <form @submit.prevent="changePassword">
      <div>
        <label for="originalPassword">原密码:</label>
        <input type="password" id="originalPassword" v-model="form.originalPassword" required>
      </div>
      <div>
        <label for="newPassword">新密码:</label>
        <input type="password" id="newPassword" v-model="form.newPassword" required>
      </div>
      <button type="submit">修改密码</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      form: {
        originalPassword: '',
        newPassword: ''
      },
      message: ''
    };
  },
  methods: {
    async changePassword() {
      try {
        // 首先调用登录接口验证原密码
        const loginResponse = await axios.post('/user/login', {
          account: this.$store.state.user.account, // 假设用户信息存储在Vuex中
          password: this.form.originalPassword
        });

        // 检查登录是否成功
        if (loginResponse.data && loginResponse.data.success) {
          // 原密码正确，调用修改密码接口
          const changePwdResponse = await axios({
            method: 'patch',
            url: '/user/changepwd',
            data: {
              id: this.$store.state.user.account, // 从Vuex获取用户账号
              password: this.form.newPassword
            },
            headers: {
              'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
            }
          });

          // 显示成功消息
          this.message = '密码修改成功';
        } else {
          // 原密码验证失败
          this.message = '原密码不正确';
        }
      } catch (error) {
        // 处理请求错误
        this.message = error.response && error.response.data && error.response.data.message || '请求失败';
      }
    }
  }
};
</script>

<style scoped>
/* 在这里添加样式 */
</style>