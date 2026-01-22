import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    //200 RPS на запись
    payConsume: {
      executor: 'ramping-arrival-rate',
      startRate: 0,   
      timeUnit: '1s',
      preAllocatedVUs: 1,
      maxVUs: 1001,
      stages: [
        { target: 200, duration: '10s' }, 
        { target: 300, duration: '20s' }, 
        { target: 300,   duration: '90s' }, 
      ],
      exec: 'payConsume',
    },
    //200 RPS на чтение
    getPayments: {
      executor: 'ramping-arrival-rate',
      startRate: 0,  
      timeUnit: '1s',
      preAllocatedVUs: 1,
      maxVUs: 1001,
      stages: [
        { target: 200, duration: '10s' }, 
        { target: 300, duration: '20s' }, 
        { target: 300,   duration: '90s' }, 
      ],
      exec: 'getPayments',
    },
  },
  thresholds: {
    'http_req_duration{scenario:payConsume}': ['p(95)<500'], // 95% запросов < 500 мс
    'http_req_duration{scenario:getPayments}': ['p(95)<200'],   // 95% запросов < 200 мс
    'http_req_failed': ['rate<0.01'], // Менее 1% ошибок
  },
};


export function payConsume() {
  const res = http.put('http://localhost/api/consumer');
  check(res, {
    'payConsume status 200': (r) => r.status === 200,
  });
}


export function getPayments() {
  const res = http.get('http://localhost/api/consumer?count=50');
  check(res, {
    'getPayments status 200': (r) => r.status === 200,
  });
}