<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useLocalePath } from '#i18n'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const auth = useAuthStore()
const route = useRoute()
const { t } = useI18n()
const localePath = useLocalePath()

const isActive = (path: string): boolean => route.path.startsWith(path)
</script>

<template>
  <aside class="inset-y fixed left-0 z-20 h-full w-[55px] flex flex-col border-r">
    <div class="border-b p-2">
      <Button variant="outline" size="icon" aria-label="Home">
        <img src="/favicon.ico">
      </Button>
    </div>
    <nav class="grid gap-1 p-2">
      <Tooltip>
        <TooltipTrigger as-child>
          <Button
            variant="ghost" size="icon" class="rounded-lg"
            :class="[{ 'bg-muted': isActive('/chats') }]"
            aria-label="Chats" @click=" router.push(localePath({ name: 'chats-uuid', params: { uuid: 'new' } }))"
          >
            <Icon name="lucide:square-terminal" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          {{ t('sidebar.chats') }}
        </TooltipContent>
      </Tooltip>

      <Tooltip>
        <TooltipTrigger as-child>
          <Button
            variant="ghost" size="icon" class="rounded-lg" aria-label="Models"
            :class="[{ 'bg-muted': isActive('/agents') }]"
            @click="router.push(localePath('agents'))"
          >
            <Icon name="lucide:bot" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          {{ t('sidebar.agents') }}
        </TooltipContent>
      </Tooltip>

      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="rounded-lg" aria-label="Knowledge bases">
            <Icon name="lucide:file-stack" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          {{ t('sidebar.knowledgeBases') }}
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
          {{ t('sidebar.help') }}
        </TooltipContent>
      </Tooltip>

      <Tooltip v-if="auth.isAdmin()">
        <TooltipTrigger as-child>
          <Button
            variant="ghost"
            size="icon" class="mt-auto rounded-lg" aria-label="Configuration" @click="router.push(localePath('admin-models'))"
          >
            <Icon name="lucide:settings" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          {{ t('sidebar.configuration') }}
        </TooltipContent>
      </Tooltip>

      <Tooltip>
        <TooltipTrigger as-child>
          <Button variant="ghost" size="icon" class="mt-auto rounded-lg" aria-label="Account" @click="router.push(localePath('account'))">
            <Icon name="lucide:square-user" class="size-5" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right" :side-offset="5" class="bg-black">
          {{ t('sidebar.account') }}
        </TooltipContent>
      </Tooltip>
    </nav>
  </aside>
</template>
