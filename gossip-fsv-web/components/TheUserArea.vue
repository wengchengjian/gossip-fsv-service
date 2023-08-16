<template>
    <el-space wrap alignment="center">
        <template v-if="userStore != null && userStore != undefined && userStore.isLogin">
            <el-popover :auto-close="5000" placement="bottom" :width="200" trigger="click">
                <template #reference>
                    <el-avatar :size="50" :alt="userStore.user.nickname"
                        :src="userStore.user.avatar ?? DEFAULT_USER_AVATAR" />
                </template>
                <el-space alignment="normal" fill direction="vertical">
                    <el-button type="text">
                        <template #icon>
                            <el-icon>
                                <Setting />
                            </el-icon>
                        </template>
                        账号设置</el-button>
                    <el-button @click="handleExitAccount" type="text">
                        <template #icon>
                            <el-icon>
                                <SwitchButton />
                            </el-icon>
                        </template>
                        退出账号</el-button>
                </el-space>
            </el-popover>
        </template>
        <template v-else>
            <el-button type="primary" @click="openLoginView">登陆</el-button>
            <el-button type="warning" @click="openUserRegisterView">注册</el-button>
        </template>
    </el-space>
</template>

<script setup lang="ts">
import { Setting, SwitchButton } from "@element-plus/icons-vue";
import { DEFAULT_USER_AVATAR } from "~/const/index"
const { useUserStore, useLoginViewStore, useRegisterViewStore } = useStore();
let userStore = useUserStore();
const loginStore = useLoginViewStore();
const registerStore = useRegisterViewStore();
const openLoginView = () => {
    loginStore.loginViewOpen();
}

const openUserRegisterView = () => {
    registerStore.registerViewOpen();
}

const handleExitAccount = async () => {
    await userStore.exitAccount();
}

</script>