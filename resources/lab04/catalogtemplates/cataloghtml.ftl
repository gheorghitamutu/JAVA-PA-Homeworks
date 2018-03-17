<html>

    <head>

        <title>$</title>

    </head>

    <body>

        <h1>${title}</h1>

        <p>${catalog.name} ${catalog.developer}</p>

        <ol>
            <#list items as item>
            <li>${item.title} ${item.path} ${item.year} </li>
                <ol>
                    <#list item.authors as author>
                        <li>${author}</li>
                    </#list>
                </ol>
            </#list>
        </ol>

    </body>
</html>