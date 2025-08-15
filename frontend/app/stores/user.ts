import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { User } from '~/interfaces/User'
import type { Page } from '~/interfaces/Page'

export const useUserStore = defineStore('user', () => {
  const users = ref<User[]>([])
  const page = ref<Page<User> | null>(null)

  async function list(pageNumber = 0, pageSize = 20) {
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

      users.value = response.content
      page.value = response
    }
    catch (e) {}
  }

  async function remove(uuid: string): Promise<{ success: boolean, message?: string }> {
    const config = useRuntimeConfig()

    try {
      await useSecureFetch(`${config.public.apiUrl}/web/users/${uuid}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${useCookie('token').value}`,
        },
      })
      await list()
      return { success: true }
    }
    catch (error: any) {
      return { success: false, message: error.message }
    }
  }

  return { users, page, list, remove }
})
