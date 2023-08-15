<template>
    <client-only>
        <el-menu default-active="1" class="fsv-front-menu-tree" mode="horizontal" @select="handleSelect">
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/community">社区</el-menu-item>
            <el-menu-item index="/share">分享</el-menu-item>
            <el-menu-item index="/backed">进入后台</el-menu-item>
            <el-menu-item index="/about">关于本站</el-menu-item>
        </el-menu>
    </client-only>
</template>
<script lang="ts" setup>
import { ref } from 'vue'

const router = useRouter();

const handleSelect = (key: string, keyPath: string[]) => {
    router.push(key);
}
</script>