import { defineStore } from "pinia";

export type FsvUser = {
    username: string;
    nickname: string;
    avatar?: string;
    email?: string;
    phone?: string;
    roles: string[];
    permissions: string[];
}

export const useUserStore = defineStore("userStore", () => {
    const user: FsvUser = {
        username: "wengchengjian",
        nickname: "weng",
        roles: ['admin'],
        avatar: 'https://avatars.githubusercontent.com/u/6481596?v=4&size=64',
        permissions: ['user.view', 'user.edit', 'user.delete', 'user.create', 'audit.view']
    };
    // 添加默认头像
    if (!user.avatar) {
        user.avatar = 'https://avatars.githubusercontent.com/u/6481596?v=4&size=64'
    }
    return user;
});

export const useLoginViewStore = defineStore("loginViewStore", () => {
    let loginViewFlag = ref(false);

    const loginViewOpen = () => {
        loginViewFlag.value = true;
    }

    const loginViewClose = () => {
        loginViewFlag.value = false;
    }

    return {
        loginViewFlag, loginViewOpen, loginViewClose
    }
});

export const useRegisterViewStore = defineStore("registerViewStore", () => {
    let registerViewFlag = ref(false);

    const registerViewOpen = () => {
        registerViewFlag.value = true;
    }

    const registerViewclose = () => {
        registerViewFlag.value = false;
    }

    return {
        registerViewFlag, registerViewOpen, registerViewclose
    }
});
