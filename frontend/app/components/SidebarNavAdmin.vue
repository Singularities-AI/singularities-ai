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
    title: t('adminSidebar.analytics'),
    to: localePath('admin-analytics'),
  },
  {
    title: t('adminSidebar.benchmark'),
    to: '#',
  },
  {
    title: '',
    to: '#',
  },
  {
    title: t('adminSidebar.users'),
    to: localePath('admin-users'),
  },
  {
    title: t('adminSidebar.profiles'),
    to: '#',
  },
  {
    title: t('adminSidebar.models'),
    to: localePath('admin-models'),
  },
  {
    title: t('adminSidebar.agents'),
    to: localePath('admin-agents'),
  },
  {
    title: t('adminSidebar.knowledgeBases'),
    to: '#',
  },
  {
    title: t('adminSidebar.supports'),
    to: '#',
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
