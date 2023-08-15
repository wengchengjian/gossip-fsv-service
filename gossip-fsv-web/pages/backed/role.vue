<template>
    <client-only>
        <NuxtPage></NuxtPage>

    </client-only>
</template>

<script setup lang="ts">


definePageMeta({
    layout: 'background',
    name: "角色管理",
    redirect: "角色列表"
})
</script>