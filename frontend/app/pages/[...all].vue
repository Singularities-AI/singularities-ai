<script setup lang="ts">
import { ref } from 'vue'
import Header from '~/components/Header.vue'
import Sidebar from '~/components/Sidebar.vue'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { Tooltip, TooltipContent, TooltipTrigger } from '@/components/ui/tooltip'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const inputMessage = ref('')
const messages = ref<{ from: 'user' | 'bot', text: string }[]>([])

async function sendMessage() {
  if (!inputMessage.value.trim())
    return

  const userText = inputMessage.value
  messages.value.push({ from: 'user', text: userText })
  inputMessage.value = ''

  // Appel API factice (à remplacer par un vrai appel backend)
  const response = await fetch('/api/chat', {
    method: 'POST',
    body: JSON.stringify({ message: userText }),
    headers: { 'Content-Type': 'application/json' },
  })

  const data = await response.json()
  messages.value.push({ from: 'bot', text: data.reply })
}

const history = ref([
  { name: 'Chat du 10 juillet' },
  { name: 'Discussion sur le projet X' },
  { name: 'Discussion sur le projet X' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Idées de contenu' },
  { name: 'Bug rapporté hier' },
])

function deleteChat(index) {
  history.value.splice(index, 1)
}
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <main class="grid flex-1 gap-4 overflow-hidden p-4 lg:grid-cols-[300px_1fr]">
        <div class="relative h-full flex flex-col">
          <!-- chats -->
          <div class="flex-1 overflow-auto pb-4">
            <fieldset class="h-full flex flex-col gap-4 border rounded-lg p-4">
              <legend class="text-sm font-medium -ml-1">
                Chats
              </legend>

              <div class="max-h-[calc(100vh-400px)] flex flex-col gap-1 overflow-y-auto scroll-smooth">
                <div
                  v-for="(item, index) in history"
                  :key="index"
                  class="group flex items-center justify-between rounded-md px-3 transition hover:bg-muted"
                >
                  <span class="truncate text-sm">{{ item.name }}</span>

                  <DropdownMenu>
                    <DropdownMenuTrigger as-child>
                      <Button
                        variant="ghost"
                        size="icon"
                        class="opacity-0 transition group-hover:opacity-100"
                      >
                        <Icon name="lucide:more-vertical" class="size-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end" class="w-32 bg-background">
                      <DropdownMenuItem class="text-red-500" @click="deleteChat(index)">
                        <Icon name="lucide:trash" class="mr-2 size-4" />
                        Supprimer
                      </DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </div>
              </div>

              <div class="flex-1" />
            </fieldset>
          </div>

          <!-- settings -->
          <div class="sticky bottom-0 z-10 bg-background">
            <fieldset class="grid gap-6 border rounded-lg p-4">
              <legend class="px-1 text-sm font-medium -ml-1">
                Settings
              </legend>
              <div class="grid gap-3">
                <Label for="model">Model</Label>
                <Select>
                  <SelectTrigger id="model" class="items-start [&_[data-description]]:hidden">
                    <SelectValue placeholder="Select a model" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="genesis">
                      <div class="flex items-start gap-3 text-muted-foreground">
                        <Icon name="lucide:rabbit" class="size-5" />
                        <p>Neural <span class="text-foreground font-medium">Genesis</span></p>
                      </div>
                    </SelectItem>
                    <SelectItem value="explorer">
                      <div class="flex items-start gap-3 text-muted-foreground">
                        <Icon name="lucide:bird" class="size-5" />
                        <div class="grid gap-0.5">
                          <p>Neural <span class="text-foreground font-medium">Explorer</span></p>
                          <p class="text-xs" data-description>
                            Performance and speed for efficiency.
                          </p>
                        </div>
                      </div>
                    </SelectItem>
                    <SelectItem value="quantum">
                      <div class="flex items-start gap-3 text-muted-foreground">
                        <Icon name="lucide:turtle" class="size-5" />
                        <div class="grid gap-0.5">
                          <p>Neural <span class="text-foreground font-medium">Quantum</span></p>
                          <p class="text-xs" data-description>
                            The most powerful model for complex computations.
                          </p>
                        </div>
                      </div>
                    </SelectItem>
                  </SelectContent>
                </Select>

                <div class="grid mt-3 gap-3">
                  <Label for="content">Context</Label>
                  <Textarea
                    id="content"
                    placeholder="You are a..."
                    class="min-h-[5.5rem]"
                  />
                </div>
              </div>
            </fieldset>
          </div>
        </div>

        <!-- chat box -->
        <div class="relative h-full min-h-[50vh] flex flex-col rounded-xl bg-muted/50 p-4">
          <Badge variant="outline" class="absolute right-3 top-3 pl-3 pr-3">
            Output
          </Badge>

          <!-- messages -->
          <div class="mb-4 max-h-[calc(100vh-200px)] flex-1 overflow-x-hidden overflow-y-auto scroll-smooth space-y-2">
            <div
              v-for="(msg, idx) in messages"
              :key="idx"
              class="p-2"
              :class="msg.from === 'user'
                ? 'ml-auto w-[80%] rounded-lg bg-primary'
                : 'w-full rounded-lg bg-muted'"
            >
              {{ msg.text }}
            </div>
          </div>

          <!-- input -->
          <form
            class="relative overflow-hidden border rounded-lg bg-background focus-within:ring-1 focus-within:ring-ring"
            @submit.prevent="sendMessage"
          >
            <Label for="message" class="sr-only">Message</Label>
            <Textarea
              id="message"
              v-model="inputMessage"
              placeholder="Type your message here..."
              class="min-h-12 resize-none border-0 p-3 shadow-none focus-visible:ring-0"
            />
            <div class="flex items-center p-3 pt-0">
              <Tooltip>
                <TooltipTrigger as-child>
                  <Button variant="ghost" size="icon">
                    <Icon name="lucide:paperclip" class="size-4" />
                    <span class="sr-only">Attach file</span>
                  </Button>
                </TooltipTrigger>
                <TooltipContent side="top" class="bg-black">
                  Attach File (Coming soon)
                </TooltipContent>
              </Tooltip>
              <Button type="submit" size="sm" class="ml-auto gap-1.5 bg-black">
                Send Message
                <Icon name="lucide:corner-down-left" class="size-3.5" />
              </Button>
            </div>
          </form>
        </div>
      </main>
    </div>
  </div>
</template>
