function confirmDeletePotres(idPotres) {
    let text = "Jeste li sigurni da želite obrisati potres?";
    if (confirm(text) == true) {
        location.href = '/stariji_potresi_admin/izbrisi/' + idPotres;
    }
}

function confirmDeleteZadnjiPotres(idPotres) {
    let text = "Jeste li sigurni da želite obrisati potres?";
    if (confirm(text) == true) {
        location.href = '/zadnji_potresi_admin/izbrisi/' + idPotres;
    }
}

function confirmPrebaciPotres(idPotres) {
    let text = "Jeste li sigurni da želite prebaciti potres u stare?";
    if (confirm(text) == true) {
        location.href = '/zadnji_potresi_admin/pomakni/' + idPotres;
    }
}

function confirmDeleteUser(username) {
    let text = "Jeste li sigurni da želite obrisati korisnika?";
    if (confirm(text) == true) {
        location.href = '/korisnici/izbrisi/' + username;
    }
}

function confirmDeleteUpitnik(idUpitnik) {
    let text = "Jeste li sigurni da želite obrisati upitnik?";
    if (confirm(text) == true) {
        location.href = '/izbrisi_upitnik/' + idUpitnik;
    }
}

function confirmDeleteUpitnikPotres(idUpitnik, idPotres) {
    let text = "Jeste li sigurni da želite obrisati upitnik?";
    if (confirm(text) == true) {
        location.href = '/izbrisi_upitnik_potres/' + idPotres + '/' + idUpitnik;
    }
}