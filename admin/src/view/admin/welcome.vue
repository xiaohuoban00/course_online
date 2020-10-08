<template>
<div style="font-size: 18px">欢迎进入总后台</div>
</template>

<script>
export default {
  name: "welcome",
  mounted() {
    this.$parent.activeSidebar("welcome-sidebar");
  }
}
</script>

<style scoped>

</style>