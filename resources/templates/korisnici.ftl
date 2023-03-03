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
    <link rel="stylesheet" href="/css/noviUpitnici.css">
    <script src="/javascript/confirmation.js"></script>
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
    <div class="titleHolder">
        <h2>Korisnici</h2>
        <hr/>
    </div>
    <#if users??>
        <#list users as user>
            <div class="btnContainer" style="cursor: default;">
                <div class="potresTab">
                    <div class="atrHolder">
                        <p class="atrTitle">Ime i prezime</p>
                        <p class="atrName">${user.getIme() + " " + user.getPrezime()}</p>
                    </div>
                    <div class="atrWithBtn">
                        <div class="atrHolder">
                            <p class="atrTitle">E-mail adresa</p>
                            <p class="atrName">${user.getEmail()}</p>
                        </div>
                    </div>
                    <div class="atrWithBtn">
                        <button type="submit" onclick="confirmDeleteUser('${user.getEmail()}')" class="customBtn adminTabBtn"><span class="fa fa-trash customBars"></span></button>
                    </div>
                </div>
            </div>
        </#list>
    <#else>
        <p style="color: red; font-size: 36px; text-align: center;">Nema korisnika u bazi</p>
    </#if>
    <div class="btnContainer2">
        <button type="button" class="customBtn" onclick="location.href = '/registracija';">Novi korisnik</button>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>