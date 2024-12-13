

// var term = new window.Terminal({
//     cursorBlink: true,
//
// });
//
// term.open(document.getElementById('terminal'));
// term.write("SQL console: ")
//
// const socket = new WebSocket("ws://localhost:6060");
//
// let send = document.getElementById('send');
//
// send.addEventListener('submit', (e) => {
//     e.preventDefault();
//     handleCommand(command.trim());
//     command = '';
// })
//
//
// socket.send(command + '\n');
//
// socket.onmessage = (event) => {
//     term.write(event.data);
// }
//
// let command = '';
// term.onData(e => {
//     if (e === '\r') { // Enter key
//         handleCommand(command.trim());
//         command = ''; // Reset command buffer
//     } else if (e === '\x7F') { // Backspace key
//         if (command.length > 0) {
//             term.write('\b \b'); // Move cursor back, erase character, move cursor back again
//             command = command.slice(0, -1); // Remove last character from command buffer
//         }
//     } else {
//         term.write(e); // Echo the typed character
//         command += e; // Add typed character to command buffer
//     }
// });
// function handleCommand(input) {
//     const args = input.split(' ');
//     const command = args[0];
//     const params = args.slice(1).join(' ');
//     switch (command) {
//         case 'echo':
//             term.write(params + '\n');
//             break;
//         case 'cat':
//             // Implement 'cat' command logic here
//             break;
//         default:
//             term.write(`Command not found: ${command}\n`);
//     }
// }