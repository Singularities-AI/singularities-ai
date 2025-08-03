<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useModelStore } from '~/stores/model'

definePageMeta({
  layout: 'admin',
  middleware: 'auth',
})

const modelStore = useModelStore()

onMounted(async () => {
  await modelStore.list()
})

async function setAsDefault(id: string) {
  const { success, message } = await modelStore.setDefault(id)
  if (!success) {
    toast({
      title: 'Error',
      description: message || 'Unable to set this model as default.',
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: 'Success',
      description: 'Model defined as default.',
    })
  }
}
</script>

<template>
  <main class="grid flex-1 gap-4 overflow-auto p-4 lg:grid-cols-2">
    <div class="relative flex flex-col gap-4">
      <div
        v-for="model in modelStore.models"
        :key="model.id"
        class="relative flex flex-col border rounded-xl bg-muted/50 p-4"
      >
        <div class="flex items-start justify-between">
          <div>
            <h3 class="flex items-center gap-2 font-semibold">
              {{ model.name }}
              <Icon
                v-if="model.isDownload"
                name="lucide:check-circle"
                class="size-4 text-green-500"
              />
            </h3>
            <p class="text-sm text-muted-foreground">
              {{ model.description }}
            </p>

            <div class="mt-1 flex flex-wrap gap-3 text-xs text-muted-foreground">
              <Icon v-if="model.isDownload" name="lucide:trash-2" class="size-5" />

              <span v-if="model.isDownload">✅ Downloaded</span>
              <span v-if="model.isDefault" class="text-primary font-semibold">⭐ Default model</span>
            </div>
          </div>

          <div class="flex flex-col items-end gap-2">
            <Button
              v-if="!model.isDownload"
              size="sm"
              :disabled="model.isDownloading"
            >
              <Icon name="lucide:download" class="mr-2 size-5" />
              Download
            </Button>

            <Button
              v-if="model.isDownload"
              size="sm"
              variant="ghost"
              disabled
            >
              <Icon name="lucide:trash" class="mr-2 size-5" />
              Delete
            </Button>

            <Button
              v-if="!model.isDefault"
              size="sm"
              variant="outline"
              @click="setAsDefault(model.id)"
            >
              <Icon name="lucide:star" class="mr-2 size-5" />
              Set as default
            </Button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>
