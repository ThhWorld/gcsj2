<template>
  <div class="login-container">
    <div class="login-box">
      <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px">
        <h3 class="login-title">欢迎登录</h3>
        <el-form-item label="账号" prop="account">
          <el-input type="text" placeholder="请输入账号" v-model="form.account" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" placeholder="请输入密码" v-model="form.password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('loginForm')">登录</el-button>
          <el-button type="primary" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
      <el-dialog
          title="温馨提示"
          :visible.sync="dialogVisible"
          width="30%"
          :before-close="handleClose"
      >
        <span>请输入账号和密码</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">确定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      form: {
        account: '',
        password: ''
      },
      // 表单验证， 需要在el-form-item元素中增加prop属性
      rules: {
        account: [{ required: true, message: '账号不可为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不可为空', trigger: 'blur' }]
      },
      // 对话框显示和隐藏
      dialogVisible: false
    }
  },
  methods: {
    handleClose() {
      console.log("Handle Close，空函数");
    },
    onSubmit(formName) {
      // 为表单绑定验证功能
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/user/login', {
            account: this.form.account,
            password: this.form.password
          })
              .then(response => {
                if (response.data.success) {
                  const user = response.data.user;
                  this.$router.push({
                    path: "/MainPage",
                    query: {
                      id: user.id,
                      account: user.account,
                      userName: user.userName,
                      phoneNumber: user.phoneNumber,
                      email: user.email,
                      sex: user.sex
                    }
                  });
                  this.$message.success("登录成功");
                } else {
                  this.$message.error(this.form.account + "登录失败");
                }
              })
              .catch(error => {
                console.error("登录时发生错误:", error);
                this.$message.error("登录失败");
              });
        } else {
          this.dialogVisible = true;
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('@/assets/logo.jpg'); /* 请确保图片路径正确 */
  background-size: cover;
}

.login-box {
  background-color: white;
  padding: 35px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 350px;
}

.login-title {
  font-size: 24px;
  color: #303133;
  margin-bottom: 30px;
}

.el-button {
  margin-right: 10px;
}

.el-dialog__header {
  background-color: #f5f5f5;
}

.el-dialog__footer {
  background-color: #f5f5f5;
}
</style>
