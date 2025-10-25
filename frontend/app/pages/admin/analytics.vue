<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { endOfDay, startOfDay, startOfYear, subDays } from 'date-fns'
import { useI18n } from 'vue-i18n'
import { useAnalyticStore } from '../../stores/analytic'

const { t } = useI18n()

definePageMeta({ layout: 'admin', middleware: 'auth' })

const analyticStore = useAnalyticStore()

const selectedDateRange = ref<'week' | 'month' | 'year' | 'custom'>('month')
const dateRange = ref<{ from: Date, to: Date } | null>(null)

function formatToISOString(date: Date): string {
  return date.toISOString()
}

const dateFilter = computed(() => {
  if (selectedDateRange.value === 'custom' && dateRange.value) {
    return {
      start: formatToISOString(dateRange.value.from),
      end: formatToISOString(dateRange.value.to),
    }
  }

  const now = new Date()
  let start: Date
  let end: Date

  switch (selectedDateRange.value) {
    case 'week':
      start = startOfDay(subDays(now, 6))
      end = endOfDay(now)
      break
    case 'month':
      start = startOfDay(subDays(now, 29))
      end = endOfDay(now)
      break
    case 'year':
      start = startOfDay(startOfYear(now))
      end = endOfDay(now)
      break
    default:
      start = startOfDay(subDays(now, 6))
      end = endOfDay(now)
  }

  return {
    start: formatToISOString(start),
    end: formatToISOString(end),
  }
})

const statsCards = computed(() => [
  {
    id: 'users-signup',
    title: t('adminAnalytics.usersSignup'),
    icon: 'lucide:user-round-plus',
    value: analyticStore.stats.usersRegister,
  },
  {
    id: 'users-signin',
    title: t('adminAnalytics.usersSignin'),
    icon: 'lucide:user-round-check',
    value: analyticStore.stats.usersLogin,
  },
  {
    id: 'total-chats',
    title: t('adminAnalytics.totalChats'),
    icon: 'lucide:square-terminal',
    value: analyticStore.stats.chats,
  },
  {
    id: 'total-messages',
    title: t('adminAnalytics.totalMessages'),
    icon: 'lucide:message-square-text',
    value: analyticStore.stats.messages,
  },
])

watch([dateFilter], () => {
  analyticStore.getStats(dateFilter.value)
})

onMounted(async () => {
  analyticStore.getStats(dateFilter.value)
})
</script>

<template>
  <main class="grid flex-1 gap-4 overflow-auto p-4">
    <div class="relative flex flex-col gap-4">
      <div class="flex items-center justify-between">
        <h1 class="text-xl font-semibold">
          {{ t('adminAnalytics.title') }}
        </h1>
        <!-- date filter -->
        <div class="flex items-center gap-4">
          <Tabs v-model="selectedDateRange">
            <TabsList>
              <TabsTrigger value="week">
                {{ t('adminAnalytics.week') }}
              </TabsTrigger>
              <TabsTrigger value="month">
                {{ t('adminAnalytics.month') }}
              </TabsTrigger>
              <TabsTrigger value="year">
                {{ t('adminAnalytics.year') }}
              </TabsTrigger>
            </TabsList>
          </Tabs>
        </div>
      </div>

      <section class="grid gap-4 lg:grid-cols-4 md:grid-cols-2">
        <Card
          v-for="stat in statsCards"
          :key="stat.id"
        >
          <CardHeader class="flex flex-row items-center justify-between pb-2 space-y-0">
            <CardTitle class="text-sm font-medium">
              {{ stat.title }}
            </CardTitle>
            <Icon :name="stat.icon" class="size-5 text-muted-foreground" />
          </CardHeader>

          <CardContent>
            <p class="text-2xl font-bold">
              {{ stat.value }}
            </p>
          </CardContent>
        </Card>
      </section>
    </div>
  </main>
</template>
