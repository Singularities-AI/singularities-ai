import process from 'node:process'
import { pwa } from './app/config/pwa'
import { appDescription } from './app/config/site'

export default defineNuxtConfig({
  modules: [
    '@vueuse/nuxt',
    '@unocss/nuxt',
    '@pinia/nuxt',
    '@pinia-plugin-persistedstate/nuxt',
    '@nuxtjs/color-mode',
    '@vite-pwa/nuxt',
    '@nuxt/eslint',
    './modules/shadcn',
    '@nuxt/content',
  ],

  debug: true,

  devtools: {
    enabled: false,
  },

  future: {
    compatibilityVersion: 4,
  },

  // Build as SPA application
  ssr: false,

  runtimeConfig: {
    public: {
      apiUrl: process.env.API_URL,
    },
  },

  vue: {
    compilerOptions: {
      isCustomElement: (tag: string) => tag === 'iconify-icon',
    },
  },

  // * Note that this option will not override the default directories (~/components, ~/composables, ~/middleware, ~/utils).
  imports: {
    dirs: ['stores'],
  },

  content: {
    highlight: {
      theme: 'github-dark-dimmed',
    },
  },

  shadcn: {
    prefix: '',
    componentDir: '@/components/ui',
  },

  css: [
    // '@unocss/reset/tailwind-compat.css',
  ],

  colorMode: {
    preference: 'system',
    fallback: 'light',
    classSuffix: '',
  },

  app: {
    head: {
      viewport: 'width=device-width,initial-scale=1',
      link: [
        { rel: 'icon', href: '/favicon.ico', sizes: 'any' },
        { rel: 'icon', type: 'image/svg+xml', href: '/nuxt.svg' },
        { rel: 'apple-touch-icon', href: '/apple-touch-icon.png' },
      ],
      meta: [
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        { name: 'description', content: appDescription },
        { name: 'apple-mobile-web-app-status-bar-style', content: 'black-translucent' },
      ],
    },
  },

  pwa,

  features: {
    // For UnoCSS
    inlineStyles: false,
  },

  eslint: {
    config: {
      standalone: false,
    },
  },

  compatibilityDate: '2024-07-12',
})
