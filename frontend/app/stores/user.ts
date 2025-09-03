import { defineStore } from 'pinia'
import type { User } from '~/interfaces/User'
import type { Page } from '~/interfaces/Page'

export const useUserStore = defineStore('user', {
  state: () => ({
    users: [] as User[],
    page: null as Page<User> | null,
  }),

  actions: {
    async list(pageNumber = 0, pageSize = 20): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await useSecureFetch<Page<User>>(
          `${config.public.apiUrl}/web/users?page=${pageNumber}&size=${pageSize}&sort=creationDate,desc`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${useCookie('token').value}`,
            },
          },
        )

        this.users = response.content
        this.page = response
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async remove(uuid: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        await useSecureFetch(`${config.public.apiUrl}/web/users/${uuid}`, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${useCookie('token').value}`,
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
