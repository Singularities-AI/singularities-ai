<script setup lang="ts">
interface Item {
  title: string
  href: string
}

const sidebarNavItems: Item[] = [
  {
    title: 'Analytics (soon)',
    href: '#',
  },
  {
    title: 'Benchmark (soon)',
    href: '#',
  },
  {
    title: '',
    href: '#',
  },
  {
    title: 'Users',
    href: '/admin/users',
  },
  {
    title: 'Profils (soon)',
    href: '#',
  },
  {
    title: 'Models',
    href: '/admin/models',
  },
  {
    title: 'Agents (soon)',
    href: '#',
  },
  {
    title: 'Knownledge bases (soon)',
    href: '#',
  },
  {
    title: 'Supports (soon)',
    href: '#',
  },
]
</script>

<template>
  <nav class="flex lg:flex-col space-x-2 lg:space-x-0 lg:space-y-1">
    <Button
      v-for="item in sidebarNavItems"
      :key="item.title"
      as="a"
      :href="item.href"
      variant="ghost"
      :class="cn(
        'w-full text-left justify-start',
        $route.path === `${item.href}` && 'bg-muted hover:bg-muted',
      )"
    >
      {{ item.title }}
    </Button>
  </nav>
</template>
