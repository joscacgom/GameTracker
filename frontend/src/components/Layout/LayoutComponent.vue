/* eslint-disable */
<template>
    <div>
      <header>
      </header>
  
      <main>
        <slot></slot>
      </main>
  
    <FooterComponent />
    </div>
  </template>

  <script>
    import FooterComponent from '@/components/Layout/FooterComponent.vue';
    export default {
        name: 'LayoutComponent',
        components: {
          FooterComponent 
        },
    };
    </script>
  
  <style scoped>
  </style>
  