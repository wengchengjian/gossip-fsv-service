// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  css: ['assets/main.css'],
  modules: ['@element-plus/nuxt', '@pinia/nuxt', '@vueuse/nuxt', '@pinia-plugin-persistedstate/nuxt'],
  pinia: {
    autoImports: [
      'defineStore'
    ]
  },
  piniaPersistedState: {
    storage: 'localStorage'
  },
  nitro: {
    devProxy: {
      "/api": {
        target: 'http://127.0.0.1:8080/api',
        prependPath: true,
        changeOrigin: true,
      }
    },
  }
})
