<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Header from '~/components/Header.vue'
import Sidebar from '~/components/Sidebar.vue'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const agents = ref<any[]>([])

onMounted(async () => {
  agents.value = [
    { id: '1', icon: 'lucide:send-horizontal', name: 'MagicBot', description: 'Created 150 days ago' },
    { id: '2', icon: 'lucide:send-horizontal', name: 'SmartAI', description: 'Created 120 days ago' },
  ]
})
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <main class="flex-1 overflow-y-auto p-6">
        <div class="mb-8 flex flex-col items-center text-center space-y-3">
          <h1 class="text-4xl font-bold">
            Agents
          </h1>
          <p class="max-w-xl text-muted-foreground">
            Agents are personalized chatbots that combine specific instructions, additional knowledge bases, and specialized skills.
          </p>
          <Input
            type="text"
            placeholder="Search agents"
            class="max-w-md w-full"
          />
        </div>

        <!-- Cartes -->
        <div class="grid gap-6 lg:grid-cols-3 sm:grid-cols-2">
          <Card
            v-for="agent in agents"
            :key="agent.id"
            class="rounded-xl"
          >
            <CardHeader class="flex flex-row items-center justify-between">
              <Icon
                :name="agent.icon"
                alt="icon"
                class="h-10 w-10 rounded-full object-cover"
              />
            </CardHeader>

            <CardContent class="h-full flex flex-col">
              <div>
                <CardTitle class="text-lg font-semibold">
                  {{ agent.name }}
                </CardTitle>
                <p class="text-sm text-muted-foreground">
                  {{ agent.description }}
                </p>
              </div>

              <Button size="sm" class="self-end gap-1.5 bg-black text-white">
                Start Chat
                <Icon name="lucide:send-horizontal" class="size-3.5" />
              </Button>
            </CardContent>
          </Card>
        </div>
      </main>
    </div>
  </div>
</template>
