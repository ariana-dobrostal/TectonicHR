<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.js'></script>
    <link href='https://api.mapbox.com/mapbox-gl-js/v2.3.1/mapbox-gl.css' rel='stylesheet' />
    <link href='/css/karta.css' rel='stylesheet' />
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/zadnji.css">
    <#if title??>
        <title>${title}</title>
    </#if>
</head>
<body>
    <#if epicenter?? && date?? && cityIntensity??>
    <h2 style="margin-bottom: 20px;">${nazivPotres}</h2>
    <h3 style="margin-bottom: 20px; margin-top: 0px;">Datum: ${date}</h3>
     <h3 style="margin-bottom: 20px; margin-top: 0px;">Intenzitet : ${potres.getIntenzitet()}</h3>
    <h3 style="margin-bottom: 20px; margin-top: 0px;">Magnituda : ${potres.getMagnituda()}</h3>
    <hr style="margin-bottom: 20px; width: 40%;" />
    <p class="legenda"><img src="/slike/circle1.svg" />Intenziteti od 1 do 3 <img src="/slike/circle2.svg" />Intenziteti od 3 do 5 <img src="/slike/circle3.svg" />Intenziteti od 5 do 7 <img src="/slike/circle4.svg" />Intenziteti od 7 do 9 <img src="/slike/circle5.svg" />Intenziteti od 9 do 11 <img src="/slike/circle6.svg" />Intenziteti od 11 do 12</p>
    <br />
    <div id='map'></div>
    <#assign keys = cityIntensity?keys>
    <#assign size = keys?size>

    <script>
        mapboxgl.accessToken = 'pk.eyJ1IjoibXJ0cmlqYXgiLCJhIjoiY2t3dno0MWY2MjRudzJwcDNtc2tyODZjYSJ9.p658jybhR1EUjChMln09ng';

        size = "${size}"
        citInt = []
        <#list keys as key>
        citInt["${key}"] = "${cityIntensity[key]}"
        </#list>
        console.log(citInt);    

        var lat = "${epicenter.lat}"
        var lng = "${epicenter.lng}"

        if(isNaN(lng) && isNaN(lat)){
            console.log("Nema lng i lat");
            lng = 0;
            lat = 0;
        }

        console.log("Lat i lng: "+lat+", "+lng);

        var center = [lng, lat]
        var map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/streets-v11',
            center: center,
            zoom: 7
        });

        const epicentar = document.createElement('div');
        epicentar.className = 'epicentar';

        // make a marker for each feature and add to the map
        if(!isNaN(${epicenter.lat}) && !isNaN(${epicenter.lng})){
            new mapboxgl.Marker(epicentar).setLngLat(center).addTo(map);
        }
        

        console.log("Ulazak u for petlju")
        for(let[key, value] of Object.entries(citInt)){
            console.log("Petlja")
            console.log(key+"="+value);
            let grad = encodeURIComponent(key.trim());
            value = parseInt(value);
            console.log(value);
            fetch("https://api.mapbox.com/geocoding/v5/mapbox.places/"+grad+".json?access_token=pk.eyJ1IjoibXJ0cmlqYXgiLCJhIjoiY2t3dno0MWY2MjRudzJwcDNtc2tyODZjYSJ9.p658jybhR1EUjChMln09ng")
                .then(res => res.json())
                .then(centerMjesto => {
                    console.log(centerMjesto);
                    console.log(centerMjesto.features[0].center);
                    var mjesto = document.createElement('div');
                    if(centerMjesto.features[0].center != center){
                        if(value >= 1 && value < 3){
                            mjesto.className = "intenzitet1";    
                        }else if(value >=3 && value < 5){
                            mjesto.className = "intenzitet2";
                        }else if(value >=5 && value < 7){
                            mjesto.className = "intenzitet3";
                        }else if(value >=7 && value < 9){
                            mjesto.className = "intenzitet4";
                        }else if(value >=9 && value < 11){
                            mjesto.className = "intenzitet5";
                        }else if(value >=11 && value < 13){
                            mjesto.className = "intenzitet6";
                        }
                        new mapboxgl.Marker(mjesto).setLngLat(centerMjesto.features[0].center).addTo(map);
                    }
                });
        }
                
        map.addControl(new mapboxgl.NavigationControl());

        L.mapbox.legendControl({ position: 'topright' }).addLegend('<strong>My walk from the White House to the hill!</strong>').addTo(map);
    </script>
    <#else>
        <p style="color: red; font-size: 36px; text-align: center;">Nema karte za ovaj potres</p>
    </#if>
</body>
</html>