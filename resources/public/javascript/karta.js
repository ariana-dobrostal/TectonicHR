mapboxgl.accessToken = 'pk.eyJ1IjoibXJ0cmlqYXgiLCJhIjoiY2t3dno0MWY2MjRudzJwcDNtc2tyODZjYSJ9.p658jybhR1EUjChMln09ng';

var city = "Zagreb"

fetch("https://api.mapbox.com/geocoding/v5/mapbox.places/"+city+".json?access_token=pk.eyJ1IjoibXJ0cmlqYXgiLCJhIjoiY2t3dno0MWY2MjRudzJwcDNtc2tyODZjYSJ9.p658jybhR1EUjChMln09ng")
    .then(res => res.json())
    .then(async (data) => {
        var center = data.features[0].center
        var map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/streets-v11',
            center: center,
            zoom: 7
        });

        const epicentar = document.createElement('div');
        epicentar.className = 'epicentar';

        // make a marker for each feature and add to the map
        new mapboxgl.Marker(epicentar).setLngLat(center).addTo(map);

        var gradovi = ["Sisak", "Samobor", "Velika gorica"];

        for(var grad of gradovi){
            grad = encodeURIComponent(grad.trim());
            console.log(grad);
            fetch("https://api.mapbox.com/geocoding/v5/mapbox.places/"+grad+".json?access_token=pk.eyJ1IjoibXJ0cmlqYXgiLCJhIjoiY2t3dno0MWY2MjRudzJwcDNtc2tyODZjYSJ9.p658jybhR1EUjChMln09ng")
                .then(res => res.json())
                .then(centerMjesto => {
                    console.log(centerMjesto);
                    var mjesto = document.createElement('div');
                    mjesto.className = "intenzitet5";
                    new mapboxgl.Marker(mjesto).setLngLat(centerMjesto.features[0].center).addTo(map);
                });
        }
        
        map.addControl(new mapboxgl.NavigationControl());
    })