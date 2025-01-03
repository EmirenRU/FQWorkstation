const form = document.getElementById('uploadProtocolForm');
form.addEventListener('submit', handleSubmit);


function handleSubmit(event) {
  event.preventDefault();

  uploadFiles();
}

function uploadFiles() {
  const url = 'https://httpbin.org/post';
  const formData = new FormData(form);

  const fetchOptions = {
    method: 'post',
    body: formData
  };

  fetch(url, fetchOptions);
}