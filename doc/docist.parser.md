
# docist.parser

> `:added 0.1`, `:author Chad Angelelli`, 

Parse Clojure/script source files into Docist Nodes.


## Publics


<a name="collectables"></a>
### collectables

> `:added 0.1`, `:author Chad A.`, 

Set of forms that will be collected from parsing.

  Collectable forms:

  ```clojure
  #{'declare 'def 'defmacro 'defmulti 'defmethod 'defn 'defn- 'defonce 'ns}
  ```

<a name=""></a>
### 





<a name="parse"></a>
### parse

> `:added 0.1`, `:author Chad A.`, 

Parse all collectable forms for namespace.

  Example:

  ```clojure
  (parse ["src/docist/parser.clj"])
  ```

  See also: `collectables`.


## Privates 


<a name="-process-meta"></a>
### -process-meta

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Returns metadata as a consistent map. Called from `-get-meta`.
  
  See also: `-get-meta`.
<a name="-get-meta"></a>
### -get-meta

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return metadata for form.
<a name="-get-docstring"></a>
### -get-docstring

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return docstring (if found) for form.
  
  See also: `-get-meta`.
<a name="-set-docstring"></a>
### -set-docstring

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return metadata, optionally with :doc key assoc'ed.
  
  See also: `-get-meta`.
<a name="-parse-ns"></a>
### -parse-ns

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `ns` form.
  
  See also: `-parse-node`.
<a name="-parse-declare"></a>
### -parse-declare

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `declare` form.
  
  See also: `-parse-node`.
<a name="-parse-var"></a>
### -parse-var

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `def` form.
  
  See also: `-parse-node`.
<a name="-parse-fn-public"></a>
### -parse-fn-public

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for public function form.
  
  See also: `-parse-node`.
<a name="-parse-fn-private"></a>
### -parse-fn-private

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for private function form.
  
  See also: `-parse-node`.
<a name="-parse-macro"></a>
### -parse-macro

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `defmacro` form.
  
  See also: `-parse-node`.
<a name="-parse-defmulti"></a>
### -parse-defmulti

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `defmulti` form.
  
  See also: `-parse-node`.
<a name="-parse-defmethod"></a>
### -parse-defmethod

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `defmethod` form.
  
  See also: `-parse-node`.
<a name="-parse-defonce"></a>
### -parse-defonce

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return node for `defonce` form.
  
  See also: `-parse-node`.
<a name="-parse-node"></a>
### -parse-node

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return `Docist Node` map representing form.
  Called from `-parse-namespace`.
  
  See also: `parse`, `-parse-namespace`.
<a name="-make-location-map"></a>
### -make-location-map

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return map of location info for collected form.
  Called from `-parse-namespace`.
  
  See also: `-parse-namespace`.
<a name="-parse-namespace"></a>
### -parse-namespace

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return nodes for collected forms of namespace. 
  Called from `-parse-namespaces`.
  
  See also: `parse`, `-parse-namespaces`.
<a name="-parse-namespaces"></a>
### -parse-namespaces

> `:private true`, `:added 0.1`, `:author Chad A.`, 

Return parsed namespaces by calling `-parse-namespace` on echo set
  of collected forms. Called from `parse`.
  
  See also: `parse`, `-parse-namespace`.
