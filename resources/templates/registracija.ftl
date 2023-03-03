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
    <nav class="navbar navbar-expand-lg customNavbar sticky-top">
        <p class="navTitle">TectonicHR</p>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="fa fa-bars customBars"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active customLi">
                    <a class="nav-link customLink" href="/korisnici">Korisnici<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item active customLi">
                    <a class="nav-link customLink" href="/novi_upitnici">Novi upitnici<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item active customLi">
                    <a class="nav-link customLink" href="/zadnji_potresi_admin">Zadnji potresi<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item active customLi">
                    <a class="nav-link customLink" href="/stariji_potresi_admin">Stariji potresi<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item active customLi">
                  <a class="nav-link customLink" href="/odjava">Logout<span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>


    <h2 class="prijavaTitle">Registracija</h2>
    <hr />
    <form action="/registracija" method="post">
        <#if registrationFailed?? && registrationFailed>
            <p style="color: red; font-size: 24px;">Registration has failed</p>
        </#if>
        <div class="formGroup">
            <label for="fullName">Ime:</label>
            <input type="text" name="ime" placeholder="  Ivan" class="customTextbox" maxlength = "15" required>
        </div>
        <div class="formGroup">
            <label for="fullName">Prezime:</label>
            <input type="text" name="prezime" placeholder="  Horvat" class="customTextbox" maxlength = "15" required>
        </div>
        <div class="formGroup">
            <label for="username">Email:</label>
            <input type="text" name="username" placeholder="  ivan.horvat@gmail.com" class="customTextbox" maxlength = "40" required>
        </div>
        <div class="formGroup">
            <label for="password">Lozinka:</label>
            <input type="password" name="password" placeholder="  ********" class="customTextbox" required>
        </div>
        <button type="submit" class="customBtn">Registriraj korisnika</button>
    </form>
</body>
</html>