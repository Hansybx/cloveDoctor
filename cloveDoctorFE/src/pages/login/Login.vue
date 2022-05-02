<template>
    <div>
        <img class="background" src="../../assets/background.jpg" />
        <el-card class="centerCard">
            <div v-if="isLogin">
                <el-form ref="ruleFormRef" label-position="Right" label-width="auto" :rules="loginRules"
                    :model="loginForm" style="max-width: 460px">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="loginForm.username" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="loginForm.password" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="验证码" prop="captcha">
                        <div class="captcha-container">
                            <el-input v-model="loginForm.captcha" />
                            <el-image :src="imgUrl" @click="capthcaRefresh()" class="captcha-img" fit="contain" />
                        </div>
                    </el-form-item>
                </el-form>
                <el-row class="btnGroup">
                    <el-button type="primary" @click="toRegister()">注册</el-button>
                    <el-button type="success" @click="login(ruleFormRef)">登录</el-button>
                </el-row>
            </div>
            <div v-else>
                <el-form ref="ruleFormRef" label-position="Right" label-width="auto" :rules="loginRules"
                    :model="loginForm" style="max-width: 460px">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="loginForm.username" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="loginForm.password" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="重复密码" prop="passwordRepeat">
                        <el-input v-model="loginForm.passwordRepeat" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="验证码" prop="captcha">
                        <div class="captcha-container">
                            <el-input v-model="loginForm.captcha" />
                            <el-image :src="imgUrl" @click="capthcaRefresh()" class="captcha-img" fit="contain" />
                        </div>
                    </el-form-item>
                </el-form>
                <el-row class="btnGroup">
                    <el-button type="primary" @click="register(ruleFormRef)">注册</el-button>
                    <el-button type="success" @click="toLogin()">返回登录</el-button>
                </el-row>
            </div>
        </el-card>
    </div>
</template>

<script setup lang='ts'>
import { reactive, onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
import type { FormInstance } from 'element-plus'
import axios from 'axios'
import Constant from '../../common/config';
import router from '../../router/router';
import { useUserStore } from '../../stores/UserInfo';
import CommonUtils from '../../common/commonUtils';

// mounted
onMounted(() => {
    console.log('Component is mounted!')
})

const user = useUserStore();
const imgUrl = ref(Constant.BASE_URL_USER + '/captcha');
const capthcaRefresh = () => {
    imgUrl.value += "?time=" + new Date().getTime();
}

const isLogin = ref(true);
const toRegister = () => {
    isLogin.value = false;
    loginForm.registerDate = CommonUtils.dateFormat(new Date());
}

const toLogin = () => {
    isLogin.value = true;
}


const loginForm = reactive({
    username: "",
    password: "",
    passwordRepeat: "",
    captcha: "",
    registerDate: ""
})
const ruleFormRef = ref<FormInstance>();
let validateRepeatPwd = (rule: any, value: string, callback: any) => {
    if (value !== loginForm.password) callback(new Error("密码不相同！"));
    callback();
}
const loginRules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 16, message: '密码长度应在6-16位之间', trigger: 'blur' },
    ],
    passwordRepeat: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { validator: validateRepeatPwd, trigger: 'blur' },
    ],
    captcha: [
        { required: true, message: '请输入验证码', trigger: 'blur' },
    ],
})

const register = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
        ElMessage.error('请符合填写规则！')
        return
    }
    await formEl.validate((valid: any, fields: any) => {
        if (valid) {
            console.log('submit!')
            loginFormPost('/register');
            formEl.resetFields()
        } else {
            ElMessage.error('请符合填写规则！')
            console.log('error submit!', fields)
        }
    })
}

const login = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
        ElMessage.error('请符合填写规则！')
        return
    }
    await formEl.validate((valid: any, fields: any) => {
        if (valid) {
            console.log('submit!')
            loginFormPost('/login');
            formEl.resetFields()
        } else {
            ElMessage.error('请符合填写规则！')
            console.log('error submit!', fields)
        }
    })
}


const loginFormPost = (uri: string) => {
    console.log(loginForm)
    axios.post(Constant.BASE_URL_USER + uri, loginForm).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            router.replace({ path: "/home" });
            user.loginSuccess(res.data.data)
            localStorage.setItem('login',res.data.data)
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，刚刚失败了！')
        }
    })
}

</script>

<style scoped>
.background {
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
    width: 100vw;
    height: 99vh;
}

.captcha-container {
    display: flex;
}

.captcha-img {
    width: 90px;
}

.centerCard {
    background: hsla(0, 0%, 100%, 0.75);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -60%);
}

.btnGroup {
    display: flex;
    justify-content: center;
}
</style>
