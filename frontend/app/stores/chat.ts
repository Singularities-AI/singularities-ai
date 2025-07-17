import { defineStore } from 'pinia'
import Cookies from 'universal-cookie'
import type { Chat } from '~/interfaces/Chat'

const cookies = new Cookies()

export const useChatStore = defineStore('chat', {
  state: () => ({}),

  actions: {
    async list(): Promise<{ success: boolean, response?: Array<Chat> | string }> {
      const config = useRuntimeConfig()

      try {
        const response = await fetch(`${config.public.apiUrl}/web/chats`, {
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

        // store response
        const json = await response.json()
        return { success: true, response: json }
      }
      catch (error: any) {
        return { success: false, response: error.message }
      }
    },
  },
})
