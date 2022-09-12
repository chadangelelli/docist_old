
# docist.generator

> `:added 0.1`, `:author Chad Angelelli`, 

Generate static documentation from source files.

  ## Output formats:

  1. `:edn` (default): EDN map
  2. `:json`: JSON string
  3. `:yaml`: YAML string

  > _NOTE_: Output is processed by default via `categorize-parsed`. 
  Provide `:uncategorized?` option to disable.

  ## Defaults

  - `:output-dir`: "doc"
  - `:output-format`: `:edn`


## Publics


<a name="categorize-parsed"></a>
### categorize-parsed

> `:added 0.1`, `:author Chad Angelelli`, 

Return categorized map of `{NAMESPACE {:publics [..] :privates []}`.

  Example:

  ```clojure
  (let [parsed (docist.parser/parse ["src/docist/parser.clj"])] 
  (docist.generator/categorize-parsed parsed))
  ```

<a name="format-parsed"></a>
### format-parsed

> `:added 0.1`, `:author Chad Angelelli`, 

Return formatted map for parsed data.

<a name="validate-options"></a>
### validate-options

> `:added 0.1`, `:author Chad Angelelli`, 

Return nil on success or error map.

<a name="get-template"></a>
### get-template

> `:added 0.1`, `:author Chad Angelelli`, 

Return template string for theme/filename.

<a name="render"></a>
### render

> `:added 0.1`, `:author Chad Angelelli`, 

Render context to disk using theme. Returns nil or throws.

<a name="generate"></a>
### generate






## Privates 


