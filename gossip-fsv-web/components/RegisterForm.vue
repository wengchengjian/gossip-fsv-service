<template>
    <el-form :model="registerForm" label-width="120px">
        <el-form-item label="用户名">
            <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
            <el-input v-model="registerForm.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
            <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
            <el-input v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button>取消</el-button>
        </el-form-item>
    </el-form>
</template>


<script setup lang="ts">
import { reactive } from 'vue'

const { useLoginViewStore } = useStore();

const { loginViewFlag, loginViewOpen, loginViewClose } = useLoginViewStore();


// do not use same name with ref
const registerForm = reactive({
    username: '',
    nickname: '',
    password: '',
    email: '',
    phone: '',
})


const onSubmit = () => {
    console.log('submit!')
}

</script>