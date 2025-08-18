<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { z } from 'zod'
import { Button } from '@/components/ui/button'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Pagination, PaginationContent, PaginationItem, PaginationNext, PaginationPrev } from '@/components/ui/pagination'
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle } from '@/components/ui/alert-dialog'
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Badge } from '@/components/ui/badge'

definePageMeta({
  layout: 'admin',
  middleware: 'auth',
})

const userStore = useUserStore()
const settingStore = useSettingStore()

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
  /^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,}$/,
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

onMounted(() => {
  loadPage(1)
  settingStore.list('AUTH_AUTHORIZED_DOMAIN')
})

async function confirmDelete() {
  if (!userToDelete.value)
    return
  const { success, message } = await userStore.remove(userToDelete.value)
  if (!success) {
    toast({
      title: 'Error',
      description: message || 'Unable to delete this user.',
      variant: 'destructive',
    })
  }
  else {
    toast({
      title: 'Deleted',
      description: 'User deleted successfully.',
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
      title: 'Error',
      description: 'This domain already exists.',
      variant: 'destructive',
    })
    return
  }

  const { success, message } = await settingStore.addValue('AUTH_AUTHORIZED_DOMAIN', newDomain.value)
  resetDomainDialog()

  if (!success) {
    toast({
      title: 'Error',
      description: message || 'Unable to create this domain.',
      variant: 'destructive',
    })
  }
}

async function removeDomain(domain: string) {
  try {
    await settingStore.removeValue('AUTH_AUTHORIZED_DOMAIN', domain)
    toast({
      title: 'Deleted',
      description: `Domain "${domain}" deleted successfully.`,
    })
  }
  catch (error: any) {
    toast({
      title: 'Error',
      description: error.message || `Unable to delete domain "${domain}".`,
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
          Users
        </h1>

        <Dialog v-model:open="showDomainDialog">
          <DialogTrigger as-child>
            <Button variant="outline">
              <Icon name="lucide:plus" class="mr-2 size-5" />
              Authorized Domains
            </Button>
          </DialogTrigger>
          <DialogContent class="max-w-lg">
            <DialogHeader>
              <DialogTitle>Authorized Domains</DialogTitle>
              Authorized domains restrict the registration of new users to specific email addresses (e.g., @domain.com). To allow all domains, simply leave the list empty.
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
                  Delete
                </Button>
              </div>
            </div>

            <div class="mt-4 flex flex-col gap-1">
              <div class="flex gap-2">
                <Input v-model="newDomain" placeholder="example.com" />
                <Button class="text-black" @click="saveDomain">
                  <Icon name="lucide:plus" class="mr-2 size-5" />Add
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
            <TableHead>UUID</TableHead>
            <TableHead>Email</TableHead>
            <TableHead>Roles</TableHead>
            <TableHead class="text-right">
              Actions
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
                  No roles
                </Badge>
              </template>
            </TableCell>
            <TableCell class="text-right">
              <Button
                variant="destructive"
                size="sm"
                class="text-red-500 hover:cursor-pointer focus:bg-red-100 hover:!bg-red-100 hover:!text-red-700"
                @click="() => { userToDelete = user.id; showDeleteDialog = true }"
              >
                <Icon name="lucide:trash" class="mr-2 size-4" />
                Delete
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
        <AlertDialogTitle>Confirm deletion</AlertDialogTitle>
        <AlertDialogDescription>
          Are you sure you want to delete this user and all this data? This action cannot be undone.
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
