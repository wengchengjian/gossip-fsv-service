<template>
    <div class="logo-box">
        <el-image class="logo-header" :src="url" @click="handleLogoClick"></el-image>

    </div>
</template>

<script setup lang="ts">
const url = '/favicon.ico'
const handleLogoClick = () => {
    window.location.href = '/'
}
</script>

<style scoped>
.logo-header {
    width: 60px;
    height: 45px;
}

.logo-box {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
}

.logo-header:hover {
    cursor: pointer;
}
</style>