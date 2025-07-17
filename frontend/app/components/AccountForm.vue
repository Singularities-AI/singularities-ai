<script setup lang="ts">
import type { User } from '~/interfaces/User'
import { useAuthStore } from '~/stores/auth'

const authStore = useAuthStore()

const initialValues = ref({
  email: '',
  context: '',
})

onMounted(async () => {
  const { success, response } = await authStore.me()
  if (success && typeof response === 'object') {
    const me = response as User
    initialValues.value.email = me.email || ''
    initialValues.value.context = me.context || ''
  }
})
</script>

<template>
  <div>
    <h3 class="text-lg font-medium">
      Account
    </h3>
    <p class="text-sm text-muted-foreground">
      Update your account settings.
    </p>
  </div>

  <Separator />

  <Form :initial-values="initialValues" class="space-y-3">
    <FormField name="email">
      <FormItem>
        <FormLabel>Email</FormLabel>
        <FormControl>
          <Input v-model="initialValues.email" placeholder="Your email" disabled type="email" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField name="context">
      <FormItem>
        <FormLabel>Context</FormLabel>
        <FormControl>
          <Textarea v-model="initialValues.context" placeholder="You are a..." class="min-h-[9.5rem]" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField type="checkbox" name="delete_account">
      <FormItem class="flex flex-row items-center justify-between border rounded-lg p-4">
        <div class="space-y-0.5">
          <FormLabel class="text-base">
            Delete my account
          </FormLabel>
          <FormDescription>
            Contact us to delete your account.
          </FormDescription>
        </div>
        <router-link to="#">
          <Button type="button" class="red-button">
            Contact us
          </Button>
        </router-link>
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
