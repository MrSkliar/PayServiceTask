# PayServiceTask

Сервис истории платежей.
Java 21+
Spring Boot
Webflux
Json Rest api
Postgres 18
Nginx
Prometeus
Grafana

Самое важное - асинхронное обращеине к внешнему сервису и асинхронная работа с базой. 

☑ Первое - приложение заглушка. Имеет одну API которая отдает тебе за раз 1 случайно сгенерированный объект платежа, но делает это с задержкой 200мс. 

☑ Второе - приложение которое имеет 2 апишки.
1 - Запросить платёж из заглушки, получить и записать в базу
2 - Отдать список платежей из базы начиная с самых последних.

☑ Авторизация не нужна

Цель 200rps на каждый эндопинт одновременно. - Вышло 300 rps

☑ Скрипт приемки - это скрипт k6 который одновременно делает 200rps на загрузку+запись и 200rps на чтение. 

+ ☑ Можно сделать одностраничный фронт (SPA, React)
+ ☑ Передача метрик бизнесовых которые ты сам инкреметишь с приложий в прометеус и графану чтобы можно было видеть внутри приложения как бегают запросы
+ ☑ Плюсом будет миграция базы на liquibase
+ ☑ Плюсом будет наличие сваггера
+ ☑ На реактивном стеке есть реактивный клиент для веб не блокирующий и перактивный адаптер к посгре r2dbc

Результат:
```
PS K6> k6 run k6.js

         /\      Grafana   /‾‾/
    /\  /  \     |\  __   /  /
   /  \/    \    | |/ /  /   ‾‾\
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/

     execution: local
        script: k6.js
        output: -

     scenarios: (100.00%) 2 scenarios, 2002 max VUs, 2m30s max duration (incl. graceful stop):
              * getPayments: Up to 300.00 iterations/s for 2m0s over 3 stages (maxVUs: 1-1001, exec: getPayments, gracefulStop: 30s)
              * payConsume: Up to 300.00 iterations/s for 2m0s over 3 stages (maxVUs: 1-1001, exec: payConsume, gracefulStop: 30s)



  █ THRESHOLDS

    http_req_duration{scenario:getPayments}
    ✓ 'p(95)<200' p(95)=7.72ms

    http_req_duration{scenario:payConsume}
    ✓ 'p(95)<500' p(95)=209.33ms

    http_req_failed
    ✓ 'rate<0.01' rate=0.00%


  █ TOTAL RESULTS

    checks_total.......: 65894   548.186459/s
    checks_succeeded...: 100.00% 65894 out of 65894
    checks_failed......: 0.00%   0 out of 65894

    ✓ getPayments status 200
    ✓ payConsume status 200

    HTTP
    http_req_duration..............: avg=103.77ms min=502.29µs med=78.66ms  max=367.61ms p(90)=204.24ms p(95)=206.53ms
      { expected_response:true }...: avg=103.77ms min=502.29µs med=78.66ms  max=367.61ms p(90)=204.24ms p(95)=206.53ms
      { scenario:getPayments }.....: avg=3.57ms   min=502.29µs med=2.6ms    max=208.5ms  p(90)=5.35ms   p(95)=7.72ms
      { scenario:payConsume }......: avg=204.14ms min=199.88ms med=202.89ms max=367.61ms p(90)=206.53ms p(95)=209.33ms
    http_req_failed................: 0.00%  0 out of 65894
    http_reqs......................: 65894  548.186459/s

    EXECUTION
    dropped_iterations.............: 106    0.881837/s
    iteration_duration.............: avg=103.83ms min=506.8µs  med=78.91ms  max=370.4ms  p(90)=204.29ms p(95)=206.59ms
    iterations.....................: 65894  548.186459/s
    vus............................: 61     min=2          max=68
    vus_max........................: 108    min=5          max=108

    NETWORK
    data_received..................: 359 MB 3.0 MB/s
    data_sent......................: 6.0 MB 50 kB/s




running (2m00.2s), 0000/0108 VUs, 65894 complete and 0 interrupted iterations
getPayments ✓ [======================================] 0000/0027 VUs  2m0s  300.00 iters/s
payConsume  ✓ [======================================] 0000/0081 VUs  2m0s  300.00 iters/s
```
<img width="1592" height="604" alt="image" src="https://github.com/user-attachments/assets/f0be7af1-f669-49df-b5f9-08cc23dbc9b1" />
