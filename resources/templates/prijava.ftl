<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/zadnji.css">
    <link rel="stylesheet" href="/css/prijava.css">
    <#if title??>
        <title>${title}</title>
    </#if>
</head>
<body>  
    <h2 class="prijavaTitle">Prijava</h2>
    <hr />
    <form action="/prijava" method="post">
        <#if error??>
            <p style="color: red; font-size: 24px;">${error}</p>
        </#if>
        <div class="formGroup">
            <label for="username">E-mail</label>
            <input type="text" name="username" placeholder="  ivan.horvat@gmail.com" class="customTextbox" required>    <!-- dodala sam placeholder-->
        </div>

        <div class="formGroup">
            <label for="password">Lozinka</label>
            <input type="password" name="password" placeholder="  ********" class="customTextbox" required>
        </div>
        
        <button type="submit" class="customBtn">Prijavite se</button>
    </form>
</body>
</html>