<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/index.css">
    <#if title??>
        <title>${title}</title>
    </#if>
</head>
<body>
    <h2>Dobrodošli na</h2>
    <h1>TectonicHR</h1>
    <hr />
    <div class="btnContainer">
        <button onclick="location.href = '/prijava';" type="button" class="customBtn">Prijavite se kao znanstvenik</button>
        <button onclick="location.href = '/zadnji_potresi';" type="button" class="customBtn">Nastavite kao građanin</button>
    </div>
</body>
</html>