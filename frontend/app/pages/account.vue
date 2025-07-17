<script setup lang="ts">
import AccountForm from '~/components/AccountForm.vue'

definePageMeta({
  layout: 'blank',
  middleware: 'auth',
})
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <FormsLayout>
        <AccountForm />
      </FormsLayout>
    </div>
  </div>
</template>
