<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <h1>${pageContext.request.userPrincipal.name} 欢迎来到首页</h1>
    <a href="/api/auth/logout">注销</a>
</body>
</html>