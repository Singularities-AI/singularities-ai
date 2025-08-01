export async function useSecureFetch<T>(url: string, options: any = {}) {
  const auth = useAuthStore()

  try {
    const response = await $fetch<T>(url, {
      ...options,
      onResponseError({ response }: any) {
        if (response?.status === 403)
          auth.logout()
      },
    })

    return response
  }
  catch (error: any) {
    let errorMessage = 'An error has occurred. Please try again later.'

    if (error.data && typeof error.data === 'object')
      errorMessage = error.data.message || errorMessage
    else if (error.message)
      errorMessage = error.message

    throw new Error(errorMessage)
  }
}
