<template>
  <main>
    <NuxtLayout>
      <NuxtPage></NuxtPage>
    </NuxtLayout>
  </main>
</template>
<script setup lang="ts">
onMounted(() => {
  console.log('mounted!')
})
</script>