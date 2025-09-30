<script setup lang="ts">
import type { MDCParserResult } from '@nuxtjs/mdc'

interface Props {
  ast?: MDCParserResult | null
  class?: string
}

const props = defineProps<Props>()
</script>

<template>
  <div v-if="ast?.body" class="mdc-content" :class="props.class">
    <MDCRenderer
      :body="ast.body"
      :data="ast.data"
    />
  </div>
</template>

<style scoped>
.mdc-content :deep(table) {
  width: 100%;
  border-collapse: collapse;
  margin: 1rem 0;
  overflow: hidden;
  border-radius: 0.5rem;
  border: 1px solid #e5e7eb;
}

.mdc-content :deep(th),
.mdc-content :deep(td) {
  border: 1px solid #e5e7eb;
  padding: 0.75rem 1rem;
  text-align: left;
}

.mdc-content :deep(th) {
  background-color: #f9fafb;
  font-weight: 600;
  font-size: 0.875rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: #374151;
}

.mdc-content :deep(td) {
  font-size: 0.875rem;
  color: #1f2937;
}

.mdc-content :deep(tbody tr:nth-child(even)) {
  background-color: #f9fafb;
}

.mdc-content :deep(tbody tr:hover) {
  background-color: #f3f4f6;
  transition: background-color 0.15s ease;
}

.mdc-content :deep(h1) {
  font-size: 1.875rem;
  font-weight: 700;
  margin: 1.5rem 0 1rem;
  color: #111827;
}

.mdc-content :deep(h2) {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 1.25rem 0 0.75rem;
  color: #1f2937;
}

.mdc-content :deep(h3) {
  font-size: 1.25rem;
  font-weight: 600;
  margin: 1rem 0 0.5rem;
  color: #374151;
}

.mdc-content :deep(h4) {
  font-size: 1.125rem;
  font-weight: 600;
  margin: 0.875rem 0 0.5rem;
  color: #4b5563;
}

.mdc-content :deep(p) {
  margin: 0.2rem 0;
  line-height: 1.625;
  color: #374151;
}

.mdc-content :deep(ul),
.mdc-content :deep(ol) {
  margin: 0.75rem 0;
  padding-left: 1.5rem;
  color: #374151;
}

.mdc-content :deep(li) {
  margin: 0.375rem 0;
  line-height: 1.625;
}

.mdc-content :deep(ul) {
  list-style-type: disc;
}

.mdc-content :deep(ol) {
  list-style-type: decimal;
}

.mdc-content :deep(code) {
  background-color: #f3f4f6;
  color: #1f2937;
  padding: 0.125rem 0.375rem;
  border-radius: 0.25rem;
  font-size: 0.875em;
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace;
}

.mdc-content :deep(pre) {
  background-color: #1f2937;
  color: #f9fafb;
  padding: 1rem;
  border-radius: 0.5rem;
  overflow-x: auto;
  margin: 1rem 0;
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace;
  font-size: 0.875rem;
  line-height: 1.5;
}

.mdc-content :deep(pre code) {
  background-color: transparent;
  padding: 0;
  color: inherit;
  font-size: inherit;
}

.mdc-content :deep(blockquote) {
  border-left: 4px solid #e5e7eb;
  padding-left: 1rem;
  margin: 1rem 0;
  color: #6b7280;
  font-style: italic;
}

.mdc-content :deep(a) {
  color: #2563eb;
  transition: color 0.15s ease;
}

.mdc-content :deep(h1 a),
.mdc-content :deep(h2 a),
.mdc-content :deep(h3 a),
.mdc-content :deep(h4 a),
.mdc-content :deep(h5 a),
.mdc-content :deep(h6 a) {
  color: inherit;
  text-decoration: none;
  pointer-events: none;
}

.mdc-content :deep(h1 a:hover),
.mdc-content :deep(h2 a:hover),
.mdc-content :deep(h3 a:hover),
.mdc-content :deep(h4 a:hover),
.mdc-content :deep(h5 a:hover),
.mdc-content :deep(h6 a:hover) {
  color: inherit;
}

.mdc-content :deep(a:hover) {
  color: #1d4ed8;
}

.mdc-content :deep(hr) {
  border: none;
  border-top: 1px solid #e5e7eb;
  margin: 1.5rem 0;
}

.mdc-content :deep(img) {
  max-width: 100%;
  height: auto;
  border-radius: 0.5rem;
  margin: 1rem 0;
}

.mdc-content :deep(strong) {
  font-weight: 600;
  color: #111827;
}

.mdc-content :deep(em) {
  font-style: italic;
}

.mdc-content :deep(del) {
  text-decoration: line-through;
  color: #6b7280;
}
</style>
