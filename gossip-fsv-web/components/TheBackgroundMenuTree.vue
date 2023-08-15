<template>
    <client-only>
        <el-menu default-active="1" class="el-menu-vertical-demo" @select="handleSelect" @close="handleClose">
            <template v-for="rootMenu in backedShowMenuRootRoutes">
                <el-sub-menu v-if="rootMenu.children.length > 0" :index="rootMenu.redirect?.toString() ?? rootMenu.path">
                    <template #title>
                        <el-icon>
                            <location />
                        </el-icon>
                        <span>{{ rootMenu.name }}</span>
                    </template>
                    <template v-if="rootMenu.children.length > 0">
                        <template v-for="subMenu in rootMenu.children">
                            <el-menu-item v-if="subMenu.meta?.display === undefined ? true : subMenu.meta.display"
                                :index="backedRouteMap.get(subMenu.name)?.path">
                                {{ subMenu.name }}
                            </el-menu-item>
                        </template>

                    </template>
                </el-sub-menu>
                <el-menu-item v-else :index="rootMenu.redirect?.toString() ?? rootMenu.path">
                    <template #title>
                        <el-icon>
                            <location />
                        </el-icon>
                        <span>{{ rootMenu.name }}</span>
                    </template>
                </el-menu-item>
            </template>

        </el-menu>
    </client-only>
</template>
<script lang="ts" setup>
import { RouteRecordNormalized } from '.nuxt/vue-router';
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'

const router = useRouter();
const backedRoutes = computed(() => {
    let routes = router.getRoutes();
    return routes.filter(item => item.path.startsWith("/backed"));
});

const backedRouteMap = computed(() => {
    let routeMap = new Map<RouteRecordNormalized["name"], RouteRecordNormalized>();
    backedRoutes.value.forEach((route) => {
        routeMap.set(route.name, route);
    });
    return routeMap;
})

const backedShowMenuRootRoutes = computed(() => {
    let arr: RouteRecordNormalized[] = [];
    backedRoutes.value.forEach((route) => {
        if (route.children) {
            for (let childRoute of route.children) {
                for (let item of arr) {
                    if (item.name === childRoute.name) {
                        let index = arr.indexOf(item);
                        arr.splice(index, 1);
                    }
                }
            }
        }
        arr.push(route);
    });

    return arr.sort((a, b) => {
        let aOrder = a.meta?.order as number ?? 5;
        let bOrder = b.meta?.order as number ?? 5;

        return aOrder - bOrder;
    })
})

console.log(backedRoutes.value)

const handleSelect = (key: string, keyPath: string[]) => {
    let openRoute = backedRouteMap.value.get(key);
    if (openRoute) {
        console.log(openRoute.path)
        router.push(openRoute.path);
    } else {
        router.push(key);
    }
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>