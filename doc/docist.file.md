
# docist.file

> `:added 0.1`, `:author Chad Angelelli`, 

Provides file-related utilities


## Publics


<a name="-&gt;file"></a>
### -&gt;file

> `:added 0.1`, `:author Chad A.`, 

Return clojure.java.io/file object for path.
  
  ```clojure
  (->file "src/docist/parser.clj") 
  ```

<a name="get-files"></a>
### get-files

> `:added 0.1`, `:author Chad A.`, 

Returns a lazy sequence of file objects.

  Options:
      :dir   Directory to scan
      :clj   Include .clj files
      :cljs  Include .cljs files
      :edn   Include .edn files

  Rules:
      1. If none of `#{:clj :cljs :edn}` are provided, default is all 3.

  Examples:

  ```clojure
  (get-files {:dir "src/docist" :clj true})
  ;;= (#object[java.io.File 0x251addc4 "src/docist/parser.clj"] ...)
  ```

<a name="find-file"></a>
### find-file

> `:added 0.1`, `:author Chad A.`, 

Recursively search dir. Return file object of first match for pattern.

<a name="directory?"></a>
### directory?

> `:added 0.1`, `:author Chad A.`, 

Returns true if directory exists and is a directory.

<a name="create-dir"></a>
### create-dir

> 

Creates directory if it doesn't exist.


## Privates 


