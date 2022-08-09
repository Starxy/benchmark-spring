# benchmark

组件列表：

- mybatis-plus
- dynamic-datasource
- druid
- p6spy

## 不开启 security

访问接口，接口内不做任何处理

```bash
$ wrk -d15s -t8 -c64 http://192.168.31.245:8089
Running 15s test @ http://192.168.31.245:8089
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.05ms    0.99ms  43.04ms   94.29%
    Req/Sec     8.30k   592.96     9.65k    72.17%
  992199 requests in 15.04s, 110.89MB read
Requests/sec:  65975.38
Transfer/sec:      7.37MB
```

访问接口，接口内打印当前日期日志到文件

```bash
$ wrk -d15s -t8 -c64 http://192.168.31.245:8089
Running 15s test @ http://192.168.31.245:8089
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.11ms    7.31ms 151.01ms   98.49%
    Req/Sec     5.85k     1.01k   12.40k    88.96%
  696518 requests in 15.10s, 77.84MB read
Requests/sec:  46128.03
Transfer/sec:      5.16MB
```

## 开启 security

访问无需授权接口，接口内打印当前日期日志到文件，未解析jwt

```bash
$ wrk -d15s -t8 -c64 http://192.168.31.245:8089
Running 15s test @ http://192.168.31.245:8089
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.60ms    6.21ms 132.95ms   93.64%
    Req/Sec     3.39k     1.09k    5.64k    71.10%
  406589 requests in 15.10s, 107.09MB read
Requests/sec:  26926.52
Transfer/sec:      7.09MB
```

访问无需授权接口，接口内打印当前日期日志到文件，解析jwt

```bash
$ wrk -d15s -t8 -c64 -H "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhbWV3b3JrLmJvb3QuYXV0b2NvbmZpZ3VyZS5TcHJpbiI6Im9yZy5zcHJpbmdmcmFtZXdvcmsuYm9vdC5TcHJpbmdBcHBsaWNhdGlvbjsiLCJuZ2ZyYW1ld29yay53ZWIuYmluZC5hbm5vdGF0aW9uLlBvIjoicGFja2FnZSBiZW5jaG1hcmsuc3ByaW5nYm9vdDsifQ.stW_THMKovex8CTYYssuhFsfKSi9KblUo_u2MoTMoyk" http://192.168.31.245:8089
Running 15s test @ http://192.168.31.245:8089
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.84ms    1.96ms  38.08ms   70.06%
    Req/Sec     2.96k   382.74     5.90k    76.83%
  355038 requests in 15.10s, 93.51MB read
Requests/sec:  23512.55
Transfer/sec:      6.19MB
```

访问无权接口

```bash
$ wrk -d15s -t8 -c64 http://192.168.31.245:8089/user
Running 15s test @ http://192.168.31.245:8089/user
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.64ms    4.64ms  61.81ms   93.69%
    Req/Sec     2.81k   803.36     8.80k    73.38%
  336071 requests in 15.10s, 74.42MB read
  Non-2xx or 3xx responses: 336071
Requests/sec:  22258.45
Transfer/sec:      4.93MB
```

## 增加切面日志

访问无日志接口

```bash
$ wrk -d15s -t8 -c64 -H "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhbWV3b3JrLmJvb3QuYXV0b2NvbmZpZ3VyZS5TcHJpbiI6Im9yZy5zcHJpbmdmcmFtZXdvcmsuYm9vdC5TcHJpbmdBcHBsaWNhdGlvbjsiLCJuZ2ZyYW1ld29yay53ZWIuYmluZC5hbm5vdGF0aW9uLlBvIjoicGFja2FnZSBiZW5jaG1hcmsuc3ByaW5nYm9vdDsifQ.stW_THMKovex8CTYYssuhFsfKSi9KblUo_u2MoTMoyk" http://192.168.31.245:8089/user

Running 15s test @ http://192.168.31.245:8089/user
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.91ms   11.18ms 193.77ms   98.38%
    Req/Sec     2.99k   532.02     4.28k    87.16%
  355132 requests in 15.02s, 78.64MB read
Requests/sec:  23649.38
Transfer/sec:      5.24MB
```

访问日志接口

```bash
$ wrk -d15s -t8 -c64 -H "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhbWV3b3JrLmJvb3QuYXV0b2NvbmZpZ3VyZS5TcHJpbiI6Im9yZy5zcHJpbmdmcmFtZXdvcmsuYm9vdC5TcHJpbmdBcHBsaWNhdGlvbjsiLCJuZ2ZyYW1ld29yay53ZWIuYmluZC5hbm5vdGF0aW9uLlBvIjoicGFja2FnZSBiZW5jaG1hcmsuc3ByaW5nYm9vdDsifQ.stW_THMKovex8CTYYssuhFsfKSi9KblUo_u2MoTMoyk" http://192.168.31.245:8089/user
2
Running 15s test @ http://192.168.31.245:8089/user2
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    37.71ms   31.85ms 430.35ms   91.96%
    Req/Sec   240.28     89.04   454.00     61.08%
  28318 requests in 15.02s, 6.27MB read
Requests/sec:   1884.85
Transfer/sec:    427.35KB
```

访问日志接口，换连接池为 hikari

```bash
$ wrk -d15s -t8 -c64 -H "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhbWV3b3JrLmJvb3QuYXV0b2NvbmZpZ3VyZS5TcHJpbiI6Im9yZy5zcHJpbmdmcmFtZXdvcmsuYm9vdC5TcHJpbmdBcHBsaWNhdGlvbjsiLCJuZ2ZyYW1ld29yay53ZWIuYmluZC5hbm5vdGF0aW9uLlBvIjoicGFja2FnZSBiZW5jaG1hcmsuc3ByaW5nYm9vdDsifQ.stW_THMKovex8CTYYssuhFsfKSi9KblUo_u2MoTMoyk" http://192.168.31.245:8089/user2
Running 15s test @ http://192.168.31.245:8089/user2
  8 threads and 64 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    69.79ms   29.91ms 421.76ms   90.44%
    Req/Sec   119.53     28.30   222.00     61.54%
  14086 requests in 15.02s, 3.12MB read
Requests/sec:    937.61
Transfer/sec:    212.59KB
```
