import { defineStore } from 'pinia'
import type { Agent } from '~/interfaces/Agent'
import type { Page } from '~/interfaces/Page'

export const useAgentStore = defineStore('agent', {
  state: () => ({
    agents: [] as Agent[],
    page: null as Page<Agent> | null,
  }),

  actions: {
    async list(pageNumber = 0, pageSize = 20, append = false): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await useSecureFetch<Page<Agent>>(
          `${config.public.apiUrl}/web/agents?page=${pageNumber}&size=${pageSize}&sort=creationDate,desc`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${useCookie('token').value}`,
            },
          },
        )

        if (append)
          this.agents = [...this.agents, ...response.content]
        else
          this.agents = response.content

        this.page = response
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async create(form: any): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch<string[]>(
          `${config.public.apiUrl}/web/agents`,
          {
            method: 'POST',
            body: JSON.stringify(form),
            headers: {
              'Authorization': `Bearer ${useCookie('token').value}`,
              'Content-Type': 'application/json',
            },
          },
        )

        await this.list()
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async update(uuid: string, form: any): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch<string[]>(
          `${config.public.apiUrl}/web/agents/${uuid}`,
          {
            method: 'PUT',
            body: JSON.stringify(form),
            headers: {
              'Authorization': `Bearer ${useCookie('token').value}`,
              'Content-Type': 'application/json',
            },
          },
        )

        await this.list()
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async delete(uuid: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch(`${config.public.apiUrl}/web/agents/${uuid}`, {
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
