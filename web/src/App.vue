<template>
  <div id="app">
    <the-header></the-header>

    <router-view/>

    <the-footer></the-footer>
  </div>
</template>

<script>
import TheHeader from "./components/the-header"
import TheFooter from "./components/the-footer";

export default {
  name: 'App',
  components:{TheFooter, TheHeader}
}
</script>
