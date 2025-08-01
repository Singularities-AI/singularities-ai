export default defineNuxtRouteMiddleware(async (to) => {
  const auth = useAuthStore()

  // redirect user to login if is not log
  if (!auth.isAuthenticated() && to.path !== '/login')
    return navigateTo('/login')

  // prevent access to certain routes for already authenticated users
  if (auth.isAuthenticated() && ['/login', '/email-confirmation'].includes(to.path))
    return navigateTo('/')

  // admin pages
  if (auth.isAuthenticated() && ['/admin'].includes(to.path) && !auth.isAdmin())
    return navigateTo('/')
})
