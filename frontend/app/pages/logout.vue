<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'

definePageMeta({
  layout: 'blank',
  middleware: 'auth',
})

const authStore = useAuthStore()

onMounted(() => {
  authStore.logout()
})
</script>

<template>
  <div />
</template>

<style scoped></style>
