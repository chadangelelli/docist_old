{% for node in _.publics %}{% ifequal node.node-type :ns %}
# {{ node.name }}

{% if node.metadata %}> {% for k,v in node.metadata %}{% ifunequal k :doc %}`{{ k }} {{ v }}`, {% endifunequal %}{% endfor %}{% endif %}

{% safe %}{{ node.metadata.doc }}{% endsafe %}
{% endifequal %}{% endfor %}

## Publics

{% for node in _.publics %}{% ifunequal node.node-type :ns %}
<a name="{{ node.name }}"></a>
### {{ node.name }}

{% if node.metadata %}> {% for k,v in node.metadata %}{% ifunequal k :doc %}`{{ k }} {{ v }}`, {% endifunequal %}{% endfor %}{% endif %}

{% safe %}{{ node.metadata.doc }}{% endsafe %}
{% endifunequal %}{% endfor %}

## Privates 

{% for node in _.privates %}
<a name="{{ node.name }}"></a>
### {{ node.name }}

{% if node.metadata %}> {% for k,v in node.metadata %}{% ifunequal k :doc %}`{{ k }} {{ v }}`, {% endifunequal %}{% endfor %}{% endif %}

{% safe %}{{ node.metadata.doc }}{% endsafe %}{% endfor %}
