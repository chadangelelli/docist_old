{% for ns,symbols in _|sort %}
## [{{ns}}]({{ns}}.md)

{% for node in symbols.publics %}
{% ifequal node.node-type :ns %}
{{ node.metadata.doc }}
{% if node.metadata.author %}
> Author: {{ node.metadata.author }}
{% endif %}
{% if node.metadata.added %}
> Added: {{ node.metadata.added }}
{% endif %}
{% endifequal %}
{% endfor %}

#### Publics

{% for node in symbols.publics %}{% ifunequal node.node-type :ns %}[{{ node.name }}]({{ ns }}#{{ node.name }}), {% endifunequal %}{% endfor %}

#### Privates

{% for node in symbols.privates %}[{{ node.name }}]({{ ns }}#{{ node.name }}), {% endfor %}

{% endfor %}
