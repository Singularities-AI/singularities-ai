<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Header from '~/components/Header.vue'
import type { User } from '~/interfaces/User'

definePageMeta({
  layout: 'blank',
})

const usersStore = useUsersStore()
const users = ref<User[]>([])
const loading = ref(false)

// Mock data for development
const mockUsers: User[] = [
  { email: 'admin@example.com', context: null },
  { email: 'user1@example.com', context: null },
  { email: 'user2@example.com', context: null },
  { email: 'test@example.com', context: null },
  { email: 'demo@example.com', context: null },
]

onMounted(async () => {
  loading.value = true

  // Use mock data for now
  users.value = mockUsers

  // Uncomment when API is ready
  // const result = await usersStore.list()
  // if (result.success && Array.isArray(result.response)) {
  //   users.value = result.response
  // }

  loading.value = false
})

async function deleteUser(email: string) {
  if (!confirm(`Are you sure you want to delete user: ${email}?`))
    return

  loading.value = true

  // Mock deletion for now
  users.value = users.value.filter(user => user.email !== email)

  // Uncomment when API is ready
  // const result = await usersStore.delete(email)
  // if (!result.success) {
  //   alert('Error deleting user: ' + result.response)
  // }

  loading.value = false
}
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <main class="flex-1 overflow-auto p-4">
        <div class="flex flex-col gap-4">
          <div>
            <h1 class="text-2xl font-bold">
              Users Management
            </h1>
            <p class="text-muted-foreground">
              Manage users in your system
            </p>
          </div>

          <div class="border rounded-lg">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Email</TableHead>
                  <TableHead class="w-[100px]">
                    Actions
                  </TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                <TableRow v-if="loading">
                  <TableCell colspan="2" class="h-24 text-center">
                    <div class="flex items-center justify-center">
                      <Icon name="lucide:loader-2" class="mr-2 size-4 animate-spin" />
                      Loading...
                    </div>
                  </TableCell>
                </TableRow>
                <TableRow v-else-if="users.length === 0">
                  <TableCell colspan="2" class="h-24 text-center text-muted-foreground">
                    No users found
                  </TableCell>
                </TableRow>
                <TableRow v-for="user in users" v-else :key="user.email">
                  <TableCell class="font-medium">
                    {{ user.email }}
                  </TableCell>
                  <TableCell>
                    <Button
                      variant="ghost"
                      size="icon"
                      class="h-8 w-8 text-red-500 hover:bg-red-50 hover:text-red-600"
                      :disabled="loading"
                      @click="deleteUser(user.email)"
                    >
                      <Icon name="lucide:trash" class="size-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </div>

          <div class="text-sm text-muted-foreground">
            Total users: {{ users.length }}
          </div>
        </div>
      </main>
    </div>
  </div>
</template>
