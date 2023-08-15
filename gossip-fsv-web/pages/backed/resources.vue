<template>
    <client-only>
        <NuxtPage></NuxtPage>

    </client-only>
</template>

<script setup lang="ts">


definePageMeta({
    layout: 'background',
    name: "资源管理",
    redirect: '资源列表'
})
</script>