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
    <nav class="navbar navbar-expand-lg customNavbar">
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
        <h2>${potres.getNazivPotres()}</h2>                     <!-- mjesto-->
        <h3>${potres.getDatum()}</h3>                <!-- datum-->
       
        <hr/>
    </div>

    <div class="btnContainer2">
        <button type="button" class="customBtn" onclick="location.href='/karta/${potres.getIdPotres()}'">Preliminarna karta intenziteta</button>
        <a href="/download_potres_info/${potres.getIdPotres()}" download="podaci.txt" class="customBtn" style="text-align: center">Preuzmite<br>podatke</a>   
    </div>

    <div class="titleHolder">
        <div class="atrWithBtn" style="justify-content: center;">
            <h3>Upitnici</h3>
            <a style="text-decoration: none;"  href="/potres/download_upitnici/${potres.getIdPotres()}" download="upitnici.txt" class="customBtn adminTabBtn"><span class="fa fa-download customBars"></span></a>
        </div>
        <hr/>
    </div>

     <#if upitnici??>
        <#list upitnici as upitnik>
            <div class="btnContainer" name="upitnikId" value=${upitnik.getIdUpitnik()};" >
                <div class="potresTab">
                    <div class="atrHolder" onclick="location.href='/ispunjeni_upitnik/${upitnik.getIdUpitnik()}'">
                        <p class="atrTitle">Mjesto</p>
                        <p class="atrName">${upitnik.getNazivMjesto()}</p>
                    </div>
                    <div class="atrHolder" onclick="location.href='/ispunjeni_upitnik/${upitnik.getIdUpitnik()}'">
                        <p class="atrTitle">Vrijeme</p>
                        <p class="atrName">${upitnik.getVrijeme()}</p>
                    </div>
                    <div class="atrWithBtn" onclick="location.href='/ispunjeni_upitnik/${upitnik.getIdUpitnik()}'">
                        <div class="atrHolder">
                            <p class="atrTitle">Datum</p>
                            <p class="atrName">${upitnik.getDatum()}</p>
                        </div>
                    </div>
                    <div class="atrWithBtn">
                         <button type="submit" class="customBtn adminTabBtn" onclick="confirmDeleteUpitnikPotres(${upitnik.getIdUpitnik()}, ${potres.getIdPotres()})" ><span class="fa fa-trash customBars"></span></button>
                         <button type="submit" onclick="location.href = '/dodijeli_upitnik_drugom/${potres.getIdPotres()}/${upitnik.getIdUpitnik()}';" class="customBtn adminTabBtn historyBtn"><span class="fa fa-file customBars"></span></button>
                    </div>               
                </div>
            </div>
      </#list>
    </#if>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>