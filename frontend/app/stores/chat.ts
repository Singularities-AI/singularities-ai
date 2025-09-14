import { defineStore } from 'pinia'
import type { Chat } from '~/interfaces/Chat'
import type { MessageResponse } from '~/interfaces/Message'
import type { Page } from '~/interfaces/Page'

export const useChatStore = defineStore('chat', {
  state: () => ({
    chats: [] as Chat[],
    page: null as Page<Chat> | null,
  }),

  actions: {
    async list(pageNumber = 0, pageSize = 20): Promise<{ success: boolean, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await useSecureFetch<Page<Chat>>(
          `${config.public.apiUrl}/web/chats?page=${pageNumber}&size=${pageSize}&sort=creationDate,desc`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${useCookie('token').value}`,
            },
          },
        )

        this.chats = response.content
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
        await useSecureFetch(`${config.public.apiUrl}/web/chats/${uuid}`, {
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

    async sendMessage(
      chatUUID: string | null,
      content: string,
      modelUUID: string,
      context: string | null,
      agentUUID: string | null,
    ): Promise<{ success: boolean, data?: MessageResponse, message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await useSecureFetch<MessageResponse>(
          `${config.public.apiUrl}/web/chats/messages`,
          {
            method: 'POST',
            body: JSON.stringify({ chatUUID, content, modelUUID, context, agentUUID }),
            headers: {
              'Authorization': `Bearer ${useCookie('token').value}`,
              'Content-Type': 'application/json',
            },
          },
        )

        return { success: true, data: response }
      }
      catch (error: any) {
        return { success: false, message: error.message }
      }
    },

    async listMessages(uuid: string): Promise<{ success: boolean, data?: MessageResponse[], message?: string }> {
      const config = useRuntimeConfig()

      try {
        const response = await useSecureFetch<MessageResponse[]>(
          `${config.public.apiUrl}/web/chats/${uuid}/messages`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${useCookie('token').value}`,
            },
          },
        )

        return { success: true, data: response }
      }
      catch (error: any) {
        return { success: false, message: error.message, data: [] }
      }
    },
  },
})
