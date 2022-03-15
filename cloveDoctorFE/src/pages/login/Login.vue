<template>
    <div>
        <img class="background" :style="screenState" src="../../assets/background.jpg" />
        <el-card class="centerCard">
            <div v-if="isLogin">
                <el-form
                    :ref="ruleFormRef"
                    label-position="Right"
                    label-width="auto"
                    :rules="loginRules"
                    :model="loginForm"
                    style="max-width: 460px"
                >
                    <el-form-item label="用户名" prop="name">
                        <el-input v-model="loginForm.name" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="loginForm.password" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="验证码" prop="checkCode">
                        <el-input v-model="loginForm.checkCode" />
                    </el-form-item>
                </el-form>
                <el-row class="btnGroup">
                    <el-button type="primary">注册</el-button>
                    <el-button type="success">登录</el-button>
                </el-row>
            </div>
            <div v-else>
                <el-form
                    :ref="ruleFormRef"
                    label-position="Right"
                    label-width="auto"
                    :rules="loginRules"
                    :model="loginForm"
                    style="max-width: 460px"
                >
                    <el-form-item label="用户名" prop="name">
                        <el-input v-model="loginForm.name" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="loginForm.password" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="重复密码" prop="passwordRepeat">
                        <el-input v-model="loginForm.passwordRepeat" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="验证码" prop="checkCode">
                        <el-input v-model="loginForm.checkCode" />
                    </el-form-item>
                </el-form>
                <el-row class="btnGroup">
                    <el-button type="primary">注册</el-button>
                </el-row>
            </div>
        </el-card>
    </div>
</template>

<script setup lang='ts'>
import { reactive, onMounted, ref } from 'vue';
import type { FormInstance } from 'element-plus'
// mounted
onMounted(() => {
    console.log('Component is mounted!')
    getCardWidth();
})

const isLogin = ref(false)

const screenState = reactive({
    width: "",
    height: "",
})

function getCardWidth() {
    screenState.height = window.screen.availHeight + "px";
    screenState.width = document.body.clientWidth + "px";
}

const loginForm = reactive({
    name: "",
    password: "",
    passwordRepeat: "",
    checkCode: "",
})
const ruleFormRef = ref<FormInstance>();
let validateRepeatPwd = (rule, value, callback) => {
    if (value !== loginForm.password) callback(new Error("密码不相同！"));
}
const loginRules = reactive({
    name: [
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
    checkCode: [
        { required: true, message: '请输入验证码', trigger: 'blur' },
    ],
})

</script>

<style>
.background {
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
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
