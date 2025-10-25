<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { z } from 'zod'
import { useI18n } from 'vue-i18n'
import { useUserStore } from '~/stores/user'
import { useSettingStore } from '~/stores/setting'
import { toast } from '@/composables/useToast'
import { Button } from '@/components/ui/button'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Pagination, PaginationContent, PaginationItem, PaginationNext, PaginationPrev } from '@/components/ui/pagination'
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle } from '@/components/ui/alert-dialog'
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Badge } from '@/components/ui/badge'

const { t } = useI18n()

definePageMeta({ layout: 'admin', middleware: 'auth' })

const userStore = useUserStore()
const settingStore = useSettingStore()

onMounted(() => {
  loadPage(1)
  settingStore.list('AUTH_AUTHORIZED_DOMAIN')
})

const currentPage = ref(1)
const itemsPerPage = 20
const totalPages = computed(() => userStore.page?.totalPages || 1)

const userToDelete = ref<string | null>(null)
const showDeleteDialog = ref(false)

// Domain modal
const showDomainDialog = ref(false)
const newDomain = ref('')
const domainError = ref('')

const domainSchema = z.string().regex(
  /^(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\.)+[a-z]{2,}$/i,
  'Please enter a valid domain (e.g., google.com)',
)

watch(newDomain, (value) => {
  const result = domainSchema.safeParse(value)
  domainError.value = result.success ? '' : result.error?.errors?.[0]?.message || 'Unknown error'
})

watch(showDomainDialog, (newVal, oldVal) => {
  if (oldVal === true && newVal === false)
    resetDomainDialog()
})

async function resetDomainDialog() {
  newDomain.value = ''
}

async function loadPage(pageNumber: number) {
  currentPage.value = pageNumber
  await userStore.list(pageNumber - 1, itemsPerPage)
}

async function confirmDelete() {
  if (!userToDelete.value)
    return
  const { success, message } = await userStore.remove(userToDelete.value)
  if (!success) {
    toast({
      title: t('adminUsers.error'),
      description: message || t('adminUsers.unableToDeleteUser'),
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: t('adminUsers.deleted'),
      description: t('adminUsers.userDeleted'),
    })
    await loadPage(currentPage.value)
  }
  userToDelete.value = null
  showDeleteDialog.value = false
}

async function saveDomain() {
  if (!newDomain.value)
    return

  const result = domainSchema.safeParse(newDomain.value)
  if (!result.success)
    return

  // Check if the domain already exists
  const existingDomains = settingStore.settings.AUTH_AUTHORIZED_DOMAIN || []
  if (existingDomains.includes(newDomain.value)) {
    toast({
      title: t('adminUsers.error'),
      description: t('adminUsers.domainExists'),
      variant: 'destructive',
    })
    return
  }

  const { success, message } = await settingStore.addValue('AUTH_AUTHORIZED_DOMAIN', newDomain.value)
  resetDomainDialog()

  if (!success) {
    toast({
      title: t('adminUsers.error'),
      description: message || t('adminUsers.unableToCreateDomain'),
      variant: 'destructive',
    })
  }
}

async function removeDomain(domain: string) {
  try {
    await settingStore.removeValue('AUTH_AUTHORIZED_DOMAIN', domain)
    toast({
      title: t('adminUsers.deleted'),
      description: t('adminUsers.domainDeleted', { domain }),
    })
  }
  catch (error: any) {
    toast({
      title: t('adminUsers.error'),
      description: error.message || t('adminUsers.unableToDeleteDomain', { domain }),
      variant: 'destructive',
    })
  }
}
</script>

<template>
  <main class="grid flex-1 gap-4 overflow-auto p-4">
    <div class="relative flex flex-col gap-4">
      <div class="flex items-center justify-between">
        <h1 class="text-xl font-semibold">
          {{ t('adminUsers.title') }} | {{ userStore.page?.totalElements || 0 }}
        </h1>

        <Dialog v-model:open="showDomainDialog">
          <DialogTrigger as-child>
            <Button variant="outline">
              <Icon name="lucide:plus" class="mr-2 size-5" />
              {{ t('adminUsers.authorizedDomains') }}
            </Button>
          </DialogTrigger>
          <DialogContent class="max-w-lg">
            <DialogHeader>
              <DialogTitle>{{ t('adminUsers.authorizedDomains') }}</DialogTitle>
              {{ t('adminUsers.domainDescription') }}
            </DialogHeader>

            <!-- Authorized domains -->
            <div class="mt-4 border rounded-md">
              <div
                v-for="domain in settingStore.settings.AUTH_AUTHORIZED_DOMAIN || []"
                :key="domain"
                class="flex items-center justify-between border-b px-4 py-2 last:border-b-0"
              >
                <span>{{ domain }}</span>
                <Button
                  class="text-red-500 hover:cursor-pointer focus:bg-red-100 hover:!bg-red-100 hover:!text-red-700"
                  variant="ghost" size="sm" @click="removeDomain(domain)"
                >
                  <Icon name="lucide:trash" class="mr-1 size-4" />
                  {{ t('adminUsers.deleteDomain') }}
                </Button>
              </div>
            </div>

            <div class="mt-4 flex flex-col gap-1">
              <div class="flex gap-2">
                <Input v-model="newDomain" :placeholder="t('adminUsers.domainError')" />
                <Button class="text-black" @click="saveDomain">
                  <Icon name="lucide:plus" class="mr-2 size-5" />{{ t('adminUsers.addDomain') }}
                </Button>
              </div>
              <p v-if="domainError" class="text-sm text-red-500">
                {{ domainError }}
              </p>
            </div>
          </DialogContent>
        </Dialog>
      </div>

      <!-- Users list -->
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>{{ t('adminUsers.uuid') }}</TableHead>
            <TableHead>{{ t('adminUsers.email') }}</TableHead>
            <TableHead>{{ t('adminUsers.roles') }}</TableHead>
            <TableHead>{{ t('adminUsers.lastLogin') }}</TableHead>
            <TableHead class="text-right">
              {{ t('adminUsers.actions') }}
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow v-for="user in userStore.users" :key="user.id">
            <TableCell>{{ user.id }}</TableCell>
            <TableCell>{{ user.email }}</TableCell>
            <TableCell>
              <template v-if="user.roles?.length">
                <div class="flex flex-wrap gap-1">
                  <Badge
                    v-for="role in user.roles"
                    :key="role"
                    variant="outline"
                    class="p-1"
                  >
                    {{ role.replace(/^ROLE_/, '') }}
                  </Badge>
                </div>
              </template>
              <template v-else>
                <Badge variant="outline" class="p-1 text-gray-400 italic">
                  {{ t('adminUsers.noRoles') }}
                </Badge>
              </template>
            </TableCell>

            <TableCell>
              <span v-if="user.lastLogin">
                {{ new Date(user.lastLogin).toLocaleString() }}
              </span>
              <span v-else class="text-gray-400 italic">
                {{ t('adminUsers.never') }}
              </span>
            </TableCell>

            <TableCell class="text-right">
              <Button
                variant="destructive"
                size="sm"
                class="text-red-500 hover:cursor-pointer focus:bg-red-100 hover:!bg-red-100 hover:!text-red-700"
                @click="() => { userToDelete = user.id; showDeleteDialog = true }"
              >
                <Icon name="lucide:trash" class="mr-2 size-4" />
                {{ t('adminUsers.deleteUser') }}
              </Button>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>

      <!-- Pagination -->
      <Pagination v-if="totalPages > 1" class="mt-4 flex justify-end">
        <PaginationContent>
          <PaginationItem>
            <PaginationPrev :disabled="currentPage === 1" @click="loadPage(currentPage - 1)" />
          </PaginationItem>
          <PaginationItem v-for="page in totalPages" :key="page">
            <Button
              :variant="page === currentPage ? 'default' : 'outline'"
              size="sm"
              class="text-black"
              @click="loadPage(page)"
            >
              {{ page }}
            </Button>
          </PaginationItem>
          <PaginationItem>
            @
            <PaginationNext :disabled="currentPage === totalPages" @click="loadPage(currentPage + 1)" />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  </main>

  <!-- Delete user dialog -->
  <AlertDialog v-model:open="showDeleteDialog">
    <AlertDialogContent>
      <AlertDialogHeader>
        <AlertDialogTitle>{{ t('adminUsers.confirmDelete') }}</AlertDialogTitle>
        <AlertDialogDescription>
          {{ t('adminUsers.deleteUserDescription') }}
        </AlertDialogDescription>
      </AlertDialogHeader>
      <AlertDialogFooter>
        <AlertDialogCancel @click="showDeleteDialog = false">
          {{ t('adminUsers.cancel') }}
        </AlertDialogCancel>
        <AlertDialogAction class="border bg-red-100 text-red-500 hover:cursor-pointer" @click="confirmDelete">
          <Icon name="lucide:trash" class="mr-2 size-4" />
          {{ t('adminUsers.yesDelete') }}
        </AlertDialogAction>
      </AlertDialogFooter>
    </AlertDialogContent>
  </AlertDialog>
</template>
