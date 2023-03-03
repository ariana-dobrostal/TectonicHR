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
        <h2>Novi potres</h2>         <!--prije je pisalo zadnji potresi-->
        <hr/>
    </div>
    <form action="/novi_potres" method="post">
        <div class="leftAlignForm">
        	<div class="formGroup">
                <label for="mjesto">Naziv potresa</label>
                <input type="text" name="naziv" placeholder="  ZGPotres" class="customTextbox" required>
            </div>
            <div class="formGroup">
                <label for="datum">Datum potresa</label>
                <input type="text" id="datum" name="datum" placeholder="  dd/mm/yyyy" class="customTextbox" onfocus="(this.type='date')" required>
            </div>
            <div class="formGroup">
                <label for="vrijeme">Vrijeme potresa</label>
                <input type="text" name="vrijeme" placeholder="  --:--" class="customTextbox" onfocus="(this.type='time')" required> <!-- promjena name iz datum u vrijeme-->
            </div>
            
  			<#if upitnici??>
  				<div class="formGroup" style="width: 90vw;">
            		<p class="radioPitanje">Dodijeljeni upitnici</p>
			        	<#list upitnici as upitnik>
			        		<div class="checkboxItem">
						      <input type="checkbox" id="${upitnik.getIdUpitnik()}" name="${upitnik.getIdUpitnik()}" value="selected">
						      <label for="${upitnik.getIdUpitnik()}" style="width: 100%">
						      	<div class="btnContainer">
					                <div class="potresTab">
					                    <div class="atrHolder">
					                        <p class="atrTitle">Mjesto</p>
					                        <p class="atrName">${upitnik.getNazivMjesto()}</p>
					                    </div>
					                    <div class="atrHolder">
					                        <p class="atrTitle">Vrijeme</p>
					                        <p class="atrName">${upitnik.getVrijeme()}</p>
					                    </div>
				                        <div class="atrHolder">
				                            <p class="atrTitle">Datum</p>
				                            <p class="atrName">${upitnik.getDatum()}</p>
				                        </div>
					                </div>
				            	</div>
						      </label>
						    </div>
			        	</#list>
				</div>
			 </#if>
        </div>
        
        <button type="submit" class="customBtn">Stvori potres</button>
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
