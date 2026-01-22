export const getPayments = async (count = 50) => {
  const res = await fetch(`/api/consumer?count=${count}`);
  if (!res.ok) throw new Error('Ошибка при получении платежей');
  return res.json();
};

export const consumePayment = async () => {
  const res = await fetch(`/api/consumer`, {
    method: 'PUT'
  });
  if (!res.ok) throw new Error('Ошибка при получении и сохранении платежа');
  return res.json();
};