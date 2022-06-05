# 客户端模式（Client Credentials）
我们可以直接向验证服务器请求一个Token**服务拿到令牌之后，才能去访问资源，比如用户信息、借阅信息等，这样资源服务器才能知道我们是谁以及是否成功登录了）。
这种方式更适用于服务内部调用的场景。
## 第一步：获取token
POST http://localhost:9031/sso/oauth/token  
表单类型： x-www-form-uriencoded  
| key | value |  
| grant_type | client_credentials |  
| client_id | web |  
| client_secret | 654321 |  

## 第二步：验证token是否有效
GET http://localhost:9031/sso/oauth/check_token?token=c2f64e49-97e4-4bd9-97d6-0287360d53c  

# 密码模式（Resource Owner Password Credentials）
POST http://localhost:9031/sso/oauth/token  
表单类型： x-www-form-uriencoded  
| key | value |  
| grant_type | password |  
| username | test |  
| password | 123456 |  

Authorization  (POSTMAN中选择，会在请求头中自动添加Authorization Basic xxx)  
Username: web  
Password: 654321  


# 隐式授权模式（Implicit Grant）

浏览器调用：http://localhost:9031/sso/oauth/authorize?client_id=web&response_type=token

# 授权码模式

浏览器调用：http://localhost:9031/sso/oauth/authorize?client_id=web&response_type=code  
获得到授权码：http://localhost:9031/login?code=jccxT5

POST http://localhost:9031/sso/oauth/token  
表单类型： x-www-form-uriencoded  
| key | value |  
| grant_type | client_credentials |  
| client_id | web |  
| client_secret | 654321 |  
| code | jccxT5 |

# 令牌刷新
POST http://localhost:9031/sso/oauth/token  
表单类型： x-www-form-uriencoded  
| key | value |  
| grant_type | refresh_token |  
| client_id | web |  
| client_secret | 654321 |  
| refresh_token | {token} |


# 代码参考网站
https://blog.csdn.net/qq_25928447/article/details/124198071