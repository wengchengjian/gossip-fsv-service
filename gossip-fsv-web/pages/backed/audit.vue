<template>
    <client-only>
        <NuxtPage></NuxtPage>

    </client-only>
</template>

<script setup lang="ts">


definePageMeta({
    layout: 'background',
    name: "审计日志",
    redirect: '日志列表'
})
</script>