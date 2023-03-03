<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/zadnji.css">
    <link rel="stylesheet" href="/css/prijava.css">
    <#if title??>
        <title>${title}</title>
    </#if>
</head>
<body>
    <div>
        <#if pitanjaIOdgovori??>
            <#list pitanjaIOdgovori as pitanjeOdgovor>
            <div>
                <h3>${pitanjeOdgovor}</h3>
            <div>

            </#list>
        </#if>
    </div>
</body>
</html>