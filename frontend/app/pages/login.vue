<script setup lang="ts">
import * as z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { useAuthStore } from '~/stores/auth'

definePageMeta({
  layout: 'blank',
  middleware: 'auth',
})

const router = useRouter()
const authStore = useAuthStore()

const isSubmitting = ref(false)

const loginFormSchema = toTypedSchema(
  z.object({
    email: z
      .string()
      .email({ message: 'Please enter a valid email address.' }),
  }),
)

async function onSubmit(values: { email: string }) {
  if (isSubmitting.value)
    return

  isSubmitting.value = true

  const { success, message } = await authStore.generateToken(values.email)

  if (success) {
    router.push(`/email-confirmation?email=${encodeURIComponent(values.email)}`)
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
        <span class="text-xl" style="font-family: 'Space Mono', monospace;">
          Singularities AI
        </span>
      </a>
    </div>

    <div class="flex items-center justify-center py-12">
      <Form :validation-schema="loginFormSchema" class="grid mx-auto w-[350px] gap-6" @submit="onSubmit">
        <div class="grid gap-2 text-center">
          <h1 class="mb-2 text-3xl" style="font-family: 'Space Mono', monospace;">
            Sign up to initiate your singularity.
          </h1>
          <p class="text-balance text-muted-foreground">
            A login code will be sent to you by email.
          </p>
        </div>

        <div class="grid gap-4">
          <FormField v-slot="{ componentField }" name="email">
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input type="email" placeholder="john@example.com" v-bind="componentField" />
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
            Continue with email
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
