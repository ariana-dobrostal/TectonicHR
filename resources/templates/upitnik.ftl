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
    <nav class="navbar navbar-expand-lg customNavbar sticky-top sticky-top">
        <p class="navTitle">TectonicHR</p>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="fa fa-bars customBars"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active customLi">
              <a class="nav-link customLink" href="/prijava">Login<span class="sr-only">(current)</span></a>
            </li>
          </ul>
        </div>
    </nav>
    <div class="titleHolder">
        <h2>Upitnik <#if potres??> za potres ${potres.getNazivPotres()}</#if></h2>
        <hr />
    </div>
    <form action="#" method="post" class="questionsDiv">
        <div class="pitanje">
            <#if potres??>
            <label for="datum">Kojeg datuma ste osijetili potres? ${potres.getDatum()}</label>
            <#else>
            <label for="datum">Kojeg datuma ste osijetili potres? </label>
            <input type="date" id="datum" name="datum" class="customTextbox" required>
            </#if>
        </div>
        <div class="pitanje">
            <label for="vrijeme">Kada ste osijetili potres?</label>
            <input type="time" name="vrijeme" class="customTextbox" required>
        </div>
        <div class="pitanje">
            <label for="mjesto">Gdje ste ga osijetili?</label>
            <select name="mjesto" class="customTextbox" required>
                <option value="" disabled selected >Odaberi mjesto</option>
                <#list mjesta as mjesto>
                    <option value="${mjesto}">${mjesto}</option>
                </#list>
            </select>
        </div>
        <#list pitanja as pitanje>
            <div class="pitanje">
                <p class="radioPitanje">${pitanje.getTekstPitanje()}</p>
                <#list pitanje.GetOdgovori() as odgovor>
                    <div>
                        <input class="radioInput" type="radio" id="${odgovor.getIdOdgovor()}" name="${odgovor.getIdPitanje()}" value="${odgovor.getIntenzitetOdgovor()}" required>
                        <label class="radioLabel" for="${odgovor.getIdOdgovor()}">${odgovor.getTekstOdgovor()}</label>
                    </div>
                </#list>
            </div>
        </#list>
        <button type="submit" class="customBtn">Pošaljite</button>
    </form>
    <script>
        var dtToday = new Date();
        var month = dtToday.getMonth() + 1;
        var day = dtToday.getDate();
        var year = dtToday.getFullYear();
        if(month < 10)
            month = '0' + month.toString();
        if(day < 10)
            day = '0' + day.toString();
        var maxDate = year + '-' + month + '-' + day;    
        document.getElementById('datum').setAttribute('max', maxDate);
    </script>
</body>
</html>