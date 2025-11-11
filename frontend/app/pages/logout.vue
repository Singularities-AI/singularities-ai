<script setup lang="ts">
import { useRouter } from 'vue-router'
import { onMounted } from 'vue'
import { useAuthStore } from '~/stores/auth'
import { useLocalePath } from '#i18n'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const authStore = useAuthStore()
const localePath = useLocalePath()
const router = useRouter()

onMounted(() => {
  authStore.logout()
  router.push(localePath('index'))
})
</script>

<template>
  <div />
</template>

<style scoped></style>
