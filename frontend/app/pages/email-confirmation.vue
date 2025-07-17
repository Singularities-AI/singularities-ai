<script setup lang="ts">
import * as z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useRoute, useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import { useAuthStore } from '~/stores/auth'

definePageMeta({
  layout: 'blank',
})

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const email = ref<string>('')
const isSubmitting = ref(false)

onMounted(() => {
  const emailParam = route.query.email

  if (typeof emailParam === 'string')
    email.value = emailParam
})

const loginFormSchema = toTypedSchema(
  z.object({
    code: z.string().length(10),
  }),
)

async function onSubmit(values: { code: string }) {
  if (isSubmitting.value)
    return

  // check if email is valid
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!email.value || !emailRegex.test(email.value)) {
    toast({
      title: 'Error',
      description: 'Invalid or missing email in URL.',
    })
    return
  }

  isSubmitting.value = true

  const { success, message } = await authStore.validCode(email.value, values.code)

  if (success) {
    router.push('/')
  }
  else {
    toast({
      title: 'Error',
      description: message,
    })
  }

  isSubmitting.value = false
}
</script>

<template>
  <div class="relative w-full lg:grid lg:grid-cols-2">
    <div class="absolute left-6 top-6 z-10">
      <a href="/" class="flex items-center space-x-3">
        <img src="public/favicon.ico" class="h-10 w-10">
        <span class="text-xl" style="font-family: 'Space Mono', monospace;">Singularities AI</span>
      </a>
    </div>

    <div class="flex items-center justify-center py-12">
      <Form :validation-schema="loginFormSchema" class="grid mx-auto w-[350px] gap-6" @submit="onSubmit">
        <div class="grid gap-2 text-center">
          <h1 class="mb-3 text-3xl" style="font-family: 'Space Mono', monospace;">
            Check your email
          </h1>
          <p class="text-balance text-muted-foreground">
            A temporary connection link and code has been sent to your email
          </p>
        </div>

        <div class="grid gap-4">
          <FormField v-slot="{ componentField }" name="code">
            <FormItem>
              <FormLabel>Code</FormLabel>
              <FormControl>
                <Input type="text" placeholder="your code" v-bind="componentField" />
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>
        </div>

        <Button type="submit" class="w-full text-black" :disabled="isSubmitting">
          <template v-if="isSubmitting">
            <svg class="mr-2 h-4 w-4 animate-spin" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
              <path
                class="opacity-75" fill="currentColor"
                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"
              />
            </svg>
            Loading...
          </template>
          <template v-else>
            Continue
          </template>
        </Button>
      </Form>
    </div>

    <div class="hidden h-screen w-full bg-black opacity-90 lg:flex">
      <video
        class="h-full w-full"
        src="public/animation.mp4"
        autoplay
        muted
        loop
        playsinline
      />
    </div>
  </div>
</template>

<style scoped></style>
