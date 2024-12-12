<!DOCTYPE html>
<html>
<head>
    <title>您的验证码</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 100%;
            max-width: 600px;
            margin: auto;
            overflow: hidden;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333333;
            text-align: center;
        }
        p {
            color: #666666;
            line-height: 1.6;
        }
        .code {
            font-size: 24px;
            font-weight: bold;
            color: #ff6600;
            text-align: center;
            padding: 20px;
            border-top: 1px solid #eaeaea;
            border-bottom: 1px solid #eaeaea;
        }
        .footer {
            text-align: center;
            color: #999999;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>欢迎使用我们的服务</h1>
    <p>感谢您使用我们的服务！为了完成使用，请使用以下验证码：</p>
    <div class="code">${code}</div>
    <p>请在5分钟内使用此验证码，过期将失效。</p>
    <div class="footer">如果您没有请求此验证码，请忽略这封邮件。</div>
</div>
</body>
</html>