import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Chat } from '~/interfaces/Chat'
import type { MessageResponse } from '~/interfaces/Message'
import type { Page } from '~/interfaces/Page'

export const useChatStore = defineStore('chat', () => {
  const chats = ref<Chat[]>([])
  const page = ref<Page<Chat> | null>(null)

  async function list(pageNumber = 0, pageSize = 20) {
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

      chats.value = response.content
      page.value = response
    }
    catch (e) {}
  }

  async function remove(uuid: string) {
    const config = useRuntimeConfig()

    try {
      await useSecureFetch(`${config.public.apiUrl}/web/chats/${uuid}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${useCookie('token').value}`,
        },
      })
      await list()
    }
    catch (e) {}
  }

  async function sendMessage(chatUUID: string | null, content: string, modelUUID: string, context: string | null): Promise<MessageResponse | null> {
    const config = useRuntimeConfig()

    try {
      const response = await useSecureFetch<MessageResponse>(
        `${config.public.apiUrl}/web/chats/messages`,
        {
          method: 'POST',
          body: JSON.stringify({ chatUUID, content, modelUUID, context }),
          headers: {
            'Authorization': `Bearer ${useCookie('token').value}`,
            'Content-Type': 'application/json',
          },
        },
      )

      return response
    }
    catch (e) {
      return null
    }
  }

  async function listMessages(uuid: string): Promise<MessageResponse[]> {
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

      return response
    }
    catch (e) {
      return []
    }
  }

  return { chats, list, remove, sendMessage, listMessages }
})
