<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Product</title>
  <!-- Include Bootstrap CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
  <div class="container mt-5">
    <h2 class="text-center mb-4">Add Product</h2>

    <form action="AddProduct" method="get">

    <div class="form-group">
      <label for="productName">Product Name:</label>
      <input type="text" class="form-control" id="productName" name="productName" required>
    </div>

    <div class="form-group">
      <label for="productPrice">Product Price:</label>
      <input type="number" class="form-control" id="productPrice" name="productPrice" required>
    </div>

    <div class="form-group">
      <label for="productQuantity">Product Quantity:</label>
      <input type="number" class="form-control" id="productQuantity" name="productQuantity" required>
    </div>

    <button type="submit" class="btn btn-primary">Add Product</button>
    <a href="showProduct.jsp" class="btn btn-primary" >Back</a>
  </form>
  </div>
  <!-- Include Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.7/dist/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
