import { useEffect, useState } from 'react';

export default function PaymentList({ payments }) {
  if (payments.length === 0) {
    return <p>Платежи отсутствуют</p>;
  }

  return (
    <div>
      <h3>Платежи</h3>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {payments.map(p => (
          <li
            key={p.id}
            style={{
              border: '1px solid #eee',
              margin: '8px 0',
              padding: '12px',
              borderRadius: '4px',
              backgroundColor: '#f9f9f9'
            }}
          >
            ID: <b>{p.id}</b><br />
            От кого: <b>{p.fromId}</b><br />
            Кому: <b>{p.toId}</b><br />
            Сумма: <b>{p.amount}</b><br />
            Время платежа: {new Date(p.transactionTime).toLocaleString()}
          </li>
        ))}
      </ul>
    </div>
  );
}