<template>
    <client-only>
        <el-page-header @back="onBack">
            <template #breadcrumb>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: './page-header.html' }">
                        homepage
                    </el-breadcrumb-item>
                    <el-breadcrumb-item><a href="./page-header.html">route 1</a></el-breadcrumb-item>
                    <el-breadcrumb-item>route 2</el-breadcrumb-item>
                </el-breadcrumb>
            </template>
        </el-page-header>
    </client-only>
</template>
<script setup lang="ts">
import { ElNotification as notify } from 'element-plus'

const route = useRoute();

const router = useRouter();
const routeMap = computed(() => {
    let routes = router.getRoutes();
    let routeMap = new Map();
    routes.forEach((route) => {
        routeMap.set(route.path, route);
    });
    return routeMap;
})

const onBack = () => {
    notify('Back')
}
</script>