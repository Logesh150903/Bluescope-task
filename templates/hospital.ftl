<hospital>
    <name>${hospital.name}</name>
    <location>${hospital.location}</location>
    <departments>
        <#list hospital.departments as department>
            <department>
                <name>${department.name}</name>
                <head>${department.head}</head>
            </department>
        </#list>
    </departments>
</hospital>
