import React from 'react'
import ReactMarkdown from 'react-markdown'

function Markdown({markdown}) {
  return <ReactMarkdown children={markdown} />
}
export default Markdown;