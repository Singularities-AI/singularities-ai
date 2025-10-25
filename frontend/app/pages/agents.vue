<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Header from '~/components/Header.vue'
import Sidebar from '~/components/Sidebar.vue'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const { t } = useI18n()

const router = useRouter()
const agentStore = useAgentStore()

const pageNumber = ref(0)
const pageSize = 20
const isLoadingMore = ref(false)

async function loadAgents(append = false) {
  if (isLoadingMore.value)
    return
  isLoadingMore.value = true

  const result = await agentStore.list(pageNumber.value, pageSize, append)
  if (result.success)
    pageNumber.value++

  isLoadingMore.value = false
}

// infinite scroll handler on main
function handleScroll() {
  const bottomReached
    = window.innerHeight + window.scrollY >= document.body.offsetHeight - 200

  if (bottomReached && !isLoadingMore.value && agentStore.page?.last === false)
    loadAgents(true)
}

onMounted(() => window.addEventListener('scroll', handleScroll))
onUnmounted(() => window.removeEventListener('scroll', handleScroll))

onMounted(async () => {
  await agentStore.list()
})
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <main class="flex-1 p-6">
        <div class="mb-8 flex flex-col items-center text-center space-y-3">
          <h1 class="text-4xl font-bold">
            {{ t('agents.title') }}
          </h1>
          <p class="max-w-xl text-muted-foreground">
            {{ t('agents.description') }}
          </p>
        </div>

        <!-- cards -->
        <div class="grid gap-6 lg:grid-cols-3 sm:grid-cols-2">
          <Card
            v-for="agent in agentStore.agents"
            :key="agent.id"
            class="h-full flex flex-col rounded-xl"
          >
            <CardHeader class="flex flex-row items-center justify-between">
              <Icon
                :name="agent.icon"
                alt="icon"
                class="h-10 w-10 rounded-full object-cover"
              />
            </CardHeader>

            <CardContent class="flex flex-1 flex-col">
              <div>
                <CardTitle class="break-words text-lg font-semibold">
                  {{ agent.name }}
                </CardTitle>
                <p class="break-words text-sm text-muted-foreground">
                  {{ agent.description }}
                </p>
                <p class="mt-3 flex items-center gap-1 text-sm text-muted-foreground">
                  <Icon name="lucide:brain" class="size-5" />
                  {{ agent.model.name }}
                </p>
              </div>

              <div class="mt-auto flex justify-end">
                <Button
                  size="sm"
                  class="gap-1.5 bg-black text-white"
                  @click="router.push(`/chats/new?agent=${agent.id}`)"
                >
                  {{ t('agents.startChat') }}
                  <Icon name="lucide:send-horizontal" class="size-3.5" />
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>

        <div v-if="isLoadingMore" class="my-6 flex justify-center">
          <svg class="h-6 w-6 animate-spin text-gray-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
          </svg>
        </div>
      </main>
    </div>
  </div>
</template>
