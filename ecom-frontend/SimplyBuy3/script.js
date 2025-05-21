 //so the fetch to request to the server and the server return data in the json format
      //fetch return the promises and promise resolve into object
      //then waits the response message in the json format it includes status code,header,body
 //check the status code in 200-299
 //using the condition we explicitly retur the response
  //textcontent is used to set and get the text in the html
  //to conver the json to javascript object and also take only the body of text
  //it create the div element
 //the created div element is add to the child as prod container 
// Browser	Where it stores localStorage (behind the scenes)
// Chrome	In a SQLite database file in the user profile directory
// localStorage is a built-in web storage system used to temporarily or persistently store data in the browser as key-value pairs.
//You can set and get product data using it, like this:
//localStorage.setItem(key, value) → Saves data
//localStorage.getItem(key) → Retrieves data
  //localStorage is a built-in Web API provided by modern browsers.
              //setItem is method of localstorage  

document.addEventListener('DOMContentLoaded', function () {
  const searchInput = document.getElementById('searchInput');

  searchInput.addEventListener('input', function () {
    const keyword = this.value.trim();
    if (keyword === '') {
      clickHandler(); // Load all products
      return;
    }

//The query parameter ?keyword=... sends the search keyword to the backend.
/*http://localhost:8080/api/products/search → The endpoint (URL path)
?keyword=iphone → A query parameter:
? starts the query part of the URL.
keyword is the parameter name.
iphone is the value the user typed (e.g., in a search box).*/ 
    fetch(`http://localhost:8080/api/products/search?keyword=${encodeURIComponent(keyword)}`)
      .then(response => {
        if (!response.ok) throw new Error('Search failed');
        return response.json();
      })
      .then(displayProducts)
      .catch(error => console.error('Search error:', error));
  });

  clickHandler(); // Initial load
});

function clickHandler() {
  fetch('http://localhost:8080/api/products')
    .then(response => {
      if (!response.ok) throw new Error('Network error');
      return response.json();
    })
    .then(displayProducts)
    .catch(error => console.error('Fetch all error:', error));
}

function displayProducts(products) {
  const prod = document.getElementById('prod');
  prod.innerHTML = '';

  if (products.length === 0) {
    prod.textContent = 'No products found.';
    return;
  }

  products.forEach(product => {
    const box = document.createElement('div');
    box.id = 'box';
    box.innerHTML = `
      ${product.name.toUpperCase()}<br>
      ${product.brand}<br>
      $${product.price}
    `;
    prod.appendChild(box);

    box.addEventListener('click', () => {

      fetch(`http://localhost:8080/api/products/${product.id}`)
        .then(response => {
          if (!response.ok) throw new Error('Failed to fetch product');
          return response.json();
        })
        .then(showProductDetail)
        .catch(error => console.error('Single fetch error:', error));
    });
  });
}

function showProductDetail(singleProduct) {

  const prod = document.getElementById('prod');
  prod.innerHTML = '';

  const detailBox = document.createElement('div');
  detailBox.id = 'detailbox';
  detailBox.innerHTML = `
    <strong>${singleProduct.name.toUpperCase()}</strong><br>
    ${singleProduct.brand}<br>
    $${singleProduct.price}<br>
    Listed on: ${singleProduct.releaseDate}<br>
    <img src="http://localhost:8080/api/products/image/${singleProduct.id}" 
         alt="${singleProduct.name}" 
         style="width:140px; height:150px; margin-top:10px;" />
  `;
  

  const updateBtn = document.createElement('button');
  updateBtn.textContent = 'Update';
  updateBtn.addEventListener('click', () => {
    localStorage.setItem('productToUpdate', JSON.stringify(singleProduct));
    window.location.href = 'add1.html';
  });

  const deleteBtn = document.createElement('button');
  deleteBtn.textContent = 'Delete';
  deleteBtn.addEventListener('click', () => {
    if (confirm("Are you sure you want to delete this product?")) {
      fetch(`http://localhost:8080/api/products/${singleProduct.id}`, {
        method: 'DELETE'
      })
        .then(response => {
          if (!response.ok) throw new Error('Failed to delete');
          alert('Product deleted successfully');
          clickHandler(); // Refresh product list
        })
        .catch(error => console.error('Delete error:', error));
    }
  });

  const buttonRow = document.createElement('div');
  buttonRow.className = 'button-row';
  buttonRow.appendChild(updateBtn);
  buttonRow.appendChild(deleteBtn);

  detailBox.appendChild(buttonRow);
  prod.appendChild(detailBox);
}


  
    //fetch retun the promise the promise contains all the status ,body header we have to see data
    //we explicitly parse the reponse and return with promise as json to javascript object we see the data 
    
/*console.log(fetch('http://localhost:8080/api/products'))
fetch('http://localhost:8080/api/products').then(response => {
  if(response.ok){
    return response.json()
  }
}).then((produc)=> console.log(produc[0].prodid,produc[0].prodname,produc[0].price))*/