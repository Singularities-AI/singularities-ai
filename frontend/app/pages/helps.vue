<script setup lang="ts">
import Header from '~/components/Header.vue'

definePageMeta({ layout: 'blank' })

const categories = [
  {
    title: 'pixfort hub',
    subtitle: 'Manage your licenses and get support in one place.',
    image: '/images/pixfort-hub.png',
  },
  {
    title: 'Videos',
    subtitle: 'How to install Essentials WordPress theme in 3 minutes!',
    image: '/images/video-thumbnail.png',
  },
  {
    title: 'Support',
    subtitle: 'Manage your licenses and get support in one place.',
    image: '/images/pixfort-hub.png',
  },
  {
    title: 'Installation',
    subtitle: 'Install Essentials theme',
    image: '/images/essentials-icon.png',
    badge: 'Install Essentials theme',
  },
  {
    title: 'Documentation',
    subtitle: 'Install Essentials theme',
    image: '/images/essentials-icon.png',
    badge: 'Install Essentials theme',
  },
]
</script>

<template>
  <div class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="w-full flex flex-col">
      <Header />

      <main class="flex flex-1 flex-col overflow-auto p-6">
        <div class="mb-6">
          <h1 class="text-3xl font-bold">
            Popular categories
          </h1>
          <p class="mt-1 text-muted-foreground">
            Combine seamlessly fitting layouts, customize everything
          </p>
        </div>

        <div class="grid grid-cols-1 gap-4 md:grid-cols-3 sm:grid-cols-2 xl:grid-cols-4">
          <div
            v-for="category in categories"
            :key="category.title"
            class="relative flex flex-col border rounded-2xl bg-muted/50 p-4 transition-shadow hover:shadow-md"
          >
            <img
              :src="category.image"
              alt="Preview"
              class="mb-3 aspect-[4/3] w-full rounded-xl object-cover"
            >

            <div class="flex-1">
              <h2 class="text-lg font-semibold">
                {{ category.title }}
              </h2>
              <p class="text-sm text-muted-foreground">
                {{ category.subtitle }}
              </p>
            </div>

            <div v-if="category.badge" class="mt-4">
              <Button variant="secondary" class="w-full text-sm">
                {{ category.badge }}
              </Button>
            </div>
          </div>
        </div>

        <div class="mt-8 flex justify-center">
          <Button variant="link" class="text-base">
            Check more articles →
          </Button>
        </div>
      </main>
    </div>
  </div>
</template>
