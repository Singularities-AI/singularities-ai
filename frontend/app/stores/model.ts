import { defineStore } from 'pinia'
import type { Model } from '~/interfaces/Model'

export const useModelStore = defineStore('model', {
  state: () => ({
    models: [] as Model[],
  }),

  actions: {
    async listAvailable(): Promise<Model[]> {
      const config = useRuntimeConfig()
      try {
        return await useSecureFetch<Model[]>(`${config.public.apiUrl}/web/models/availables`, {
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        })
      }
      catch (error) {
        return []
      }
    },

    async list(): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const data = await useSecureFetch<Model[]>(`${config.public.apiUrl}/web/models`, {
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        })
        this.models = data
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async setDefault(uuid: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch(`${config.public.apiUrl}/web/models/${uuid}/default`, {
          method: 'PUT',
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        })
        await this.list()
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async download(id: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch(`${config.public.apiUrl}/web/models/${id}/download`, {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        })

        await this.list()
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async delete(id: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch(`${config.public.apiUrl}/web/models/${id}`, {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        })

        await this.list()
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },
  },
})
