export default defineNuxtRouteMiddleware(async (to) => {
  const auth = useAuthStore()

  // redirect user to login if is not log
  if (!auth.isAuthenticated() && to.path !== '/login')
    return navigateTo('/login')

  // Empêcher l'accès à /login pour les utilisateurs déjà authentifiés
  if (auth.isAuthenticated() && to.path === '/login')
    return navigateTo('/')

  if (auth.isAuthenticated() && to.path === '/register')
    return navigateTo('/')
})
