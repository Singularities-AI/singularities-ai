<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'

const auth = useAuthStore()

function handleLogout() {
  auth.logout()
}

const userInitials = computed(() => {
  const firstName = auth.user?.firstName || ''
  const lastName = auth.user?.lastName || ''
  return `${firstName.charAt(0)}${lastName.charAt(0)}`.toUpperCase()
})
</script>

<template>
  <DropdownMenu>
    <DropdownMenuTrigger as-child>
      <Button variant="ghost" class="relative h-8 w-8 rounded-full">
        <Avatar class="h-8 w-8">
          <AvatarFallback>{{ userInitials }}</AvatarFallback>
        </Avatar>
      </Button>
    </DropdownMenuTrigger>
    <DropdownMenuContent class="w-56" align="end">
      <DropdownMenuLabel class="flex font-normal">
        <div class="flex flex-col space-y-1">
          <p class="text-sm font-medium leading-none">
            {{ auth.user?.firstName }} {{ auth.user?.lastName }}
          </p>
          <p class="text-xs text-muted-foreground leading-none">
            {{ auth.user?.email }}
          </p>
        </div>
      </DropdownMenuLabel>
      <DropdownMenuSeparator />
      <DropdownMenuGroup>
        <DropdownMenuItem>
          <NuxtLink key="account" href="/account" class="w-full">
            Account
          </NuxtLink>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <NuxtLink key="billing" href="/billing" class="w-full">
            Configuration
          </NuxtLink>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <NuxtLink key="invoice" href="/invoice" class="w-full">
            Invoices
          </NuxtLink>
        </DropdownMenuItem>
      </DropdownMenuGroup>
      <DropdownMenuSeparator />
      <DropdownMenuItem as="button" class="w-full cursor-pointer hover:bg-gray-200" @click="handleLogout">
        Déconnexion
      </DropdownMenuItem>
    </DropdownMenuContent>
  </DropdownMenu>
</template>
