<script setup lang="ts">
import { onMounted, watch } from 'vue'
import * as z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
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

const agentFormSchema = toTypedSchema(
  z.object({
    icon: z.string().min(1, 'Icon is required.').max(254, 'Icon must be at most 254 characters.'),
    name: z.string().min(10, 'Name must be at least 10 characters.').max(45, 'Name must be at most 45 characters.'),
    description: z.string().min(30, 'Description must be at least 30 characters.').max(240, 'Description must be at most 240 characters.'),
    prompt: z.string().min(75, 'Prompt must be at least 75 characters.').max(4000, 'Prompt must be at most 4000 characters.'),
    modelUUID: z.string().min(1, 'A model must be selected.'),
  }),
)

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

async function onSubmit(values: typeof emptyForm) {
  if (isSubmitting.value)
    return

  isSubmitting.value = true
  let result

  if (isEditing.value && agentToEdit.value)
    result = await agentStore.update(agentToEdit.value.id, values)
  else
    result = await agentStore.create(values)

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
        Agents | {{ agentStore.page?.totalElements || 0 }}
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
            :validation-schema="agentFormSchema"
            :initial-values="createForm" class="mt-2 space-y-3"
            @submit="onSubmit"
          >
            <FormField v-slot="{ componentField }" name="icon">
              <FormItem class="flex flex-col">
                <FormLabel>Icon</FormLabel>
                <FormControl class="mt-1">
                  <IconPicker v-bind="componentField" />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField v-slot="{ componentField }" name="name">
              <FormItem>
                <FormLabel>Name</FormLabel>
                <FormControl>
                  <Input
                    type="text"
                    placeholder="Ex: Marketing Expert"
                    v-bind="componentField"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField v-slot="{ componentField }" name="description">
              <FormItem>
                <FormLabel>Description</FormLabel>
                <FormControl>
                  <Textarea
                    class="min-h-[4.5rem]"
                    placeholder="Ex: An AI agent specializing in marketing strategy and automation, optimizing campaigns and audiences."
                    v-bind="componentField"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField v-slot="{ componentField }" name="modelUUID">
              <FormItem>
                <FormLabel>Model</FormLabel>
                <FormControl>
                  <Select v-bind="componentField">
                    <SelectTrigger id="model">
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

            <FormField v-slot="{ componentField }" name="prompt">
              <FormItem>
                <FormLabel>Prompt</FormLabel>
                <FormControl>
                  <Textarea
                    class="min-h-[9.5rem]"
                    placeholder="Ex: You are a marketing expert..."
                    v-bind="componentField"
                  />
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

    <div
      v-if="agentStore.page?.totalElements > 0"
      class="grid gap-6 lg:grid-cols-2 sm:grid-cols-2"
    >
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
            <p class="mt-1 break-words text-sm text-muted-foreground">
              {{ agent.description }}
            </p>
            <p class="mt-3 flex items-center gap-1 text-sm text-muted-foreground">
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
    <div v-else class="items-center justify-center pt-10 text-center">
      <Icon
        name="lucide:bot"
        class="h-10 w-10 text-muted-foreground"
      />
      <div>
        <h2 class="text-xl font-semibold">
          No result found
        </h2>
        <p class="text-sm text-muted-foreground">
          Click on the Create Agent button to set up our first agent
        </p>
      </div>
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
