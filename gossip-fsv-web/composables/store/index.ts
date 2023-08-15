import { useUserStore, useLoginViewStore, useRegisterViewStore } from "./user/index";


const useBackedPage = () => {
    const route = useRoute();

    const isBacked = computed(() => {
        return route.path.startsWith('/backed')
    });
    return isBacked;
}

const stores = {
    useUserStore,
    useLoginViewStore,
    useRegisterViewStore,
    useBackedPage
}

export default stores;