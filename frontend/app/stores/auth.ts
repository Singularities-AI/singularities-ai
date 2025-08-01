import { defineStore } from 'pinia'
import Cookies from 'universal-cookie'
import type { User } from '~/interfaces/User'

const cookies = new Cookies()

export const useAuthStore = defineStore('auth', {
  state: () => ({
    roles: [] as string[],
  }),

  actions: {
    async generateToken(email: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await fetch(`${config.public.apiUrl}/web/auth/generate`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email }),
        })

        if (!response.ok)
          return { success: false, message: 'An error has occurred. Please try again later.' }
        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: 'An error has occurred. Please try again later.' }
      }
    },

    async validCode(email: string, code: string): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await fetch(`${config.public.apiUrl}/web/auth/token`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, code }),
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

        // put token in cookie
        cookies.set('token', await response.text())

        // load roles
        this.me()

        return { success: true }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async me(): Promise<{ success: boolean, response?: User | string }> {
      const config = useRuntimeConfig()

      try {
        const userData = await useSecureFetch<User>(`${config.public.apiUrl}/web/auth/me`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${cookies.get('token')}`,
            'Content-Type': 'application/json',
          },
        })

        this.roles = userData.roles
        return { success: true, response: userData }
      }
      catch (error: any) {
        return { success: false, response: error.message }
      }
    },

    logout(): void {
      cookies.remove('token')
      useRouter().push('/login')
    },

    isAuthenticated(): boolean {
      return !!cookies.get('token')
    },

    isAdmin(): boolean {
      return this.roles.includes('ROLE_ADMIN')
    },
  },
})
