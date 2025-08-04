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

async function download(id: string) {
  const { success, message } = await modelStore.download(id)
  if (!success) {
    toast({
      title: 'Error',
      description: message || 'Unable to download this model.',
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: 'Success',
      description: 'Download started successfully.',
    })
  }
}

async function remove(id: string) {
  const { success, message } = await modelStore.delete(id)
  if (!success) {
    toast({
      title: 'Error',
      description: message || 'Unable to delete this model.',
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: 'Deleted',
      description: 'Model deleted successfully.',
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
                v-if="model.download"
                name="lucide:check-circle"
                class="size-4 text-green-500"
              />
            </h3>
            <p class="text-sm text-muted-foreground">
              {{ model.description }}
            </p>
          </div>

          <div class="w-[200px] flex flex-col items-end gap-2 text-black">
            <div class="w-full">
              <Button
                v-if="model.downloading"
                size="sm"
                class="w-full justify-center bg-muted text-muted-foreground"
                disabled
              >
                <Icon name="lucide:loader-2" class="mr-2 size-5 animate-spin" />
                Downloading...
              </Button>

              <template v-else>
                <Button
                  v-if="!model.download"
                  size="sm"
                  class="w-full justify-center bg-green-400"
                  @click="download(model.id)"
                >
                  <Icon name="lucide:download" class="mr-2 size-5" />
                  Download
                </Button>

                <Button
                  v-else
                  size="sm"
                  variant="ghost"
                  class="w-full justify-center bg-red-300"
                  :disabled="model.default"
                  @click="remove(model.id)"
                >
                  <Icon name="lucide:trash" class="mr-2 size-5" />
                  Delete
                </Button>
              </template>
            </div>

            <!-- Set as default -->
            <Button
              v-if="!model.default"
              size="sm"
              variant="outline"
              class="w-full justify-center"
              :disabled="!model.download"
              @click="setAsDefault(model.id)"
            >
              <Icon name="lucide:star" class="mr-2 size-5" />
              Set as default
            </Button>

            <!-- Default (tooltip) -->
            <Tooltip>
              <TooltipTrigger as-child>
                <Button
                  v-if="model.default"
                  size="sm"
                  variant="outline"
                  class="w-full justify-center bg-yellow300"
                  disabled
                >
                  <Icon name="lucide:star" class="mr-2 size-5" />
                  Default
                </Button>
              </TooltipTrigger>
              <TooltipContent class="bg-black">
                <p class="text-white">
                  Select another model to change the default one
                </p>
              </TooltipContent>
            </Tooltip>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>
