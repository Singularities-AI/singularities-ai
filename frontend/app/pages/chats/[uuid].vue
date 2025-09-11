<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Header from '~/components/Header.vue'
import Sidebar from '~/components/Sidebar.vue'
import { useModelStore } from '~/stores/model'
import { useChatStore } from '~/stores/chat'
import type { Agent } from '~/interfaces/Agent'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const router = useRouter()
const route = useRoute()
const modelStore = useModelStore()
const chatStore = useChatStore()
const agentStore = useAgentStore()

const selectedModel = ref<string>('')
const context = ref<string>('')
const selectedChatId = ref<string | null>(null)
const inputMessage = ref('')
const messages = ref<Message[]>([])
const loading = ref<boolean>(false)

const agent = ref<Agent>()

interface Message {
  from: 'USER' | 'AGENT' | 'ERROR'
  text: string
  retry?: () => void
}

onMounted(async () => {
  await Promise.all([
    // load models available
    modelStore.listAvailable().then(() => {
      const defaultModel = modelStore.modelsAvailable.find(m => m.default)
      if (defaultModel)
        selectedModel.value = defaultModel.id
    }),

    // load chats histories
    chatStore.list(),
  ])

  // load chat if uuid is present in url
  const chatUUID = route.params.uuid as string | undefined
  if (chatUUID && chatUUID !== 'new') {
    selectedChatId.value = chatUUID
    await chatStore.listMessages(chatUUID).then((res) => {
      messages.value = res.data?.map(msg => ({
        from: msg.role === 'USER' ? 'USER' : 'AGENT',
        text: msg.content,
      })) || []
    })

    // load agent by current chat
    const chat = chatStore.chats.find(c => c.id === chatUUID)
    if (chat) {
      if (chat.agent)
        agent.value = chat.agent
    }
  }

  // load agent if agent uuid is present in url
  const agentUUID = route.query.agent as string | undefined
  if (agentUUID)
    agent.value = (await agentStore.getByUUID(agentUUID)).data
})

async function sendMessage() {
  if (!inputMessage.value.trim())
    return

  const userText = inputMessage.value
  inputMessage.value = ''

  // display user message
  messages.value.push({ from: 'USER', text: userText })

  const retry = () => {
    // deletes the last error message so it doesn't get duplicated
    messages.value = messages.value.filter(msg => msg.from !== 'ERROR' || msg.text !== userText)
    inputMessage.value = userText
    sendMessage()
  }

  try {
    loading.value = true

    const res = await chatStore.sendMessage(
      selectedChatId.value,
      userText,
      selectedModel.value,
      context.value,
      agent.value?.id || null,
    )

    messages.value.push({ from: 'AGENT', text: res.data?.content || '' })

    if (!selectedChatId.value) {
      selectedChatId.value = res.data?.chatUUID || null
      router.push(`/chats/${res.data?.chatUUID}`)
      await chatStore.list()
    }
  }
  catch (error) {
    messages.value.push({ from: 'ERROR', text: userText, retry })
  }
  finally {
    loading.value = false
  }
}

function deleteChat(id: string) {
  if (id === selectedChatId.value) {
    selectedChatId.value = null
    router.push('/chats/new')
  }

  chatStore.remove(id)
}

function openChat(id: string) {
  router.push(`/chats/${id}`)
}

function newChat() {
  selectedChatId.value = null
  router.push('/chats/new')
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
              <legend class="ml-1 text-sm font-medium">
                Chats
              </legend>

              <div class="max-h-[calc(100vh-400px)] flex flex-col gap-1 overflow-y-auto scroll-smooth">
                <div class="max-h-[calc(100vh-400px)] flex flex-col gap-1 overflow-y-auto scroll-smooth">
                  <div
                    class="group flex items-center justify-between rounded-md hover:cursor-pointer hover:bg-muted"
                    @click="newChat()"
                  >
                    <Button
                      variant="ghost"
                      size="sm"
                      class="flex items-center gap-2"
                    >
                      <Icon name="lucide:square-pen" class="size-4" />
                      <span class="truncate text-sm text-black">New chat</span>
                    </Button>
                  </div>
                </div>

                <Separator />

                <div
                  v-for="(item, index) in chatStore.chats"
                  :key="index"
                  class="group flex items-center justify-between rounded-md px-3 transition hover:cursor-pointer hover:bg-muted"
                  :class="{ 'bg-muted': selectedChatId === item.id }"
                  @click="openChat(item.id)"
                >
                  <span class="truncate text-sm">{{ item.title }}</span>

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
                    <DropdownMenuContent align="end" class="w-32">
                      <DropdownMenuItem
                        class="text-red-500 hover:cursor-pointer focus:bg-red-100 hover:!bg-red-100 hover:!text-red-700"
                        @click="deleteChat(item.id)"
                      >
                        <Icon name="lucide:trash" class="mr-2 size-4" />
                        Delete
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
                <Select v-model="selectedModel">
                  <SelectTrigger id="model" class="items-start [&_[data-description]]:hidden">
                    <SelectValue placeholder="Select a model" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem
                      v-for="model in modelStore.modelsAvailable"
                      :key="model.id"
                      :value="model.id"
                    >
                      <div class="flex items-start gap-3 text-muted-foreground">
                        <Icon name="lucide:brain" class="size-5" />
                        <p>{{ model.name }}</p>
                      </div>
                    </SelectItem>
                  </SelectContent>
                </Select>

                <div class="grid mt-3 gap-3">
                  <Label for="content">Context</Label>
                  <Textarea
                    id="content"
                    v-model="context"
                    placeholder="You are a..."
                    class="min-h-[5.5rem]"
                  />
                </div>
              </div>
            </fieldset>
          </div>
        </div>

        <!-- chat box -->
        <div class="relative h-full min-h-[50vh] flex flex-col border rounded-xl p-4">
          <div class="relative flex-1 overflow-x-hidden overflow-y-auto scroll-smooth">
            <!-- agent center -->
            <div v-if="messages.length === 0 && agent" class="absolute inset-0 flex flex-col items-center justify-center text-center space-y-4">
              <Icon
                :name="agent?.icon || 'lucide:brain'"
                class="h-20 w-20 text-muted-foreground"
              />
              <div>
                <h2 class="text-xl font-semibold">
                  {{ agent?.name }}
                </h2>
                <p class="text-sm text-muted-foreground">
                  {{ agent?.model?.name }}
                </p>
                <p class="mt-2 max-w-md text-sm text-muted-foreground">
                  {{ agent?.description }}
                </p>
              </div>
            </div>

            <Badge variant="outline" class="absolute left-3 top-3 flex items-center gap-2 px-3 py-1">
              <Icon
                v-if="agent"
                name="lucide:bot"
                class="h-4 w-4"
              />
              <Icon
                v-else
                name="lucide:square-terminal"
                class="h-4 w-4"
              />
              <span v-if="agent" class="text-sm font-medium">
                {{ agent?.name }}
              </span>
              <span v-else class="text-sm font-medium">
                Basic Chat
              </span>
            </Badge>

            <!-- messages -->
            <div class="flex flex-col px-2 pt-13 space-y-4">
              <div
                v-for="(msg, idx) in messages" :key="idx" class="p-2"
                :class="{
                  'ml-auto w-[80%] rounded-lg bg-primary text-black': msg.from === 'USER',
                  'w-full rounded-lg bg-muted': msg.from === 'AGENT',
                  'w-[100%] rounded-lg bg-red-400 text-black': msg.from === 'ERROR',
                }"
              >
                <template v-if="msg.from === 'ERROR'">
                  <div class="mx-3 w-full flex items-center justify-between">
                    <div class="text-sm">
                      An error has occurred. Please try again.
                    </div>
                    <Button variant="secondary" size="sm" class="mr-3 flex items-center gap-2" @click="msg.retry?.()">
                      Retry <Icon name="lucide:refresh-ccw" class="size-4" />
                    </Button>
                  </div>
                </template>
                <template v-else>
                  <div class="mx-3 text-sm">
                    {{ msg.text }}
                  </div>
                </template>
              </div>

              <div v-if="loading === true" class="w-full rounded-lg bg-muted p-2">
                <div class="mx-3 w-full flex text-sm">
                  <svg class="mr-2 h-5 w-5 animate-spin" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
                  </svg>
                  Generating your response...
                </div>
              </div>
            </div>
          </div>

          <!-- input -->
          <form class="relative overflow-hidden border rounded-lg bg-background focus-within:ring-1 focus-within:ring-ring" @submit.prevent="sendMessage">
            <Label for="message" class="sr-only">Message</Label>
            <Textarea id="message" v-model="inputMessage" placeholder="Type your message here..." class="min-h-12 resize-none border-0 p-3 shadow-none focus-visible:ring-0" />
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
                Send Message <Icon name="lucide:corner-down-left" class="size-3.5" />
              </Button>
            </div>
          </form>
        </div>
      </main>
    </div>
  </div>
</template>
