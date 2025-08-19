import { defineStore } from 'pinia'
import { reactive } from 'vue'
import type { Setting } from '~/interfaces/Setting'

export const useSettingStore = defineStore('setting', () => {
  const settings = reactive<Record<string, string[]>>({})
  const config = useRuntimeConfig()

  async function list(key: string) {
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
      settings[key] = response.values
    }
    catch (e) {
      settings[key] = []
    }
  }

  async function addValue(key: string, value: string): Promise<{ success: boolean, message?: string }> {
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

      await list(key)
      return { success: true }
    }
    catch (e: any) {
      return { success: false, message: e.message }
    }
  }

  async function removeValue(key: string, value: string): Promise<{ success: boolean, message?: string }> {
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

      if (settings[key])
        settings[key] = settings[key].filter(v => v !== value)

      return { success: true }
    }
    catch (e: any) {
      return { success: false, message: e.message }
    }
  }

  return {
    settings,
    list,
    addValue,
    removeValue,
  }
})
