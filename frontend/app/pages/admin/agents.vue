<script setup lang="ts">
import { onMounted, onUnmounted, ref, watch } from 'vue'
import * as z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useI18n } from 'vue-i18n'
import { useAgentStore } from '~/stores/agent'
import { useModelStore } from '~/stores/model'
import { toast } from '@/composables/useToast'

definePageMeta({ layout: 'admin', middleware: 'auth' })

const { t } = useI18n()

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
    icon: z.string().min(1, t('adminAgents.icon')).max(254, t('adminAgents.icon')),
    name: z.string().min(10, t('adminAgents.name')).max(45, t('adminAgents.name')),
    description: z.string().min(30, t('adminAgents.description')).max(240, t('adminAgents.description')),
    prompt: z.string().min(75, t('adminAgents.prompt')).max(4000, t('adminAgents.prompt')),
    modelUUID: z.string().min(1, t('adminAgents.model')),
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
      title: t('adminAgents.success'),
      description: isEditing.value ? t('adminAgents.agentUpdated') : t('adminAgents.agentCreated'),
    })
  }
  else {
    toast({
      title: t('adminAgents.error'),
      description: message || t('adminAgents.somethingWentWrong'),
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
      title: t('adminAgents.error'),
      description: message || t('adminAgents.unableToDelete'),
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: t('adminAgents.deleted'),
      description: t('adminAgents.agentDeleted'),
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
        {{ t('adminAgents.title') }} | {{ agentStore.agents?.length || 0 }}
      </h1>

      <Dialog v-model:open="showCreateAgentDialog">
        <DialogTrigger as-child>
          <Button variant="outline" @click="isEditing = false">
            <Icon name="lucide:bot" class="mr-2 size-5" />
            {{ t('adminAgents.createAgent') }}
          </Button>
        </DialogTrigger>
        <DialogContent class="max-w-lg">
          <DialogHeader>
            <DialogTitle>
              {{ isEditing ? t('adminAgents.updateAgent') : t('adminAgents.createAgent') }}
            </DialogTitle>
          </DialogHeader>

          <Form
            :validation-schema="agentFormSchema"
            :initial-values="createForm" class="mt-2 space-y-3"
            @submit="onSubmit"
          >
            <FormField v-slot="{ componentField }" name="icon">
              <FormItem class="flex flex-col">
                <FormLabel>{{ t('adminAgents.icon') }}</FormLabel>
                <FormControl class="mt-1">
                  <IconPicker v-bind="componentField" />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField v-slot="{ componentField }" name="name">
              <FormItem>
                <FormLabel>{{ t('adminAgents.name') }}</FormLabel>
                <FormControl>
                  <Input
                    type="text"
                    :placeholder="t('adminAgents.namePlaceholder')"
                    v-bind="componentField"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField v-slot="{ componentField }" name="description">
              <FormItem>
                <FormLabel>{{ t('adminAgents.description') }}</FormLabel>
                <FormControl>
                  <Textarea
                    class="min-h-[4.5rem]"
                    :placeholder="t('adminAgents.descriptionPlaceholder')"
                    v-bind="componentField"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            </FormField>

            <FormField v-slot="{ componentField }" name="modelUUID">
              <FormItem>
                <FormLabel>{{ t('adminAgents.model') }}</FormLabel>
                <FormControl>
                  <Select v-bind="componentField">
                    <SelectTrigger id="model">
                      <SelectValue :placeholder="t('adminAgents.selectModel')" />
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
                <FormLabel>{{ t('adminAgents.prompt') }}</FormLabel>
                <FormControl>
                  <Textarea
                    class="min-h-[9.5rem]"
                    :placeholder="t('adminAgents.promptPlaceholder')"
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
                  {{ t('adminAgents.saving') }}
                </template>
                <template v-else>
                  {{ isEditing ? t('adminAgents.update') : t('adminAgents.save') }}
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
              {{ t('adminAgents.delete') }}
            </Button>

            <Button
              variant="secondary"
              size="sm"
              class="hover:cursor-pointer focus:bg-gray-100 hover:!bg-gray-100"
              @click="editAgent(agent)"
            >
              <Icon name="lucide:pencil" class="mr-2 size-5" />
              {{ t('adminAgents.updateButton') }}
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
          {{ t('adminAgents.noResults') }}
        </h2>
        <p class="text-sm text-muted-foreground">
          {{ t('adminAgents.noResultsDescription') }}
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
        <AlertDialogTitle>{{ t('adminAgents.confirmDeletion') }}</AlertDialogTitle>
        <AlertDialogDescription>
          {{ t('adminAgents.deleteAgentDescription') }}
        </AlertDialogDescription>
      </AlertDialogHeader>
      <AlertDialogFooter>
        <AlertDialogCancel @click="showDeleteDialog = false">
          {{ t('adminAgents.cancel') }}
        </AlertDialogCancel>
        <AlertDialogAction class="border bg-red-100 text-red-500 hover:cursor-pointer" @click="confirmDelete">
          <Icon name="lucide:trash" class="mr-2 size-4" />
          {{ t('adminAgents.yesDelete') }}
        </AlertDialogAction>
      </AlertDialogFooter>
    </AlertDialogContent>
  </AlertDialog>
</template>
