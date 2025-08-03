<template>
  <main id="admin-layout" class="grid h-screen w-full pl-[53px]">
    <Sidebar />

    <div class="flex flex-col">
      <Header />

      <FormsLayoutAdmin>
        <slot />
      </FormsLayoutAdmin>
    </div>
  </main>
</template>
