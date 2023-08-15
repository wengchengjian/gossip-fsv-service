<template>
    <client-only>
        <Teleport to="body">
            <el-dialog v-if="loginStore.loginViewFlag" v-model="loginStore.loginViewFlag" title="Fsv登陆" width="30%"
                :before-close="loginStore.loginViewClose">
                <LoginForm />
            </el-dialog>
            <el-dialog v-if="registerStore.registerViewFlag" v-model="registerStore.registerViewFlag" title="Fsv注册"
                width="30%" :before-close="registerStore.registerViewclose">
                <RegisterForm />
            </el-dialog>
        </Teleport>
    </client-only>
</template>

<script setup lang="ts">

const { useLoginViewStore, useRegisterViewStore } = useStore();

const loginStore = useLoginViewStore();
const registerStore = useRegisterViewStore();


</script>