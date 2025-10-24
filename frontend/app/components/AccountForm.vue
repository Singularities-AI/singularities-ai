<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import type { User } from '~/interfaces/User'
import { useAuthStore } from '~/stores/auth'
import { toast } from '@/composables/useToast'

const { t } = useI18n()
const authStore = useAuthStore()

const initialValues = ref({
  email: '',
  context: '',
})

const initialContext = ref('')

onMounted(async () => {
  const { success, response } = await authStore.me()
  if (success && typeof response === 'object') {
    const me = response as User
    initialValues.value.email = me.email || ''
    initialValues.value.context = me.context || ''
    initialContext.value = me.context || ''
  }
})

const isSubmitting = ref(false)
const isModified = computed(() => initialValues.value.context !== initialContext.value)

async function onSubmit() {
  if (isSubmitting.value)
    return

  isSubmitting.value = true

  const { success, response } = await authStore.update({
    context: initialValues.value.context,
  })

  if (success) {
    toast({
      title: t('successTitle'),
      description: t('successMessage'),
    })
    initialContext.value = initialValues.value.context
  }
  else {
    toast({
      title: t('errorTitle'),
      description: response || 'Something went wrong.',
    })
  }

  isSubmitting.value = false
}
</script>

<template>
  <div>
    <h3 class="text-lg font-medium">
      {{ t('accountTitle') }}
    </h3>
    <p class="text-sm text-muted-foreground">
      {{ t('accountDescription') }}
    </p>
  </div>

  <Separator />

  <Form
    :initial-values="initialValues" class="space-y-3"
    @submit="onSubmit"
  >
    <FormField name="email">
      <FormItem>
        <FormLabel>{{ t('emailLabel') }}</FormLabel>
        <FormControl>
          <Input v-model="initialValues.email" :placeholder="t('accountEmailPlaceholder')" disabled type="email" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField name="context">
      <FormItem>
        <FormLabel>{{ t('context') }}</FormLabel>
        <FormControl>
          <Textarea v-model="initialValues.context" :placeholder="t('contextPlaceholder')" class="min-h-[9.5rem]" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <div v-if="isModified" class="text-right text-black">
      <Button
        type="submit"
        :disabled="isSubmitting"
        class="text-black"
      >
        <template v-if="isSubmitting">
          <svg class="mr-2 h-4 w-4 animate-spin" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
          </svg>
          {{ t('loadingButton') }}
        </template>
        <template v-else>
          {{ t('saveChanges') }}
        </template>
      </Button>
    </div>

    <FormField type="checkbox" name="delete_account">
      <FormItem class="flex flex-row items-center justify-between border rounded-lg p-4">
        <div class="space-y-0.5">
          <FormLabel class="text-base">
            {{ t('deleteAccountLabel') }}
          </FormLabel>
          <FormDescription>
            {{ t('deleteAccountDescription') }}
          </FormDescription>
        </div>
        <a href="mailto:contact@singularities-ai.com">
          <Button type="button" class="red-button">
            {{ t('contactUs') }}
          </Button>
        </a>
      </FormItem>
    </FormField>
  </Form>
</template>

<style scoped>
.red-button {
  background-color: rgb(185 28 28 / 1);
  color: white;
}
</style>
