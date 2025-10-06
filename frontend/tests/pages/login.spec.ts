import { expect, test } from '@playwright/test'

test('Check if the login page exists', async ({ page }) => {
  await page.goto('http://127.0.0.1:7000/login')
  await expect(page.getByText('Email', { exact: true })).toBeVisible()
  await expect(page.getByRole('textbox', { name: 'Email' })).toBeEmpty()
})
