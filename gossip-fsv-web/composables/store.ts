import { DEFAULT_TOKEN_NAME } from "~/const";
import stores from "./store/index";
import { ApiResponse, TokenInfo } from "~/types";

export const useStore = () => {
    return stores;
};

const transferResponse = (response: any) => {
    return response.value;
}

const transferRequest = (url: string, options: any) => {
    let tokenInfo: TokenInfo = JSON.parse(localStorage.getItem(DEFAULT_TOKEN_NAME) ?? '{}');

    options = {
        ...options,
        headers: {
            ...options.headers,
            'Content-Type': 'application/json',
        }
    }

    if (tokenInfo) {
        let tokenName = tokenInfo.tokenName;
        let tokenValue = tokenInfo.tokenValue;
        options.headers[tokenName] = tokenValue;
    }


    return {
        url, options
    }
}

const handleResponseCode = (response: ApiResponse) => {

    let code = response.code;
    if (code !== 200) {
        let msg = response.data.msg;
        ElMessage({
            message: msg,
            type: 'error'
        })
    }
}

export const useCustomFetch = async (preUrl: string, preOptions: any, server = true) => {
    const { url, options } = transferRequest(preUrl, preOptions);

    options['server'] = server;

    let { data, error, pending, status } = await useFetch(url, options);


    let newData: ApiResponse = transferResponse(data);

    handleResponseCode(newData);

    // 处理响应码

    let result = newData.data;

    return {
        result, error, pending, status
    }

}