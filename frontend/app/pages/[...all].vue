<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useLocalePath } from '#i18n'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const router = useRouter()
const localePath = useLocalePath()

onMounted(async () => {
  router.push(localePath({ name: 'chats-uuid', params: { uuid: 'new' } }))
})
</script>
