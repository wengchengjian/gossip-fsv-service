<template>
    <el-space wrap alignment="center">
        <template v-if="user != null && user != undefined">
            <el-avatar :size="50" :src="user.avatar ?? DEFAULT_USER_AVATAR" />
        </template>
        <template v-else>
            <el-button type="primary" @click="openLoginView">登陆</el-button>
            <el-button type="warning" @click="openUserRegisterView">注册</el-button>
        </template>
    </el-space>
</template>

<script setup lang="ts">
import { DEFAULT_USER_AVATAR } from "~/const/index"
const { useUserStore, useLoginViewStore, useRegisterViewStore } = useStore();
let user = useUserStore();
user = null;
const loginStore = useLoginViewStore();
const registerStore = useRegisterViewStore();
const openLoginView = () => {
    loginStore.loginViewOpen();
    console.log(loginStore.loginViewFlag)
}

const openUserRegisterView = () => {
    registerStore.registerViewOpen();
    console.log(registerStore.registerViewFlag)

}

</script>