import { useState, useEffect } from 'react';
import ConsumeButton from './components/ConsumeButton';
import PaymentList from './components/PaymentList';
import { getPayments } from './services/api';

export default function App() {
  const [payments, setPayments] = useState([]);
  const [count, setCount] = useState(50);

  const loadPayments = async () => {
    try {
      const data = await getPayments(count);
      setPayments(data);
    } catch (err) {
      console.error('Ошибка загрузки:', err);
    }
  };

  useEffect(() => {
    loadPayments();
  }, [count]);

  const handleNewPayment = (newPayment) => {
    loadPayments();
  };

  return (
    <div style={{ maxWidth: '800px', margin: '0 auto', padding: '20px' }}>
      <h1>Платежи</h1>

      <div style={{ marginBottom: '20px' }}>
        <label>
          Кол-во: 
          <input
            type="number"
            value={count}
            onChange={e => setCount(Number(e.target.value))}
            style={{ width: '60px', marginLeft: '8px' }}
          />
        </label>
      </div>

      <ConsumeButton onNewPayment={handleNewPayment} />
      <PaymentList payments={payments} />
    </div>
  );
}