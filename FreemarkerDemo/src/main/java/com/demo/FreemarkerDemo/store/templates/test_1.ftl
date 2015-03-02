<html>
<head>
  <title>Welcome!</title>
</head>
<body>
<#setting locale="zh_CN">
<#setting number_format="0.####">
  <h1>Welcome ${user}!</h1>
  <p>Our latest product:
  <a href="${latestProduct.url}">${latestProduct.name}</a>!
</body>
</html>  