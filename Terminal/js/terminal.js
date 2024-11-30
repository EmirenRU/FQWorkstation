var term = new window.Terminal({
    cursorBlink: true,
});
term.open(document.getElementById('terminal'));

const socket = new WebSocket("ws://localhost:6060");

let send = document.getElementById('send');

send.addEventListener('submit', (e) => {
    e.preventDefault();
    socket.send(command + '\n');

socket.onmessage = (event) => {
    term.write(event.data);
}

})


