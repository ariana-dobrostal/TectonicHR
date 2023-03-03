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
              <a class="nav-link customLink" href="/svi_potresi">Svi potresi<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active customLi">
              <a class="nav-link customLink" href="/odjava">Logout<span class="sr-only">(current)</span></a>
            </li>
          </ul>
        </div>
    </nav>
    <div class="titleHolder">
        <h2>Svi potresi</h2>
        <hr/>
    </div>
    <#if potresi?size != 0>
        <#list potresi as earthquake>
            <div class="btnContainer" onclick="location.href='/potres/${earthquake.getIdPotres()}'">
                <div class="potresTab">
                    <div class="tighterDiv">
                        <div class="atrHolder">
                            <p class="atrTitle">Naziv</p>
                            <p class="atrName">${earthquake.getNazivPotres()}</p>
                        </div>
                        <div class="atrHolder">
                            <p class="atrTitle">Datum</p>
                            <p class="atrName">${earthquake.getDatum()}</p>
                        </div>
                        <div class="atrHolder">
                            <p class="atrTitle">Magnituda</p>
                            <p class="atrName">${earthquake.getMagnituda()}</p>
                        </div>
                        <div class="atrHolder">
                            <p class="atrTitle">Intenzitet</p>
                            <p class="atrName">${earthquake.getIntenzitet()}</p>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    <#else>
        <p style="color: red; font-size: 36px; text-align: center;">Nema potresa</p>
    </#if>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
