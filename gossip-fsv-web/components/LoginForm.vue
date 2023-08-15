<template>
    <el-form :model="loginForm" label-width="120px">
        <el-form-item label="用户名">
            <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input v-model="loginForm.password" placeholder="请输入密码"></el-input>
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
const loginForm = reactive({
    username: '',
    password: '',
})


const onSubmit = () => {
    console.log('submit!')
}

</script>