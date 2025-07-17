<script setup lang="ts">
import SidebarNav from '../components/SidebarNav.vue'
</script>

<template>
  <div class="hidden p-10 pb-16 md:block space-y-6">
    <div class="flex flex-col lg:flex-row space-y-8 lg:space-x-12 lg:space-y-0">
      <aside class="-mx-4 lg:w-1/5">
        <SidebarNav />
      </aside>
      <div class="flex-1">
        <div class="space-y-6">
          <slot />
        </div>
      </div>
    </div>
  </div>
</template>
