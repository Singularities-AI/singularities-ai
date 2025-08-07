<script setup lang="ts">
import { onMounted } from 'vue'

definePageMeta({ layout: 'blank', middleware: 'auth' })

const router = useRouter()

onMounted(async () => {
  router.push('/chats/new')
})
</script>
