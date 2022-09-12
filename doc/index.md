
## [docist.cmdline](docist.cmdline.md)















#### Publics

[cli-options](docist.cmdline#cli-options), [help](docist.cmdline#help), [-main](docist.cmdline#-main), 

#### Privates

[-make-parse-file-list](docist.cmdline#-make-parse-file-list), 


## [docist.file](docist.file.md)



Provides file-related utilities

> Author: Chad Angelelli


> Added: 0.1














#### Publics

[-&gt;file](docist.file#-&gt;file), [get-files](docist.file#get-files), [find-file](docist.file#find-file), [directory?](docist.file#directory?), [create-dir](docist.file#create-dir), 

#### Privates




## [docist.fmt](docist.fmt.md)



Formatting tools and vars.

  - Logo Ascii art generated using https://patorjk.com/software/taag/#p=display&amp;f=Graffiti&amp;t=Type%20Something%20
  - Test Ascii art generated using https://patorjk.com/software/taag/#p=display&amp;f=Rectangles&amp;t=Parser%20Test 
  





























































#### Publics

[DOCIST-LOGO](docist.fmt#DOCIST-LOGO), [PARSER-TEST](docist.fmt#PARSER-TEST), [FILE-TEST](docist.fmt#FILE-TEST), [GENERATOR-TEST](docist.fmt#GENERATOR-TEST), [FORMAT-TEST](docist.fmt#FORMAT-TEST), [CMDLINE-TEST](docist.fmt#CMDLINE-TEST), [UTIL-TEST](docist.fmt#UTIL-TEST), [NC](docist.fmt#NC), [BOLD](docist.fmt#BOLD), [ITAL](docist.fmt#ITAL), [RED](docist.fmt#RED), [BLUE](docist.fmt#BLUE), [GREEN](docist.fmt#GREEN), [PURPLE](docist.fmt#PURPLE), [ORANGE](docist.fmt#ORANGE), [SUCCESS](docist.fmt#SUCCESS), [INFO](docist.fmt#INFO), [WARN](docist.fmt#WARN), [ERROR](docist.fmt#ERROR), [DEBUG](docist.fmt#DEBUG), [echo](docist.fmt#echo), [pretty-errors](docist.fmt#pretty-errors), [spaces](docist.fmt#spaces), [make-options-help](docist.fmt#make-options-help), [make-help-menu](docist.fmt#make-help-menu), [get-percent](docist.fmt#get-percent), [make-progress-bar](docist.fmt#make-progress-bar), [print-progress-bar](docist.fmt#print-progress-bar), 

#### Privates




## [docist.generator](docist.generator.md)



Generate static documentation from source files.

  ## Output formats:

  1. `:edn` (default): EDN map
  2. `:json`: JSON string
  3. `:yaml`: YAML string

  &gt; _NOTE_: Output is processed by default via `categorize-parsed`. 
  Provide `:uncategorized?` option to disable.

  ## Defaults

  - `:output-dir`: &quot;doc&quot;
  - `:output-format`: `:edn`

> Author: Chad Angelelli


> Added: 0.1
















#### Publics

[categorize-parsed](docist.generator#categorize-parsed), [format-parsed](docist.generator#format-parsed), [validate-options](docist.generator#validate-options), [get-template](docist.generator#get-template), [render](docist.generator#render), [generate](docist.generator#generate), 

#### Privates




## [docist.parser](docist.parser.md)



Parse Clojure/script source files into Docist Nodes.

> Author: Chad Angelelli


> Added: 0.1










#### Publics

[collectables](docist.parser#collectables), [](docist.parser#), [parse](docist.parser#parse), 

#### Privates

[-process-meta](docist.parser#-process-meta), [-get-meta](docist.parser#-get-meta), [-get-docstring](docist.parser#-get-docstring), [-set-docstring](docist.parser#-set-docstring), [-parse-ns](docist.parser#-parse-ns), [-parse-declare](docist.parser#-parse-declare), [-parse-var](docist.parser#-parse-var), [-parse-fn-public](docist.parser#-parse-fn-public), [-parse-fn-private](docist.parser#-parse-fn-private), [-parse-macro](docist.parser#-parse-macro), [-parse-defmulti](docist.parser#-parse-defmulti), [-parse-defmethod](docist.parser#-parse-defmethod), [-parse-defonce](docist.parser#-parse-defonce), [-parse-node](docist.parser#-parse-node), [-make-location-map](docist.parser#-make-location-map), [-parse-namespace](docist.parser#-parse-namespace), [-parse-namespaces](docist.parser#-parse-namespaces), 


## [docist.util](docist.util.md)











#### Publics

[try*](docist.util#try*), 

#### Privates




