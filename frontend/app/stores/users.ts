import { defineStore } from 'pinia'
import Cookies from 'universal-cookie'
import type { User } from '~/interfaces/User'

const cookies = new Cookies()

export const useUsersStore = defineStore('users', {
  state: () => ({
    users: [] as User[],
  }),

  actions: {
    async list(): Promise<{ success: boolean, response?: Array<User> | string }> {
      const config = useRuntimeConfig()

      try {
        const response = await fetch(`${config.public.apiUrl}/web/users`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${cookies.get('token')}`,
            'Content-Type': 'application/json',
          },
        })

        if (!response.ok) {
          let errorMessage = 'An error has occurred. Please try again later.'
          const contentType = response.headers.get('Content-Type') || ''

          if (contentType.includes('application/json')) {
            const errorData = await response.json()
            errorMessage = errorData.message || errorMessage
          }
          else {
            errorMessage = await response.text()
          }
          throw new Error(errorMessage)
        }

        const json = await response.json()
        this.users = json
        return { success: true, response: json }
      }
      catch (error: any) {
        return { success: false, response: error.message }
      }
    },

    async delete(email: string): Promise<{ success: boolean, response?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await fetch(`${config.public.apiUrl}/web/users/${email}`, {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer ${cookies.get('token')}`,
            'Content-Type': 'application/json',
          },
        })

        if (!response.ok) {
          let errorMessage = 'An error has occurred. Please try again later.'
          const contentType = response.headers.get('Content-Type') || ''

          if (contentType.includes('application/json')) {
            const errorData = await response.json()
            errorMessage = errorData.message || errorMessage
          }
          else {
            errorMessage = await response.text()
          }
          throw new Error(errorMessage)
        }

        // Remove user from local state
        this.users = this.users.filter(user => user.email !== email)
        return { success: true }
      }
      catch (error: any) {
        return { success: false, response: error.message }
      }
    },
  },
})
