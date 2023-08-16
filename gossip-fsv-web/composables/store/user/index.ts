import { DEFAULT_TOKEN_NAME, DEFAULT_USER_AVATAR, DEFAULT_USER_INFO_NAME } from "~/const";
import { defineStore } from "pinia";
import { TokenInfo } from "~/types";
import { skipHydrate } from 'pinia'

export type FsvUser = {
    username: string;
    nickname: string;
    avatar?: string;
    email?: string;
    phone?: string;
    roles: string[];
    permissions: string[];
}

export const useTokenStore = defineStore("tokenStore", () => {
    let tokenInfo = useLocalStorage(DEFAULT_TOKEN_NAME, {} as TokenInfo)


    const setTokenInfo = (newToken: TokenInfo) => {
        tokenInfo.value = newToken;
    }

    return {
        tokenInfo, setTokenInfo
    };
}, {
    persist: true
});

export const useUserStore = defineStore("userStore", () => {

    let user = useLocalStorage(DEFAULT_USER_INFO_NAME, {} as FsvUser);

    let tokenStore = useTokenStore();

    const setUserInfo = (newUser: FsvUser) => {
        user.value = newUser;
    }

    const exitAccount = async () => {
        let _ = await useFetch("/api/fsv/user/exit");

        user.value = null;
        tokenStore.tokenInfo = null;
    }

    // 添加默认头像
    if (!user.value.avatar) {
        user.value.avatar = DEFAULT_USER_AVATAR;
    }
    const isLogin = computed(() => {
        if (user === null || user.value === null) {
            return false;
        }
        return user.value.username !== undefined;
    });
    return {
        user, setUserInfo, isLogin, exitAccount
    };
}, {
    persist: true
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
