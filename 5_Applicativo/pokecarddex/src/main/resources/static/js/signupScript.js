async function registerUser(email, username, password) {
    // Prepariamo l'oggetto con le stesse chiavi definite nel DTO Java
    const payload = {
        email: email,
        username: username,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/api/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Dichiariamo che stiamo inviando JSON
            },
            body: JSON.stringify(payload) // Convertiamo l'oggetto in stringa JSON
        });

        const message = await response.text();

        if (response.ok) {
            alert('Registrazione completata: ' + message);
            // Reindirizza l'utente alla pagina di login
        } else {
            alert('Errore: ' + message); // Mostra il messaggio del server (es: "Username già in uso")
        }
    } catch (error) {
        console.error('Errore di rete:', error);
    }
}
