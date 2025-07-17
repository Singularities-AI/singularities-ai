<script setup lang="ts">
import { onMounted, onUnmounted, ref, watch } from 'vue'

const props = defineProps<{
  isOpen: boolean
  title: string
  text: string
  onCancel: () => void
  onConfirm: () => void
  loading?: boolean
}>()

const modalRef = ref<HTMLDivElement | null>(null)

function handleKeyDown(event: KeyboardEvent) {
  if (event.key === 'Escape')
    props.onCancel()
}

function handleOverlayClick(event: MouseEvent) {
  if (event.target === modalOverlayRef.value)
    props.onCancel()
}

const modalOverlayRef = ref<HTMLDivElement | null>(null)

watch(() => props.isOpen, (open) => {
  if (open) {
    document.body.style.overflow = 'hidden'
    window.addEventListener('keydown', handleKeyDown)
    setTimeout(() => {
      modalRef.value?.focus()
    }, 0)
  }
  else {
    document.body.style.overflow = ''
    window.removeEventListener('keydown', handleKeyDown)
  }
})

onUnmounted(() => {
  document.body.style.overflow = ''
  window.removeEventListener('keydown', handleKeyDown)
})
</script>

<template>
  <div
    v-if="isOpen"
    ref="modalOverlayRef"
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
    aria-modal="true"
    role="dialog"
    tabindex="-1"
    aria-label="Confirmation overlay"
    @mousedown="handleOverlayClick"
  >
    <div
      ref="modalRef"
      class="max-w-md w-full rounded-lg bg-white p-6 text-black shadow-lg outline-none dark:bg-zinc-900 dark:text-white"
      tabindex="0"
      aria-label="Confirmation"
    >
      <h2 class="mb-2 text-lg font-bold">
        {{ title }}
      </h2>
      <p class="mb-6 text-gray-600" v-html="text" />
      <div class="flex justify-end gap-2">
        <button
          type="button"
          class="rounded bg-gray-100 px-4 py-2 text-gray-700 hover:bg-gray-200 focus:outline-none"
          aria-label="Annuler"
          @click="onCancel"
        >
          Annuler
        </button>
        <button
          type="button"
          class="flex items-center gap-2 rounded bg-gray-200 px-4 py-2 text-black hover:bg-gray-200 focus:outline-none"
          :disabled="loading"
          aria-label="Valider"
          @click="onConfirm"
        >
          <span v-if="loading" class="mr-2 h-4 w-4 animate-spin border-2 border-white border-t-transparent rounded-full" />
          Valider
        </button>
      </div>
    </div>
  </div>
</template>
