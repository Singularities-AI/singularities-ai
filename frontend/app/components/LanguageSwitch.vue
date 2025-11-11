<script setup>
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'

const { locale, locales } = useI18n()
const switchLocalePath = useSwitchLocalePath()

const availableLocales = computed(() => {
  return locales.value
})

const getFlagEmoji = function (code) {
  const flags = {
    en: '🇬🇧',
    fr: '🇫🇷',
  }
  return flags[code] || '🏳️'
}

const getCurrentLocaleName = computed(() => {
  const current = locales.value.find(l => l.code === locale.value)
  return current ? `${getFlagEmoji(current.code)} ${current.name}` : ''
})
</script>

<template>
  <DropdownMenu>
    <DropdownMenuTrigger class="outline-none">
      <span class="cursor-pointer border border-gray-300 rounded px-2 py-1 dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-800">{{ getCurrentLocaleName }}</span>
    </DropdownMenuTrigger>
    <DropdownMenuContent>
      <DropdownMenuItem
        v-for="loc in availableLocales"
        :key="loc.code"
        class="hover:bg-gray-100 dark:hover:bg-gray-800"
        @click="$router.push(switchLocalePath(loc.code))"
      >
        <span class="cursor-pointer text-gray-800 dark:text-gray-200 hover:text-gray-800 dark:hover:text-gray-200">{{ getFlagEmoji(loc.code) }} {{ loc.name }}</span>
      </DropdownMenuItem>
    </DropdownMenuContent>
  </DropdownMenu>
</template>
