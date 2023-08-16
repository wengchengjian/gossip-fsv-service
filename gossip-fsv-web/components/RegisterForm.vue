<template>
    <el-form :model="registerForm" label-width="80px">
        <el-form-item required label="用户名">
            <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item required label="密码">
            <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item required label="昵称">
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
            <el-button @click="registerViewclose">取消</el-button>
        </el-form-item>
    </el-form>
</template>


<script setup lang="ts">
import { FsvUser, useTokenStore, useUserStore } from '~/composables/store/user';
import { TokenInfo } from '~/types';
import { reactive } from 'vue'

const { useRegisterViewStore } = useStore();

const { registerViewFlag, registerViewOpen, registerViewclose } = useRegisterViewStore();
const { tokenInfo, setTokenInfo } = useTokenStore();
const { userInfo, setUserInfo } = useUserStore();

// do not use same name with ref
const registerForm = reactive({
    username: '',
    nickname: '',
    password: '',
    email: '',
    phone: '',
})


const onSubmit = async () => {
    useCustomFetch('/api/fsv/user/register', {
        method: 'POST',
        body: {
            ...registerForm
        }
    }).then(async (tokenResponse) => {
        let tokenInfo: TokenInfo = tokenResponse.result;

        setTokenInfo(tokenInfo);

        useCustomFetch("/api/fsv/user/getUserInfoBySelf", {
            method: 'GET',
        }).then(({ result }) => {
            let userInfo: FsvUser = result;

            setUserInfo(userInfo);
            ElMessage({
                message: "注册成功",
                type: 'success'
            });
            registerViewclose();
        })
    });
}

</script>