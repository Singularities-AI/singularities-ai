<script setup lang="ts">
import { onMounted, watch } from 'vue'
import { useModelStore } from '~/stores/model'

definePageMeta({ layout: 'admin', middleware: 'auth' })

const agentStore = useAgentStore()
const modelStore = useModelStore()

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

onMounted(async () => {
  await loadAgents()
  await modelStore.listAvailable()
})

// modal agent creation / edition
const showCreateAgentDialog = ref(false)
const isSubmitting = ref(false)
const isEditing = ref(false)
const agentToEdit = ref<any | null>(null)

const emptyForm = {
  icon: '',
  name: '',
  description: '',
  prompt: '',
  modelUUID: '',
}

const createForm = ref({ ...emptyForm })
watch(showCreateAgentDialog, (open) => {
  if (!open) {
    createForm.value = { ...emptyForm }
    isEditing.value = false
    agentToEdit.value = null
  }
})

async function onSubmit() {
  if (isSubmitting.value)
    return

  isSubmitting.value = true
  let result

  if (isEditing.value && agentToEdit.value)
    result = await agentStore.update(agentToEdit.value.id, createForm.value)
  else
    result = await agentStore.create(createForm.value)

  const { success, message } = result

  if (success) {
    toast({
      title: 'Success',
      description: isEditing.value ? 'Agent updated successfully.' : 'Agent created successfully.',
    })
  }
  else {
    toast({
      title: 'Error',
      description: message || 'Something went wrong.',
    })
  }

  isSubmitting.value = false
  showCreateAgentDialog.value = false
}

function editAgent(agent: any) {
  createForm.value = {
    icon: agent.icon || '',
    name: agent.name || '',
    description: agent.description || '',
    prompt: agent.prompt || '',
    modelUUID: agent.model.id || '',
  }
  agentToEdit.value = agent
  isEditing.value = true
  showCreateAgentDialog.value = true
}

// delete agent
const showDeleteDialog = ref(false)
const agentToDelete = ref<string | null>(null)

async function confirmDelete() {
  if (!agentToDelete.value)
    return
  const { success, message } = await agentStore.delete(agentToDelete.value)
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
  agentToDelete.value = null
  showDeleteDialog.value = false
}

// infinite scroll handler
function handleScroll() {
  const bottomReached
    = window.innerHeight + window.scrollY >= document.body.offsetHeight - 200

  if (bottomReached && !isLoadingMore.value && agentStore.page?.last === false)
    loadAgents(true)
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<template>
  <div class="relative flex flex-col gap-4">
    <div class="flex items-center justify-between">
      <h1 class="text-xl font-semibold">
        Agents
      </h1>

      <Dialog v-model:open="showCreateAgentDialog">
        <DialogTrigger as-child>
          <Button variant="outline" @click="isEditing = false">
            <Icon name="lucide:bot" class="mr-2 size-5" />
            Create Agent
          </Button>
        </DialogTrigger>
        <DialogContent class="max-w-lg">
          <DialogHeader>
            <DialogTitle>
              {{ isEditing ? 'Update Agent' : 'Create Agent' }}
            </DialogTitle>
          </DialogHeader>

          <Form
            :initial-values="createForm" class="mt-2 space-y-3"
            @submit="onSubmit()"
          >
            <FormField name="icon">
              <FormItem class="flex flex-col">
                <FormLabel>Icon</FormLabel>
                <FormControl class="mt-1">
                  <IconPicker v-model="createForm.icon" />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField name="name">
              <FormItem>
                <FormLabel>Name</FormLabel>
                <FormControl>
                  <Input v-model="createForm.name" placeholder="Ex: Marketing Expert" type="text" />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField name="description">
              <FormItem>
                <FormLabel>Description</FormLabel>
                <FormControl>
                  <Textarea v-model="createForm.description" placeholder="Ex: An AI agent specializing in marketing strategy and automation, optimizing campaigns and audiences." class="min-h-[4.5rem]" />
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
                  <Textarea v-model="createForm.prompt" placeholder="Ex: You are a marketing expert specializing in digital acquisition. Analyze my product and propose a comprehensive strategy (SEO, social media, email marketing, and paid advertising) to increase visibility and conversions. Provide concrete, quantified actions prioritized by importance." class="min-h-[9.5rem]" />
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
                  Saving..
                </template>
                <template v-else>
                  {{ isEditing ? 'Update' : 'Save' }}
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
            <CardTitle class="text-lg font-semibold">
              {{ agent.name }}
            </CardTitle>
            <p class="mt-1 text-sm text-muted-foreground">
              {{ agent.description }}
            </p>
            <p class="mt-3 text-sm text-muted-foreground">
              <Icon name="lucide:brain" class="size-5" />
              {{ agent.model.name }}
            </p>
          </div>

          <!-- btn area -->
          <div class="mt-auto flex flex-wrap justify-end gap-2">
            <Button
              variant="destructive"
              size="sm"
              class="text-red-500 hover:cursor-pointer focus:bg-red-100 hover:!bg-red-100 hover:!text-red-700"
              @click="() => { agentToDelete = agent.id; showDeleteDialog = true }"
            >
              <Icon name="lucide:trash" class="mr-2 size-5" />
              Delete
            </Button>

            <Button
              variant="secondary"
              size="sm"
              class="hover:cursor-pointer focus:bg-gray-100 hover:!bg-gray-100"
              @click="editAgent(agent)"
            >
              <Icon name="lucide:pencil" class="mr-2 size-5" />
              Update
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>

    <div v-if="isLoadingMore" class="my-4 flex justify-center">
      <svg class="h-6 w-6 animate-spin text-gray-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
      </svg>
    </div>
  </div>

  <!-- delete modal -->
  <AlertDialog v-model:open="showDeleteDialog">
    <AlertDialogContent>
      <AlertDialogHeader>
        <AlertDialogTitle>Confirm deletion</AlertDialogTitle>
        <AlertDialogDescription>
          Are you sure you want to delete this agent and all this data? This action cannot be undone.
          <br>
          All conversations related to this agent will be converted into independent conversations.
        </AlertDialogDescription>
      </AlertDialogHeader>
      <AlertDialogFooter>
        <AlertDialogCancel @click="showDeleteDialog = false">
          Cancel
        </AlertDialogCancel>
        <AlertDialogAction class="border bg-red-100 text-red-500 hover:cursor-pointer" @click="confirmDelete">
          <Icon name="lucide:trash" class="mr-2 size-4" />
          Yes, delete
        </AlertDialogAction>
      </AlertDialogFooter>
    </AlertDialogContent>
  </AlertDialog>
</template>
