// 1. Gestisce solo mostra/nascondi dei menu a tendina
function aggiornaVisibilitaFiltri() {
    const tipoCarta = document.getElementById('filtro-supertype').value;
    const selectEnergia = document.getElementById('filtro-energia');
    const selectAllenatore = document.getElementById('filtro-allenatore');

    if (tipoCarta === 'Pokemon' || tipoCarta === 'Energy') {
        selectEnergia.style.display = 'inline-block';
    } else {
        selectEnergia.style.display = 'none';
        selectEnergia.value = '';
    }

    if (tipoCarta === 'Trainer') {
        selectAllenatore.style.display = 'inline-block';
    } else {
        selectAllenatore.style.display = 'none';
        selectAllenatore.value = '';
    }
}

// 2. Costruisce l'URL leggendo direttamente tutti gli input del <form>
function costruisciUrlDalForm(page) {
    const form = document.getElementById('form-filtri');
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);
    params.set('page', page);
    return `/addCard/cerca?${params.toString()}`;
}

// 3. Chiamata al click sul bottone "Cerca Carte" (o invio da tastiera)
function inviaFiltri(event) {
    event.preventDefault(); // Blocca il ricaricamento completo della pagina!

    fetch(costruisciUrlDalForm(0))
        .then(res => res.text())
        .then(html => {
            document.getElementById('griglia-carte').innerHTML = html;
        });
}

// 4. Paginazione "Carica altre" mantenendo i filtri attuali del form
function caricaAltre(numeroPagina) {
    fetch(costruisciUrlDalForm(numeroPagina))
        .then(res => res.text())
        .then(html => {
            const btn = document.getElementById('box-carica-altre');
            if (btn) btn.remove();
            document.getElementById('griglia-carte').insertAdjacentHTML('beforeend', html);
        });
}

// 5. Reset completo del form e ricaricamento iniziale
function resetFiltri() {
    document.getElementById('form-filtri').reset();
    aggiornaVisibilitaFiltri();
    fetch(costruisciUrlDalForm(0))
        .then(res => res.text())
        .then(html => document.getElementById('griglia-carte').innerHTML = html);
}