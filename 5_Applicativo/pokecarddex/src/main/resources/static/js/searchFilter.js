let timeoutRicerca;

function gestisciCambioTipoCarta() {
    const tipoCarta = document.getElementById('filtro-categoria').value;
    const selectEnergia = document.getElementById('filtro-tipo');
    const selectAllenatore = document.getElementById('filtro-tipoAllenatore');

    // Mostra Energia solo per POKEMON o ENERGIA
    if (tipoCarta === 'Pokemon' || tipoCarta === 'Energy') {
        selectEnergia.style.display = 'inline-block';
    } else {
        selectEnergia.style.display = 'none';
        selectEnergia.value = ''; // Reset filtro nascosto
    }

    // Mostra Tipo Allenatore solo per ALLENATORE
    if (tipoCarta === 'Trainer') {
        selectAllenatore.style.display = 'inline-block';
    } else {
        selectAllenatore.style.display = 'none';
        selectAllenatore.value = ''; // Reset filtro nascosto
    }

    filtraCarte();
}

function costruisciUrl(page) {
    const params = new URLSearchParams({
        nome: document.getElementById('filtro-nome').value,
        espansione: document.getElementById('filtro-espansione').value,
        tipoCarta: document.getElementById('filtro-categoria').value,
        tipoEnergia: document.getElementById('filtro-tipo').value,
        tipoAllenatore: document.getElementById('filtro-tipoAllenatore').value,
        page: page
    });
    return `/carte/cerca?${params.toString()}`;
}

function filtraCarte() {
    clearTimeout(timeoutRicerca);
    timeoutRicerca = setTimeout(() => {
        fetch(costruisciUrl(0))
        .then(res => res.text())
        .then(html => document.getElementById('griglia-carte').innerHTML = html);
    }, 300);
}

function caricaAltre(numeroPagina) {
    fetch(costruisciUrl(numeroPagina))
        .then(res => res.text())
        .then(html => {
            const btn = document.getElementById('box-carica-altre');
            if (btn) btn.remove();
            document.getElementById('griglia-carte').insertAdjacentHTML('beforeend', html);
        });
}
