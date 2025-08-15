<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { Button } from '@/components/ui/button'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Pagination, PaginationContent, PaginationItem, PaginationNext, PaginationPrev } from '@/components/ui/pagination'
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger } from '@/components/ui/alert-dialog'

definePageMeta({
  layout: 'admin',
  middleware: 'auth',
})

const userStore = useUserStore()

const currentPage = ref(1)
const itemsPerPage = 20
const totalPages = computed(() => userStore.page?.totalPages || 1)

const userToDelete = ref<string | null>(null)
const showDeleteDialog = ref(false)

async function loadPage(pageNumber: number) {
  currentPage.value = pageNumber
  await userStore.list(pageNumber - 1, itemsPerPage)
}

onMounted(() => {
  loadPage(1)
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
</script>

<template>
  <main class="grid flex-1 gap-4 overflow-auto p-4">
    <div class="relative flex flex-col gap-4">
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
                class="text-red-500 hover:cursor-pointer focus:bg-red-100 hover:!bg-red-100 hover:!text-red-700"
                size="sm"
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
            <PaginationPrev
              :disabled="currentPage === 1"
              @click="loadPage(currentPage - 1)"
            />
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
            <PaginationNext
              :disabled="currentPage === totalPages"
              @click="loadPage(currentPage + 1)"
            />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  </main>

  <!-- Alert Dialog -->
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
        <AlertDialogAction
          class="border bg-red-100 text-red-500 hover:cursor-pointer"
          @click="confirmDelete"
        >
          <Icon name="lucide:trash" class="mr-2 size-4" />
          Yes, delete
        </AlertDialogAction>
      </AlertDialogFooter>
    </AlertDialogContent>
  </AlertDialog>
</template>
