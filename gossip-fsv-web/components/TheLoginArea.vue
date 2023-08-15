<template>
    <el-row style="height: 100%" align="middle">
        <el-col :span="18">
            <el-row v-if="!isBacked" align="middle" justify="center">
                <el-col :span="14">
                    <TheFrontMenuTree />
                </el-col>
                <el-col :span="4">
                    <TheFrontSearch />
                </el-col>
            </el-row>
            <el-row v-else align="middle">
                <el-col :span="14">
                    <TheBackedPageHeader />
                </el-col>
            </el-row>

        </el-col>
        <el-col :span="6">
            <TheUserArea />
        </el-col>
    </el-row>
</template>

<script setup lang="ts">

const route = useRoute();

const isBacked = computed(() => {
    return route.path.startsWith('/backed')
});

</script>