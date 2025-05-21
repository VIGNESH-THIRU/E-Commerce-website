
    //When I click the Submit button, the browser normally reloads the page and submits the form.
  //  To prevent this default behavior, we use e.preventDefault()
  //  so that we can handle the form submission manually using JavaScript without reloading the page.
  
 // "When the submit button is clicked, the browser triggers the submit event on the form. 
  //The function you've attached as the event listener is called,
   //and the form element is available as e.target inside that function
   //e is the full event object created by the browser when an event happens (like submit, click, etc.).
//e.target is the specific HTML element that triggered the event.
//blob is convert the images as binary large objects
//it is used to send the javascript objects as json format like a file
//method: "POST": This tells the browser you’re sending data to the server.
//body: formData: This attaches the data (form fields, JSON, files) to the request.
//querySelector is a method used in JavaScript to select the first HTML element that matches a CSS-style selector.

document.getElementById('productForm').addEventListener('submit', function (e) {
    e.preventDefault();

  const form = e.target;
  const formData = new FormData();

  const imageFile = form.querySelector('input[name="imageFile"]').files[0];

  const product = {
    name: form.querySelector('input[name="name"]').value,
    brand: form.querySelector('input[name="brand"]').value,
    desc: form.querySelector('input[name="desc"]').value,
    price: parseFloat(form.querySelector('input[name="price"]').value),
    category: form.querySelector('select[name="category"]').value,
    quantity: parseInt(form.querySelector('input[name="quantity"]').value),
    releaseDate: form.querySelector('input[name="releaseDate"]').value,
    available: true
  };

  formData.append("product", new Blob([JSON.stringify(product)], { type: "application/json" }));
  formData.append("imageFile", imageFile);

  fetch("http://localhost:8080/api/products", {
    method: "POST",
    body: formData
  })
    .then(response => {
      if (!response.ok) throw new Error("Failed to upload product.");
      return response.json();
    })
    .then(data => {
      alert("Product uploaded successfully!");
     form.reset();
    })
    .catch(error => {
      alert("Error: " + error.message);
    });
});


