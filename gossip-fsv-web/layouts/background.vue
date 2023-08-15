<template>
    <el-row>
        <TheHeader />
    </el-row>
    <el-row>
        <el-col :span="4">
            <TheBackgroundMenuTree />
        </el-col>
        <el-col :span="20">
            <slot />
        </el-col>
    </el-row>
    <el-row>
        <TheFooter />
    </el-row>
    <TheLoginRegisterViewDialog />
</template>