<script setup lang="ts">
import { onMounted } from 'vue'
import { useModelStore } from '~/stores/model'

definePageMeta({ layout: 'admin', middleware: 'auth' })

const router = useRouter()
const agentStore = useAgentStore()
const modelStore = useModelStore()

onMounted(async () => {
  await agentStore.list()
  await modelStore.listAvailable()
})

// modal agent creation
const showCreateAgentDialog = ref(false)
const isSubmitting = ref(false)

const createForm = ref({
  icon: '',
  name: '',
  description: '',
  prompt: '',
  modelUUID: '',
})

async function onSubmit() {
  if (isSubmitting.value)
    return

  isSubmitting.value = true

  const { success, response } = await agentStore.create(createForm.value)

  if (success) {
    toast({
      title: 'Success',
      description: 'Agent created successfully.',
    })
    createForm.value = null
  }
  else {
    toast({
      title: 'Error',
      description: response || 'Something went wrong.',
    })
  }

  isSubmitting.value = false
}

async function remove(uuid: string) {
  const { success, message } = await agentStore.delete(uuid)
  if (!success) {
    toast({
      title: 'Error',
      description: message || 'Unable to delete this agent.',
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: 'Deleted',
      description: 'Agent deleted successfully.',
    })
  }
}
</script>

<template>
  <main class="grid flex-1 gap-4 overflow-auto p-4 lg:grid-cols-2">
    <div class="relative flex flex-col gap-4">
      <div class="flex items-center justify-between">
        <h1 class="text-xl font-semibold">
          Agents
        </h1>

        <Dialog v-model:open="showCreateAgentDialog">
          <DialogTrigger as-child>
            <Button variant="outline">
              <Icon name="lucide:robot" class="mr-2 size-5" />
              Create Agent
            </Button>
          </DialogTrigger>
          <DialogContent class="max-w-lg">
            <DialogHeader>
              <DialogTitle>Create Agent</DialogTitle>
            </DialogHeader>

            <Form
              :initial-values="createForm" class="space-y-3"
              @submit="onSubmit"
            >
              <FormField name="icon">
                <FormItem>
                  <FormLabel>Icon</FormLabel>
                  <FormControl>
                    <Input v-model="createForm.icon" placeholder="Your email" type="text" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              </FormField>

              <FormField name="name">
                <FormItem>
                  <FormLabel>Name</FormLabel>
                  <FormControl>
                    <Input v-model="createForm.name" placeholder="Your email" type="text" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              </FormField>

              <FormField name="description">
                <FormItem>
                  <FormLabel>Description</FormLabel>
                  <FormControl>
                    <Textarea v-model="createForm.description" placeholder="You are a..." class="min-h-[4.5rem]" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              </FormField>

              <FormField name="model">
                <FormItem>
                  <FormLabel>Model</FormLabel>
                  <FormControl>
                    <Select v-model="createForm.modelUUID">
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
                  </FormControl>
                  <FormMessage />
                </FormItem>
              </FormField>

              <FormField name="prompt">
                <FormItem>
                  <FormLabel>Prompt</FormLabel>
                  <FormControl>
                    <Textarea v-model="createForm.prompt" placeholder="You are a..." class="min-h-[9.5rem]" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              </FormField>

              <div class="text-right text-black">
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
                    Loading..
                  </template>
                  <template v-else>
                    Save changes
                  </template>
                </Button>
              </div>
            </Form>
          </DialogContent>
        </Dialog>
      </div>

      <div class="grid gap-6 lg:grid-cols-3 sm:grid-cols-2">
        <Card
          v-for="agent in agentStore.agents"
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

            <Button size="sm" class="mt-3 self-end gap-1.5 bg-black text-white" @click="router.push(`/chats/new?agent=${agent.id}`)">
              Start Chat
              <Icon name="lucide:send-horizontal" class="size-3.5" />
            </Button>
          </CardContent>
        </Card>
      </div>
    </div>
  </main>
</template>
