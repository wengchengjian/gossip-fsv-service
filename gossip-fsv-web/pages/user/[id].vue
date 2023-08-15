<template>
    <div>用户: {{ $route.params.id }}</div>
    <div>query: {{ $route.query }}</div>
</template>

<script setup lang="ts">

definePageMeta({
    layout: "background"
})

</script>