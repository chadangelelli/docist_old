{% for ns,symbols in _|sort %}
## [{{ns}}]({{ns}}.md)

{% for node in symbols.publics %}
{% ifequal node.node-type :ns %}
{% if node.metadata %}{% for k,v in node.metadata %}{% ifunequal k :doc %}
{{ k }}: {{ v }}
{% endifunequal %}{% endfor %}{% endif %}
{% safe %}{{ node.metadata.doc }}{% endsafe %}
{% endifequal %}
{% endfor %}

#### Publics

{% for node in symbols.publics %}{% ifunequal node.node-type :ns %}[{{ node.name }}]({{ ns }}#{{ node.name }}), {% endifunequal %}{% endfor %}

#### Privates

{% for node in symbols.privates %}[{{ node.name }}]({{ ns }}#{{ node.name }}), {% endfor %}

{% endfor %}
