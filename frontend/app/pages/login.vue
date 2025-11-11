<script setup lang="ts">
import * as z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { useAuthStore } from '~/stores/auth'
import { toast } from '@/composables/useToast'
import { useLocalePath } from '#i18n'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const { t } = useI18n()
const localePath = useLocalePath()
const router = useRouter()
const authStore = useAuthStore()

const isSubmitting = ref(false)

const loginFormSchema = toTypedSchema(
  z.object({
    email: z
      .string()
      .email({ message: t('emailValidationMessage') }),
  }),
)

async function onSubmit(values: { email: string }) {
  if (isSubmitting.value)
    return

  isSubmitting.value = true

  const { success, message } = await authStore.generateToken(values.email)

  if (success) {
    router.push(localePath(`/email-confirmation?email=${encodeURIComponent(values.email)}`))
  }
  else {
    toast({
      title: t('errorTitle'),
      description: message,
    })
  }

  isSubmitting.value = false
}
</script>

<template>
  <div class="relative w-full lg:grid lg:grid-cols-2">
    <div class="absolute left-6 top-6 z-10">
      <NuxtLink :to="localePath('index')" class="flex items-center space-x-3">
        <img src="/favicon.ico" class="h-10 w-10">
        <span class="text-xl" style="font-family: 'Space Mono', monospace;">
          {{ t('title') }}
        </span>
      </NuxtLink>
    </div>

    <div class="flex items-center justify-center py-12">
      <Form :validation-schema="loginFormSchema" class="grid mx-auto w-[350px] gap-6" @submit="onSubmit">
        <div class="grid gap-2 text-center">
          <h1 class="mb-2 text-3xl" style="font-family: 'Space Mono', monospace;">
            {{ t('signupMessage') }}
          </h1>
          <p class="text-balance text-muted-foreground">
            {{ t('loginCodeMessage') }}
          </p>
        </div>

        <div class="grid gap-4">
          <FormField v-slot="{ componentField }" name="email">
            <FormItem>
              <FormLabel>{{ t('emailLabel') }}</FormLabel>
              <FormControl>
                <Input type="email" :placeholder="t('emailPlaceholder')" v-bind="componentField" />
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
            {{ t('loadingMessage') }}
          </template>
          <template v-else>
            {{ t('continueButton') }}
          </template>
        </Button>
      </Form>
    </div>

    <div class="hidden h-screen w-full bg-black opacity-90 lg:flex">
      <video
        class="h-full w-full"
        src="/animation.mp4"
        autoplay
        muted
        loop
        playsinline
      />
    </div>
  </div>
</template>

<style scoped></style>
