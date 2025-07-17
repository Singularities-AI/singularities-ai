export default defineNuxtRouteMiddleware(async (to) => {
  const auth = useAuthStore()

  // Redirect user to login if is not log
  if (!auth.isAuthenticated() && to.path !== '/login')
    return navigateTo('/login')

  // Prevent access to certain routes for already authenticated users
  if (auth.isAuthenticated() && ['/login', '/email-confirmation'].includes(to.path))
    return navigateTo('/')
})
