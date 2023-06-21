/* eslint-disable */
<template>
    <div id="app">
      <LayoutComponent>
        <router-view></router-view>
      </LayoutComponent>
  </div>
</template>

<script>
import LayoutComponent from '@/components/Layout/LayoutComponent.vue';
export default {
  name: 'App',
  components: {
    LayoutComponent 
  },
}
</script>

<style>

</style>
