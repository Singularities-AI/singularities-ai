import { defineStore } from 'pinia'
import type { Setting } from '~/interfaces/Setting'

export const useSettingStore = defineStore('setting', {
  state: () => ({
    settings: {} as Record<string, string[]>,
  }),

  actions: {
    async list(key: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await useSecureFetch<Setting>(
          `${config.public.apiUrl}/web/settings/${key}`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${useCookie('token').value}`,
            },
          },
        )

        this.settings[key] = response.values
        return { success: true }
      }
      catch (error: any) {
        this.settings[key] = []
        return { success: false, message: error.message }
      }
    },

    async addValue(key: string, value: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch<string[]>(
          `${config.public.apiUrl}/web/settings/${key}/values`,
          {
            method: 'POST',
            body: JSON.stringify([value]),
            headers: {
              'Authorization': `Bearer ${useCookie('token').value}`,
              'Content-Type': 'application/json',
            },
          },
        )

        await this.list(key)
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async removeValue(key: string, value: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch(
          `${config.public.apiUrl}/web/settings/${key}/values?value=${encodeURIComponent(value)}`,
          {
            method: 'DELETE',
            headers: {
              Authorization: `Bearer ${useCookie('token').value}`,
            },
          },
        )

        if (this.settings[key])
          this.settings[key] = this.settings[key].filter(v => v !== value)

        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },
  },
})
