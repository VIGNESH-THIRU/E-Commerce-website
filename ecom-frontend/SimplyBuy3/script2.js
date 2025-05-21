//"It waits for the page's DOM to load, then runs the logic inside "
window.addEventListener('DOMContentLoaded', () => {
  const storedProduct = localStorage.getItem('productToUpdate');
  if (storedProduct) {
    const product = JSON.parse(storedProduct);
    

    document.querySelector('input[name="name"]').value = product.name;
    document.querySelector('input[name="brand"]').value = product.brand;
    document.querySelector('input[name="desc"]').value = product.desc;
    document.querySelector('input[name="price"]').value = product.price;
    document.querySelector('select[name="category"]').value = product.category;
    document.querySelector('input[name="quantity"]').value = product.quantity;
    document.querySelector('input[name="releaseDate"]').value = product.releaseDate;

    document.getElementById('buttn').textContent = 'Update';
  }
});

document.getElementById('productForm').addEventListener('submit', function (e) {
  e.preventDefault();

  const storedProduct = localStorage.getItem('productToUpdate');
  if (!storedProduct) {
    alert('No product selected for update.');
    return;
  }

  const existingProduct = JSON.parse(storedProduct);
  const productId = existingProduct.id;

  const form = e.target;
  const formData = new FormData();

  const updatedProduct = {
    id: productId,
    name: form.name.value,
    brand: form.brand.value,
    desc: form.desc.value,
    price: parseFloat(form.price.value),
    category: form.category.value,
    quantity: parseInt(form.quantity.value),
    releaseDate: form.releaseDate.value,
    available: true 
  };

  const imageFile = form.imageFile.files[0];
  if (!imageFile) {
    alert('Please select an image file to continue updating.');
    return;
  }


  formData.append('product', new Blob([JSON.stringify(updatedProduct)], {
    type: 'application/json'
  }));
  formData.append('imageFile', imageFile);


  fetch(`http://localhost:8080/api/products/${productId}`, {
    method: 'PUT',
    body: formData
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Update failed');
      }
      return response.text();
    })
    .then(message => {
      alert('Product updated successfully!');
      localStorage.removeItem('productToUpdate'); 
    })
    .catch(error => {
      console.error('Error during update:', error);
      alert('Error updating product');
    });
});
