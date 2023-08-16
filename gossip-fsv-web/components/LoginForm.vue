<template>
    <el-form :model="loginForm" label-width="60px">
        <el-form-item label="用户名">
            <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="loginViewClose">取消</el-button>
        </el-form-item>
    </el-form>
</template>


<script setup lang="ts">
import { DEFAULT_TOKEN_NAME, DEFAULT_USER_INFO_NAME } from '~/const';
import { reactive } from 'vue'
import { TokenInfo } from '~/types'
import { useTokenStore, useUserStore } from '~/composables/store/user';
import { FsvUser } from '~/composables/store/user';
const { useLoginViewStore } = useStore();

const { loginViewFlag, loginViewOpen, loginViewClose } = useLoginViewStore();

const { tokenInfo, setTokenInfo } = useTokenStore();
const { userInfo, setUserInfo } = useUserStore();

// do not use same name with ref
const loginForm = reactive({
    username: '',
    password: '',
})


const onSubmit = async () => {
    let tokenResponse = await useCustomFetch('/api/fsv/user/doLogin', {
        method: 'GET',
        params: {
            ...loginForm
        }
    });
    let tokenInfo: TokenInfo = tokenResponse.result;

    setTokenInfo(tokenInfo);

    let { result } = await useCustomFetch("/api/fsv/user/getUserInfoBySelf", {
        method: 'GET',
    })

    let userInfo: FsvUser = result;

    setUserInfo(userInfo);
    ElMessage({
        message: "登陆成功",
        type: 'success'
    });
    loginViewClose();
}

</script>