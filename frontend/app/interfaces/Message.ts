export interface MessageRequest {
  chatUUID: string | null
  content: string
  modelUUID: string
  context: string | null
}

export interface MessageResponse {
  id: string
  chatUUID: string
  content: string
  role: 'USER' | 'AGENT'
  createdAt: string
}
