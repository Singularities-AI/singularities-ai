import type { Model } from './Model'

export interface Agent {
  id: string
  creationDate: string
  lastUpdate: string
  icon: string
  model: Model
  name: string
  description: string
}
