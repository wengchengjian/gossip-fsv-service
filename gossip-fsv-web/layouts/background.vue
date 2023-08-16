<template>
    <el-row>
        <TheHeader />
    </el-row>
    <el-row>
        <el-col :span="4">
            <TheBackgroundMenuTree />
        </el-col>
        <el-col :span="20">
            <div class="fsv-backed-content">
                <slot />
            </div>
        </el-col>
    </el-row>
    <el-row>
        <TheFooter />
    </el-row>
    <TheLoginRegisterViewDialog />
</template>

<style scoped>
.fsv-backed-content {
    padding: 20px;
    height: 100%;
    background-color: #F5F6FA;
}
</style>