<script setup lang="ts">
const router = useRouter()
const auth = useAuthStore()
</script>

<template>
  <aside class="inset-y fixed left-0 z-20 h-full w-[55px] flex flex-col border-r">
    <div class="border-b p-2">
      <Button variant="outline" size="icon" aria-label="Home">
        <img src="public/favicon.ico">
      </Button>
    </div>
    <nav class="grid gap-1 p-2">
      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="rounded-lg bg-muted" aria-label="Chats" @click="router.push('/')">
            <Icon name="lucide:square-terminal" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          Chats
        </TooltipContent>
      </Tooltip>

      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="rounded-lg" aria-label="Models" @click="router.push('/agents')">
            <Icon name="lucide:bot" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          Agents
        </TooltipContent>
      </Tooltip>

      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="rounded-lg" aria-label="Knowledge bases">
            <Icon name="lucide:file-stack" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          Knowledge bases (Coming soon)
        </TooltipContent>
      </Tooltip>
    </nav>

    <nav class="grid mt-auto gap-1 p-2">
      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="mt-auto rounded-lg" aria-label="Help">
            <Icon name="lucide:life-buoy" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          Help (Coming soon)
        </TooltipContent>
      </Tooltip>

      <Tooltip v-if="auth.isAdmin()">
        <TooltipTrigger as-child>
          <Button
            variant="ghost"
            size="icon" class="mt-auto rounded-lg" aria-label="Configuration" @click="router.push('/admin/models')"
          >
            <Icon name="lucide:settings" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          Configuration
        </TooltipContent>
      </Tooltip>

      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="mt-auto rounded-lg" aria-label="Account" @click="router.push('/account')">
            <Icon name="lucide:square-user" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          Account
        </TooltipContent>
      </Tooltip>
    </nav>
  </aside>
</template>
