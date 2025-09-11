import type { Agent } from './Agent'
import type { Model } from './Model'

export interface Chat {
  id: string
  title: string
  context: string | null
  lastUpdate: string
  model: Model
  agent: Agent
}
