import {
  createMarkdownParser,
  createShikiHighlighter,
  rehypeHighlight,
} from '@nuxtjs/mdc/runtime'
// Import desired Shiki themes and languages
import MaterialThemePalenight from '@shikijs/themes/material-theme-palenight'
import HtmlLang from '@shikijs/langs/html'
import MdcLang from '@shikijs/langs/mdc'
import TsLang from '@shikijs/langs/typescript'
import VueLang from '@shikijs/langs/vue'
import ScssLang from '@shikijs/langs/scss'
import YamlLang from '@shikijs/langs/yaml'

export default function useMarkdownParser() {
  let parser: Awaited<ReturnType<typeof createMarkdownParser>>

  const parse = async (markdown: string) => {
    if (!parser) {
      parser = await createMarkdownParser({
        rehype: {
          plugins: {
            highlight: {
              instance: rehypeHighlight,
              options: {
                // Pass in your desired theme(s)
                theme: 'material-theme-palenight',
                // Create the Shiki highlighter
                highlighter: createShikiHighlighter({
                  bundledThemes: {
                    'material-theme-palenight': MaterialThemePalenight,
                  },
                  // Configure the bundled languages
                  bundledLangs: {
                    html: HtmlLang,
                    mdc: MdcLang,
                    vue: VueLang,
                    yml: YamlLang,
                    scss: ScssLang,
                    ts: TsLang,
                    typescript: TsLang,
                  },
                }),
              },
            },
          },
        },
      })
    }
    return parser(markdown)
  }

  return parse
}
