<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import { useRoute } from 'vue-router'
import { useLocalePath } from '#i18n'
import { cn } from '@/utils'

const { t } = useI18n()
const localePath = useLocalePath()
const route = useRoute()

interface Item {
  title: string
  to: string
}

const sidebarNavItems: Item[] = [
  {
    title: t('sidebarNav.account'),
    to: localePath('account'),
  },
  {
    title: t('sidebarNav.logout'),
    to: localePath('logout'),
  },
]
</script>

<template>
  <nav class="flex lg:flex-col space-x-2 lg:space-x-0 lg:space-y-1">
    <Button
      v-for="item in sidebarNavItems"
      :key="item.title"
      as="a"
      :href="item.to"
      variant="ghost"
      :class="cn(
        'w-full text-left justify-start',
        route.path === item.to && 'bg-muted hover:bg-muted',
      )"
    >
      {{ item.title }}
    </Button>
  </nav>
</template>
