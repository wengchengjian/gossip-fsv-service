<template>
    <client-only>
        <NuxtPage></NuxtPage>
    </client-only>
</template>

<script setup lang="ts">


definePageMeta({
    layout: 'background',
    name: "用户管理",
    redirect: '用户列表'
})
</script>