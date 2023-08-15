<template>
    <el-input class="search-input" autofocus v-model="searchKey" clearable maxlength="50" @mouseenter="handleFsvSearch"
        :suffix-icon="Search" placeholder="搜索Fsv社区" />
</template>
<script setup lang="ts">
import { Search } from '@element-plus/icons-vue'
let searchKey = ref('');

const handleFsvSearch = () => {
    console.log(searchKey.value);
}

</script>

<style scoped>
.search-input {
    width: 200px;
}
</style>