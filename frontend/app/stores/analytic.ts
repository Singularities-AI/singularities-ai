import { defineStore } from 'pinia'
import type { AnalyticStats } from '../interfaces/AnalyticStats'

interface DateFilter {
  start: string
  end: string
}

export const useAnalyticStore = defineStore('model', {
  state: () => ({
    stats: {} as AnalyticStats,
  }),

  actions: {
    async getStats(dateFilter?: DateFilter): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      const queryParams = new URLSearchParams()
      if (dateFilter?.start)
        queryParams.append('start', dateFilter.start)

      if (dateFilter?.end)
        queryParams.append('end', dateFilter.end)

      try {
        const data = await useSecureFetch<Model[]>(`${config.public.apiUrl}/web/analytics/stats?${queryParams.toString()}`, {
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        })

        this.stats = data
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },
  },
})
