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
<h2 class="updateTitle">Promjena lozinke</h2>
<hr />
<form action="/update_lozinku" method="post">
    <#if error??>
        <p style="color: red; font-size: 24px;">${error}</p>
    </#if>
    <div class="formGroup">
        <label for="password">Nova lozinka</label>
        <input type="password" name="new_pass" placeholder="  ********" class="customTextbox" required>
    </div>

    <div class="formGroup">
        <label for="password">Potvrdi lozinku</label>
        <input type="password" name="confirm_pass" placeholder="  ********" class="customTextbox" required>
    </div>

    <button type="submit" class="customBtn">Promijeni</button>

    <button type="button" onclick = "location.href = '/bez_promjene_lozinke';" class="customBtn">Ne želim promijeniti lozinku</button>
    
</form>
</body>
</html>