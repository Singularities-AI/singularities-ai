<script setup lang="ts">
import { ref } from 'vue'
import Header from '~/components/Header.vue'

definePageMeta({
  layout: 'blank',
  middleware: 'auth',
})

const models = [
  {
    name: 'Jan-Nano-Gguf',
    description: 'Jan Nano is a fine-tuned language model built on top of the Qwen3 architecture...',
    author: 'Menlo',
    downloads: 19148,
    size: '2.11GB',
    variants: [
      { name: 'Menlo:Jan-nano-gguf:jan-nano-4b-Q3_K_S.gguf', size: '1.76GB' },
      { name: 'Menlo:Jan-nano-gguf:jan-nano-4b-Q3_K_M.gguf', size: '1.93GB' },
    ],
  },
  {
    name: 'Intellect-2',
    description: 'Prime Intellect released INTELLECT-2, a 32 billion parameter large language model...',
    author: 'PrimeIntellect',
    downloads: 1665,
    size: '18.49GB',
    variants: [],
  },
]

const showVariants = ref<{ [key: string]: boolean }>({})
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <main class="grid flex-1 gap-4 overflow-auto p-4 lg:grid-cols-2">
        <div class="relative flex flex-col gap-4">
          <div
            v-for="model in models"
            :key="model.name"
            class="relative flex flex-col border rounded-xl bg-muted/50 p-4"
          >
            <div class="flex items-start justify-between">
              <div>
                <h3 class="font-semibold">
                  {{ model.name }}
                </h3>
                <p class="text-sm text-muted-foreground">
                  {{ model.description }}
                </p>
                <div class="mt-1 flex gap-3 text-xs text-muted-foreground">
                  <span>By {{ model.author }}</span>
                  <span>{{ model.downloads.toLocaleString() }} downloads</span>
                </div>
              </div>
              <div class="flex flex-col items-end gap-2">
                <Button size="sm">
                  Download
                </Button>
                <Button
                  variant="ghost"
                  size="sm"
                  @click="showVariants[model.name] = !showVariants[model.name]"
                >
                  {{ showVariants[model.name] ? 'Hide variants' : 'Show variants' }}
                </Button>
              </div>
            </div>
            <div v-if="showVariants[model.name]" class="mt-2">
              <div
                v-for="variant in model.variants"
                :key="variant.name"
                class="flex items-center justify-between border-t py-1 text-sm"
              >
                <span class="truncate">{{ variant.name }}</span>
                <div class="flex items-center gap-2">
                  <span class="text-xs text-muted-foreground">{{ variant.size }}</span>
                  <Button variant="ghost" size="icon">
                    <Icon name="lucide:download" class="size-4" />
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>
